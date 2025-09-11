package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.EntityUser;
import java.io.IOException;

@WebFilter(urlPatterns = {"/user/*", "/manager/*", "/admin/*"})
public class RoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        EntityUser user = (session != null) ? (EntityUser) session.getAttribute("user") : null;
        String uri = req.getRequestURI();

        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        int roleId = user.getRoleId();
        if (uri.startsWith(req.getContextPath() + "/user/") && roleId != 1 && roleId != 3) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        if (uri.startsWith(req.getContextPath() + "/manager/") && roleId != 2) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        if (uri.startsWith(req.getContextPath() + "/admin/") && roleId != 3) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }
}
