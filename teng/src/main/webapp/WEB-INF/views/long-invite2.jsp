<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>邀请赚现金</title>
<meta name="viewport" content="width=content-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
<meta name="format-detection" content="telephone=no"/>
<meta name="msapplication-tap-highlight" content="no"/>
<meta http-equiv="pragma" content="no-cache" />
<link href="<%=request.getContextPath()%>/css/mereCash/design2.css"  rel="stylesheet" type="text/css">
</head>
<body style="background:#f3f4f4;">
<script type="text/javascript">
	//全局设置字体格式  
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
    <div class="main-content">
        <div class="banner"><img src="<%=request.getContextPath()%>/images/mereCash/banner.png"></div>
        <div class="box-shadow"></div>
        <div class="expecting"><img src="<%=request.getContextPath()%>/images/mereCash/expecting.png">
        	<p>施工中</p>
        	<p>敬请期待!</p>
        </div>
       
    </div>
</body>

</html>
