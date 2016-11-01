<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=content-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
<meta name="format-detection" content="telephone=no"/>
<meta name="msapplication-tap-highlight" content="no"/>
<link href="<%=request.getContextPath()%>/css/mereCash/design.css"  rel="stylesheet" type="text/css">
</head>
<body style="background:rgba(0,0,0,5)">
    <div class="poupbox cfix">
        <div class="share-wrap cfix">
            <div class="share-tip  cfix"><img src="<%=request.getContextPath()%>/images/mereCash/bubble.png"></div>
            <div class="share-box">
                <ul class="cfix">
                     <li><a class="share-way" ><img src="<%=request.getContextPath()%>/images/mereCash/share-wx.png"></a><a>微信好友</a></li>
                     <li><a class="share-way" ><img src="<%=request.getContextPath()%>/images/mereCash/share-wxzone.png"></a><a>微信朋友圈</a></li>
                     <li><a class="share-way"   href="<%=request.getContextPath()%>/cashMere/invite.do"><img src="<%=request.getContextPath()%>/images/mereCash/share-ewm.png"></a><a>邀请二维码</a></li>
                     <div style="clear:both;"></div>
                </ul>
                <a class="cancel-btn">取消</a>
            </div>
            <div style="clear:both;"></div>
       </div>
    </div>
    
     <div class="poupbox cfix">
        <div class="withdraw-success">
            <p>提交成功</p>
            <p>金额将于1天内到账，请注意查收</p>
        </div>
    </div>
</body>
</html>
