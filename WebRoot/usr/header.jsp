<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="css/common.css" />
	<link rel="stylesheet" href="css/usr_header.css" />
	<script type="text/javascript">
		$(function()
		{
			$(".global-subnav > li > a").corner("top 3px cc:#4cb9fc");

			$("#stateWrongDialog").dialog({
				autoOpen: false,
				width: 400,
				resizable: false,
				buttons: {
					好的: function() {
						$(this).dialog("close");
					}
				}
			});
		});

		function showStateWrongDialog()
		{
			$('#stateWrongDialog').dialog('open');
		}
		
	</script>
<div id="header">
	<div name="header-top">
    </div>
	<div name="header-center">
		<ul class="global-subnav">
			<li name="homePage">
				<a href="queryPublish.action">
					首页
				</a>
			</li>
			<li name="item-fill">
				<a href="
					<s:if test='systemState.systemType == 1 || systemState.state == 2'>
						queryCollectionItemForFilling.action
					</s:if><s:else>
						javascript:showStateWrongDialog();
					</s:else>">
						提交信息
				</a>
			</li>
			<!-- 
			<li name="public-hearings-fill">
				<a href="
					<s:if test='systemState.systemType == 1 || (systemState.state == 2 || systemState.state == 3)'>
						queryPublicHearingsItemForFill.action
					</s:if><s:else>
						javascript:showStateWrongDialog();
					</s:else>">
						参与互评
				</a>
			</li>
			 -->
            <li name="thought">
				<a href="queryThought.action">
						感想和感想
				</a>
            </li>
			<li name="item-check">
				<a href="
					<s:if test='systemState.systemType == 1 || systemState.state == 3'>
						queryCollectionItemForCheckByCurrentUser.action
					</s:if><s:else>
						javascript:showStateWrongDialog();
					</s:else>">
						成绩认定
				</a>
            </li>
            <li name="item-publicity">
				<a href="
					<s:if test='systemState.systemType == 1 || systemState.state == 6'>
						queryItemWhichShallBePublicized.action
					</s:if><s:else>
						javascript:showStateWrongDialog();
					</s:else>">
						公示与成绩
				</a>
            </li>
            <!-- 
            <li name="public-hearings-text">
				<a href="
					<s:if test='systemState.systemType == 1 || systemState.state >= 6'>
						queryItemWhichShallBePublicizedText.action
					</s:if><s:else>
						javascript:showStateWrongDialog();
					</s:else>">
						收到的建议
				</a>
            </li>
             -->
            <li name="message">
				<a href="message.jsp">
						站内信
				</a>
            </li>
            <li>
				<a href="../usr_in/gotoIndexPage.action">
						管理员系统
				</a>
            </li>
            <li>
				<a href="../usr/gotoChangePasswordPage.action">
						密码管理
				</a>
            </li>
		</ul>  
	</div>
</div> 
<div class="clear">
</div>
<div id="header-margin">
</div> 
<div id="stateWrongDialog" title="消息框" class="hidden">
	<span>系统状态不允许此操作，详细信息请联系管理员</span>
</div>
