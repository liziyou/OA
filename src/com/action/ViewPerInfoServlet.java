package com.action;import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.dao.MemoDAO;
import com.dao.ProjectDAO;
import com.dao.ViewPerInfoDAO;
import com.model.ProjectForm;
import com.model.ViewPerInfoForm;
import com.tools.StringUtils;

public class ViewPerInfoServlet extends HttpServlet {
	private ViewPerInfoDAO viewPerInfoDAO = new ViewPerInfoDAO();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if ("personView".equals(action)) {
			this.personView(request, response); // 查看个人信息
		} else if ("personupdate".equals(action)) {
			this.personupdate(request, response); // 修改个人信息
		} else if ("personAdd".equals(action)) {
			this.personAdd(request, response); // 添加用户信息
		}else if ("personsearch".equals(action)) {
			this.personsearch(request, response); // 查询用户信息
		}
}

	private void personView(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException  {
		HttpSession session = request.getSession();
		if (null == session.getAttribute("userName")) {
			request.setAttribute("error", "您的账户已经过期，请重新登录！");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			String url="ViewPerInfoServlet?action=personView";
			ArrayList<ViewPerInfoForm> list=viewPerInfoDAO.getViewPerInfotList();
			request.setAttribute("personlist", list); // 保存备忘信息
			request.setAttribute("url", url);		//保存打开页面的URL地址
			request.getRequestDispatcher("person_list.jsp").forward(request,
					response);
		}
		
	}
	
	

	private void personsearch(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException  {
		HttpSession session = request.getSession();
		if (null == session.getAttribute("userName")) {
			request.setAttribute("error", "您的账户已经过期，请重新登录！");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			StringUtils su = new StringUtils();
			String key=su.StringtoSql(su.toGBK(request.getParameter("key")));
			String url="ViewPerInfoServlet?action=personsearch&key="+key;
			System.out.println("查看项目信息时的URL："+url);
			List<ViewPerInfoForm> list=viewPerInfoDAO.getpersontByPjid(key);
			request.setAttribute("personlist", list); // 保存项目信息
			request.setAttribute("url", url);		//保存打开页面的URL地址
			request.getRequestDispatcher("person_list.jsp").forward(request,
					response);
			
			
		}
		
		
	}
	
	

	private void personAdd(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void personupdate(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

			
}