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
			$("#itemListDiv div[name='indexDiv']").bind("click", itemChoose);
			
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
			
			$("#itemForm").ajaxForm({
				url: 'createCollectionItem.action',
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
					var $tempMin = $("#itemForm input[name='minScore']").attr("value");
					var $tempMax = $("#itemForm input[name='maxScore']").attr("value");
					var $tempDefault = $("#itemForm input[name='defaultScore']").attr("value");
					if ($tempMin == null || $tempMin.length == 0 || $tempMax == null || $tempMax.length == 0 || $tempDefault == null || $tempDefault.length == 0){
						$ifErr = true;
						$errMsg += "最高分最低分和默认得分不能为空<br />";
					}
					var $ifCollection = $("#itemForm input[name='ifCollection']").attr("checked");
					var $ifCheck = $("#itemForm input[name='ifCheck']").attr("checked");
					if (!($ifCollection == "checked" || $ifCheck == "checked")) {
						$ifErr = true;
						$errMsg += "是否认定和是否收集数据至少要选中一项<br />";
					}
					$temp = $("#itemForm textarea[name='contents']").attr("value");
					if ($temp.length > 255){
						$ifErr = true;
						$errMsg += "用户提示信息不能超255个字符<br />";
					}
					$temp = $("#itemForm textarea[name='checkRequirement']").attr("value");
					if ($temp.length > 255){
						$ifErr = true;
						$errMsg += "认定提示信息不能超255个字符<br />";
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
						$("#itemList").append("<li value=" + data.collectionItem.id + ">" + data.collectionItem.itemId + " : " + data.collectionItem.name + "</li>");
						$("#itemList li:last").bind("click", itemChoose);
					}
					else
					{
						$("#itemList li[value=" + data.collectionItem.id + "]").replaceWith("<li value=" + data.collectionItem.id + ">" + data.collectionItem.itemId + " : " + data.collectionItem.name + "</li>");
						$("#itemList li[value=" + data.collectionItem.id + "]").bind("click", itemChoose);
					}
					$("#saveSuccessDialog").dialog("open");
					setCollectionItem(data);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					$("#saveErrorDialog").dialog("open");
				}
			});

			$("#calculate-trigger").bind("click", function(){
				$("#calculatingDialog").dialog("open");
				$.ajax({
					url:"calculateCollectionItemScoreById.action",
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
			$("#header li.global-nav-master.current li[name='item-setting']").addClass("current");
			
			$("#itemListDiv, #itemListDiv > div[name='list']").corner("left");
			$("#contents").corner("right");
		});

		function itemChoose()
		{
			$("#itemList li").removeClass("current");
			$("#itemListDiv div[name='indexDiv']").removeClass("current");
			$nowEditingId = $(this).attr("value");
			if ($nowEditingId > 0)
			{
				$.get(
					'queryCollectionItemById.action', 
					{Id: $nowEditingId}, 
					setCollectionItem, 
					'json'
				);
			}
			else
				setCollectionItem({collectionItem:{id:null}});
			$(this).addClass("current");
		}
		
		function newItem()
		{
			$("#itemList li").removeClass("current");
			var newItem = {}
			var collectionItem = {}
			collectionItem.id = 0;
			collectionItem.itemId = null;
			collectionItem.name = null;
			collectionItem.type = 0;
			collectionItem.minScore = null;
			collectionItem.maxScore = null;
			collectionItem.defaultScore = null;
			collectionItem.averageScore = null;
			collectionItem.deltaScore = null;
			collectionItem.ifCollection = false;
			collectionItem.ifCheck = false;
			collectionItem.ifUserSubmitText = false;
			collectionItem.ifUserSubmitPic = false;
			collectionItem.ifUserSubmitCode = false;
			collectionItem.contents = null;
			collectionItem.checkRequirement = null;
			collectionItem.codeDirectory = "";
			newItem.collectionItem = collectionItem;
			setCollectionItem(newItem);
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
					'deleteCollectionItemById.action', 
					{Id: $nowEditingId}, 
					function(data, textStatus){
						$("#itemList li[value=" + data.id + "]").remove();
						$nowEditingId = 0;
						var nullData = {};
						var item = {};
						item.id = 0;
						nullData.collectionItem = item;
						setCollectionItem(nullData);
						$("#saveSuccessDialog").dialog("open");
					}, 
					'json'
				);
			}
		}
		
		function setCollectionItem(data)
		{
			$nowEditingId = data.collectionItem.id;
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
			$("#itemDiv input[name='itemId']").attr("value", data.collectionItem.itemId);
			$("#itemDiv input[name='name']").attr("value", data.collectionItem.name);
			$("#itemDiv input[name='minScore']").attr("value", data.collectionItem.minScore);
			$("#itemDiv input[name='maxScore']").attr("value", data.collectionItem.maxScore);
			$("#itemDiv input[name='defaultScore']").attr("value", data.collectionItem.defaultScore);
			$("#itemDiv input[name='averageScore']").attr("value", data.collectionItem.averageScore);
			$("#itemDiv input[name='deltaScore']").attr("value", data.collectionItem.deltaScore);
			$("#itemDiv input[name='ifCollection']").attr("checked", data.collectionItem.ifCollection);
			$("#itemDiv input[name='ifCheck']").attr("checked", data.collectionItem.ifCheck);
			$("#itemDiv input[name='ifUserSubmitText']").attr("checked", data.collectionItem.ifUserSubmitText);
			$("#itemDiv input[name='ifUserSubmitPic']").attr("checked", data.collectionItem.ifUserSubmitPic);
			$("#itemDiv input[name='ifUserSubmitCode']").attr("checked", data.collectionItem.ifUserSubmitCode);
			$("#itemDiv input[name='codeDirectory']").attr("value", data.collectionItem.codeDirectory);
			$("#itemDiv select[name='type']").attr("value", data.collectionItem.type);
			$("#itemDiv textarea[name='contents']").attr("value", data.collectionItem.contents);
			$("#itemDiv textarea[name='checkRequirement']").attr("value", data.collectionItem.checkRequirement);
			$("#itemDiv input[name='startFillingTime']").attr("value", data.collectionItem.startFillingTime);
			$("#itemDiv input[name='endFillingTime']").attr("value", data.collectionItem.endFillingTime);
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
                        <table>
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
                            <tr>
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
                            <tr>
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
                            <tr>
                                <td>
                                    题目类型：
                                </td>
                                <td colspan="3">
                                    <select width="20px" name="type" enabled="false">
                                        <option value="0">直接得分</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div name="settingSet">
                        <table>
                            <tr>
                                <td>
                                    是否需要收集数据
                                </td>
                                <td>
                                    <input type="checkbox" name="ifCollection" />
                                </td>
                                <td>
                                    是否需要认定
                                </td>
                                <td>
                                    <input type="checkbox" name="ifCheck" />
                                </td>
                            </tr>
                            <tr>
                                <td name="mark">
                                    是否允许用户提交解释
                                </td>
                                <td name="check">
                                    <input type="checkbox" name="ifUserSubmitText" />
                                </td>
                                <td name="mark">
                                    是否允许用户提交图片
                                </td>
                                <td name="check">
                                    <input type="checkbox" name="ifUserSubmitPic" />
                                </td>
                            </tr>
                            <tr>
                                <td name="mark">
                                    是否允许用户提交代码
                                </td>
                                <td name="check">
                                    <input type="checkbox" name="ifUserSubmitCode" />
                                </td>
                                <td name="mark">
                                    用户Git版本库地址
                                </td>
                                <td>
                                    <input type="text" name="codeDirectory" />
                                </td>
                            </tr>
                            <tr>
                                <td name="startFillingTime">
                                    开始提交时间
                                </td>
                                <td name="check">
                                    <input type="text" name="startFillingTime" />
                                </td>
                                <td name="endFillingTime">
                                    结束提交时间
                                </td>
                                <td name="check">
                                    <input type="text" name="endFillingTime" />
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div name="contents">
                        用户提示信息：
                        <textarea name="contents"></textarea>
                    </div>
                    <div name="checkRequirement">
                        认定提示信息：
                        <textarea name="checkRequirement"></textarea>
                    </div>
                    <div class="clear"></div>
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