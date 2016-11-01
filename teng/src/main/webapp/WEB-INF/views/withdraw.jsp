<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gamax" uri="/gamax.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta charset="utf-8">
<title>提现</title>
<meta name="viewport" content="width=content-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
<meta name="format-detection" content="telephone=no"/>
<meta name="msapplication-tap-highlight" content="no"/>
<link href="<%=request.getContextPath()%>/css/mereCash/design.css"  rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/pop-mask.js?tmp=1111"></script>

</head>
<body>
<form action  = "drawCase.do" method = "post" id = "drawCashFrm">
	<input type = "hidden" name = "token" value = "${token}" /> 
	<div class="input-value">
	    <span class="lable-rmb">¥</span>
	    <input name="amount" type="number" id = "amount" value = "" style="width: 60%;"  />
	    <input type = "hidden" id = "balance" value = "${user.balance}" />
	</div>
	<p class="wrong-message" id = "Amount_error" style="color: #77a8ef;">少于50元收取2元手续费</p>   		    
	<ul class="identify-information">
	    <li>
	    	<span class="">持卡人</span>
	    	<input name="name" id = "name" type="text" style="width: 70%;" maxlength = "20" placeholder="请输入持卡人姓名" <c:if test = "${not empty  bankCardInfo.cardUserName}"> value = "${bankCardInfo.cardUserName}" </c:if> />
	    </li>
	    <li>
	    	<span>身份证</span>
	    	<input class="identify-card" name = "idCardNo" style="width: 70%;" maxlength = "18" id = "idCardNo" style="margin-left:20px;" <c:if test = "${not empty  bankCardInfo.idCardNum}"> value = "${bankCardInfo.idCardNum }" </c:if> placeholder="请输入身份证号" />
	    </li>
	    <li>
	    	<span>银行卡</span>
	    	<input name="bankCardNo" id = "bankCardNo" style="width: 70%;" maxlength = "19" type="text" placeholder="请输入银行卡号" <c:if test = "${not empty  bankCardInfo.cardNum}"> value = "${bankCardInfo.cardNum }" </c:if> />
	    </li>
	    <li>
	    	<span>手机号</span>
	    	<input name="mobile" id = "mobile" type="text" style="width: 70%;" maxlength = "11" placeholder="请输入银行预留手机号"  value = "${user.mobile }" readonly="readonly"/>
	    </li>
	</ul>
	<p class="wrong-message" id = "info_error"></p>	
	<ul class="identify-message">
    	<li>
    		<input name="pinCode" id = "pinCode" type="text" maxlength = "4" placeholder="请输入4位短信验证码">
    		<span class="get-btn" onclick = "getPinCode()" id = "getPinCode">获取</span>
    	</li>
    	<li>
    		<input name="validateCode" id = "validateCode" type="text" maxlength = "4" placeholder="请输入右边图形验证码">
    		<span style="color:#255596;margin-left:5px;" class="fr" onClick = "changeImg()">换一张</span>
    		<span class="check-num fr"><img id= "imgObj"  alt="验证码" src="code.do" src="code.do"></span>
    	</li>
	</ul>
	<input type = "hidden" name = "sign" id = "sign" />
	<p class="wrong-message" id = "sms_error"></p>
	<a class="blue-btn"  style="text-align:center;margin-top:10%;font-size:18px;">确认提现</a> 
</form>
<div class="reminder rule-border" style="border: none;">
    <h3><span class="rule-title">温馨提示</span></h3>
    <ol>
        <li>仅支持提现至银行储蓄卡；</li>
        <li>请务必输入真实有效的收款信息。</li>
        <li>提现金额需为整数，最小提现金额3元，不满50元需支付手续费2元；</li>
        <li>提现金额将于1天内转入银行账户，如转账失败将返还至待提取金额。</li>
    </ol>
