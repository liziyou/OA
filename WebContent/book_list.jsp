<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<%@ page import="com.model.AddrBookForm" import="com.dao.AddrBookDAO"%>
<%List<AddrBookForm> list=(List<AddrBookForm>)request.getAttribute("booklist");
String temp="";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>�鿴ͨѶ¼</title>
</head>
<body>
<%@ include file="top.jsp"%>
<div id="main" style="padding-top:5px">
  <table width="98%" height="48" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td height="30" align="center" >
  <form name="form1" method="post" action="AddrBookServlet?action=search">�������ѯ�ؼ��֣�
  <input name="key" type="text" size="40">&nbsp;
  <input name="Submit" type="submit" class="btn_bg" value="��ѯ">
    </form>
      </td>
    </tr>
    <tr>
      <td height="5px"></td>
    </tr>
  </table>
  <table width="98%" height="60" border="0" cellpadding="0" cellspacing="1" bgcolor="#F2F2F0">
<tr>
  <td width="17%" height="27" align="center" bgcolor="#EBDFC9">����</td>
   <td width="8%" align="center" bgcolor="#EBDFC9">�Ա�</td>
   <td width="17%" align="center" bgcolor="#EBDFC9">����</td>
   <td width="49%" align="center" bgcolor="#EBDFC9">��ַ</td>
   <td width="15%" align="center" bgcolor="#EBDFC9">��ϵ��ʽ</td>
   <td width="10%" align="center" bgcolor="#EBDFC9">������</td>
</tr>
<%if(list.size()==0){%>
	<tr>
		<td colspan="6" align="center" bgcolor="#FFFFFF">����ͨѶ¼��</td>
	</tr>
<%}else{%>
	<%for(int i=0;i<list.size();i++){ %>
	<tr>
		<td height="27" bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getAddrname()%></td>
		<td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getAddrsex()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getE_mail()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getAddress()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getPhone()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getLandine()%></td>
	  </tr>     
	<%}
}%> 
</table>
</div>
<%@ include file="copyright.jsp"%>
</body>
</html>

