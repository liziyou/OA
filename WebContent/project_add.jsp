<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<%@page import="java.text.SimpleDateFormat"%>
<jsp:useBean id="su" class="com.tools.StringUtils" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>�����Ŀ��Ϣ</title>

<script language="javascript"> 
function check(form){
   if(form.pjname.value==""){
     alert("��������Ŀ����");form.title.focus();return false;
   }
  if(form.pjleader.value==""){
	   alert("�����븺���ˣ�");form.pjleader.focus();return false;
	   }
  if(form.pjstt.value==""){
	   alert("�����뿪ʼʱ�䣡");form.pjstt.focus();return false;
	   }
   if(form.pjtad.value==""){
	   alert("���������ʱ�䣡");form.pjtad.focus();return false;
	   }
  
   }
   </script> 
</head>
<body>
<form name="form1" method="post" action="ProjectServlet?action=addproject" onSubmit="return check(this)">
  <table width="556" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="5%" height="30">&nbsp;</td>
      <td width="11%">��&nbsp;&nbsp;Ŀ&nbsp;&nbsp;����</td>
      <td width="84%">
        <input name="pjname" type="text" id="pjname" size="40">      </td>
    </tr>
    <tr>
      <td width="5%" height="30">&nbsp;</td>
      <td width="11%">��&nbsp;&nbsp;��&nbsp;&nbsp;�ˣ�</td>
      <td width="84%">
        <input name="pjleader" type="text" id="pjleader" size="40"></td>
    </tr>
     <tr>
      <td width="5%" height="30">&nbsp;</td>
      <td width="11%">��&nbsp;ʼ&nbsp;ʱ&nbsp;��&nbsp;��</td>
      <td width="84%">
        <input name="pjstt" type="text" id="pjstt" size="40"></td>
    </tr>
     <tr>
      <td width="5%" height="30">&nbsp;</td>
      <td width="11%">��&nbsp;��&nbsp;ʱ&nbsp;��&nbsp;��</td>
      <td width="84%">
        <input name="pjtad" type="text" id="pjtad" size="40"></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td cols="4" rows="2">��&nbsp;&nbsp;&nbsp;&nbsp;ע��</td>
      <td><input name="remark" type="text" id="remark" size="40"></td></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td height="40">&nbsp;</td>
      <td><input name="Submit" type="submit" class="btn_bg" value="����">
      &nbsp;
      <input name="Reset" type="reset" class="btn_bg" value="����"></td>
    </tr>
  </table>
</form>
  </body>
</html>
