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
			this.add(request, response); // 添加备忘信息
		} else if ("list".equals(action)) {
			this.list(request, response); // 查看备忘信息
		} else if ("listAll".equals(action)) {
			this.listAll(request, response); // 查看全部备忘信息
		}else if("del".equals(action)){
			this.del(request, response);	//删除备忘信息
		}else if("getRemind".equals(action)){
			this.getRemind(request, response);	//获取提醒信息
		}else if("showRemindMsg".equals(action)){
			this.showRemindMsg(request, response);	//获取详细的提醒信息
		}else if("search".equals(action)){
			this.search(request, response);			//查询备忘信息
		}
	}
	// 添加备忘信息
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");
		StringUtils su = new StringUtils();
		MemoForm f = new MemoForm();
		f.setTitle(su.StringtoSql(su.toGBK(request.getParameter("title")))); // 获取标题
		f.setRemindTime(request.getParameter("remindTime")); // 获取提醒时间
		f.setContent(su.StringtoSql(su.toGBK(request.getParameter("content")))); // 获取内容
		int remindMode = Integer.parseInt(request.getParameter("remindMode"));
		f.setRemindMode(remindMode); // 获取提醒方式
		boolean over = false; // 是否过期
		// 根据提醒方式获取标记信息
		switch (remindMode) {
		case 0:
			String time = request.getParameter("flag");
			time=time.substring(0,time.indexOf("-")+1)+String.valueOf(Integer.parseInt(time.substring(time.indexOf("-")+1,time.lastIndexOf("-"))))+"-"+String.valueOf(Integer.parseInt(time.substring(time.lastIndexOf("-")+1,time.length())));
			f.setFlag(time);
			time += " " + request.getParameter("remindTime")+":00";
			try {
				Date dateR = java.text.DateFormat.getDateTimeInstance().parse(
						time); // 获取提醒时间
				Date dateC = new Date(); // 获取当前时间
				if (dateR.getTime() < dateC.getTime()) {
					over = true; // 表示已经过期
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("判断是否过期时产生的错误："+e.getMessage());
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
			request.setAttribute("error", "该备忘信息已经过期，不能进行创建！");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			HttpSession session = request.getSession();
			if (null == session.getAttribute("userName")) {
				request.setAttribute("error", "您的账户已经过期，请重新登录！");
				request.getRequestDispatcher("error.jsp").forward(request,
						response);
			} else {
				f.setCreator(su.toGBK(session.getAttribute("userName")
						.toString())); // 获取并保存创建者

				int r = memoDAO.insert(f);
				//int r=1;
				if (r == 1) {
					request.setAttribute("messages", 1);
					request.getRequestDispatcher("memo_ok.jsp").forward(
							request, response);
				} else if (r == 2) {
					request.setAttribute("error", "该备忘信息已经存在！");
					request.getRequestDispatcher("error.jsp").forward(request,
							response);
				} else {
					request.setAttribute("error", "添加备忘信息失败！");
					request.getRequestDispatcher("error.jsp").forward(request,
							response);
				}
			}
		}
	}

	// 查看备忘信息
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (null == session.getAttribute("userName")) {
			request.setAttribute("error", "您的账户已经过期，请重新登录！");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			int y = Integer.parseInt(request.getParameter("y"));
			int m = Integer.parseInt(request.getParameter("m"));
			int d = Integer.parseInt(request.getParameter("d"));
			int week=Integer.parseInt(request.getParameter("week"));
			String url="MemoServlet?action=list&y="+y+"&m="+m+"&d="+d+"&week="+week;
System.out.println("查看备忘信息时的URL："+url);
			List<MemoForm> list = memoDAO.query(session.getAttribute("userName").toString(),y, m, d,week);

			request.setAttribute("memoList", list); // 保存备忘信息
			request.setAttribute("url", url);		//保存打开页面的URL地址
			request.getRequestDispatcher("memo_list.jsp").forward(request,response);
		}
	}

	// 查看全部备忘信息
	public void listAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (null == session.getAttribute("userName")) {
			request.setAttribute("error", "您的账户已经过期，请重新登录！");
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} else {
			String url="MemoServlet?action=listAll";
			List<MemoForm> list = memoDAO.query(session.getAttribute("userName").toString());
			request.setAttribute("memoList", list); // 保存备忘信息
			request.setAttribute("url", url);		//保存打开页面的URL地址
			request.getRequestDispatcher("memo_list.jsp").forward(request,
					response);
		}
	}
	//按指定条件查询备忘信息
	public void search(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	HttpSession session = request.getSession();
	if (null == session.getAttribute("userName")) {
		request.setAttribute("error", "您的账户已经过期，请重新登录！");
		request.getRequestDispatcher("error.jsp")
				.forward(request, response);
	} else {
		StringUtils su = new StringUtils();
		String key=su.StringtoSql(su.toGBK(request.getParameter("key")));
		String url="MemoServlet?action=search&key="+key;
	System.out.println("查看备忘信息时的URL："+url);
	
	List<MemoForm> list = memoDAO.query(session.getAttribute("userName").toString(),key);

	request.setAttribute("memoList", list); // 保存备忘信息
	request.setAttribute("url", url);		//保存打开页面的URL地址
	request.getRequestDispatcher("memo_list.jsp").forward(request,
			response);
}
}
	//删除备忘信息
	public void del(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html;charset=GBK");
		int id=Integer.parseInt(request.getParameter("id"));
		int rtn=memoDAO.del(id);
		String url=request.getParameter("url").toString();
		//注意：此处必须这么获取URL地址，因为通过获取url参数的值，只能得到类似MemoServlet?action=list的URL
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
		 //将页面重定向到显示备忘信息页面
		if(rtn==0){
			request.setAttribute("error","删除备忘信息失败！");
			request.getRequestDispatcher("error.jsp").forward(request,
					response);
		}else{
			PrintWriter out;
			out = response.getWriter();
			out.print("<script language=javascript>alert('删除备忘信息成功！');window.location.href='"+url+"';</script>");			

		}
	}
	//查询提醒信息
	public void getRemind(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String content="";
		HttpSession session = request.getSession();
		content=memoDAO.getRemind(session.getAttribute("userName").toString());		//查询到期提醒的备忘信息
		request.setAttribute("remindMessage",content);
		if(null==session.getAttribute("flag")){
			session.setAttribute("flag","");
		}
		if("".equals(content)){
			if(!"".equals(session.getAttribute("flag"))){
				int r=memoDAO.updateIsRead(session.getAttribute("flag").toString());	//将数据库中的isRead改为0
				if(r>0){
					session.setAttribute("flag","");
				}
			}
		}else{
			String id=content.substring(content.lastIndexOf("[")+1,content.lastIndexOf("]"));
			session.setAttribute("flag",id);		//将提醒的备忘信息的ID号保存到Session变量中
		}
		request.getRequestDispatcher("remindMessage.jsp").forward(request,
				response);
	}
	//查询详细的提醒信息
	public void showRemindMsg(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String id=request.getParameter("id");
		String url="MemoServlet?action=showRemindMsg&id1="+id;
		request.setAttribute("url", url);		//保存打开页面的URL地址
		request.setAttribute("remindMsgList", memoDAO.getRemindDetail(id)); // 查询备忘信息
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
