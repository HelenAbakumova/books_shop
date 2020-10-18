package controller.api;

import entity.User;
import service.api.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class UserControllerImpl extends HttpServlet {
private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        userService = (UserService)servletContext.getAttribute("user service");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("=====================================");
        List<User> products = userService.findAll();
        System.out.println("=====================================");
        req.setAttribute("name", "User");
        req.setAttribute("products", products);
//req.getRequestDispatcher("mainPage.jsp").forward(req, resp);
        getServletContext().getRequestDispatcher("/mainPage.jsp").forward(req, resp);
//        super.doGet(req, resp);resp
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
