package controller;

import entity.Cart;
import entity.Product;
import service.api.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final String CART = "cart";

    private ProductService productService;

    @Override
    public void init() {
        productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));

        HttpSession session = req.getSession(false);
        Cart cart = (Cart) session.getAttribute(CART);
        String totalCost = null;
        if (action.equals("+")) {
            cart.increaseCount(id);
            totalCost = cart.getTotalSum();
        }
        if (action.equals("-")) {
            cart.decreaseCount(id);
            totalCost = cart.getTotalSum();
        }
        resp.getWriter().write(cart.getCountById(id) + " " + totalCost);
        session.setAttribute(CART, cart);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        HttpSession session = req.getSession();

        if (session.getAttribute(CART) == null) {
            session.setAttribute(CART, new Cart());
        }

        Cart cart = (Cart) session.getAttribute("cart");
        Product product = productService.getById(Integer.parseInt(id));
        cart.add(product);
        String size = String.valueOf(cart.getCart().size());
        String totalCost = cart.getTotalSum();
        resp.getWriter().write(size);
        session.setAttribute(CART, cart);
        session.setAttribute("totalCost", String.valueOf(totalCost));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession(false);
        Cart cart = (Cart) session.getAttribute(CART);
        cart.remove(id);
        System.out.println(cart.getCart());
        String totalCost = cart.getTotalSum();

        resp.getWriter().write(String.valueOf(totalCost));
        session.setAttribute(CART, cart);
    }
}
