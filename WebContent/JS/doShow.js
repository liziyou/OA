/**
 * 菜单导航
 */
 function doShow(id){
  
 var did=document.getElementById("ul"+id);   //获得ul的id
 
  if(did.style.display=="none"){
             did.style.display="block";   //设置ul显示
     }else{                      
          did.style.display="none";    //设置ul隐藏
     }
 
    }
