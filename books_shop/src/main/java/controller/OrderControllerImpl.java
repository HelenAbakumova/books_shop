package com.epam.preprod.store.servlet;

import com.epam.preprod.store.bean.OrderForm;
import com.epam.preprod.store.cart.Cart;
import com.epam.preprod.store.entity.User;
import com.epam.preprod.store.parser.FormParser;
import com.epam.preprod.store.service.impl.OrderServiceImpl;
import com.epam.preprod.store.util.PrintableConstant;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
        } else {
            req.getRequestDispatcher("order.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletContext servletContext = getServletContext();
        OrderServiceImpl orderServiceImpl = (OrderServiceImpl) servletContext.getAttribute("order service");
        FormParser parser = (FormParser) servletContext.getAttribute(PrintableConstant.PARSER);
        HttpSession session = req.getSession();
        String card = req.getParameter("payment");
        User user = (User) session.getAttribute("user");

        OrderForm orderForm = parser.readOrderForm(req);
        orderForm.setDate(new Timestamp(System.currentTimeMillis()));
        orderForm.setStatus("Accepted");
        orderForm.setComment("User made an order");
        orderForm.setUserId(user.getId());

        if (orderServiceImpl.create((Cart) session.getAttribute("cart"), orderForm) != 0) {
            resp.sendRedirect("index.jsp");
            session.removeAttribute("cart");
        } else {
            resp.sendRedirect("error.jsp");
        }
    }
}
