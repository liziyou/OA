<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��ʾ��Ϣ</title>
<link href="CSS/style.css" rel="stylesheet">
<%
int rtn=Integer.parseInt(request.getAttribute("messages").toString());
		if(rtn==1){
		%>
		<script language="javascript">
			alert("������Ϣ��ӳɹ���");
			window.close();			
		</script>
		<%
		}else if(rtn==2){%>
		<script language="javascript">
			alert("������Ϣɾ���ɹ���");
			opener.location.reload();
			window.close();
		</script>	
		<%
		}
%>
</script>
</head>

<body>

</body>
</html>