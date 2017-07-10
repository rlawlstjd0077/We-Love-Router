<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <link>
    <title>$중계서버$</title>
    <link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Table Style</title>
    <meta name="viewport" content="initial-scale=1.0; maximum-scale=1.0; width=device-width;">
</head>

<body>
<%
    String DB_URL = "jdbc:mysql://localhost:3306/info";
    String DB_USER = "root";
    String DB_PASSWORD= "4112665aa";
    Connection conn = null;
    try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    } catch(Exception e) {
    }
%>

    <div class="table-title">
        <h3>Device List</h3>
    </div>
    <table class="table-fill">
        <thead>
        <tr>
            <th class="text-left">ID</th>
            <th class="text-left">IP</th>
        </tr>
        </thead>
        <tbody class="table-hover">
        <%
            PreparedStatement preparedStatement = null;
            ResultSet rs = null;
            try{
                String sql = "select * from info_list where id like '%301%'";
                preparedStatement = conn.prepareStatement(sql);
                rs = preparedStatement.executeQuery();
                while(rs.next()){
                 String id = rs.getString("id");
                 String ip = rs.getString("ip");
        %>
        <tr>
            <td class="text-left"><%=id %></td>
            <td class="text-left"><%=ip %></td>
        </tr>
        <%
            }
            }catch (SQLException se){
            }finally {
                try{
                    if(rs != null) rs.close();
                    if(preparedStatement != null) preparedStatement.close();
                    if(conn != null) conn.close();
                }catch (SQLException se){
                }
            }
        %>
        </tbody>
    </table>
</body>
</html>
