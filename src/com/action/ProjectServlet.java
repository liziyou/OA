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
				this.addproject(request, response); // �����Ŀ
			} else if ("MyproAll".equals(action)) {
				this.MyproAll(request, response); // �鿴��Ŀ��Ϣ
			} else if("delproject".equals(action)){
				this.delproject(request, response);	//ɾ����Ŀ��Ϣ
			}else if("searchpro".equals(action)){
				this.searchpro(request, response); //��ѯ��Ŀ��Ϣ
		}
			else if("upproject".equals(action)){//�����޸�Ҫ��ת����Ӧ��ҳ�洦��
				this.uuproject(request,response);
			}
}       //�޸���Ŀ��Ϣ
		private void uuproject(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			
		}
		//��ѯ��Ŀ��Ϣ
		private void searchpro(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			
			HttpSession session = request.getSession();
			if (null == session.getAttribute("userName")) {
				request.setAttribute("error", "�����˻��Ѿ����ڣ������µ�¼��");
				request.getRequestDispatcher("error.jsp")
						.forward(request, response);
			} else {
				StringUtils su = new StringUtils();
				String key=su.StringtoSql(su.toGBK(request.getParameter("key")));
				String url="ProjectServlet?action=searchpro&key="+key;
				System.out.println("�鿴��Ŀ��Ϣʱ��URL��"+url);
				List<ProjectForm> list=ProjectDAO.getProjectByPjid(key);
				request.setAttribute("myproList", list); // ������Ŀ��Ϣ
				request.setAttribute("url", url);		//�����ҳ���URL��ַ
				request.getRequestDispatcher("project_list.jsp").forward(request,
						response);
				
				
			}
		}
			
		//ɾ��ĳ����Ŀ
		private void delproject(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			response.setContentType("text/html;charset=GBK");
			String pjid=request.getParameter("pjid");
			int r=projectDAO.deleteProject(pjid);
			String url=request.getParameter("url").toString();
			if(r==0){
				request.setAttribute("error","ɾ����Ŀ��Ϣʧ�ܣ�");
				request.getRequestDispatcher("error.jsp").forward(request,
						response);
			}
			else{
				PrintWriter out;
				out = response.getWriter();
				out.print("<script language=javascript>alert('ɾ����Ŀ��Ϣ�ɹ���');window.location.href='"+url+"';</script>");	
			}
			
		}
		
	
		
		// �鿴�ҵ���Ŀ��Ϣ
		private void MyproAll(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException  {
			// TODO Auto-generated method stub
			HttpSession session = request.getSession();
			if (null == session.getAttribute("userName")) {
				request.setAttribute("error", "�����˻��Ѿ����ڣ������µ�¼��");
				request.getRequestDispatcher("error.jsp")
						.forward(request, response);
			} else {
				String url="ProjectServlet?action=MyproAll";
				ArrayList<ProjectForm> list=ProjectDAO.getProjectList();
				request.setAttribute("myproList", list); // ���汸����Ϣ
				request.setAttribute("url", url);		//�����ҳ���URL��ַ
				request.getRequestDispatcher("project_list.jsp").forward(request,
						response);
				
			}
			
			
		}
		
		
		// �����Ŀ
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
