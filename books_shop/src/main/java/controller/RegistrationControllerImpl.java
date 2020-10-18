package com.epam.preprod.store.servlet;

import com.epam.preprod.store.bean.RegistrationForm;
import com.epam.preprod.store.entity.User;
import com.epam.preprod.store.captcha.Captcha;
import com.epam.preprod.store.parser.FormParser;
import com.epam.preprod.store.service.api.UserService;
import com.epam.preprod.store.util.PrintableConstant;
import com.epam.preprod.store.validator.UserValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

/**
 * Servlet which provides registration for users.
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class RegistrationServlet extends HttpServlet {

    private UserService userService;
    private UserValidator userValidator;
    private FormParser parser;
    private String filePath;
    private String extension;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        userService = (UserService) servletContext.getAttribute(PrintableConstant.USER_SERVICE);
        userValidator = (UserValidator) servletContext.getAttribute(PrintableConstant.VALIDATOR);
        parser = (FormParser) servletContext.getAttribute(PrintableConstant.PARSER);
        filePath = getServletContext().getInitParameter("file-download");
        extension = ".png";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("errors", session.getAttribute("errors"));
        request.setAttribute("bean", session.getAttribute("bean"));
        request.getRequestDispatcher("registration.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Captcha captcha = (Captcha) request.getServletContext().getAttribute(PrintableConstant.CAPTCHA);
        RegistrationForm registrationForm = parser.readRegForm(request);
        Map<String, String> errors = userValidator.validateRegForm(registrationForm);

        captcha.validateCaptcha(request, errors);
        HttpSession session = request.getSession();
        if (userService.exists(registrationForm.getEmail())) {
            errors.put(PrintableConstant.EMAIL_ERR, PrintableConstant.EMAIL_ALREADY_EXISTS_MSG);
        }
        if (!errors.isEmpty()) {
            registrationForm.setPassword("");
            if (session != null) {
                session.setAttribute("bean", registrationForm);
                session.setAttribute("errors", errors);
                response.sendRedirect("registration");
                captcha.deleteCaptcha(request);
            }
        } else {
            registrationForm.setImage(loadAvatar(request));
            userService.create(transformToDomain(registrationForm));
            response.sendRedirect("registrationSuccessful.html");
            captcha.deleteCaptcha(request);
            session.removeAttribute("bean");
            session.removeAttribute("errors");
        }
    }

    private User transformToDomain(RegistrationForm registrationForm) {
        return new User(registrationForm.getName(), registrationForm.getLastName(), registrationForm.getEmail(),
                registrationForm.getPassword(), registrationForm.getDispatch(), registrationForm.getImage());
    }

    private String loadAvatar(HttpServletRequest req) throws IOException, ServletException {
        Part filePart = req.getPart("file");
        String fileName = null;
        if (filePart != null) {
            InputStream fileContent = filePart.getInputStream();
            File uploads = new File(filePath);
            fileName = req.getParameter("email").replaceFirst("\\.", "_") + extension;
            String filePath = uploads + File.separator + fileName;
            File file = new File(filePath);
            Files.copy(fileContent, file.toPath());
        }
        return fileName;
    }
}

