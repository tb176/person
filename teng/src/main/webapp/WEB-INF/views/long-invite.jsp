<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gamax" uri="/gamax.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>邀请赚现金</title>
<meta name="viewport" content="width=content-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
<meta name="format-detection" content="telephone=no"/>
<meta name="msapplication-tap-highlight" content="no"/>
<link href="<%=request.getContextPath()%>/css/mereCash/design.css"  rel="stylesheet" type="text/css">

    <style type="text/css">
    
         .bgNew{ display: block;  position: absolute;  background-color: rgba(0,0,0,.5);  z-index:200;  -moz-opacity: 0.7;  opacity:.70;  filter: alpha(opacity=70);} 
         .showNew{display: block; z-index:999;}  
    
		.txtMarquee-left .bd{ padding:-10px;width:auto;}  
		.txtMarquee-left .bd ul{ overflow:hidden; zoom:1; } 
		.txtMarquee-left .bd ul li{ margin-right:20px;  float:left; height:44px; line-height:24px;  text-align:left; _display:inline; width:auto !important;font-size:12px;  }
	</style>
	 <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.9.0.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/mereCash/jquery.SuperSlide.2.1.1.js"></script>

</head>
<body style="background:#f3f4f4;" id="body">
 
<!-- 分享隐藏页面  start-->
 <%--  <div class="poupbox cfix" style="display:none" id="show">
        <div class="share-wrap cfix">
            <div class="share-tip  cfix"><img src="<%=request.getContextPath()%>/images/mereCash/bubble.png"></div>
            <div class="share-box">
                <ul class="cfix">
                     <li><a class="share-way"><img src="<%=request.getContextPath()%>/images/mereCash/share-wx.png"></a><a>微信好友</a></li>
                     <li><a class="share-way"><img src="<%=request.getContextPath()%>/images/mereCash/share-wxzone.png"></a><a>微信朋友圈</a></li>
                     <li><a class="share-way"><img src="<%=request.getContextPath()%>/images/mereCash/share-ewm.png"></a><a>邀请二维码</a></li>
                     <div style="clear:both;"></div>
                </ul>
                <a class="cancel-btn">取消</a>
            </div>
            <div style="clear:both;"></div>
       </div>
    </div> --%>
<!-- end -->
    <div class="main-content" id="bg">
        <div class="banner"><img src="<%=request.getContextPath()%>/images/mereCash/banner.png"></div>
        <div class="box-shadow"></div>
        <div class="invite-detail cfix">
            <div class="blue-bg">
                <img src="<%=request.getContextPath()%>/images/mereCash/labelbg.png">
                <div class="information">
                <!-- 跑马灯代码  start-->
					 <div class="txtMarquee-left">
						<div class="bd">
							<ul>
								<c:forEach items="${marqueeList}" var="marquee">
									<li><span class="mr5"><a href="javascript:void(0)">${marquee}</a></span></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				<!-- 跑马灯代码  end-->	
				</div>
            </div>
            <div class="view-detail"><span class="fl" style="font-size:0.25em;">现金<b style="margin:0 10px;font-size:18px;font-weight:normal;">¥${balance}</b></span><a class="view-button fr" style="color:#77a8ef" href="<%=request.getContextPath()%>/cashMere/myAward.do">查看详情</a></div>
            <div style="clear:both;"></div>
        </div>
        <a class="bbtn" href="${url}">立即赚钱</a>
        
    
    
        <div class="award-rule rule-border" >
            <h3><span class="rule-title">奖励规则</span></h3>
            <ul>
                <li>邀请一位好友并成功注册获得<div class="coupon fr"><img src="<%=request.getContextPath()%>/images/mereCash/coupon-bg.png"><b class="coupon-number" style=" font-weight:bold">1</b></div></li>
                <li>被邀请好友首次借款成功获得<div class="coupon fr"><img src="<%=request.getContextPath()%>/images/mereCash/coupon-bg.png"><b class="coupon-number" style=" font-weight:bold">10</b></div></li>
                <li>被邀请好友首次还款成功获得<div class="coupon fr"><img src="<%=request.getContextPath()%>/images/mereCash/coupon-bg.png"><b class="coupon-number"  style=" font-weight:bold">10</b></div></li>
                <li style="text-indent:2em;">
	                <span class="label-new"><img src="<%=request.getContextPath()%>/images/mereCash/new.png"></span>成功注册的新好友可获得
	                <div class="coupon fr">
	                	<img src="<%=request.getContextPath()%>/images/mereCash/rcoupon.png">
	                	<b class="coupon-number"  style=" font-weight:bold">10</b>
	                </div>
                </li>
            </ul>
         </div>
         <div class="invite-rule rule-border">  
            <h3><span class="rule-title">邀请规则</span></h3>
            <ul>
                 <li>1. 本次活动仅针对2016年9月30日0:00之后建立邀请关系的用户；</li>
                 <li>2. 通过点击邀请链接或扫描邀请二维码输入手机号建立邀请关系；</li>
                 <li>3. 下载App V7.4.0 或以上版本注册成功的新用户为有效邀请好友；</li>
                 <li>4. 同一设备首个注册用户视为有效邀请好友；</li>
                 <li>5. 活动暂无结束时间，如须停止将提前3天以上通知。</li>
            </ul>
         </div> 
          <p style="color:#417cd0;text-align:center;padding:10px 0 25px 0;font-size:0.25em;">本活动最终解释权归mo9信用钱包所有</p>
        </div>
      
      

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
 <script  type="text/javascript">
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
	<!--  //-----------------遮罩层代码不用了
	   //遮罩层代码
	       /*  function showdiv() {
	        	$("#bg").addClass("bgNew");
	        	//$("#show").addClass("showNew");
	              document.getElementById("show").style.zIndex = "999";
	              document.getElementById("show").style.display ="block";
	        }
	        function hidediv() {
	           $("#bg").removeClass("bgNew");
	            document.getElementById("show").style.display ='none';
	        }  */ -->
	        
 
 
    
</body>
</html>
