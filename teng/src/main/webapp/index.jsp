<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="application/x-javascript">
        addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); }
    </script>
    <meta name="keywords" content="Flat Dark Web Login Form Responsive Templates, Iphone Widget Template, Smartphone login forms,Login form, Widget Template, Responsive Templates, a Ipad 404 Templates, Flat Responsive Templates" />
    <link href="css/style.css" rel='stylesheet' type='text/css' />
    <!--webfonts-->
    <link href='css/style.css?family=PT+Sans:400,700,400italic,700italic|Oswald:400,300,700' rel='stylesheet' type='text/css'>
    <link href='css/style.css?family=Exo+2' rel='stylesheet' type='text/css'>
    <!--//webfonts-->
    <script src="js/jquery-1.9.1.min.js"></script>
</head>
<body>
<script>
    (document).ready(function(c) {
         $('.close').on('click', function(c){
              $('.login-form').fadeOut('slow', function(c){
                  $('.login-form').remove();
             });
            });
        });



</script>
<!--SIGN UP-->
<h1>klasikal Login Form</h1>
<div class="login-form">
    <div class="close"> </div>
    <div class="head-info">
        <label class="lbl-1"> </label>
        <label class="lbl-2"> </label>
        <label class="lbl-3"> </label>
    </div>
    <div class="clear"> </div>
    <div class="avtar">
        <img src="images/avtar.png" />
    </div>
    <form>
        <input type="text" class="text" value="Username" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Username';}" >
        <div class="key">
            <input type="password" value="Password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}">
        </div>
    </form>
    <div class="signin">
        <input type="submit" value="登录" >
    </div>
</div>
<div class="copy-rights">
    <p>Copyright &copy; 2015.Company name All rights reserved.More Templates <a href="https://ke.qq.com/course/27346?from=13" target="_blank" title="腾讯课堂">腾讯课堂</a>
        - Collect from <a href="https://ke.qq.com/course/27346?from=13" title="腾讯课堂" target="_blank">腾讯课堂</a></p>
</div>

</body>
</html>