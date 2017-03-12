package com.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.dao.ProjectDAO;
import com.model.MemoForm;
import com.model.ProjectForm;
import com.tools.StringUtils;
public class ProjectServlet extends HttpServlet {
	private ProjectDAO projectDAO = new ProjectDAO();
		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			doPost(request, response);
		}
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			String action = request.getParameter("action");
			if ("addproject".equals(action)) {
				this.addproject(request, response); // 添加项目
			} else if ("MyproAll".equals(action)) {
				this.MyproAll(request, response); // 查看项目信息
			} else if("delproject".equals(action)){
				this.delproject(request, response);	//删除项目信息
			}else if("searchpro".equals(action)){
				this.searchpro(request, response); //查询项目信息
		}
			else if("upproject".equals(action)){//接受修改要求，转向相应的页面处理
				this.uuproject(request,response);
			}
}       //修改项目信息
		private void uuproject(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			
		}
		//查询项目信息
		private void searchpro(HttpServletRequest request, HttpServletResponse response)
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
				String url="ProjectServlet?action=searchpro&key="+key;
				System.out.println("查看项目信息时的URL："+url);
				List<ProjectForm> list=ProjectDAO.getProjectByPjid(key);
				request.setAttribute("myproList", list); // 保存项目信息
				request.setAttribute("url", url);		//保存打开页面的URL地址
				request.getRequestDispatcher("project_list.jsp").forward(request,
						response);
				
				
			}
		}
			
		//删除某个项目
		private void delproject(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			response.setContentType("text/html;charset=GBK");
			String pjid=request.getParameter("pjid");
			int r=projectDAO.deleteProject(pjid);
			String url=request.getParameter("url").toString();
			if(r==0){
				request.setAttribute("error","删除项目信息失败！");
				request.getRequestDispatcher("error.jsp").forward(request,
						response);
			}
			else{
				PrintWriter out;
				out = response.getWriter();
				out.print("<script language=javascript>alert('删除项目信息成功！');window.location.href='"+url+"';</script>");	
			}
			
		}
		
	
		
		// 查看我的项目信息
		private void MyproAll(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException  {
			// TODO Auto-generated method stub
			HttpSession session = request.getSession();
			if (null == session.getAttribute("userName")) {
				request.setAttribute("error", "您的账户已经过期，请重新登录！");
				request.getRequestDispatcher("error.jsp")
						.forward(request, response);
			} else {
				String url="ProjectServlet?action=MyproAll";
				ArrayList<ProjectForm> list=ProjectDAO.getProjectList();
				request.setAttribute("myproList", list); // 保存备忘信息
				request.setAttribute("url", url);		//保存打开页面的URL地址
				request.getRequestDispatcher("project_list.jsp").forward(request,
						response);
				
			}
			
			
		}
		
		
		// 添加项目
		private void addproject(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			String pjname=request.getParameter("pjname");
			String pjleader=request.getParameter("pjleader");
			String pjstt=request.getParameter("pjstt");
			String pjtad=request.getParameter("pjtad");
			String remark=request.getParameter("remark");
			int r=projectDAO.addProject(pjname, pjleader, pjstt, pjtad, remark);
			if(r==1){
				request.getRequestDispatcher("successPro.jsp").forward(request, response);
			}else{
				request.getRequestDispatcher("failurePro.jsp").forward(request, response);
			}
		}
}
