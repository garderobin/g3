<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>认定设定-评分系统</title>
    <link rel="stylesheet" href="css/common.css" />
    <link rel="stylesheet" href="css/item-setting.css" />
    <link href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/list-move.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function()
		{
			$("#itemList li").bind("click", itemChoose);
			$("#itemListDiv div[name='indexDiv']").bind("click", itemChoose);
			
			$("#saveSuccessDialog").dialog({
				autoOpen: false,
				width: 300,
				resizable: false,
				buttons: {
					好的: function() {
						$(this).dialog("close");
					}
				}
			});
			
			$.ajax({
				type:"GET", 
				dataType:"json",
				url:"queryAllUser.action",
				success: function(data, textStatus)
				{
					users = data.users;
					var $allUsersDiv = $("#itemDiv div[name='allUsers']");
					$allUsersDiv.children().remove();
					$("#itemDiv div[name='checkUsers']").children().remove();
					$.each(data.users, function()
					{
						var $element = $("<div class='users' name='" + this.id + "'><a href=''>" + this.username + " " + this.name + "</a></div>");
						$element.bind("click", moveUserToCheck);
						$allUsersDiv.append($element);
					});
				},
				error: function(XMLHttpRequest, textStatus, errorThrown)
				{
					alert("用户列表加载失败");
				}

				
			});

			$("#itemForm").submit(function()
			{
				var $act = "createCollectionItemCheck.action?id=" + $("#itemDiv input[name='id']").attr("value");
				$("#itemDiv div[name='checkUsers']").children().each(function()
				{
					$act = $act + "&checkOperatorId=" + $(this).attr("name");
				});
				$.ajax({
					type: "GET",
					url: $act, 
					dataType: "json",
					success: function(data, textStatus){
						$("#saveSuccessDialog").dialog("open");
						setCollectionItem(data);
					}, 
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert("操作不成功");
					}
				});
				return false;
			});
			
			$nowEditingId = 0;
			$("#itemListDiv div[name='indexDiv']").addClass("current");
			$("#header .global-nav-master[name='system-setting']").addClass("current");
			$("#header li.global-nav-master.current li[name='item-check-setting']").addClass("current");
			
			$("#itemListDiv, #itemListDiv > div[name='list']").corner("left");
			$("#contents").corner("right");
		});

		function moveUserToCheck()
		{
			var $element = $(this);
			var $checkUsersDiv = $("#itemDiv div[name='checkUsers']");
			$element.unbind("click");
			$element.bind("click", moveUserToUncheck);
			$checkUsersDiv.append($element);
			return false;
		}

		function moveUserToUncheck()
		{
			var $element = $(this);
			var $allUsersDiv = $("#itemDiv div[name='allUsers']");
			$element.unbind("click");
			$element.bind("click", moveUserToCheck);
			$allUsersDiv.append($element);
			return false;
		}

		function itemChoose()
		{
			$("#itemList li").removeClass("current");
			$("#itemListDiv div[name='indexDiv']").removeClass("current");
			$nowEditingId = $(this).attr("value");
			if ($nowEditingId > 0)
			{
				$.get(
					'queryCollectionItemWithCheckOperatorsById.action', 
					{Id: $nowEditingId}, 
					setCollectionItem, 
					'json'
				);
			}
			else
				setCollectionItem();
			$(this).addClass("current");
		}
		
		function setCollectionItem(data)
		{
			if ($nowEditingId != null)
			{
				$("#markDiv").addClass("hidden");
				$("#itemDiv").removeClass("hidden");
			}
			else
			{
				$("#itemDiv").addClass("hidden");
				$("#markDiv").removeClass("hidden");
				return;
			}
			$("#itemDiv input[name='id']").attr("value", data.collectionItem.id);
			//$("#itemDiv div[name='title'] span").text(data.collectionItem.itemId + " : " + data.collectionItem.name);
			$("#itemDiv div[name='checkUsers']").children().each(function()
			{
				moveUserToUncheck.apply($(this));
			});
			$.each(data.collectionItem.checkOperator, function()
			{
				$("#itemDiv div.users[name=" + this.id + "]").each(function(){
					moveUserToCheck.apply($(this));
				});
			});
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
            </div>
            <div id="itemDiv" class="hidden">
                <form id="itemForm">
                    <div name="title">
                        <span></span>
                    </div>
                    <input type="hidden" name="id" />
                    <div name="userSet">
                        <div name="checkUsers">
                            <div class="users" name="1">
                                <a href="">
                                    10302010001 四字名字 按
                                </a>
                            </div>
                            <div class="users" name="1">
                                <a href="">
                                    10302010001 四字名字 按
                                </a>
                            </div>
                            <div class="users" name="1">
                                <a href="">
                                    10302010001 四字名字 按
                                </a>
                            </div>
                        </div>
                        <div name="allUsers">
                        </div>
                        <div class="clear">
                        </div>
                    </div>
                    <div name="submit">
                        <s:if test="systemState.state<=2">
                            <input type="submit" name="submit" value="提交更改" />
                        </s:if>
                        <s:else>
                            <span name="unsubmitable">系统状态不允许修改</span>
                        </s:else>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
    <div id="saveSuccessDialog" title="消息框" class="hidden">
		<span>保存成功！</span>
	</div> 
</body>
</html>