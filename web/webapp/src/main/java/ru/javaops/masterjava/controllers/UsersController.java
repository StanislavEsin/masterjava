package ru.javaops.masterjava.controllers;

import ru.javaops.masterjava.persist.model.User;
import ru.javaops.masterjava.persist.services.UserServices;
import ru.javaops.masterjava.persist.services.impl.UserServicesImpl;
import static ru.javaops.masterjava.common.web.ThymeleafListener.engine;
import java.util.List;
import java.util.stream.Collectors;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * UsersController.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 08.06.2018
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 1)
public class UsersController extends HttpServlet {
    private final UserServices userServices = new UserServicesImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());

        List<User> users = userServices.getWithLimit(20).collect(Collectors.toList());
        webContext.setVariable("users", users);

        engine.process("users", webContext, resp.getWriter());
    }
}