<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<link rel="stylesheet" href="css/common.css" />
	<link rel="stylesheet" href="css/usr_in_header.css" /> 
<!-- 	<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
-->	<script type="text/javascript">
		$(function()
		{
			$("#header").corner("top 3px");
			$(".global-nav-master > a").each(function(){
				$(this).corner("top 3px cc:#f0890e");
			});

			$("#stateWrongDialog, #standardSystemNoFunctionDialog").dialog({
				autoOpen: false,
				width: 300,
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
			$("#stateWrongDialog").dialog("open");
		}
		function showStandardSystemNoFunctionDialog()
		{
			$("#standardSystemNoFunctionDialog").dialog('open');
		}

	</script>
<div id="header">
	<ul>
		<li name="system-setting" class="global-nav-master">
			<a href="querySystemState.action">
				<strong>系统设定</strong>
            </a>
			<ul class="global-subnav">
            	<li name="systemstate-setting">
                	<a href="querySystemState.action">
						运行阶段
					</a>
                </li>
				<li name="item-setting">
					<a href="queryCollectionItemForEdit.action">
						项目设定
					</a>
				</li>
				<li name="public-hearings-item-setting">
					<a href="queryPublicHearingsItemForEdit.action">
						互评项目设定
					</a>
				</li>
				<li name="item-check-setting">
					<a href="queryCollectionItemForCheckSet.action">
						认定设定
					</a>
				</li>
                <li name="item-publicity-setting">
                	<a href="queryCollectionItemForPublicitySet.action">
						公示设定
					</a>
                </li>
			</ul>
		</li>
        <li name="system-using" class="global-nav-master">
			<a href="../usr/gotoIndexPage.action">
				<strong>系统使用</strong>
            </a>
		</li>
		<li name="system-moniter" class="global-nav-master">
			<a href="javascript:showStandardSystemNoFunctionDialog();">
				<strong>状态监控</strong>
            </a>
		</li>
		<li name="result-create" class="global-nav-master">
			<a href="
				<s:if test='systemState.systemType == 1 || systemState.state == 6 || systemState.state == 7'>
					generateReport.action
				</s:if><s:else>
					javascript:showStateWrongDialog();
				</s:else>">
				<strong>生成报表</strong>
            </a>
		</li>
    </ul>
</div>
<div class="clear"></div>
<div id="stateWrongDialog" title="消息框" class="hidden">
	<span>系统状态不允许此操作</span>
</div>
<div id="standardSystemNoFunctionDialog" title="消息框" class="hidden">
	<span>此版本不提供此功能，如需要此功能，请联系提供商</span>
</div>
