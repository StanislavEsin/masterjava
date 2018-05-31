package ru.javaops.masterjava;

import org.thymeleaf.TemplateEngine;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ThymeleafListener.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 31.05.2018
 */
@WebListener
public class ThymeleafListener implements ServletContextListener {
    public static TemplateEngine engine;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        engine = ThymeleafUtil.getTemplateEngine(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}