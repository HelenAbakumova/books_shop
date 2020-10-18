package com.epam.preprod.store.parser;

import com.epam.preprod.store.bean.Filter;
import com.epam.preprod.store.bean.LoginForm;
import com.epam.preprod.store.bean.OrderForm;
import com.epam.preprod.store.bean.RegistrationForm;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class FormParser {
    private static final int DEFAULT_LIMIT = 5;
    private static final int DEFAULT_PAGE = 1;
    private static final String DEFAULT_SORT = " name asc";

    public RegistrationForm readRegForm(HttpServletRequest request) {
        return new RegistrationForm(request.getParameter("name"),
                request.getParameter("lastName"),
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("rePassword"),
                Boolean.valueOf(request.getParameter("dispatch")),
                request.getParameter("image"));
    }

    public LoginForm readLoginForm(HttpServletRequest request) {
        return new LoginForm(request.getParameter("email"), request.getParameter("password"));
    }

    /**
     * Parse fields from product filter form to bean.
     *
     * @param request HttpServletRequest.
     * @return entity of filter.
     */
    public Filter readFilter(HttpServletRequest request) {
        Filter filter = new Filter();
        boolean isChecked = false;
        if (request.getParameterValues("name") != null &&
                request.getParameter("name").matches("^\\w+\\s\\w+$")) {
            filter.setName(request.getParameter("name"));
        }
        if (request.getParameterValues("brand") != null) {
            for (String brand : request.getParameterValues("brand")) {
                isChecked = brand.matches("^\\d+$");
            }
            if (isChecked) {
                filter.setBrand(Arrays.asList(request.getParameterValues("brand")));
            }
            isChecked = false;
        }

        if (request.getParameterValues("category") != null) {
            for (String category : request.getParameterValues("category")) {
                isChecked = category.matches("^\\w+$");
            }
            if (isChecked) {
                filter.setCategory(Arrays.asList(request.getParameterValues("category")));
            }
            isChecked = false;
        }

        if (request.getParameterValues("material") != null) {
            for (String material : request.getParameterValues("material")) {
                isChecked = material.matches("^\\w+$");
            }
            if (isChecked) {
                filter.setMaterial(Arrays.asList(request.getParameterValues("material")));
            }
        }
        if (request.getParameterValues("priceFrom") != null) {
            if (request.getParameter("priceFrom").matches("^\\d+$")) {
                filter.setPriceFrom(request.getParameter("priceFrom"));
            }
        }
        if (request.getParameterValues("priceTo") != null &&
                request.getParameter("priceTo").matches("^\\d+$")) {
            filter.setPriceTo(request.getParameter("priceTo"));
        }
        if (request.getParameter("sort") != null &&
                request.getParameter("sort").matches("^\\w+\\s\\w+$")) {
            filter.setSort(request.getParameter("sort"));
        } else {
            filter.setSort(DEFAULT_SORT);
        }

        if (request.getParameter("limit") != null && request.getParameter("limit").matches("^\\d+$")) {
            filter.setLimit(Integer.valueOf(request.getParameter("limit")));
        } else {
            filter.setLimit(DEFAULT_LIMIT);
        }

        if (request.getParameter("page") != null && request.getParameter("page").matches("^\\d+$")) {
            filter.setPage(Integer.valueOf(request.getParameter("page")));
        } else {
            filter.setPage(DEFAULT_PAGE);
        }
        return filter;
    }

    public OrderForm readOrderForm(HttpServletRequest request) {
        OrderForm orderForm = new OrderForm();
        orderForm.setAddress(request.getParameter("address"));
        orderForm.setPayment(request.getParameter("payment"));
        return orderForm;
    }

}
