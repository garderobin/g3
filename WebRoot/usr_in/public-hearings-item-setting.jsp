<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>项目设定-评分系统</title>
    <link rel="stylesheet" href="css/common.css" />
    <link rel="stylesheet" href="css/item-setting.css" />
    <link href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-form/jquery.form.js" type="text/javascript" ></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/list-move.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function()
		{
			$("#itemList li").bind("click", itemChoose);
			$("#itemListDiv div[name='indexDiv']").bind("click", function(){
				$("#itemList li").removeClass("current");
				$("#itemListDiv div[name='indexDiv']").addClass("current");
				$nowEditingId = null;
				setPublicHearingsItem({publicHearingsItem:{id:null}});
			});
			
			$("#saveSuccessDialog, #saveErrorDialog").dialog({
				autoOpen: false,
				width: 300,
				resizable: false,
				buttons: {
					好的: function() {
						$(this).dialog("close");
					}
				}
			});

			$("#calculatingDialog").dialog({
				autoOpen: false,
				width: 300,
				resizable: false
			});
			
			$("#itemForm select[name='type']").bind("change", function()
			{
				var value = $("#itemForm select[name='type']").attr("value");
				if (value == 0)
				{
					$("#itemForm table tr[name='scoreRow']").removeClass("hidden");
				}
				else if(value == 1)
				{
					$("#itemForm table tr[name='scoreRow']").addClass("hidden");
				}
			});

			$("#itemForm").ajaxForm({
				url: 'createPublicHearingsItem.action',
				type: 'GET',
				dataType: 'json',
				beforeSubmit: function(){
					var $ifErr = false;
					var $errMsg = "<strong>有如下错误，请检查后重新提交。</strong><br />";
					var $temp;
					$temp = $("#itemForm input[name='name']").attr("value");
					if ($temp == null || $temp.length == 0){
						$ifErr = true;
						$errMsg += "名称不能为空<br />";
					}
					if ($("#itemForm select[name='type']").attr("value") == 0)
					{
						var $tempMin = $("#itemForm input[name='minScore']").attr("value");
						var $tempMax = $("#itemForm input[name='maxScore']").attr("value");
						var $tempDefault = $("#itemForm input[name='defaultScore']").attr("value");
						if ($tempMin == null || $tempMin.length == 0 || $tempMax == null || $tempMax.length == 0 || $tempDefault == null || $tempDefault.length == 0){
							$ifErr = true;
							$errMsg += "最高分最低分和默认得分不能为空<br />";
						}
					}
					if ($ifErr)
					{
						$("#saveErrorDialog").html($errMsg);
						$("#saveErrorDialog").dialog("open");
						return false;
					}
				},
				success: function(data)
				{
					if (!($nowEditingId > 0))
					{
						$("#itemList").append("<li value=" + data.publicHearingsItem.id + ">" + data.publicHearingsItem.itemId + " : " + data.publicHearingsItem.name + "</li>");
						$("#itemList li:last").bind("click", itemChoose);
					}
					else
					{
						$("#itemList li[value=" + data.publicHearingsItem.id + "]").replaceWith("<li value=" + data.publicHearingsItem.id + ">" + data.publicHearingsItem.itemId + " : " + data.publicHearingsItem.name + "</li>");
						$("#itemList li[value=" + data.publicHearingsItem.id + "]").bind("click", itemChoose);
					}
					$("#saveSuccessDialog").dialog("open");
					setPublicHearingsItem(data);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					$("#saveErrorDialog").dialog("open");
				}
			});

			$("#calculate-trigger").bind("click", function(){
				$("#calculatingDialog").dialog("open");
				$.ajax({
					url:"calculatePublicHearingsItemScoreById.action",
					type:"get",
					datatype:"json",
					data:{itemId: $nowEditingId },
					success:function(data)
					{
						$("#calculatingDialog").dialog("close");
						$("#saveSuccessDialog").dialog("open");
					},
					error:function(data)
					{
						$("#calculatingDialog").dialog("close");
						$("#saveErrorDialog").dialog("open");
					}
				});
			});

			$nowEditingId = null;
			$("#itemListDiv div[name='indexDiv']").addClass("current");
			$("#header .global-nav-master[name='system-setting']").addClass("current");
			$("#header li.global-nav-master.current li[name='public-hearings-item-setting']").addClass("current");
			
			$("#itemListDiv, #itemListDiv > div[name='list']").corner("left");
			$("#contents").corner("right");
		});

		function itemChoose()
		{
			$("#itemList li").removeClass("current");
			$("#itemListDiv div[name='indexDiv']").removeClass("current");
			$.get(
				'queryPublicHearingsItemById.action', 
				{Id: $(this).attr("value")}, 
				setPublicHearingsItem, 
				'json'
			);
			$(this).addClass("current");
		}
		
		function newItem()
		{
			$("#itemList li").removeClass("current");
			var newItem = {}
			var publicHearingsItem = {}
			publicHearingsItem.id = 0;
			publicHearingsItem.itemId = null;
			publicHearingsItem.name = null;
			publicHearingsItem.minScore = null;
			publicHearingsItem.maxScore = null;
			publicHearingsItem.defaultScore = null;
			publicHearingsItem.averageScore = null;
			publicHearingsItem.deltaScore = null;
			publicHearingsItem.contents = null;
			newItem.publicHearingsItem = publicHearingsItem;
			setPublicHearingsItem(newItem);
		}

		function delItem()
		{
			if (!($nowEditingId > 0))
			{
				alert("请选择项目");
			}
			else
			{
				$.get(
					'deletePublicHearingsItemById.action', 
					{Id: $nowEditingId}, 
					function(data, textStatus){
						$("#itemList li[value=" + data.id + "]").remove();
						$nowEditingId = 0;
						var nullData = {};
						var item = {};
						item.id = 0;
						nullData.publicHearingsItem = item;
						setPublicHearingsItem(nullData);
						$("#saveSuccessDialog").dialog("open");
					}, 
					'json'
				);
			}
		}
		
		function setPublicHearingsItem(data)
		{
			$nowEditingId = data.publicHearingsItem.id;
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
			$("#itemDiv input[name='id']").attr("value", data.publicHearingsItem.id);
			$("#itemDiv input[name='itemId']").attr("value", data.publicHearingsItem.itemId);
			$("#itemDiv input[name='name']").attr("value", data.publicHearingsItem.name);
			$("#itemDiv input[name='minScore']").attr("value", data.publicHearingsItem.minScore);
			$("#itemDiv input[name='maxScore']").attr("value", data.publicHearingsItem.maxScore);
			$("#itemDiv input[name='defaultScore']").attr("value", data.publicHearingsItem.defaultScore);
			$("#itemDiv input[name='averageScore']").attr("value", data.publicHearingsItem.averageScore);
			$("#itemDiv input[name='deltaScore']").attr("value", data.publicHearingsItem.deltaScore);
			$("#itemDiv select[name='type']").attr("value", data.publicHearingsItem.type);
			$("#itemDiv select[name='type']").triggerHandler("change");
			$("#itemDiv textarea[name='contents']").attr("value", data.publicHearingsItem.contents);
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
                        <s:iterator value="publicHearingsItemList" id="items">
                        <li value=<s:property value="id" />> 
                            <s:property value="itemId" />&nbsp;:&nbsp;<s:property value="name" />
                        </li>
                        </s:iterator>
                    </ol>
                </div>
                <div name="control">
            		<a name="up" href="javascript:listUp();">up</a>
                	<a name="down" href="javascript:listDown();">down</a>
                	<s:if test="systemState.state==1">
            			<a name="new" href="javascript:newItem();">&nbsp;新建</a>
                		<a name="del" href="javascript:delItem();">&nbsp;删除</a>
            		</s:if>
            	</div>
            </div>
        </div>
        <div id="contents">
            <div id="markDiv">
            </div>
            <div id="itemDiv" class="hidden">
                <form id="itemForm">
                    <input type="hidden" name="id" />
                    <div name="scoreSet">
                        <table name="scoreSet">
                            <tr>
                                <td>
                                    题号：
                                </td>
                                <td name="input">
                                    <input type="text" name="itemId" />
                                </td>
                                <td>
                                    名称：
                                </td>
                                <td colspan="3">
                                    <input type="text" name="name" />
                                </td>
                            </tr>
                            <tr name="scoreRow">
                                <td name="mark">
                                    最低分：
                                </td>
                                <td name="input">
                                    <input type="text" name="minScore" />
                                </td>
                                <td name="mark">
                                    最高分：
                                </td>
                                <td name="input">
                                    <input type="text" name="maxScore" />
                                </td>
                                <td name="mark">
                                    默认得分：
                                </td>
                                <td name="input">
                                    <input type="text" name="defaultScore" />
                                </td>
                            </tr>
                            <tr name="scoreRow">
                                <td>
                                    平均分：
                                </td>
                                <td>
                                    <input type="text" name="averageScore" />
                                </td>
                                <td>
                                    离散程度：
                                </td>
                                <td>
                                    <input type="text" name="deltaScore" />
                                </td>
                            </tr>
                            </table>
                            <table>
                            <tr>
                                <td>
                                    题目类型：
                                </td>
                                <td colspan="3">
                                    <select width="20px" name="type">
                                        <option value="0">投票</option>
                                        <option value="1">建议</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div name="public-hearings-contents">
                        用户提示信息：
                        <textarea name="contents"></textarea>
                    </div>
                    <div name="submit">
                        <s:if test="systemState.systemType == 1">
                            <input name="submit" type="submit" value="提交更改" />
                            <a id="calculate-trigger" name="calculate">统计得分</a>
                        </s:if>
                        <s:elseif test="systemState.systemType == 0">
                        	<s:if test="systemState.state == 1">
                            	<input name="submit" type="submit" value="提交更改" />
                           	</s:if>
                           	<s:else>
                            	<span name="unsubmitable">系统状态不允许修改</span>
                        	</s:else>
                        </s:elseif>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
    <div id="saveSuccessDialog" title="消息框" class="hidden">
		<span>操作已成功！</span>
	</div>
    <div id="calculatingDialog" title="消息框" class="hidden">
		<div name="loadingDiv">
		    <div name="word">
		        <span>统计中，请稍候。</span>
		    </div>
		    <div name="loadingBar">
		    </div>
		</div>
	</div>
    <div id="saveErrorDialog" title="消息框" class="hidden">
		<span>失败</span>
	</div>
</body>
</html>