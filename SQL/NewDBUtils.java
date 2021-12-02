package SQL;

import java.sql.*;
import java.util.Map;

/**
 * Data base Utils class
 * Utils department for query execution, and for entering accurate data into query templates
 */
public class NewDBUtils {

    /**
     * @param sql    The query we want to send to sql
     * @param params That we want to put into the query
     * @return Query with the exact parameters for this case
     * @throws SQLException         if error in SQL
     * @throws InterruptedException if error in connection
     */
    public static PreparedStatement prepareMapStatement(String sql, Map<Integer, Object> params) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        params.forEach((key, value) -> {
            try {
                if (value instanceof Integer) {
                    statement.setInt(key, (int) value);
                } else if (value instanceof String) {
                    statement.setString(key, String.valueOf(value));
                } else if (value instanceof Date) {
                    statement.setDate(key, (Date) value);
                } else if (value instanceof Boolean) {
                    statement.setBoolean(key, (Boolean) value);
                } else if (value instanceof Double) {
                    statement.setDouble(key, (Double) value);
                } else if (value instanceof Float) {
                    statement.setFloat(key, (Float) value);
                } else if (value instanceof Timestamp) {
                    statement.setTimestamp(key, (Timestamp) value);
                }
            } catch (SQLException err) {
                System.out.println("Error in sql :" + err.getMessage());
            } finally {
                try {
                    ConnectionPool.getInstance().returnConnection(connection);
                } catch (SQLException e) {
                    System.out.println("Error in sql :" + e.getMessage());
                }
            }
        });
        return statement;
    }

    /**
     * @param sql     The query we want to send to sql
     * @param integer That we want to put into the query. Designed for a case where only one statistic needs to be entered
     *                Type integer.
     * @return Query with the exact parameters for this case
     * @throws SQLException         if error in SQL
     * @throws InterruptedException if error in connection
     */
    public static PreparedStatement prepareIntStatement(String sql, int integer) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        try {
            statement.setInt(1, integer);
        } catch (SQLException err) {
            System.out.println("Error in sql :" + err.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException e) {
                System.out.println("Error in sql :" + e.getMessage());
            }
        }
        return statement;
    }

    /**
     * @param sql    The query we want to send to sql
     * @param string That we want to put into the query. Designed for a case where only one statistic needs to be entered
     *               Type String.
     * @return Query with the exact parameters for this case
     * @throws SQLException         if error in SQL
     * @throws InterruptedException if error in connection
     */
    public static PreparedStatement prepareStringStatement(String sql, String string) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        try {
            statement.setString(1, string);
        } catch (SQLException err) {
            System.out.println("Error in sql :" + err.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException e) {
                System.out.println("Error in sql :" + e.getMessage());
            }
        }
        return statement;
    }

    /**
     * Runs an exact query, for which we have already entered the relevant data
     *
     * @param statement The exact query we want to run
     */
    public static void runStatment(PreparedStatement statement) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println("Error in sql :" + err.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException e) {
                System.out.println("Error in sql :" + e.getMessage());
            }
        }
    }

    /**
     * Runs an exact query, for which we have already entered the relevant data, In case we need to get data back
     *
     * @param statement The exact query we want to run
     * @return The data we got back in an object type ResultSet
     */
    public static ResultSet runStatmentGetResult(PreparedStatement statement) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            resultSet = statement.executeQuery();
        } catch (SQLException | InterruptedException err) {
            System.out.println(err);
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return resultSet;
    }

    /**
     * Runs a simple query, no need to enter data, in case we need to get data back
     *
     * @param sql The  query we want to run
     * @return The data we got back in an object type ResultSet
     * @throws SQLException if error in SQL
     */
    public static ResultSet runSimpleQueryGetResult(String sql) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return resultSet;
    }

    /**
     * Runs a simple query, no need to enter data
     *
     * @param sql The  query we want to run
     * @throws SQLException if error in SQL
     */
    public static void runSimpleQuery(String sql) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

}




