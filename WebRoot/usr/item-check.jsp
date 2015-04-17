<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>评分认定-评分系统</title>
    <link rel="stylesheet" href="css/common.css" />
    <link rel="stylesheet" href="css/item-optional-frame.css" />
    <link href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-form/jquery.form.js" type="text/javascript" ></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
	<script src="../plugins/jquery-syntax/jquery.syntax.js" type="text/javascript"></script>
	<script src="../plugins/jquery-syntax/jquery.syntax.cache.js" type="text/javascript"></script>
    <script src="js/list-move.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function()
		{
			$("#itemList li").bind("click", itemChoose);
			$("#itemListDiv div[name='indexDiv']").bind("click", itemChoose);
			
			$("#itemDiv").height(340);
			
			$("#itemListDiv div[name='indexDiv']").addClass("current");
			$("#header .global-subnav li[name='item-check']").addClass("current");
			$("#itemListDiv, #itemListDiv > div[name='list']").corner("left");
			$("#contents").corner("right");

			$("#codeDialog").dialog({
				autoOpen: false,
				width: 850,
				resizable: false,
				buttons: {
					"关闭": function() {
						$(this).dialog("close");
					}
				}
			});
		});
		
		function itemChoose()
		{
			$("#itemList li").removeClass("current");
			$("#itemListDiv div[name='indexDiv']").removeClass("current");
			var value = $(this).attr("value");
			if (value > 0)
			{
				$("#itemDiv").attr("src",  "queryUserOutWithItemInfoForCheckOneByItemId.action?itemId=" + value);
				$("#itemDiv").addClass("hidden");
				$("#markDiv").removeClass("hidden");
				$("#markDiv > div[name='loadingDiv']").removeClass("hidden");
			}
			else
			{
				$("#itemDiv").attr("src", "");
				$("#itemDiv").addClass("hidden");
				$("#markDiv").removeClass("hidden");
			}
			$(this).addClass("current");
		}

		function showCode(version, path, itemId, userId)
		{
			var $url;
			if (version == "")
				$url = "versionsProvider.action";
			else
				$url = "directoryProvider.action";
			$.ajax({
				type: "GET",
				url: $url,
				dataType: "html",
				data: {version: version, path: path, itemId: itemId, userId: userId},
				success: function(data)
				{
					$("#codeDialog").html(data);
					$("#codeDialog").dialog("open");
				},
				error: function()
				{
				}
			});
		}
		
		function showContent(version, path, name, itemId, userId)
		{
			var $url;
			$url = "fileProvider.action",
			$.ajax({
				type: "GET",
				url: $url,
				dataType: "html",
				data: {version: version, name: name, path: path, itemId: itemId, userId: userId},
				success: function(data)
				{
					$("#codeDialog").html(data);
					$("#codeDialog").dialog("open");
				}
			});
		}
		
		function subFrameLoaded()
		{
			$("#markDiv").addClass("hidden");
			$("#markDiv > div[name='loadingDiv']").addClass("hidden");
			$("#itemDiv").removeClass("hidden");
			var $subDocument = document.getElementById('itemDiv').contentWindow;
			var $height = $subDocument.getTotalHeight();
			if ($height < 280)
			{
				$height = 280;
			}
			
			$("#itemDiv").height($height + 20);
		}
	</script>
</head>

<body>
    <%@ include file="header.jsp" %>
    <div id="wrapper">
        <div id="itemListDiv">
            <div name="list">
            	<div name="indexDiv">
                	<span>初始页面</span>
                </div>
                <div name="limitDiv">
                    <ol id="itemList">
                        <s:iterator value="collectionItemList" id="items">
                            <li value=<s:property value="id" />> 
                                <s:property value="itemId" />&nbsp;:&nbsp;<s:property value="name" />
                            </li>
                        </s:iterator>
                    </ol>
                </div>
                <div name="control">
                    <a name="up" href="javascript:listUp();">up</a>
                    <a name="down" href="javascript:listDown();">down</a>
                </div>
            </div>
        </div>
        <div id="contents">
            <div id="markDiv">
            	<div name="loadingDiv" class="hidden">
                	<div name="word">
                    	<span>加载中，请稍候。</span>
                    </div>
                    <div name="loadingBar">
                    </div>
                </div>
            </div>
            <iframe id="itemDiv" src="">
                
            </iframe>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
    <div id="saveSuccessDialog" title="消息框" class="hidden">
		<span>保存成功！</span>
	</div>
	<div id="deleteSuccessDialog" title="消息框" class="hidden">
		<span>删除成功！</span>
	</div> 
    <div id="codeDialog" title="查看代码" class="hidden">
	</div>
</body>
</html>