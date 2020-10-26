package net.class101.homework1.global.db;

import org.h2.tools.RunScript;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class InsertTestData {
    private final static String RESOURCE = "src/main/resources/properties/db-connection.properties";
    private final static String CREATE_SQL = "src/main/resources/db/createTable.sql";
    private final static String INSERT_TEST_DATA_SQL = "src/main/resources/db/testData.sql";
    private final static String DROP_SQL = "src/main/resources/db/dropTable.sql";

    public static void initData() {
        try {
            InsertTestData.createTable();
            InsertTestData.insertTestData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createTable() throws SQLException {
        execute(CREATE_SQL);
    }

    public static void insertTestData() throws SQLException {
        execute(INSERT_TEST_DATA_SQL);
    }

    public static void dropTable() throws SQLException {
        execute(DROP_SQL);
    }

    private static void execute(String sqlFileDir) throws SQLException {
        String[] attributes = getConnectionAttribute();

        String forName = attributes[0];
        String url = attributes[1];
        String name = attributes[2];
        String password = attributes[3];

        try (Connection conn = DriverManager.getConnection(url, name, password);
             FileInputStream sqlFis = new FileInputStream(sqlFileDir)) {

            Class.forName(forName);
            RunScript.execute(conn, new InputStreamReader(sqlFis, "UTF-8"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] getConnectionAttribute() {
        String[] attributes = new String[4];
        Properties properties = new Properties();

        try(FileInputStream propFis = new FileInputStream(RESOURCE)) {

            properties.load(new BufferedInputStream(propFis));

            attributes[0] = properties.getProperty("driver-class-name");
            attributes[1] = properties.getProperty("url");
            attributes[2] = properties.getProperty("username");
            attributes[3] = properties.getProperty("password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return attributes;
    }
}
