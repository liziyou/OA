<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<%@page import="java.text.SimpleDateFormat"%>
<jsp:useBean id="su" class="com.tools.StringUtils" scope="request"/>
<%
String date=request.getParameter("date");
if("".equals(date) || date==null){
	date=new SimpleDateFormat("yyyy-M-d").format(new Date());
}
Date date1=new Date();
String time=date1.getHours()+":"+su.formatNO(date1.getMinutes(),2);

%>
<html>
  <head>
    <title>��ӱ�����Ϣ</title>
	<link href="CSS/style.css" rel="stylesheet"/>
<script language="javascript"> 
function check(form){
   if(form.title.value==""){
     alert("��������⣡");form.title.focus();return false;
   }
   	var temp="";
	for(i=0;i<form.remindMode.length;i++){
		if(form.remindMode[i].checked){
			temp=form.remindMode[i].value;
			break;				//����ѭ�������ٲ���Ҫ��ѭ������
		}
	}
	switch(temp){
		case "0":			//����
			break;
		case "1":			//ÿ��
			break;
		case "2":			//ÿ��
			var flag1 = false;
			for(i=0;i<form.flag.length;i++){
				if(form.flag[i].checked){
					flag1 = true;
					break;
				}
			}
			if(!flag1){
				alert("��ѡ��Ҫ���ѵ����ڣ�");
				return false;
			}
			break;	
		case "3":			//ÿ��
			var flag1 = false;
			for(i=0;i<form.flag.length;i++){
				if(form.flag[i].checked){
					flag1 = true;
					break;
				}
			}
			if(!flag1){
				alert("��ѡ��Ҫ���ѵ����ڣ�");
				return false;
			}
			break;	
		case "4":			//ÿ��
			if(form.flag_m.value==2 && form.flag_d.value>29){
				alert("����������ڲ���ȷ��");form.flag_d.focus();
				return false;
			}else if(form.flag_m.value%2==0 && form.flag_d.value>30){
				alert("����������ڲ���ȷ��");form.flag_d.focus();
				return false;
			}
			break;	
	}
   
   if(form.remindTime.value==""){
     alert("����������ʱ�䣡");form.remindTime.focus();return false;
   }else{
	   if(!checkTime(form.remindTime.value)){
			alert("�����������ʱ���ʽ����ȷ��");form.remindTime.focus();return false;
	   }
   }
}
</script> 
<script language="javascript">
//�ж�����������Ƿ���ȷ
function CheckDate(INDate){
 if (INDate=="")			//�����������Ϊ��
    {return true;}
	subYY=INDate.substr(0,4)	 //��ȡ�������ڵ�ǰ4λ
	if(isNaN(subYY) || subYY<=0){	//�������Ĳ������ֻ����Ǹ���
		return true;
	}
	//ת���·ݣ����������ֵĵ�0λ��ʼ���ң��ж��Ƿ����ַ���"-"������н�separate��ֵ����Ϊ"-"
	if(INDate.indexOf('-',0)!=-1){	separate="-"}
	else{
		if(INDate.indexOf('/',0)!=-1){separate="/"}//���û���ַ���"-"����ô��separate��ֵ����Ϊ"/"
		else {return true;}//����������߾���ѯ�������򷵻�true
		}
		area=INDate.indexOf(separate,0)//���ַ����ĵ�һ��λ�ÿ�ʼ���ң������ض��ַ���λ��
		subMM=INDate.substr(area+1,INDate.indexOf(separate,area+1)-(area+1))
		if(isNaN(subMM) || subMM<=0){
		return true;
	}
		if(subMM.length<2){subMM="0"+subMM}//����·�ֻ��1λ����ô������ǰ���"0"
	//ת����
	area=INDate.lastIndexOf(separate)//���ַ��������һλ��ʼ���ң������ض��ַ���λ��
	subDD=INDate.substr(area+1,INDate.length-area-1)
	if(isNaN(subDD) || subDD<=0){
		return true;
	}
	if(eval(subDD)<10){subDD="0"+eval(subDD)}	//����Ϊ���ֱ��ʽ���ַ�����ֵ
	NewDate=subYY+"-"+subMM+"-"+subDD
	if(NewDate.length!=10){return true;}	//����������ڵĳ���С��10
    if(NewDate.substr(4,1)!="-"){return true;}	//����������ڵĵ�5λ����"-"
    if(NewDate.substr(7,1)!="-"){return true;}	//����������ڵĵ�8λ����"-"
	var MM=NewDate.substr(5,2);		//�ӵ�5λ��ʼ��ȡ�ַ�������ȡ2Ϊ��������·�
	var DD=NewDate.substr(8,2);		//�ӵ�8λ��ʼ��ȡ�ַ�������ȡ2Ϊ���������
	//�ж��Ƿ�Ϊ���꣨�ܱ�4�������Ҳ��ܱ�100��������ֻ�ܱ�400��������ݾ�Ϊ���꣩
	if((subYY%4==0 && subYY%100!=0)||subYY%400==0){ //�ж��Ƿ�Ϊ����
		if(parseInt(MM)==2){	//��MMת��Ϊ����
			if(DD>29){return true;}
		}
	}else{
		if(parseInt(MM)==2){
			if(DD>28){return true;}
		}	
	}
	var mm=new Array(1,3,5,7,8,10,12); //�ж�ÿ���е��������
	for(i=0;i< mm.length;i++){
		if (parseInt(MM) == mm[i]){	//�ж��·��Ƿ�Ϊ�����е�ֵ
			if(parseInt(DD)>31){
				return true;
			}else{
				return false;
			}
		}
	}
   if(parseInt(DD)>30){return true;}
   if(parseInt(MM)>12){return true;}//����·ݴ���12
   return false;	//�����������������򷵻�false
}
function checkTime(str){
	 //��JavaScript�У�������ʽֻ��ʹ��"/"��ͷ�ͽ���������ʹ��˫����
	var Expression=/^(([0?0-9])|[01][0-9]|20|21|22|23):([0-5][0-9])$/; 
	var objExp=new RegExp(Expression);
	if(objExp.test(str)==true){
		return true;
	}else{
		return false;
	}
}   
function checkDay(str){
	 //��JavaScript�У�������ʽֻ��ʹ��"/"��ͷ�ͽ���������ʹ��˫����
	var Expression=/^([1-9]|[12][0-9]|30|31)$/; 		//ֻ������1-31����
	var objExp=new RegExp(Expression);
	if(objExp.test(str)==true){
		return true;
	}else{
		return false;
	}	
}
</script>
  </head>
  
  <body  style=" background-color:#FFFFFF;padding:3px; background-image:url(images/add_bottom.gif); background-repeat:repeat-x; background-position:bottom">
