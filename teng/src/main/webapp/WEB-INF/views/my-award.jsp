<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gamax" uri="/gamax.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta charset="utf-8">
	<title>我的奖励</title>
	<cashhome id="cashhome" style="display:none;">true</cashhome>
	<meta name="viewport" content="width=content-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
	<meta name="format-detection" content="telephone=no"/>
	<meta name="msapplication-tap-highlight" content="no"/>
	<link href="<%=request.getContextPath()%>/css/mereCash/design.css"  rel="stylesheet" type="text/css">
	<style type="text/css">
		.txtMarquee-left .bd{ padding:-10px;width:auto;}  
		.txtMarquee-left .bd ul{ overflow:hidden; zoom:1; } 
		.txtMarquee-left .bd ul li{ margin-right:20px;  float:left; height:44px; line-height:24px;  text-align:left; _display:inline; width:auto !important;font-size:12px;  }
	</style>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/mereCash/jquery.SuperSlide.2.1.1.js"></script>
</head>
<body>
    <div class="tip-information">
        <i class="icon-tip mr5"><img src="<%=request.getContextPath()%>/images/mereCash/icon-tip.png"></i>
        <div class="txtMarquee-left">
			<div class="bd">
				<ul>
					<c:forEach items="${marqueeList}" var="marquee">
						<li><span class="mr5"><a href="javascript:void(0)">${marquee}</a></span></li>
					</c:forEach>
				</ul>
			</div>
		</div>
    </div>
    <div class="withdraw-detail">
        <div  class="icon-cash"><img src="<%=request.getContextPath()%>/images/mereCash/icon-cash.png"></div>
        <b>待提现</b>
        <b>¥${balance}</b>
        <a class="gray-btn" style="font-size: medium" id="gray">满3元可提现</a>
        <a class="blue-btn" style="font-size: medium;display:none;" id="light">提现</a>
        <a class="f-blue">奖励明细></a>
        <p>已成功邀请<span>${successInvited}</span>人</p>
        <p>累计赚得现金<span>${taotalCash}</span>元</p>
    </div>
    <p style="position:absolute;width:90%;padding:0 5%;color:#417cd0;text-align:center;font-size:14px;bottom:3%;">关注微信公众号“mo9金融中心”</p>
	 <script type="text/javascript">
    	//跑马灯js代码
		jQuery(".txtMarquee-left").slide({
			mainCell:".bd ul",
			autoPlay:true,
			effect:"leftMarquee",
			vis:1,
			interTime:50
		});
	</script>
	
	<script type="text/javascript">
	$(function(){
		$(".clone").hide();
		
		if(parseInt("${balance}")<3){
			$("#gray").show();
			$("#light").hide();
		}else{
			$("#gray").hide();
			$("#light").show();
		}
		
		$(".blue-btn").bind("click",function(){
			console.info("${balance}");
			var balance = "${balance}";
			if(parseInt(balance)<3){
				alert("用户余额大于3元才可以提现！");
				return false;
			}
			//$.get("withDraw.do");
			window.location.href="withDraw.do";
		});
		
		$(".f-blue").bind("click",function(){
			var mobile = '${mobile}';
			top.location = '<%=request.getContextPath()%>/cashMere/awardDetail.do?mobile=' + mobile + "&pageNo=1";
		});
	});
	 </script>
</body>
</html>
 