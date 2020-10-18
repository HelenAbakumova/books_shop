package com.epam.preprod.store.servlet;

import com.epam.preprod.store.cart.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/clear")
public class ClearCartServlet extends HttpServlet {

    private static final String CART = "cart";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Cart cart = (Cart) session.getAttribute(CART);
        cart.clear();
        resp.sendRedirect("range.jsp");
        session.removeAttribute(CART);
        session.removeAttribute("totalCost");
    }
}