<img src="images/add_top.gif">
<script language="javascript">
//�������ѷ�ʽѡ��ʱ��ִ�еĴ������
function deal(form){
	var flag="";
	for(i=0;i<form.remindMode.length;i++){
		if(form.remindMode[i].checked){
			flag=form.remindMode[i].value;
			break;				//����ѭ�������ٲ���Ҫ��ѭ������
		}
	}
	//����ÿ����ʾ�ı������
	var temp="";
	var arrTemp=new Array("����","��һ","�ܶ�","����","����","����","����");
	for(i=1;i<8;i++){
		temp=temp+"<input name=\"flag\" type=\"checkbox\" class=\"noborder\" id=\"flag\" value=\""+i+"\">"+arrTemp[i-1]+"&nbsp;";
	}
	switch(flag){
		case "0":			//����
			document.getElementById("remindFlag").innerHTML="�������ڣ�<input name=\"flag\" type=\"text\" id=\"flag\" value=\"<%=date%>\" size=\"10\" onblur=\"if(CheckDate(this.value)){alert('����������ڲ���ȷ����Ϊ�գ�����������');this.focus();}\">";
			break;
		case "1":			//ÿ��
			document.getElementById("remindFlag").innerHTML="";document.getElementById("remindFlag").style.height="0px";
			break;
		case "2":			//ÿ��
			document.getElementById("remindFlag").innerHTML=temp;
			break;	
		case "3":			//ÿ��
			document.getElementById("remindFlag").innerHTML="<%for(int i=1;i<32;i++){%><input name=\"flag\" type=\"checkbox\" class=\"noborder\" id=\"flag\"  value=\"<%=su.formatNO(i,2)%>\"><%=su.formatNO(i,2)%><%if(i%9==0){out.print("<br>");}}%>";
			break;	
		case "4":			//ÿ��
			document.getElementById("remindFlag").innerHTML="����<select name=\"flag_m\" class=\"wenbenkuang\" id=\"flag_m\"><%for(int i=1;i<13;i++){%><option value=\"<%=i%>\"><%=i%></option><%}%></select>��<input type=\"text\" name=\"flag_d\" id=\"flag_d\" size=\"2\" value=\"1\" onblur=\"if(!checkDay(this.value)){alert('����������ڲ���ȷ��');this.focus();}\">��";
			break;	
	}
}
</script>

<form name="form1" method="post" action="MemoServlet?action=add" onSubmit="return check(this)">
  <table width="556" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="5%" height="30">&nbsp;</td>
      <td width="11%">��&nbsp;&nbsp;&nbsp;&nbsp;�⣺</td>
      <td width="84%">
        <input name="title" type="text" id="title" size="40">      </td>
    </tr>
    <tr>
      <td height="30">&nbsp;</td>
      <td>���ѷ�ʽ��</td>
      <td><input name="remindMode" type="radio" class="noborder" value="0" checked onClick="deal(this.form)">
        ����&nbsp;
        <input name="remindMode" type="radio" class="noborder" value="1" onClick="deal(this.form)"> 
        ÿ��&nbsp;
        <input name="remindMode" type="radio" class="noborder" value="2" onClick="deal(this.form)">
ÿ��&nbsp;
<input name="remindMode" type="radio" class="noborder" value="3" onClick="deal(this.form)">
ÿ�� 
        &nbsp;
        <input name="remindMode" type="radio" class="noborder" value="4" onClick="deal(this.form)">
ÿ��&nbsp;</td>
    </tr>
    <tr>
      <td height="30">&nbsp;</td>
      <td>&nbsp;</td>
      <td id="remindFlag">�������ڣ�
        <input name="flag" type="text" id="flag" value="<%=date%>" size="10" onBlur="if(CheckDate(this.value)){alert('����������ڲ���ȷ������������');this.focus();}"></td>
    </tr>
    <tr>
      <td height="30">&nbsp;</td>	
      <td height="30">����ʱ�䣺</td>
      <td><input name="remindTime" type="text" id="remindTime" value="<%=time%>" size="10"></td>
    </tr>
	
    <tr>
      <td>&nbsp;</td>
      <td>��&nbsp;&nbsp;&nbsp;&nbsp;�ݣ�</td>
      <td><textarea name="content" cols="60" rows="10" class="wenbenkuang" id="content"></textarea></td>
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
