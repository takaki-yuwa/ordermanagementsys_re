package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/jsp/*", "/servlet/*" })
public class LoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;

			HttpSession session = req.getSession(false);
			String loginURI = req.getContextPath() + "/Login.jsp";

			boolean loggedIn = (session != null && session.getAttribute("userId") != null);
			boolean loginRequest = req.getRequestURI().equals(loginURI) || req.getRequestURI().endsWith("/Login");
			boolean isStaticResource = req.getRequestURI().matches(".*(\\.css|\\.js|\\.png|\\.jpg|\\.gif)$");

			if (loggedIn || loginRequest || isStaticResource) {
				chain.doFilter(request, response); // 通過
			} else {
				request.setAttribute("noLoginMessage", "ログインしてください");
				res.sendRedirect(loginURI); // 未ログインはログイン画面へ
			}
		} catch (Exception e) {
			System.err.println("LoginFilterで予期しない例外が発生しました");
			e.printStackTrace();

			// エラーページにリダイレクトするなどの対応（必要に応じて）
			if (response instanceof HttpServletResponse) {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "内部エラーが発生しました");
			}
		}
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}