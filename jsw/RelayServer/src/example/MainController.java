package example;

import org.json.JSONML;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by dsm_045 on 2017-07-10.
 */
public class MainController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InfoDAO infoDAO = InfoDAO.getInstance();
        String id, ip;
        PrintWriter writer = resp.getWriter();
        String type = req.getParameter("type").toString();
        if (type != null) {
            switch (type) {
                case "reg_info":
                    if ((id = req.getParameter("id")) != null && (ip = req.getParameter("ip")) != null) {
                        try {
                            infoDAO.insertAuth(id, ip);
                        } catch (SQLException e) {
                            resp.setStatus(500);
                            writer.write(JsonManager.makeResp("서버 오류 발생"));
                            break;
                        }
                        resp.setStatus(200);
                        writer.write(JsonManager.makeResp("성공적으로 수행되었습니다"));
                    } else {
                        resp.setStatus(400);
                        writer.write(JsonManager.makeResp("파라미터가 잘못되었습니다."));
                    }
                    break;
                case "get_info":
                    Info info;
                    if ((id = req.getParameter("id")) != null) {
                        try {
                            info = infoDAO.getInfo(id);
                        } catch (SQLException e) {
                            resp.setStatus(500);
                            writer.write(JsonManager.makeResp("서버 오류 발생"));
                            break;
                        }
                        resp.setStatus(200);
                        writer.write(JsonManager.makeRespInfo(info));
                    } else {
                        resp.setStatus(400);
                        writer.write(JsonManager.makeResp("파라미터가 잘못되었습니다."));
                    }
                    break;
                case "get_info_list":
                    ArrayList<Info> infoList;
                    if ((id = req.getParameter("id")) != null && (id.equals("RPI") || id.equals("PC"))) {
                        try {
                            infoList = infoDAO.getInfoList(id);
                        } catch (SQLException e) {
                            resp.setStatus(500);
                            writer.write(JsonManager.makeResp("서버 오류 발생"));
                            break;
                        }
                        resp.setStatus(200);
                        writer.write(JsonManager.makeRespInfoList(infoList));
                    } else {
                        resp.setStatus(400);
                        writer.write(JsonManager.makeResp("파라미터가 잘못되었습니다."));
                    }
                    break;
            }
        } else {
            resp.setStatus(400);
            writer.write(JsonManager.makeResp("파라미터가 잘못되었습니다."));
        }
    }

}