</div>
<div class="poupbox cfix" id= "award-div" style="display: none;z-index: 2050;position: fixed;">
    <div class="withdraw-success" id = "tips">
        <p id ="title">提交成功</p>
        <p id ="msg">金额将于1天内到账，请注意查收</p>
    </div>
</div>
<script type="text/javascript">
	var t_Num = /^[0-9]*[1-9][0-9]*$/;
	var t_Name =/^([a-zA-Z0-9\u4e00-\u9fa5\·]{1,10})$/;
	var t_idCard =/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
	var t_Card = /^(\d{16}|\d{19})$/;
	var t_mobile = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
	var t_PinCode = /^\d{4}$/;	
	var t_validate = /^[0-9A-Za-z]{4}$/;
	var countdown = 60; //60s计数器
	var isSendPinCode = true;
	
	$("#amount").change(function(){
        if ($(this).val().match(new RegExp(t_Num)) == null || $(this).val()<3) {
            $("#Amount_error").removeAttr("style").text("最小提现金额3元");
            return;
        }

        if($("#balance").val()<$(this).val()*100){
        	var msgInfo = "最大提现金额"+$("#balance").val()/100+"元";
        	$("#Amount_error").removeAttr("style").text(msgInfo);
        	return;
        }
        
        $("#Amount_error").removeAttr("style").attr("style","color: #77a8ef;").text("少于50元收取2元手续费");
    	
	});

	$("#name").change(function(){
        if ($(this).val().match(new RegExp(t_Name)) == null) {
            $("#info_error").text("请输入姓名");
            return;
        }
        $("#info_error").text("");
	});
	
	$("#idCardNo").change(function(){
        if ($(this).val().match(new RegExp(t_idCard)) == null) {
            $("#info_error").text("请输入正确格式的身份证号");
            return;
        }
        $("#info_error").text("");
	});
	
	$("#bankCardNo").change(function(){
        if ($(this).val().match(new RegExp(t_Card)) == null) {
            $("#info_error").text("请输入正确格式的银行卡号");
            return;
        }
        $("#info_error").text("");
	});
	
	$("#mobile").change(function(){
        if ($(this).val().match(new RegExp(t_mobile)) == null) {
            $("#info_error").text("请输入正确格式的手机号");
            return;
        }
        $("#info_error").text("");
	});
	
	function getPinCode(){
		if(isSendPinCode){
			if($("#validateCode").val() == ""){
				$("#sms_error").text("请输入图形验证码");
				return;
			}
			$("#sms_error").text("");
			$.post('sendPinCode.do',{validateCode:$("#validateCode").val()}, 
					function (data) {
					if(data.status == "success"){
						setTime(); 
					}else{
						$("#sms_error").text(data.description);
					}
					changeImg();
					$("#validateCode").val("");
			});			
		}
	}

    function changeImg() {
	    var imgSrc = $("#imgObj");
	    var src = imgSrc.attr("src");
	    imgSrc.attr("src", chgUrl(src));
	 };
	  
	 //时间戳   
	 //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳   
	 function chgUrl(url) {
	    var timestamp = (new Date()).valueOf();
	    url = url.substring(0, 17);
	    if ((url.indexOf("&") >= 0)) {
	      url = url + "×tamp=" + timestamp;
	    } else {
	      url = url + "?timestamp=" + timestamp;
	    }
	    return url;
	  };
	  
	  //获取验证码读秒
	  function setTime() {
	  	countdown--; 
	  	$("#getPinCode").removeClass("get-btn").addClass("no-get-btn").text(countdown+"s");
	  	isSendPinCode = false;
	  	if (countdown == 0) { 
	  		countdown = 60;
	  		$("#getPinCode").removeClass("no-get-btn").addClass("get-btn").text("获取");
	  		isSendPinCode = true;
	  		return;
	  	}
	  	setTimeout(function() { 
	  		setTime(); 
	  		},1000) ; 	
	 };
	  
	 $(".blue-btn").bind("click",function(){
	   
	   	$("#amount").blur();
        if ($("#amount").val().match(new RegExp(t_Num)) == null || $("#amount").val()<3) {
           	 $("#Amount_error").removeAttr("style").text("最小提现金额3元");
           	 $(".gray-btn").attr("class","blue-btn").text("确认提现");
           	 return;
      	}
       
        $("#amount").blur();
        if($("#balance").val()<$("#amount").val()*100){
       		var msgInfo = "最大提现金额"+$("#balance").val()/100+"元";
       		$("#Amount_error").removeAttr("style").text(msgInfo);
       		$(".gray-btn").attr("class","blue-btn").text("确认提现");
       		return;
        }
       
        $("#Amount_error").removeAttr("style").attr("style","color: #77a8ef;").text("少于50元收取2元手续费");
        
        $("#name").blur();
        if ($("#name").val().match(new RegExp(t_Name)) == null) {
            $("#info_error").text("请输入姓名");
            $(".gray-btn").attr("class","blue-btn").text("确认提现");
            return;
        }
        
        $("#idCardNo").blur();
        if ($("#idCardNo").val().match(new RegExp(t_idCard)) == null) {
            $("#info_error").text("请输入正确格式的身份证号");
            $(".gray-btn").attr("class","blue-btn").text("确认提现");
            return;
        }
    	
        $("#bankCardNo").blur();
        if ($("#bankCardNo").val().match(new RegExp(t_Card)) == null) {
            $("#info_error").text("请输入正确格式的银行卡号");
            $(".gray-btn").attr("class","blue-btn").text("确认提现");
            return;
        }
        
        $("#mobile").blur();
        if ($("#mobile").val().match(new RegExp(t_mobile)) == null) {
            $("#info_error").text("请输入正确格式的手机号");
            $(".gray-btn").attr("class","blue-btn").text("确认提现");
            return;
        }
        
        $("#pinCode").blur();
        if($("#pinCode").val().match(new RegExp(t_PinCode)) == null){
        	$("#sms_error").text("请输入正确格式的短信验证码");
        	return;
        }
        $("#sms_error").text("");
        
        $("#validateCode").blur();
        if($("#validateCode").val().match(new RegExp(t_validate)) == null){
        	$("#sms_error").text("请输入正确格式的图片验证码");
        	return;
        }
        $("#sms_error").text("");
        
    	$(".blue-btn").attr("class","gray-btn").text("提现中，请等待···");
    	var param = $("#amount").val()+$("#bankCardNo").val()+$("#idCardNo").val()+$("#mobile").val()+$("#pinCode").val();
        var sign = calcMD5($.trim(param));
        console.log(param);
       	console.log(sign);
        $("#sign").val(sign);
        $.ajax({
            type: "POST",
            url: $("#drawCashFrm").attr("action"),
            data: $("#drawCashFrm").serialize(),
            dataType: "json",
            success: function(data){
            	
            	//alert(data.description);
            	if(data.status == "success"){
            	    var info = "<p id ='title'>提交成功</p><p id ='msg'>"+data.description+"</p>";
            	    $("#tips").html("");
            	    $("#tips").html(info);
            		showWind("award-div");
            		setTimeout(function(){  location.href = "myAward.do";  },3000);
            	}else{
            		//$(".gray-btn").attr("class","blue-btn").text("确认提现");
            		var info = "<p id ='msg'>"+data.description+"</p>";
            		$("#tips").html("");
            	    $("#tips").html(info);
            		showWind("award-div");
            		setTimeout(function(){ location.reload(); },3000);
            		
            	}
            	
            }, 
           	error: function(e) { 
           		//alert("请求异常,请重新尝试"); 
           		var info = "<p id ='msg'>请求异常,请重新尝试</p>";
           		$("#tips").html("");
            	$("#tips").html(info);
            	showWind("award-div");
            	setTimeout(function(){ location.reload(); },3000);
            } 
        });
	});
	 
</script>
</body>
</html>
