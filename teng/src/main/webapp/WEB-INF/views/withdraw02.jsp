<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>提现</title>
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

<div class="input-value">
    <span class="lable-rmb">¥</span><input name="value">
</div>
<p class="tip">少于50元收取2元手续费</p>
<ul class="identify-information">
    <li><span class="">持卡人</span><input name="name" type="text" placeholder="请输入持卡人姓名"></li>
    <li><span>身份证</span><span class="identify-card" style="margin-left:20px;">310104199908253374</span></li>
    <li><span>银行卡</span><input name="visa" type="text" placeholder="请输入银行卡号"></li>
    <li><span>手机号</span><input name="tel" type="text" placeholder="请输入银行预留手机号"></li>
</ul>
<a class="blue-btn" style="text-align:center;">确认提现</a>
<div class="reminder">
    <h3>—————————温馨提示—————————</h3>
    <ol>
        <li>仅支持提现至银行储蓄卡；</li>
        <li>请务必输入真实有效的收款信息。</li>
        <li>提现金额需为整数，最小提现金额3元，不满50元需支付手续费2元；</li>
        <li>提现金额将于1天内转入银行账户，如转账失败将返还至待提取金额。</li>
    </ol>
</div>
</body>
</html>
