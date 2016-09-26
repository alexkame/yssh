<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>索引管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function view(href){
			top.$.jBox.open('iframe:'+href,'查看文档',$(top.document).width()-220,$(top.document).height()-180,{
				buttons:{"关闭":true},
				loaded:function(h){
					//$(".jbox-content", top.document).css("overflow-y","hidden");
					//$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
				}
			});
			return false;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a>索引管理</a></li>
	</ul><br/>
	<div>
		<span style="margin-left: 20px;"><input type="button" id="createIndexButton" value="生成文章索引"></span>
	</div>


	<script type="text/javascript">
		jQuery.ajaxSetup({cache:false});
		var searchIndex = (function($){
			'use strict';
			var _eventBind = function(){
				$('#createIndexButton').on('click',function () {
					var $this = $('#createIndexButton');
					$this.prop("disabled","disabled");
					$this.val("创建中，请稍等。。。");
					$.ajax({
						url:'${ctx}/cms/search/createAticleIndex',
					}).done(function (result) {
						$this.val("生成文章索引");
						$this.removeProp("disabled");
					});
				});
			};
			var init=function(){
				_eventBind();
			};
			var module = {};
			module.init = init;
			return module;

		})(jQuery);

		searchIndex.init();
	</script>
</body>

</html>
