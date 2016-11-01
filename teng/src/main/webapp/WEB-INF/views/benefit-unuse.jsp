<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>新人领券</title>
<meta name="viewport" content="width=content-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
<meta name="format-detection" content="telephone=no"/>
<meta name="msapplication-tap-highlight" content="no"/>
<link href="<%=request.getContextPath()%>/css/mereCash/design.css"  rel="stylesheet" type="text/css">
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

<div class="wrap-benefit">
    <div class="benefit-bg"><img src="<%=request.getContextPath()%>/images/mereCash/benefit-bg.png"></div>
    <div class="benefit-content" style="top:37%;">
        <!--<div class="benefit-coupon"><img src="images/benefit.png"><b class="benefit-number">10</b></div>-->
        <p style="position:relative;width:75%;color:#fff;margin:20px auto;font-size:0.35em;text-align:center;"><span style="color:#f9dc28;">你已经领不了新人抵用券~</span></p>
        <a class="collect-btn" style="width:75%;    color: #fff;" href="http://a.app.qq.com/o/simple.jsp?pkgname=com.mo9.app.view" >赚现金</a>
        <p class="download-tip">登录"mo9信用钱包"客户端参与赚现金</p>
        <div class="activity-rule">
            <h3>-活动规则-</h3>
            <p>1. 可在mo9信用钱包江湖救急申请借款时使用；</p>
            <p>2. 领取手机号必须与申请借款手机号一致；</p>
            <p>3. 使用时不与其他优惠同享；</p>
            <p>4. 本活动最终解释权归mo9信用钱包所有。</p>
        </div>
    </div>
</div>
</body>
</html>
