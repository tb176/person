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

<script type="text/javascript">
var isSubmit = true;	//防止表单重复提交
function getValidate(){

	
	var targetmobile = $('#number').val();//被邀请人手机号
		if (targetmobile == "" || targetmobile.length == 0) {
			alert("请输入手机号码~");
			return false;
		}

		if (targetmobile.length != 11) {
			alert('请输入有效的手机号码！');
			return false;
		}

		//var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//以13、15、18开头的手机号码 后面补上0-9的一位数字+0-9的8位数字
		//var myreg = /^1(3|4|5|7|8)\d{9}$/;//表示以1开头，第二位可能是3/4/5/7/8等的任意一个，在加上后面的\d表示数字[0-9]的9位，总共加起来11位结束。
		var myreg = /^\d{11}$/;// 手机号码
		if (myreg.test(targetmobile)) {
			if(isSubmit){
				isSubmit =  false;
				$("#getBenefit").submit();//表单提交
			}
			
		}else{
		   alert("请输入正确的手机号码~");
	}
	
	
}


</script>

<div class="wrap-benefit">
    <div class="benefit-bg"><img src="<%=request.getContextPath()%>/images/mereCash/benefit-bg.png"></div>
    <div class="benefit-content">
        <div class="benefit-coupon"><img src="<%=request.getContextPath()%>/images/mereCash/benefit.png"><b class="benefit-number">10</b></div>
        <form class="post-data cfix" action="getBenefit.do" method ="get" id = "getBenefit">
            <input class="tel"  name="targetmobile" id="number" placeholder="请输入您的手机号码" >
            <input type = "hidden" name = "token" value = "${token}"/>
            <a  class="collect-btn" onclick="getValidate();">点击领取</a>
        </form>
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
