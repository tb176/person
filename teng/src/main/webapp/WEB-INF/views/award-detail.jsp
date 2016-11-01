<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="gamax" uri="/gamax.tld" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>奖励明细</title>
<meta name="viewport" content="width=content-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
<meta name="format-detection" content="telephone=no"/>
<meta name="msapplication-tap-highlight" content="no"/>
<link href="<%=request.getContextPath()%>/css/mereCash/design.css"  rel="stylesheet" type="text/css">
<body>

 <script type="text/javascript">
 $(function(){
	 
	 if('false' == '${showTag}'){
		 $("#showTag").hide();
	 }
 });
 
 function loadMore(){
	var pageNo = parseInt($("#pageNo").val()) + parseInt(1);
	$("#pageNo").val(pageNo);
	
    var mobile = $("#mobile").val();
    var pageNo = $("#pageNo").val();
	$.ajax({
		type:'GET',
		async:false,
		url:'<%=request.getContextPath()%>/cashMere/awardDetail.do?mobile='+mobile+'&pageNo='+pageNo+'&fromAjax=true',
		contentType : "application/json",
		dataType:"text",
		success:function(data, textStatus, request){
			if(data != ""){
				$(data).appendTo($("#area"));
			}
			if(request != ""){
				if("false" == request.getResponseHeader('showTag')){
					$("#showTag").hide();
				}
			}
			$('body,html').animate({scrollTop:$('#area')[0].scrollHeight},500); 
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("error:"+errorThrown);
		}
	});
	
 }
 
</script>
    <div id="area" style="height:auto;overflow:auto;">
    
    	<c:if test="${empty dealList}">
  			<ul class="award-detail cfix">
  				<li><span style="color:white">·</span></li>
	  			<li>
	    			<span style="font-size:16px;text-align:center;">您暂时还没有奖励记录~</span>
	    		</li>
	    		<li><span style="color:white">·</span></li>
	    		<div style="clear:both;"></div>
    		</ul>
    		
    	</c:if>
    	
	    <c:forEach items="${dealList}" var="deal">
		    <ul class="award-detail cfix">
		        <li>
		        	<span>
		        		<fmt:formatDate value="${deal.updateTime}" pattern="MM-dd"/>
		        		</br>
		        		<fmt:formatDate value="${deal.updateTime}" pattern="HH:mm"/>
		        	</span>
		        </li>
		        <li><span>${deal.targetMsg}</span></br>${deal.dealMsg}</li>
		        <li><span>
					<c:choose>
						<c:when test="${deal.amount gt '0'}">
							+${deal.amount}
						</c:when>
						<c:when test="${deal.amount le '0'}">
							${deal.amount}
						</c:when>
					</c:choose> 
		        </span></li>
		        <div style="clear:both;"></div>
	    	</ul>
	    </c:forEach>
    </div>
    
    <div id="showTag" name="showTag" style="font-size:16px;text-align:center;"><a href="#" onclick="loadMore();" >加载更多∨</a></div>
    
    <input type="hidden" id="pageNo" name="pageNo" value="1"/>
    <input type="hidden" id="mobile" name="mobile" value="${mobile}"/>
</body>

</html>
