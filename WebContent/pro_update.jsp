<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*"%>
<%@ page import="com.model.ProjectForm" import="com.model.ProjectDAO"%>
<%@ page import="java.util.*,java.io.*,java.text.*"%> 
<%@ page import="java.util.*" %> 
<%@ page import="java.text.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>�޸���Ŀ��Ϣ</title>
</head>
<body>
<%
ProjectForm f=(ProjectForm)request.getAttribute("pjid");
%>
<form name="form1" method="post" action="ProjectServlet?action=project_update">
<input type="hidden" name="pjid" value="<%=f.getPjid() %>">
  <table width="50%" border="1" align="center">
  <caption>�޸���Ŀ��Ϣ</caption>
  <tr>
     <th width="30%">��Ŀ��</th>
     <td width="70%"><input name="pjname" type="text" value="<%=f.getPjname() %>"></td>
  </tr>
  <tr>
    <th> ��Ŀ������</th>
    <td><input name="pjleader" type="text" value=<%=f.getPjleader() %>></td>
  </tr>
  <tr>
    <th>��ʼʱ��</th>
    <td><input name="pjstt" type="text" value=<%=f.getPjstt() %>></td>
  </tr>
  <tr>
    <th>����ʱ��</th>
    <td><input name="pjtad" type="text" value=<%=f.getPjtad() %>></td>
  </tr>
  <tr>
    <th>��ע</th>
    <td><input name="pjtad" type="text" value=<%=f.getRemark() %>></td>
  </tr>
  <tr>
     <th colspan="2">
      <input type="submit" value="���">
      <input type="reset" value="����">
     </th>
  </tr>
  </table>
</form>
</body>
</html> 