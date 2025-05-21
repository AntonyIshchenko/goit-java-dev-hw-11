import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import utils.ConfigManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() {

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(ConfigManager.getProperty("templates.path"));
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);

        engine = new TemplateEngine();
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String timezone = req.getParameter("timezone");
        if (timezone == null || timezone.isBlank()) {
            Cookie[] cookies = req.getCookies();
            String lastTimeZone = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lastTimezone")) {
                    lastTimeZone = cookie.getValue();
                    break;
                }
            }

            timezone = lastTimeZone.isBlank() ? "UTC" : lastTimeZone;
        } else {
            timezone = timezone.trim().replace(" ", "+");

            resp.addCookie(new Cookie("lastTimezone", timezone));
        }

        resp.setContentType("text/html; charset=utf-8");

        Context context = new Context(req.getLocale());
        context.setVariable("time", getFormattedDateTime(timezone));

        engine.process("time", context, resp.getWriter());

        resp.getWriter().close();
    }

    private String getFormattedDateTime(String timezone) {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of(timezone));
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " " + timezone;
    }
}
