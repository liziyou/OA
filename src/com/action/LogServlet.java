package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.dao.LogDAO;
import com.dao.MemoDAO;
import com.dao.ProjectDAO;
import com.model.LogForm;
import com.model.MemoForm;
import com.model.ProjectForm;
import com.tools.StringUtils;

public class LogServlet extends HttpServlet {
	private LogDAO logDAO = new LogDAO();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("logAll".equals(action)) {
			this.logAll(request, response); // 查看日志信息
		} else if ("logsearch".equals(action)) {
			this.logsearch(request, response); // 查询日志信息
			}
		else if ("logadd".equals(action)) {
			this.logadd(request, response); // 添加日志信息
			}
	}
	//添加日志信息
	private void logadd(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=GBK");
		StringUtils su = new StringUtils();
		LogForm f = new LogForm();
		f.setLogcreater(su.StringtoSql(su.toGBK(request.getParameter("logcreater"))));
		f.setLogcon(su.StringtoSql(su.toGBK(request.getParameter("logcon"))));
		f.setLogtitle(su.StringtoSql(su.toGBK(request.getParameter("logtitle"))));
		f.setLogtime(su.StringtoSql(su.toGBK(request.getParameter("logtime"))));
		HttpSession session = request.getSession();
		if (null == session.getAttribute("userName")) {
			request.setAttribute("error", "您的账户已经过期，请重新登录！");
			request.getRequestDispatcher("error.jsp").forward(request,
					response);
		} else {
			f.setLogcreater(su.toGBK(session.getAttribute("logcreater")
					.toString())); // 获取并保存创建者
			int r = LogDAO.insert(f);
			if (r == 1) {
				request.setAttribute("messages", 1);
				request.getRequestDispatcher("log_ok.jsp").forward(
						request, response);
			} else if (r == 2) {
				request.setAttribute("error", "该日志信息已经存在！");
				request.getRequestDispatcher("error.jsp").forward(request,
						response);
			} else {
				request.setAttribute("error", "添加日志信息失败！");
				request.getRequestDispatcher("error.jsp").forward(request,
						response);
			}

		}
	}
	
	// 查询日志信息
	private void logsearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (null == session.getAttribute("userName")) {
			request.setAttribute("error", "您的账户已经过期，请重新登录！");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			StringUtils su = new StringUtils();
			String key=su.StringtoSql(su.toGBK(request.getParameter("key")));
			String url="LogServlet?action=logsearch&key="+key;
			System.out.println("查看备忘信息时的URL："+url);
			List<LogForm> list = LogDAO.searchlog(key);
			request.setAttribute("logList", list); // 保存日志信息
			request.setAttribute("url", url);		//保存打开页面的URL地址
			request.getRequestDispatcher("log_list.jsp").forward(request,
					response);
		}
	}

	
	
	 // 查看日志信息
	private void logAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (null == session.getAttribute("userName")) {
			request.setAttribute("error", "您的账户已经过期，请重新登录！");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			String url="LogServlet?action=logAll";
			List<LogForm> list=LogDAO.loglist(); 
			request.setAttribute("logList", list); // 保存备忘信息
			request.setAttribute("url", url);		//保存打开页面的URL地址
			request.getRequestDispatcher("log_list.jsp").forward(request,
					response);
	}
 }
	
}
