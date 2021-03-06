<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="author" content="http://xshell.cn"/>
<meta http-equiv="X-UA-Compatible" content="IE=7,IE=9,IE=10" />
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>

<c:if test="${site.theme eq 'basic'}">
	<link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
	<!--[if lte IE 6]><link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
	<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStaticTheme}/style.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStaticTheme}/script.js" type="text/javascript"></script>
</c:if>

<c:if test="${site.theme eq 'green'}">
	<!-- style start -->
	<link type="text/css" rel="stylesheet" href="${ctxStaticTheme}/css/base.css">
	<link type="text/css" rel="stylesheet" href="${ctxStaticTheme}/css/page.css">
	<!-- style end -->

	<!--javascript start-->
	<script type="text/javascript" src="${ctxStaticTheme}/js/libs/jquery.min.js"></script>
	<script type="text/javascript" src="${ctxStaticTheme}/js/libs/jquery.SuperSlide.2.1.js"></script>
	<script type="text/javascript" src="${ctxStaticTheme}/js/site.js"></script>
	<!--javascript end-->
</c:if>

<c:if test="${site.theme eq 'zlx'}">
	<!-- style start -->
	<link type="text/css" rel="stylesheet" href="${ctxStaticTheme}/css/base.css">
	<link type="text/css" rel="stylesheet" href="${ctxStaticTheme}/css/page.css">
	<!-- style end -->

	<!--javascript start-->
	<script type="text/javascript" src="${ctxStaticTheme}/js/libs/jquery.min.js"></script>
	<script type="text/javascript" src="${ctxStaticTheme}/js/libs/jquery.bxslider.min.js"></script>
	<script type="text/javascript" src="${ctxStaticTheme}/js/libs/perspective-slider.js"></script>
	<script type="text/javascript" src="${ctxStaticTheme}/js/site.js"></script>
	<!--javascript end-->

</c:if>

<c:if test="${site.theme eq 'zxf'}">
	<!-- style start -->
	<link type="text/css" rel="stylesheet" href="${ctxStaticTheme}/css/base.css">
	<link type="text/css" rel="stylesheet" href="${ctxStaticTheme}/css/page.css">
	<!-- style end -->

	<!--javascript start-->
	<script type="text/javascript" src="${ctxStaticTheme}/js/libs/jquery.min.js"></script>
	<script type="text/javascript" src="${ctxStaticTheme}/js/libs/jquery.bxslider.min.js"></script>
	<script type="text/javascript" src="${ctxStaticTheme}/js/libs/perspective-slider.js"></script>
	<script type="text/javascript" src="${ctxStaticTheme}/js/site.js"></script>
	<!--javascript end-->

</c:if>