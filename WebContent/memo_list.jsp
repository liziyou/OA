<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<%@ page import="com.model.MemoForm"%>
<%List<MemoForm> list=(List<MemoForm>)request.getAttribute("memoList");
String temp="";
%>
<html>
  <head>
    <title>�鿴����¼��Ϣ</title>
	<link href="CSS/style.css" rel="stylesheet"/>
  </head>
	<script language="javascript">
	function del(para){
		if(confirm("���Ҫɾ���ñ�����Ϣ��")){
			window.location.href="MemoServlet?action=del&id="+para+"&url=<%=request.getAttribute("url").toString()%>";
		}
	}
	</script>  
  <body>
<%@ include file="top.jsp"%>
<div id="main" style="padding-top:5px;">

  <table width="98%" height="48" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="30" align="center" > <form name="form1" method="post" action="MemoServlet?action=search">�������ѯ�ؼ��֣�
       
          <input name="key" type="text" size="40">
          &nbsp;
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
<td width="49%" align="center" bgcolor="#EBDFC9">����</td>
<td width="8%" align="center" bgcolor="#EBDFC9">����ʱ��</td>
<td width="7%" align="center" bgcolor="#EBDFC9">���ѷ�ʽ</td>
<td width="15%" align="center" bgcolor="#EBDFC9">��������</td>
<td width="4%" align="center" bgcolor="#EBDFC9">ɾ��</td>
</tr>
<%if(list.size()==0){%>
	<tr>
		<td colspan="6" align="center" bgcolor="#FFFFFF">���ޱ�����Ϣ��</td>
	</tr>
<%}else{%>
	<%for(int i=0;i<list.size();i++){ 
		int remindMode=list.get(i).getRemindMode();
		switch (remindMode) {
			case 0:temp="����";break;
			case 1:temp="ÿ��";break;
			case 2:temp="ÿ��";break;
			case 3:temp="ÿ��";break;
			case 4:temp="ÿ��";break;
		}
		String flag=list.get(i).getFlag();
		if(remindMode==2){		//�����ѷ�ʽΪÿ�ܵ���������ת��Ϊ������,��һ�������ĸ�ʽ
			flag=flag.substring(0,flag.length()-1);
			String[] arrFlag=flag.split(",");
			int week=0;
			flag="";
			for(int j=0;j<arrFlag.length;j++){
				week=Integer.parseInt(arrFlag[j]);
				switch (week) {
					case 1:flag+="����,";break;
					case 2:flag+="��һ,";break;
					case 3:flag+="�ܶ�,";break;
					case 4:flag+="����,";break;
					case 5:flag+="����,";break;
					case 6:flag+="����,";break;
					case 7:flag+="����,";break;
				}				
			}
			flag=flag.substring(0,flag.length()-1);
		}else if(remindMode==3){
			flag=flag.substring(0,flag.length()-1);
		}
	%>
	  <tr>
		<td height="27" bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getTitle()%></td>
		<td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getContent()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getRemindTime()%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=temp%></td>
	    <td bgcolor="#FFFFFF">&nbsp;<%=flag%></td>
	    <td align="center" bgcolor="#FFFFFF"><a href="#" onClick="del(<%=list.get(i).getId()%>)"><img src="images/del.gif" border="0"></a></td>
	  </tr>     
	<%}
}%> 
</table>
</div>
<%@ include file="copyright.jsp"%>
  </body>
</html>
