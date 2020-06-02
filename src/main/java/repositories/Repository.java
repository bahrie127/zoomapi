package repositories;

import annonations.*;
import exceptions.InvalidEntityException;
import util.DateUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Repository<T> {

    private Connection connection = null;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private final String DATABASE_URL = "jdbc:sqlite:cache.db";
    private Field[] fields;
    private Class entityClass;
    private String tableName;

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

    }

    public void save(T entity) {
        save(new ArrayList<>(Arrays.asList(entity)));
    }

    protected List<T> get(Map<String, Object> params) {
        boolean firstEntry = true;
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (firstEntry) {
                firstEntry = false;
            } else {
                stringBuilder.append(" AND ");
            }

            stringBuilder.append(entry.getKey());
            stringBuilder.append(" = ");

            String appendix = "";
            if (entry.getValue().getClass() == String.class) {
                stringBuilder.append("'");
                appendix = "'";
            }

            stringBuilder.append(entry.getValue());
            stringBuilder.append(appendix);
        }

        stringBuilder.append(";");

        return get(stringBuilder.toString());
    }

    protected List<T> get(String where) {
        List<T> results = new ArrayList<>();
        try {
            connect();

            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ");
            sqlBuilder.append(this.tableName);

            if (where != null && !where.isEmpty()) {
                sqlBuilder.append(" WHERE ");
                sqlBuilder.append(where);
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());

            //logger.info(sqlBuilder.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                results.add(entryToEntity(resultSet));
            }

        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException exception) {
            logger.warning(exception.getMessage());
        }

        return results;
    }

    protected void removeByCondition(String condition) {
        try {
            connect();

            StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ");
            sqlBuilder.append(this.tableName);
            sqlBuilder.append(" WHERE ");
            sqlBuilder.append(condition);

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.warning(exception.getMessage());
        }
    }

    protected void removeByCondition(Map<String, Object> params) {
        try {
            connect();

            StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ");
            sqlBuilder.append(this.tableName);
            sqlBuilder.append(" WHERE ");
            List<Object> values = new ArrayList<>();
            boolean firstValue = true;

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (firstValue) {
                    firstValue = false;
                } else {
                    sqlBuilder.append(" AND ");
                }
                sqlBuilder.append(entry.getKey());
                sqlBuilder.append(" = ? ");
                values.add(entry.getValue());
            }

            sqlBuilder.append(";");

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());

            int i = 1;
            for (Object value : values) {
                preparedStatement.setObject(i, value);
                i++;
            }

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.warning(exception.getMessage());
        }
    }

    public void removeAll() {
        try {
            connect();

            StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ");
            sqlBuilder.append(this.tableName);
            sqlBuilder.append(";");

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.warning(exception.getMessage());
        }

    }

    private void createTable() throws InvalidEntityException {
        try {
            connect();

            StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
            sqlBuilder.append(this.tableName);
            sqlBuilder.append("(");
            List<String> primaryKeys = new ArrayList<>();
            boolean firstField = true;

            for (Field field : this.fields) {
                if (firstField) {
                    firstField = false;
                } else {
                    sqlBuilder.append(",");
                }

                sqlBuilder.append(generateColumn(field));
                if (field.isAnnotationPresent(PrimaryKey.class)) {
                    primaryKeys.add(toSQLColumnName(field));
                }
            }

            if (primaryKeys.isEmpty()) {
                throw new InvalidEntityException("No primary keys present");
            }

            sqlBuilder.append(", PRIMARY KEY(");
            for (int i = 0; i < primaryKeys.size(); i++) {
                if (i > 0) {
                    sqlBuilder.append(",");
                }

                sqlBuilder.append(primaryKeys.get(i));
            }

            sqlBuilder.append("));");

            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlBuilder.toString());

            logger.info("Created table " + this.tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateColumn(Field field) throws InvalidEntityException {
        String columnName = toSQLColumnName(field);
        StringBuilder columnBuilder = new StringBuilder(columnName);

        columnBuilder.append(" " + toSQLDataType(field));

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

    private String toSQLDataType(Field field) {
        Class<?> type = field.getType();

        if (type.equals(String.class)) {
            if (field.isAnnotationPresent(Size.class)) {
                int size = field.getAnnotation(Size.class).value();
                return "VARCHAR(" + size + ")";
            }

            return "TEXT";
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
