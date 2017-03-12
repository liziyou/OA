package com.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.catalina.connector.Request;

import com.dao.AddrBookDAO;
import com.dao.MemoDAO;
import com.model.AddrBookForm;
import com.model.MemoForm;
import com.tools.StringUtils;


public class AddrBookServlet extends HttpServlet {
	private AddrBookDAO addrBookDAO = new AddrBookDAO();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action=request.getParameter("action");
		if("BookAll".equals(action)){
			this.BookAll(request, response); // �鿴ȫ��ͨѶ¼��Ϣ		
		}
		else if("search".equals(action)){
			this.search(request, response);			//��ѯͨѶ¼��Ϣ
		}
	}
	//ָ�������鿴ͨѶ¼
	public void search(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		if(null==session.getAttribute("userName")){
			request.setAttribute("error", "�����˻��Ѿ������ˣ������µ�¼��");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}else{
			StringUtils su=new StringUtils();
			String key=su.StringtoSql(su.toGBK(request.getParameter("key")));
			String url="AddrBookServlet?action=search&key="+key;
			System.out.println("�鿴ͨѶ¼��Ϣʱ��URL��"+url);
			List<AddrBookForm> list = addrBookDAO.searchbook(key);
			request.setAttribute("booklist", list);//����ͨѶ¼��Ϣ
			request.setAttribute("url", url);
			request.getRequestDispatcher("book_list.jsp").forward(request, response);
		}	
    }
	

  private void BookAll(HttpServletRequest request, HttpServletResponse response) 
		  throws ServletException,IOException{
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (null == session.getAttribute("userName")) {
			request.setAttribute("error", "�����˻��Ѿ����ڣ������µ�¼��");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			String url="AddrBookServlet?action=BookAll";
			
			ArrayList<AddrBookForm> list=addrBookDAO.getBookList(url);
			request.setAttribute("booklist", list);//����ͨѶ¼��Ϣ
			request.setAttribute("url", url);
			request.getRequestDispatcher("book_list.jsp").forward(request, response);;
		}
      }
  
  
  
	public void init() throws ServletException {
		// Put your code here
	}
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
}

