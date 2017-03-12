

 /*  
 *    ��Ϣ����  
 */  
function PopBubble(width,height,title,content,foot){  
    this.content  = content;  				//��ʾ����
    this.title= title; 			 			//��ʾ����
	this.foot= foot; 						//ҳ������  
    this.width    = width?width:200;  		//���õ������ڵĿ��
    this.height = height?height:120;  		//���õ������ڵĸ߶�
    this.timeout= 150;						//���ô��ڵ�ͣ��ʱ��
    this.speed    = 10; 					//���ô��ڵĵ����ٶ� 
    this.step    = 1; 						//���ô��ڵĵ�������
    this.right    = screen.width -1;  		//���ô����ұߵ�λ��
    this.bottom = screen.height; 			//���ô��ڵױߵ�λ��
    this.left    = this.right - this.width; 	//���ô�����ߵ�λ��
    this.top    = this.bottom - this.height; 	//���ô��ڶ��ߵ�λ��
    this.timer    = 0; 							//��ʱ��
    this.pause    = false;						//����Ƿ�ֹͣ����
    this.close    = false;						//����Ƿ�ر�
    this.autoHide    = true;					//����Ƿ��Զ�����
}  
  
//�����������ڵķ���  
PopBubble.prototype.hide = function(){  
		//���õ������ڵĸ߶�
        var offset  = this.height>this.bottom-this.top?this.height:this.bottom-this.top; 
        var obj  = this;  
        if(this.timer>0){   
            window.clearInterval(obj.timer);  
        }  
 
        var fun = function(){  
            if(obj.pause==false||obj.close){	//�ж��Ƿ񵯳�����
                var x  = obj.left; 				//��ȡ������߿��λ��
                var y  = 0; 
                var width = obj.width; 			//��ȡ���ڵĿ��
				//��ȡ���ڵĸ߶�
                var height = 0; 
                if(obj.offset>0){ 
                    height = obj.offset; 
                } 
     
                y  = obj.bottom - height; 		//��ȡ���ڶ��߿��λ��
     
                if(y>=obj.bottom){ 
                    window.clearInterval(obj.timer);  
                    obj.Pop.hide(); 			//�������� 
                } else { 
                    obj.offset = obj.offset - obj.step;  
                } 
                obj.Pop.show(x,y,width,height);  //��������  
            }             
        }  
 
        this.timer = window.setInterval(fun,this.speed)      
    
}  
//��ʾ�������ڵķ���
PopBubble.prototype.show = function(){  
	//����һ�����㴰�ڶ��󣬸ö���ֻ����IE5.5�����ϰ汾�����������ʹ��
    var oPopup = window.createPopup();   
    this.Pop = oPopup;  
    var w = this.width;  
    var h = this.height;  
    this.offset  = 0; 
	 
   var str = "<div style='border-right: #455690 1px solid; border-top: #a6b4cf 1px solid; z-index: 99999; left: 0px; border-left: #a6b4cf 1px solid; width: " + w + "px; border-bottom: #455690 1px solid; position: absolute; top: 0px; height: " + h + "px; background-color: #7ACCC8'>"  
        str += "<table style='border-top: #ffffff 1px solid; border-left: #ffffff 1px solid' cellSpacing=0 cellPadding=0 width='100%' bgColor='#7ACCC8' border=0>"  
        str += "<tr>"  
        str += "<td width=30 height=24></td>"  
        str += "<td style='padding-left: 4px; font-weight: normal; font-size: 12px; color: #331C09; padding-top: 4px' vAlign=center width='100%'>" + this.title + "</td>"  
        str += "<td style='padding-right: 2px; padding-top: 2px' valign=center align=right width=19>"  
        str += "<span title=�ر� style='font-weight: bold; font-size: 12px; cursor: hand; color: #FF4200; margin-right: 4px' id='btn_Close' >��</span></td>"  
        str += "</tr>"  
        str += "<tr>"  
        str += "<td style='padding: 1px' colSpan=3 height=" + (h-28) + ">"  
        str += "<div style='background-color: #ECF3F7;border-right: #b9c9ef 1px solid; padding-right: 8px; border-top: #728eb8 1px solid; padding-left: 8px; font-size: 12px; padding-bottom: 8px; border-left: #728eb8 1px solid; width: 100%; padding-top: 8px; border-bottom: #b9c9ef 1px solid; height: 100%'><a href='javascript:void(0)' hidefocus=true id='aLink'>" + this.content + "</a><br><br>"  
		str += "<div style='word-break: break-all' align='left' style='color:#1F347B'>" + this.foot +"<embed id='soundControl' src='images/Windows.wav' mastersound hidden='true' loop='0' autostart='true'></embed>"+ "</div>" 
        str += "</div>"  
        str += "</td>"  
        str += "</tr>"  
        str += "</table>"  
        str += "</div>"  
	//�����úõ�div ��ӵ����㴰�ڵ�body��  
    oPopup.document.body.innerHTML = str; 
    
  

    var obj  = this;  
	//��Ӷ��㴰�ڵ������ͣ�¼������¼������ô��ڲ�����
    oPopup.document.body.onmouseover = function(){obj.pause=true;}
	//��Ӷ��㴰�ڵ�����Ƴ��¼������¼������ô�������
    oPopup.document.body.onmouseout = function(){obj.pause=false;}

    var fun = function(){  
        var x  = obj.left; 				//��ȡ������߿��λ��
        var y  = 0; 
        var width    = obj.width; 		//��ȡ���ڵĿ��
		//��ȡ���ڵĸ߶�
        var height    = obj.height; 	
            if(obj.offset>obj.height){ 
                height = obj.height; 
            } else { 
                height = obj.offset; 
            } 
 		//��ȡ���ڶ��߿��λ��
        y  = obj.bottom - obj.offset; 
        if(y<=obj.top){ 
            obj.timeout--; 
            if(obj.timeout==0){ 
                window.clearInterval(obj.timer);  
                if(obj.autoHide){
                    obj.hide(); 		//��������
                }
            } 
        } else { 
            obj.offset = obj.offset + obj.step; 
        } 
        obj.Pop.show(x,y,width,height);    //��������
 
    }  
    this.timer = window.setInterval(fun,this.speed)  //���speedʱ�����fun���������    
  	//�������رա���ťʱִ�еĲ���
    oPopup.document.getElementById("btn_Close").onclick = function(){  
        obj.close = true;
        obj.hide();  	//��������
    }  
  	//����������ʱִ�еĲ���
   oPopup.document.getElementById("aLink").onclick = function(){  
        obj.oncommand();  //����oncommand����򿪲鿴��������ϸ��Ϣ�Ĵ���
    }    
}  

//���õ������ڵ���ߡ��ұߡ����ߺ͵ױ߿��λ��
PopBubble.prototype.box = function(left,right,top,bottom){ 
    try { 
        this.left = left !=null?left:this.right-this.width; 
        this.right = right!=null?right:this.left +this.width; 
        this.bottom  = bottom!=null?(bottom>screen.height?screen.height:bottom):screen.height; 
        this.top = top !=null?top:this.bottom - this.height; 
    } catch(e){} 
} 

