<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>邀请二维码</title>
<meta name="viewport" content="width=content-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
<meta name="format-detection" content="telephone=no"/>
<meta name="msapplication-tap-highlight" content="no"/>
<link href="<%=request.getContextPath()%>/css/mereCash/design.css"  rel="stylesheet" type="text/css">
 <script type="text/javascript" src="<%=request.getContextPath()%>/js/mereCash/jquery.qrcode.min.js"></script>
</head>
<body>
<script type="text/javascript">
		(function (doc, win) {
		    var docEl = doc.documentElement,
		      resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
		      recalc = function () {
		        var clientWidth = docEl.clientWidth;
		        if (!clientWidth) return;
		        docEl.style.fontSize = 100 * (clientWidth / 640) + 'px';
		      };
		    if (!doc.addEventListener) return;
		    win.addEventListener(resizeEvt, recalc, false);
		    doc.addEventListener('DOMContentLoaded', recalc, false);
		  })(document, window);

</script>


<div  class="wrapper">
    <div class="wrapper-bg"><img src="<%=request.getContextPath()%>/images/mereCash/invite-bg.png"></div>
    <div class="content">
<%--          <img src="<%=request.getContextPath()%>/images/mereCash/qrcode.png">   --%>
          <div  id ="img"  style="text-align:center"></div> 
        <p>官方客户端：mo9信用钱包</p>
        <p>微信公众号：mo9金融中心</p>
    </div>
</div>
<script type="text/javascript">
jQuery(function(){
	jQuery('#img').qrcode("${qrcodeUrl}");
	
})


</script>
</body>

</html>
