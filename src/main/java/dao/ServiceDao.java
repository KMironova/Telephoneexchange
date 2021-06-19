package dao;

import domain.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "117MrDeer";
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "telephone_exchange";

    private static final String DB_URL = "jdbc:mysql://"
            + DB_HOST + ":"
            + DB_PORT + "/"
            + DB_NAME
            + "?characterEncoding=utf8";

    public Connection getDbConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public int check (String login, String password) {
        List<Object> resultList = getResultSet("SELECT id_sub FROM telephone_exchange.sub_tb WHERE login = ? AND password = ?",
                                                getParameters(login,password));
        return resultList.size() > 0 ?
                        (int) resultList.get(0)
                            : 0;
    }

    public Service get(int id) {
        List<Object> resultList = getResultSet("SELECT * FROM services_table WHERE id = ?",
                                                                    getParameters(String.valueOf(id)));
        return resultList.size() > 0 ?
                new Service((String) resultList.get(0),(int) resultList.get(1),(int) resultList.get(2))
                : null;
    }

    private List<Object> getResultSet (String sqlRequest, Object [] parameters) {
        List<Object> result = new ArrayList<>();

        try (Connection connection = getDbConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
            if (parameters.length > 0) {
                int cnt = 1;
                for (Object parameter : parameters) {
                    preparedStatement.setObject(cnt, parameter);
                    cnt++;
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            int cnt = 1;
            while (resultSet.next()) {
               result.add(resultSet.getObject(cnt));
               cnt++;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    private List<Object> getResultSet (String sqlRequest) {
        List<Object> result = new ArrayList<>();
        try (Connection connection = getDbConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlRequest);

            int cnt = 1;
            while (resultSet.next()) {
                result.add(resultSet.getObject(cnt));
                cnt++;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    private String [] getParameters (String ... parameter) {
        String [] parameters = new String[parameter.length];

        for (int i = 0; i<parameter.length; i++) {
            parameters[i] = parameter[i];
        }
        return parameters;
    }
}
