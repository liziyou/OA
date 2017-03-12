package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.dao.MemoDAO;
import com.model.MemoForm;
import com.tools.StringUtils;

public class MemoServlet extends HttpServlet {
	private MemoDAO memoDAO = new MemoDAO();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if ("add".equals(action)) {
			this.add(request, response); // ��ӱ�����Ϣ
		} else if ("list".equals(action)) {
			this.list(request, response); // �鿴������Ϣ
		} else if ("listAll".equals(action)) {
			this.listAll(request, response); // �鿴ȫ��������Ϣ
		}else if("del".equals(action)){
			this.del(request, response);	//ɾ��������Ϣ
		}else if("getRemind".equals(action)){
			this.getRemind(request, response);	//��ȡ������Ϣ
		}else if("showRemindMsg".equals(action)){
			this.showRemindMsg(request, response);	//��ȡ��ϸ��������Ϣ
		}else if("search".equals(action)){
			this.search(request, response);			//��ѯ������Ϣ
		}
	}
	// ��ӱ�����Ϣ
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");
		StringUtils su = new StringUtils();
		MemoForm f = new MemoForm();
		f.setTitle(su.StringtoSql(su.toGBK(request.getParameter("title")))); // ��ȡ����
		f.setRemindTime(request.getParameter("remindTime")); // ��ȡ����ʱ��
		f.setContent(su.StringtoSql(su.toGBK(request.getParameter("content")))); // ��ȡ����
		int remindMode = Integer.parseInt(request.getParameter("remindMode"));
		f.setRemindMode(remindMode); // ��ȡ���ѷ�ʽ
		boolean over = false; // �Ƿ����
		// �������ѷ�ʽ��ȡ�����Ϣ
		switch (remindMode) {
		case 0:
			String time = request.getParameter("flag");
			time=time.substring(0,time.indexOf("-")+1)+String.valueOf(Integer.parseInt(time.substring(time.indexOf("-")+1,time.lastIndexOf("-"))))+"-"+String.valueOf(Integer.parseInt(time.substring(time.lastIndexOf("-")+1,time.length())));
			f.setFlag(time);
			time += " " + request.getParameter("remindTime")+":00";
			try {
				Date dateR = java.text.DateFormat.getDateTimeInstance().parse(
						time); // ��ȡ����ʱ��
				Date dateC = new Date(); // ��ȡ��ǰʱ��
				if (dateR.getTime() < dateC.getTime()) {
					over = true; // ��ʾ�Ѿ�����
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("�ж��Ƿ����ʱ�����Ĵ���"+e.getMessage());
			}
			break;
		case 2:
		case 3:
			String flag[] = request.getParameterValues("flag");
			String temp = "";
			for (int i = 0; i < flag.length; i++) {
				temp = temp + flag[i] + ",";
			}
			//temp = temp.substring(0, temp.length() - 1);
			f.setFlag(temp);
			break;
		case 4:
			f.setFlag(request.getParameter("flag_m") + "-"
					+ request.getParameter("flag_d"));
			break;
		}
		if (over) {
			request.setAttribute("error", "�ñ�����Ϣ�Ѿ����ڣ����ܽ��д�����");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			HttpSession session = request.getSession();
			if (null == session.getAttribute("userName")) {
				request.setAttribute("error", "�����˻��Ѿ����ڣ������µ�¼��");
				request.getRequestDispatcher("error.jsp").forward(request,
						response);
			} else {
				f.setCreator(su.toGBK(session.getAttribute("userName")
						.toString())); // ��ȡ�����洴����

				int r = memoDAO.insert(f);
				//int r=1;
				if (r == 1) {
					request.setAttribute("messages", 1);
					request.getRequestDispatcher("memo_ok.jsp").forward(
							request, response);
				} else if (r == 2) {
					request.setAttribute("error", "�ñ�����Ϣ�Ѿ����ڣ�");
					request.getRequestDispatcher("error.jsp").forward(request,
							response);
				} else {
					request.setAttribute("error", "��ӱ�����Ϣʧ�ܣ�");
					request.getRequestDispatcher("error.jsp").forward(request,
							response);
				}
			}
		}
	}

	// �鿴������Ϣ
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (null == session.getAttribute("userName")) {
			request.setAttribute("error", "�����˻��Ѿ����ڣ������µ�¼��");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			int y = Integer.parseInt(request.getParameter("y"));
			int m = Integer.parseInt(request.getParameter("m"));
			int d = Integer.parseInt(request.getParameter("d"));
			int week=Integer.parseInt(request.getParameter("week"));
			String url="MemoServlet?action=list&y="+y+"&m="+m+"&d="+d+"&week="+week;
System.out.println("�鿴������Ϣʱ��URL��"+url);
			List<MemoForm> list = memoDAO.query(session.getAttribute("userName").toString(),y, m, d,week);

			request.setAttribute("memoList", list); // ���汸����Ϣ
			request.setAttribute("url", url);		//�����ҳ���URL��ַ
			request.getRequestDispatcher("memo_list.jsp").forward(request,response);
		}
	}

	// �鿴ȫ��������Ϣ
	public void listAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (null == session.getAttribute("userName")) {
			request.setAttribute("error", "�����˻��Ѿ����ڣ������µ�¼��");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			String url="MemoServlet?action=listAll";
			List<MemoForm> list = memoDAO.query(session.getAttribute("userName").toString());
			request.setAttribute("memoList", list); // ���汸����Ϣ
			request.setAttribute("url", url);		//�����ҳ���URL��ַ
			request.getRequestDispatcher("memo_list.jsp").forward(request,
					response);
		}
	}
	//��ָ��������ѯ������Ϣ
	public void search(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	HttpSession session = request.getSession();
	if (null == session.getAttribute("userName")) {
		request.setAttribute("error", "�����˻��Ѿ����ڣ������µ�¼��");
		request.getRequestDispatcher("error.jsp")
				.forward(request, response);
	} else {
		StringUtils su = new StringUtils();
		String key=su.StringtoSql(su.toGBK(request.getParameter("key")));
		String url="MemoServlet?action=search&key="+key;
	System.out.println("�鿴������Ϣʱ��URL��"+url);
	
	List<MemoForm> list = memoDAO.query(session.getAttribute("userName").toString(),key);

	request.setAttribute("memoList", list); // ���汸����Ϣ
	request.setAttribute("url", url);		//�����ҳ���URL��ַ
	request.getRequestDispatcher("memo_list.jsp").forward(request,
			response);
}
}
	//ɾ��������Ϣ
	public void del(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html;charset=GBK");
		int id=Integer.parseInt(request.getParameter("id"));
		int rtn=memoDAO.del(id);
		String url=request.getParameter("url").toString();
		//ע�⣺�˴�������ô��ȡURL��ַ����Ϊͨ����ȡurl������ֵ��ֻ�ܵõ�����MemoServlet?action=list��URL
		if(null!=request.getParameter("y")){
			int y = Integer.parseInt(request.getParameter("y"));
			int m = Integer.parseInt(request.getParameter("m"));
			int d = Integer.parseInt(request.getParameter("d"));
			int week=Integer.parseInt(request.getParameter("week"));
			url+="&y="+y+"&m="+m+"&d="+d+"&week="+week;
		}else if(null!=request.getParameter("id1")){
			url+="&id="+request.getParameter("id1");
		}
		//System.out.println("URL:"+url);
		//System.out.println("rtn:"+rtn);
		 //��ҳ���ض�����ʾ������Ϣҳ��
		if(rtn==0){
			request.setAttribute("error","ɾ��������Ϣʧ�ܣ�");
			request.getRequestDispatcher("error.jsp").forward(request,
					response);
		}else{
			PrintWriter out;
			out = response.getWriter();
			out.print("<script language=javascript>alert('ɾ��������Ϣ�ɹ���');window.location.href='"+url+"';</script>");			

		}
	}
	//��ѯ������Ϣ
	public void getRemind(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String content="";
		HttpSession session = request.getSession();
		content=memoDAO.getRemind(session.getAttribute("userName").toString());		//��ѯ�������ѵı�����Ϣ
		request.setAttribute("remindMessage",content);
		if(null==session.getAttribute("flag")){
			session.setAttribute("flag","");
		}
		if("".equals(content)){
			if(!"".equals(session.getAttribute("flag"))){
				int r=memoDAO.updateIsRead(session.getAttribute("flag").toString());	//�����ݿ��е�isRead��Ϊ0
				if(r>0){
					session.setAttribute("flag","");
				}
			}
		}else{
			String id=content.substring(content.lastIndexOf("[")+1,content.lastIndexOf("]"));
			session.setAttribute("flag",id);		//�����ѵı�����Ϣ��ID�ű��浽Session������
		}
		request.getRequestDispatcher("remindMessage.jsp").forward(request,
				response);
	}
	//��ѯ��ϸ��������Ϣ
	public void showRemindMsg(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String id=request.getParameter("id");
		String url="MemoServlet?action=showRemindMsg&id1="+id;
		request.setAttribute("url", url);		//�����ҳ���URL��ַ
		request.setAttribute("remindMsgList", memoDAO.getRemindDetail(id)); // ��ѯ������Ϣ
		request.getRequestDispatcher("showRemindMsg_detail.jsp").forward(request,response);
	}
	public void init() throws ServletException {
		// Put your code here
	}
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
}
