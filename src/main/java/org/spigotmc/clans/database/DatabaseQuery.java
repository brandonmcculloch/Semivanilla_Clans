package org.spigotmc.clans.database;

import org.bukkit.entity.Player;
import org.spigotmc.clans.model.ClanModel;
import org.spigotmc.clans.model.UserModel;

import java.sql.*;

public class DatabaseQuery {

    private static DatabaseQuery databaseQuery;

    private DatabaseQuery() {}

    public static DatabaseQuery getInstance() {
        if(databaseQuery == null) {
            databaseQuery = new DatabaseQuery();
        }
        return databaseQuery;
    }

    public boolean createClan(String name) {
        if(retrieveClan(name) != null) return false;
        String sql = "INSERT INTO clans_clan(name, description, origin, status) VALUES(?, ?, ?, ?);";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Timestamp time = new Timestamp(System.currentTimeMillis());
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, "New Clan");
            statement.setTimestamp(3, time);
            statement.setString(4, "ACTIVE");
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ClanModel retrieveClan(String name) {
        String sql = "SELECT * FROM clans_clan WHERE name = ?;";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            ClanModel clan = buildClanModel(resultSet);
            resultSet.close();
            statement.close();
            conn.close();
            return clan;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClanModel retrieveClan(int id) {
        String sql = "SELECT * FROM clans_clan WHERE id = ? AND status = 'ACTIVE';";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            ClanModel clan = buildClanModel(resultSet);
            resultSet.close();
            statement.close();
            conn.close();
            return clan;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClanModel buildClanModel(ResultSet resultSet) throws SQLException {
        ClanModel clan = null;
        if(resultSet.next()) {
            int id = resultSet.getInt("id");
            String clanName = resultSet.getString("name");
            String desc = resultSet.getString("description");
            Timestamp origin = resultSet.getTimestamp("origin");
            String status = resultSet.getString("status");
            clan = new ClanModel(id, clanName, desc, origin, status);
        }
        return clan;
    }

    public boolean updateClanName(ClanModel clan, String newName) {
        String sql = "UPDATE clans_clan SET name = ? WHERE id = ?;";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newName);
            statement.setInt(2, clan.getId());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean disbandClan(int id) {
        String eject = "UPDATE clans_player SET clan_id = -1, permission = 0 WHERE clan_id = ?;";
        String disband = "UPDATE clans_clan SET status = 'DISBANDED' WHERE id = ?;";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(eject);
            statement.setInt(1, id);
            boolean ejected = statement.execute();
            statement = conn.prepareStatement(disband);
            statement.setInt(1, id);
            boolean disbanded = statement.execute();
            return ejected && disbanded;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createUser(Player player) {
        if(retrieveUser(player) != null) return false;
        String sql = "INSERT INTO clans_player (uuid, clan_id, permission) VALUES (?, -1, 0);";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UserModel retrieveUser(Player player) {
        String sql = "SELECT * FROM clans_player WHERE uuid = ?;";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            UserModel user = null;
            if(resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                int clanId = resultSet.getInt("clan_id");
                int permission = resultSet.getInt("permission");
                user = new UserModel(uuid, player, clanId, permission);
            }
            resultSet.close();
            statement.close();
            conn.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserClan(UserModel user, int newClanId, int permission) {
        String sql = "UPDATE clans_player SET clan_id = ?, permission = ? WHERE uuid = ?;";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, newClanId);
            statement.setInt(2, permission);
            statement.setString(3, user.getUUID());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(UserModel user) {
        String sql = "DELETE FROM clans_player WHERE uuid = ?;";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getUUID());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
