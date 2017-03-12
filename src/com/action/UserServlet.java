package com.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDAO;
import com.model.UserForm;
import com.tools.StringUtils;

public class UserServlet extends HttpServlet {
	private UserDAO userDAO = new UserDAO();

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if ("login".equals(action)) {
			this.login(request, response); // 用户登录
		} 
	}

	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");
		StringUtils su = new StringUtils();
		UserForm f = new UserForm();
		f.setUserName(su.toGBK(request.getParameter("userName"))); // 获取并设置用户名
		f.setPwd(request.getParameter("pwd")); // 获取并设置密码
		int r = userDAO.login(f);
		if (r == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("userName", f.getUserName());
			request.getRequestDispatcher("login_ok.jsp").forward(request,
					response);
		} else {
			request.setAttribute("error", "您输入的用户名或密码错误！");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		}

	}



	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
