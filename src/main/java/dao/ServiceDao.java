package dao;

import java.sql.*;
import java.util.ArrayList;
import configs.MessageСonstants;

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

    public boolean isSubExist (String login) {
        List<Object> resultList = getResultSet("SELECT id_sub FROM telephone_exchange.sub_tb WHERE login = ?",
                getParameters(login));
        return resultList.size() != 0;
    }

    public int addNewSub (String login,String password) {
        if (!(isSubExist(login))) {
            update("INSERT INTO telephone_exchange.sub_tb (login,password) VALUES (?,?)",
                    getParameters(login,password));
            return MessageСonstants.SUCSESS;
        } else return MessageСonstants.SUB_ALREADY_EXIST;
    }

    public List <Object> getAllServices() {
        List<Object> resultList = getResultSet("SELECT * FROM telephone_exchange.service_tb;",3);
        return resultList.size() > 0 ? resultList
                                       : null;
    }

    public List <Object> getAllSubServices () {
        List<Object> resultList = getResultSet("SELECT * FROM telephone_exchange.service_tb b\n" +
                "inner join telephone_exchange.sub_service_tb e ON \n" +
                "b.id_service = e.id_service join telephone_exchange.sub_tb f ON e.id_sub = f.id_sub",8);
        return resultList.size() > 0 ? resultList
                                        : null;
    }
    public int addServiceToSub (String login,int idService) {
        if (isServiceExist(idService)) {
            if(!(isSubHaveThisService(login,idService))) {

                int idSub = getIdSub(login);
                update("INSERT INTO `telephone_exchange`.`sub_service_tb` (`id_service`, `id_sub`) VALUES (?,?)",
                        getParameters(String.valueOf(idService), String.valueOf(idSub)));
                return MessageСonstants.SUCSESS;

            } else return MessageСonstants.SUB_ALREADY_HAVE_THIS_SERVICE;
        } else return MessageСonstants.SERVICE_NOT_EXIST;
    }

    public int deleteSubService (int service, String subLogin) {
           if (isServiceExist(service)) {
               if (isSubHaveThisService(subLogin,service)) {

                   int idSub = getIdSub(subLogin);
                   update("DELETE FROM `telephone_exchange`.`sub_service_tb` WHERE id_Service = ? AND id_sub = ?",
                           getParameters(String.valueOf(service), String.valueOf(idSub)));
                   return MessageСonstants.SUCSESS;
               }else return MessageСonstants.SUB_HAVE_NOT_THIS_SERVICE;
           } else return MessageСonstants.SERVICE_NOT_EXIST;
    }

    private int getIdSub(String login) {
        List<Object> resultList = getResultSet("SELECT * FROM telephone_exchange.sub_tb WHERE login = ?",
                getParameters(login));
        return resultList.size() > 0 ?
                             (int) resultList.get(0)
                             : 0;
    }

    private boolean isServiceExist (int id) {
        List<Object> resultList = getResultSet("SELECT * FROM telephone_exchange.service_tb WHERE id_service = ?",
                                                                            getParameters(String.valueOf(id)));
        return resultList.size() != 0;
    }

    private boolean isSubHaveThisService(String subLogin, int idService) {
        int idSub = getIdSub(subLogin);
        List<Object> resultList = getResultSet("SELECT * FROM telephone_exchange.sub_service_tb WHERE id_service = ? AND id_sub = ?",
                getParameters(String.valueOf(idService),String.valueOf(idSub)));
        return resultList.size() != 0;
    }

    private void update (String sqlRequest, Object [] parameters) {
        try (Connection connection = getDbConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
            if (parameters.length > 0) {
                int cnt = 1;
                for (Object parameter : parameters) {
                    preparedStatement.setObject(cnt, parameter);
                    cnt++;
                }
            }
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    private List<Object> getResultSet (String sqlRequest, int columnNumber) {
        List<Object> result = new ArrayList<>();
        try (Connection connection = getDbConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                for (int i = 1; i < columnNumber+1; i++) {
                    result.add(resultSet.getObject(i));
                }
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
