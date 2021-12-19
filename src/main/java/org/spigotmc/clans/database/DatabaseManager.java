package org.spigotmc.clans.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

public class DatabaseManager {

    private MysqlDataSource dataSource = new MysqlDataSource();

    public DatabaseManager(FileConfiguration config) {
        dataSource.setServerName(config.getString("host"));
        dataSource.setPortNumber(config.getInt("port"));
        dataSource.setDatabaseName(config.getString("database"));
        dataSource.setUser(config.getString("username"));
        dataSource.setPassword(config.getString("password"));
    }

}
