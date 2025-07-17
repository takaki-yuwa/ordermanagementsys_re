package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/Login.jsp";

        boolean loggedIn = (session != null && session.getAttribute("userId") != null);
        boolean loginRequest = req.getRequestURI().equals(loginURI) || req.getRequestURI().endsWith("/Login");
        boolean isStaticResource = req.getRequestURI().matches(".*(\\.css|\\.js|\\.png|\\.jpg|\\.gif)$");
        
        if (loggedIn || loginRequest || isStaticResource) {
            chain.doFilter(request, response); // 次のフィルターまたはサーブレットへ進む
        } else {
        	request.setAttribute("noLoginMessage", "ログインしてください");
            res.sendRedirect(loginURI); // 未ログインの場合はログイン画面へリダイレクト
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {}
    public void destroy() {}
}
