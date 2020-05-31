package repositories;

import annonations.*;
import exceptions.InvalidEntityException;
import util.DateUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Repository<T, K> {

    private Connection connection = null;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private final String DATABASE_URL = "jdbc:sqlite:cache.db";
    private Field[] fields;
    private Class entityClass;
    private String tableName;
    private String idFieldName;

    public Repository(Class<T> entityClass) throws InvalidEntityException {

        if (!entityClass.isAnnotationPresent(Table.class)) {
            throw new InvalidEntityException("Entity is missing table name.");
        }

        this.entityClass = entityClass;
        this.fields = entityClass.getDeclaredFields();
        this.tableName = entityClass.getDeclaredAnnotation(Table.class).value();
        createTable();
    }

    private void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(DATABASE_URL);
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Couldn't establish database connection.");
            }
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Couldn't close database connection.");
            }
        }
    }

    public void save(List<T> entities) {
        try {
            connect();

            // forms sql string
            boolean firstField = true;
            StringBuilder sqlBuilder = new StringBuilder("INSERT OR REPLACE INTO ");
            StringBuilder valuesBuilder = new StringBuilder();

            sqlBuilder.append(this.tableName);
            sqlBuilder.append("(");

            for (Field field : fields) {
                if (firstField) {
                    firstField = false;
                } else {
                    sqlBuilder.append(",");
                    valuesBuilder.append(",");
                }

                sqlBuilder.append(toSQLColumnName(field));
                valuesBuilder.append("?");

            }

            sqlBuilder.append(") VALUES (");
            sqlBuilder.append(valuesBuilder);
            sqlBuilder.append(");");

            PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());

            //prepares values
            for (T entity : entities) {
                int i = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(entity);

                    if (value != null && value.getClass().equals(LocalDateTime.class)) {
                        value = DateUtil.localDateTimeToString((LocalDateTime) value);
                    }

                    statement.setObject(i , value);
                    i++;
                }
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException | IllegalAccessException exception) {
            logger.log(Level.WARNING, exception.getMessage());
        }

        close();
    }

    public void save(T entity) {
        save(new ArrayList<>(Arrays.asList(entity)));
    }

    public Optional<T> findById(K id) {
        try {
            connect();

            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ");
            sqlBuilder.append(this.tableName);
            sqlBuilder.append(" WHERE ");
            sqlBuilder.append(this.idFieldName);
            sqlBuilder.append(" = ?;");

            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlBuilder.toString());
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                T entity = entryToEntity(resultSet);
                close();
                return Optional.of(entity);
            }

        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException exception) {
            logger.warning(exception.getMessage());
        }

        close();
        return Optional.ofNullable(null);
    }

    public List<T> get(String where) {
        List<T> results = new ArrayList<>();
        try {
            connect();

            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ");
            sqlBuilder.append(this.tableName);
            sqlBuilder.append(" WHERE ");
            sqlBuilder.append(where);

            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlBuilder.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                results.add(entryToEntity(resultSet));
            }

        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException exception) {
            logger.warning(exception.getMessage());
        }

        close();
        return results;
    }

    public void remove(K id) {
        try {
            connect();

            StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ");
            sqlBuilder.append(this.tableName);
            sqlBuilder.append(" WHERE ");
            sqlBuilder.append(this.idFieldName);
            sqlBuilder.append(" = ?;");

            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlBuilder.toString());

            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.warning(exception.getMessage());
        }

        close();
    }

    private void createTable() throws InvalidEntityException {
        try {
            connect();

            StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
            sqlBuilder.append(this.tableName);
            sqlBuilder.append("(");
            boolean firstField = true;

            for (Field field : this.fields) {
                if (firstField) {
                    firstField = false;
                } else {
                    sqlBuilder.append(",");
                }

                sqlBuilder.append(generateColumn(field));
            }

            sqlBuilder.append(");");

            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();
    }

    private String generateColumn(Field field) throws InvalidEntityException {
        String columnName = toSQLColumnName(field);
        StringBuilder columnBuilder = new StringBuilder(columnName);

        columnBuilder.append(" " + toSQLDataType(field.getType()));

        if (field.isAnnotationPresent(PrimaryKey.class)) {
            columnBuilder.append(" PRIMARY KEY");
            this.idFieldName = columnName;
        }

        if (field.isAnnotationPresent(NotNull.class)) {
            columnBuilder.append(" NOT NULL");
        }

        columnBuilder.append(generateForeignKey(field));

        return columnBuilder.toString();
    }

    private String toSQLColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String columnName = field.getDeclaredAnnotation(Column.class).value();
            if (columnName != null && !columnName.isEmpty()) {
                return columnName;
            }
        }

        return field.getName();
    }

    private String toSQLDataType(Class<?> type) {
        if (type.equals(String.class)) {
            return "VARCHAR(255)";
        } else if (type.equals(Integer.class)) {
            return "INTEGER";
        } else if (type.equals(LocalDateTime.class)) {
            return "DATETIME";
        }

        return "";
    }

    private String generateForeignKey(Field field) throws InvalidEntityException {
        if(field.isAnnotationPresent(ForeignKey.class)) {
            StringBuilder stringBuilder = new StringBuilder(" references ");
            Class<?> referencedClass = field.getDeclaredAnnotation(ForeignKey.class).value();
            if (!referencedClass.isAnnotationPresent(Table.class)) {
                throw new InvalidEntityException("Foreign key entity is missing table name.");
            }

            stringBuilder.append(referencedClass.getDeclaredAnnotation(Table.class).value());
            stringBuilder.append("(");

            for (Field referencedField : referencedClass.getDeclaredFields()) {
                if (referencedField.isAnnotationPresent(PrimaryKey.class)) {
                    stringBuilder.append(referencedField.getName());
                }
            }
            stringBuilder.append(")");

            return stringBuilder.toString();
        }

        return "";
    }

    private T entryToEntity(ResultSet resultSet) throws NoSuchMethodException, SQLException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T entity = (T) entityClass.getDeclaredConstructor().newInstance();
        for (Field field : this.fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                String fieldName = field.getDeclaredAnnotation(Column.class).value();

                Object value = resultSet.getObject(fieldName);
                if (field.getType().equals(LocalDateTime.class)) {
                    value = DateUtil.parseLocalDateTime((String) value);
                }

                field.set(entity, value);
            }
        }

        return entity;
    }
}
