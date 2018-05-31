package ru.javaops.masterjava.controllers;

import ru.javaops.masterjava.dto.User;
import ru.javaops.masterjava.ThymeleafUtil;
import ru.javaops.masterjava.xml.util.DTOUtils;
import static ru.javaops.masterjava.ThymeleafListener.engine;
import java.io.File;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.xml.stream.XMLStreamException;

/**
 * UploadController.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 30.05.2018
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 1)
@MultipartConfig
public class UploadController extends HttpServlet {
    private static final String SAVE_DIR = "uploadFiles";
    private static final DTOUtils DTO_UTILS = new DTOUtils();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        TemplateEngine engine = ThymeleafUtil.getTemplateEngine(getServletContext());
        engine.process("upload", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());

        String savePath = req.getServletContext().getRealPath("") + SAVE_DIR;

        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) fileSaveDir.mkdir();

        try {
            Part part = req.getPart("fileXML");
            String fileName = part.getSubmittedFileName();
            String fullFileName = savePath + File.separator + fileName;
            part.write(fullFileName);

            Set<User> users = DTO_UTILS.usersFromXMLFile(fullFileName).collect(toSet());
            webContext.setVariable("users", users);
            engine.process("result", webContext, resp.getWriter());
        } catch (XMLStreamException | IOException e) {
            webContext.setVariable("exception", e);
            engine.process("exception", webContext, resp.getWriter());
        }
    }
}