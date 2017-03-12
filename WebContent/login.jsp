<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language="javascript"> 
function check(){
   if(form1.userName.value==""){
     alert("请输入用户名！");form1.userName.focus();return false;
   }
   if(form1.pwd.value==""){
     alert("请输入密码！");form1.pwd.focus();return false;
   }
}
</script> 

</head>
<body>
<form name="form1" method="post" action="UserServlet?action=login" onSubmit="return check()">
    <table width="583" height="281" border="0" cellpadding="0" cellspacing="0" background="images/bg_login.jpg" border="1">
      <tr>
        <td height="173" colspan="2" align="center">&nbsp;</td>
      </tr>
      <tr>
        <td width="200" height="27" align="right">用&nbsp;户&nbsp;名：&nbsp;</td>
        <td width="383"><input name="userName" type="text" id="userName"></td>
      </tr>
      <tr>
        <td height="27" align="right">密&nbsp;&nbsp;&nbsp;&nbsp;码：&nbsp;</td>
        <td><input name="pwd" type="password" id="pwd"></td>
      </tr>
      <tr>
        <td height="25" colspan="2" align="center"><input name="Submit2" type="submit" class="btn_bg" value="确定">
        &nbsp;
        <input name="Submit3" type="reset" class="btn_bg" value="重置">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      </tr>
      <tr>
        <td height="25" colspan="2" align="center">&nbsp;</td>
      </tr>
    </table>
  
  </form>
  </body>
</html>
