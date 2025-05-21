import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        boolean isValidationOk = false;

        String timezone = req.getParameter("timezone");
        if (timezone == null || timezone.isBlank()) {
            isValidationOk = true;
        } else {
            try {
                String formattedTimezone = timezone.trim().replace(" ", "+");

                ZoneId.of(formattedTimezone);

                isValidationOk = true;
            } catch (Exception ex) {
                resp.setStatus(400);
                resp.setContentType("text/html; charset=utf-8");
                resp.getWriter().write("<h2>Invalid timezone</h2>");
                resp.getWriter().close();
            }
        }

        if (isValidationOk) {
            chain.doFilter(req, resp);
        }
    }
}
