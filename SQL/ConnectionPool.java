package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * connection pool class
 */
public class ConnectionPool {

    /**
     * connection pool instance
     */
    private static ConnectionPool instance = null;
    /**
     * maximum connection
     */
    public static final int NUM_OF_CONNECTION = 10;
    /**
     * stack of connection
     */
    private Stack<Connection> connections = new Stack<>();


    /**
     * Constructor for creating instance of class (SingleTon)
     *
     * @throws SQLException if we have an exception regarding SQL
     */
    private ConnectionPool() throws SQLException {
        openAllConnections();
    }

    /**
     * get instance of connection pool (SingleTon)
     *
     * @return a connection to mySQL database
     * @throws SQLException throws SQL exception
     */
    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    /**
     * open all connection to data base
     *
     * @throws SQLException exception handler for SQL exceptions
     */
    public void openAllConnections() throws SQLException {
        for (int index = 0; index < NUM_OF_CONNECTION; index += 1) {
            //DATABASE credentials
            Connection connection = DriverManager.getConnection(DBManager.URL, DBManager.USER_NAME, DBManager.PASSWORD);
            connections.push(connection);
        }
    }

    /**
     * get a single connection
     *
     * @return a connection to the database
     * @throws InterruptedException throws interrupt exception
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                connections.wait();
            }
            return connections.pop();
        }
    }

    /**
     * return a connection from user
     *
     * @param connection get an existing connection
     */
    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }
    }

    /**
     * close all connections
     *
     * @throws InterruptedException handle stop of thread
     */
    public void closeAllConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONNECTION) {
                connections.wait();
            }
            connections.removeAllElements();
            System.out.println("close all connections");
        }
    }

}
