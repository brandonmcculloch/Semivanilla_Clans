package org.spigotmc.clans.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.spigotmc.clans.Clans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class DatabaseManager {

    private static DatabaseManager instance;

    private MysqlDataSource dataSource = new MysqlDataSource();

    private DatabaseManager() {
        dataSource.setServerName(Clans.getInstance().getConfig().getString("host"));
        dataSource.setPortNumber(Clans.getInstance().getConfig().getInt("port"));
        dataSource.setDatabaseName(Clans.getInstance().getConfig().getString("database"));
        dataSource.setUser(Clans.getInstance().getConfig().getString("username"));
        dataSource.setPassword(Clans.getInstance().getConfig().getString("password"));
    }

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void initialize() {
        String setup = "";
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("db.sql")) {
            setup = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            Clans.getInstance().getLogger().log(Level.SEVERE, "Database Setup Failed", e);
            e.printStackTrace();
        }
        String[] queries = setup.split(";");
        for (String query : queries) {
            if (query.isEmpty()) continue;
            try {
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
