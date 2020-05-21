package repositories;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Repository<T> {

    private Connection connection = null;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String databaseUrl = "jdbc:sqlite:cache.db";
    private Class<?> entityClass;
    private Field[] fields;
    private String tableName;

    public Repository(String tableName, Class<T> entityClass) {
        this.connect();
        
        this.entityClass = entityClass;
        this.fields = entityClass.getDeclaredFields();
        this.tableName = tableName;
        createTable();
    }

    private void connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(databaseUrl);
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

    public void store(T entity) {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        StringBuilder valuesBuilder = new StringBuilder();
        List<Object> values = new ArrayList<>();
        boolean firstField = true;

        sqlBuilder.append(this.tableName);
        sqlBuilder.append("(");

        try {
            for (Field field : fields) {
                if (firstField) {
                    firstField = false;
                } else {
                    sqlBuilder.append(",");
                    valuesBuilder.append(",");
                }

                sqlBuilder.append(field.getName());
                valuesBuilder.append("?");
                field.setAccessible(true);
                values.add(field.get(entity));
            }

            sqlBuilder.append(") VALUES (");
            sqlBuilder.append(valuesBuilder);
            sqlBuilder.append(");");

            PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());

            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }

            statement.executeUpdate();
        } catch (IllegalAccessException | SQLException exception) {
            logger.log(Level.WARNING, exception.getMessage());
        }
    }

    public void update(T entity) {

    }

    public List<T> get() {
        return null;
    }

    public void delete() {

    }

    private void createTable() {
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

            sqlBuilder.append(field.getName());
            sqlBuilder.append(" " + toSqlType(field.getType()));

            /*if (field.getName().equals("id ")) {
                sqlBuilder.append(" PRIMARY KEY");
            }*/

            sqlBuilder.append(" NOT NULL");
        }

        sqlBuilder.append(");");

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String toSqlType(Class<?> type) {
        if (type.equals(String.class)) {
            return "VARCHAR(255) ";
        } else if (type.equals(Integer.class)) {
            return "INTEGER";
        }
        return "";
    }
    
}
