<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>运行状态-评分系统</title>
    <link rel="stylesheet" href="css/common.css" />
    <link href="css/systemstate-setting.css" rel="stylesheet" type="text/css" />
    <link href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function()
		{
			$nowEditingId = 0;
			
			$("#contents").corner();

			$("#saveSuccessDialog").dialog({
				modal: true,
				resizable: false,
				autoOpen: false,
				buttons: {
					确定: function() {
						$("#saveSucess").dialog('close');
					}
				}
			});
			$('#warningDialog').dialog({
				autoOpen: false,
				width: 400,
				resizable: false,
				buttons: {
					是: function() {
						$(this).dialog("close");
						$("#contents > div[name='submit']").addClass("hidden");
						$("#contents > div[name='loadingDiv']").removeClass("hidden");
						var $act = "changeSystemStateToNextStep.action";
						$.ajax({
							type: "GET",
							url: $act, 
							dataType: "json",
							success: function(data, textStatus){
								window.location.reload(true);
								/*var $h1 = $("#contents > div[name='state'] > h1");
								if (data.systemState.enabled == 1)
								{
									$("#contents > div[name='submit']").removeClass("hidden");
									$("#contents > div[name='loadingDiv']").addClass("hidden");
								}
								if (data.systemState.state == 2)
								{
									$h1.text("数据收集阶段");
								}
								else if (data.systemState.state == 3)
								{
									$h1.text("得分认定阶段");
								}
								else if (data.systemState.state == 4)
								{
									$h1.text("保留阶段");
								}
								else if (data.systemState.state == 5)
								{
									$h1.text("保留阶段");
								}
								else if (data.systemState.state == 6)
								{
									$h1.text("公示阶段");
									$("#contents > div[name='submit'] > a[name='submit']").replaceWith("<span name='unsubmitable'>系统运行已经结束</span>");
								}
								$("#saveSuccessDialog").open("open");*/
							}, 
							error: function(XMLHttpRequest, textStatus, errorThrown){
								alert("操作不成功");
							}
						});
					},
					否: function() {
						$(this).dialog("close");
					}
				}
			});
			
			$("#contents a[name='submit']").bind("click", function()
			{
				$('#warningDialog').dialog('open');
				return false;
			});

			$("#header .global-nav-master[name='system-setting']").addClass("current");
			$("#header li.global-nav-master.current li[name='systemstate-setting']").addClass("current");
		});
	</script>
</head>

<body>
    <%@ include file="header.jsp" %>
	<div id="wrapper">
        <div id="contents">
        	<s:if test='systemState.systemType == 0'>
	        	<div name="state">
	            	<h1>您正在使用的是助教评定系统<br />系统状态为
	            		<s:if test="systemState.state==1">设定阶段</s:if>
		                <s:if test="systemState.state==2">数据收集阶段</s:if>
		                <s:if test="systemState.state==3">得分认定阶段</s:if>
		                <s:if test="systemState.state==4">保留阶段</s:if>
		                <s:if test="systemState.state==5">保留阶段</s:if>
		                <s:if test="systemState.state==6">公示阶段</s:if></h1>
	            </div>
	            <div name="loadingDiv" class="<s:if test='systemState.enabled == 1'>hidden</s:if>">
	                <div name="word">
	                    <span>更改中，请稍候。</span>
	                </div>
	                <div name="loadingBar">
	                </div>
	            </div>
	            <div name="submit" class="<s:if test='systemState.enabled == 0'>hidden</s:if>">
	                <s:if test="systemState.state < 6">
	                    <a name="submit" href="">进入下阶段</a>
	                </s:if>
	                <s:else>
	                    <span name="unsubmitable">系统运行已经结束</span>
	                </s:else>
	            </div>
	        </s:if>
	        <s:elseif test='systemState.systemType == 1'>
	        	<div name="state">
	            	<h1>您正在使用的是助教评定系统<br />系统状态为运行中</h1>
	            </div>
	        </s:elseif>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
    <div id="warningDialog" title="消息框" class="hidden">
		<span>系统状态修改不能退回，您确定吗？</span>
	</div> 
    <div id="saveSuccessDialog" title="消息框" class="hidden">
		<span>系统状态修改成功</span>
	</div> 
</body>
</html>