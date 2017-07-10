package example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by dsm_045 on 2017-07-10.
 */
public class MainController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InfoDAO infoDAO = InfoDAO.getInstance();
        PrintWriter writer = resp.getWriter();
        String type = req.getParameter("type").toString();
    }
}
