package example;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by dsm_045 on 2017-07-10.
 */
public class InfoDAO {
    private static InfoDAO instance = new InfoDAO();
    private Statement st = null;
    private Connection connection = null;
    private final String TABLE_NAME = "info_list";

    public static InfoDAO getInstance() {
        return instance;
    }

    private InfoDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/info?useSSL=true", "root", "4112665aa");
            st = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print("DB 연결 성공");
    }

    public void insertAuth(String id, String ip) throws SQLException {
        String selectSql = "select * from " + TABLE_NAME + " where id = '" + id + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(selectSql);
        rs.next();
        if(rs.getString("id") != null){
            String updateSql = "update " + TABLE_NAME + " set ip = '" + ip + "' where id = '" + id + "'";
            statement.executeUpdate(updateSql);
        } else {
            String insertSql = "insert into " + TABLE_NAME + " (id, ip) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, ip);
            preparedStatement.setString(2, id);
            preparedStatement.execute();
        }
    }

    public Info getInfo(String id) throws SQLException {
        String sql = "select * from " + TABLE_NAME + " where id = '" + id + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        return new Info(rs.getString("id"), rs.getString("ip"));
    }

    public ArrayList<Info> getInfoList(String type) throws SQLException {
        ArrayList<Info> infoList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME + " where id like '"+ type +"%'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            Info info = new Info(rs.getString("id"), rs.getString("ip"));
            infoList.add(info);
        }
        return infoList;
    }
}
