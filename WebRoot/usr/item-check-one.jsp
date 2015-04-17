<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>数据提交-评分系统</title>
    <link rel="stylesheet" href="css/common.css" />
    <link rel="stylesheet" href="css/item-seperate-item.css" />
    <link href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-form/jquery.form.js" type="text/javascript" ></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
    
	<script type="text/javascript">
		$(function()
		{
			
			$(".item textarea, .item input").bind("keyup", function()
			{
				var $div = $(this).parent().parent();
				$(".item[name=" + $div.attr("name") + "] > form input[name='submit']").removeClass("hidden"); 
			});

			$("form.itemForm").ajaxForm({
				url: 'fillCollectionItemInfoCheck.action',
				type: 'post',
				dataType: 'json',
				beforeSubmit: function(){},
				success: function(data)
				{
					$(".item[name=" + data.infoTarget + "] input[name='value']").attr("value", data.value);
					var $unSubmittedSpan = $(".item[name=" + data.infoTarget + "] div[name='user'] span");
					var $submitButton = $(".item[name=" + data.infoTarget + "] div[name='submit'] > input[name='submit']");
					var $submitSuccessMsg = $(".item[name=" + data.infoTarget + "] div[name='submit'] > span");
					$unSubmittedSpan.addClass("hidden");
					$submitButton.addClass("hidden");
					$submitSuccessMsg.fadeIn(800).fadeOut();
				}
			});
			
			$(".valueSlider").each(function()
			{
				var $id = $(this).attr('name');
				var $input = $(".item[name=" + $id + "] div[name='valueDisplay'] input[name='value']");
				var $min = $(this).attr("minScore") * 1;
				var $max = $(this).attr("maxScore") * 1;
				var $current = $(this).attr("value") * 1;
				
				$(this).slider({
					range: "min",
					value: $current,
					min: $min,
					max: $max,
					slide: function(event, ui) {
						$input.val(ui.value);
						$(".item[name=" + $(this).attr("name") + "] div[name='submit'] input[name='submit']").removeClass("hidden");
					},
					change: function()
					{
						$(".item[name=" + $(this).attr("name") + "] div[name='submit'] input[name='submit']").trigger("click");
					}
				});
			});
			
			parent.subFrameLoaded();
		});	
		
		function showPic(itemId, userId)
		{
			$.ajax({
				type: "GET",
				url: "queryCollectionItemInfoImage.action",
				dataType: "json",
				data: {itemId: itemId, userId: userId},
				success: function(data)
				{
					$(".item[name=" + data.userId + "] div[name='picDiv'] div.picFrame").remove();
					$.each(data.itemInfoImage, function(n, value){
						addOnePicFrame(data.userId, value.id);
					});
					$(".item[name=" + data.userId + "] div[name='picDiv']").removeClass("hidden");
				}
			});	
		}

		function showCode(version, path, itemId, userId)
		{
			parent.showCode(version, path, itemId, userId);
			/*var $url;
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
				}
			});*/
		}
		
		function addOnePicFrame(itemName, id)
		{
			var $tempPicFrame = $("#picFrameModel").clone();
			$tempPicFrame.removeAttr("id");
			$tempPicFrame.removeClass("hidden");
			$tempPicFrame.prependTo($(".item[name=" + itemName + "] div[name='picDiv']"));
			$tempPicFrame.attr("name", id);
			$(".item[name=" + itemName + "] div[name='picDiv'] div[name=" + id + "] img").attr("src", "queryCollectionItemInfoOneImage.action?id=" + id);
			$(".item[name=" + itemName + "] div[name='picDiv'] div[name=" + id + "] img").attr("onClick", "javascript:window.open('queryCollectionItemInfoOneImage.action?id=" + id + "');");
		}
		
		function getTotalHeight(){
			return document.getElementById("theEnd").offsetTop;
		}
		
	</script>
</head>

<body>
	<div id="wrapper">
        <div id="contents">
        	<div id="itemsDiv">
            	<div name="item-checkRequirement">
                	<span><s:property value="collectionItem.checkRequirement" /></span>
            	</div>
                <div class="clear"></div>
                <s:iterator value="users">
                	<div class="item" name="<s:property value='id' />">
                    	<div name="user">
                    		<h1>	
                            	<s:property value='name' />
                            	<span class="<s:if test="collectionItemInfoCheckTarget[0] != null">hidden</s:if>">
                            		(未认定)
                            	</span>
                            </h1>
                        </div>
                        <s:if test="collectionItemInfo[0].text != null">
	                        <div name="userSubmittedText">
	                        	用户的解释：<s:property value='collectionItemInfo[0].text' />
	                        </div>
                        </s:if>
                        <s:if test="collectionItemInfo[0].value != null">
	                        <div name="userSubmittedValue">
								用户期望得分：<s:property value='collectionItemInfo[0].value' />
	                        </div>
                        </s:if>
                        <s:if test="collectionItemInfo[0].images != null">
                            <div class="clear">
                            </div>
                            <div name="showPicDiv">
                                <a href="javascript:showPic(<s:property value='itemId' />, <s:property value='id' />);">用户提交了图片。</a>
                            </div>
                            <div name="picDiv" class="hidden">
                                <div class="clear">
                                </div>
                            </div>
                        </s:if>
                        <s:if test="collectionItem.ifUserSubmitCode == true">
                            <div class="clear">
                            </div>
                            <div name="showCodeDiv">
                                <a href="javascript:showCode('', '', <s:property value='itemId' />, <s:property value='id' />);">该项目有关联的代码。</a>
                            </div>
                        </s:if>
                        <a href="#" onclick="javascript:window.open('queryThoughtOfCertainUser.action?itemId=<s:property value='itemId' />&userId=<s:property value='id' />');">查看感想</a>
                        <form class="itemForm" >
                        	<input type="hidden" name="itemId" value="<s:property value='itemId' />" />
                            <input type="hidden" name="infoTarget" value="<s:property value='id' />" />
                            <div name="checkValue">
                            	<s:if test="collectionItemInfoCheckTarget[0] != null">
                            		<div class="valueSlider" name="<s:property value='id' />" 
                            			minScore="<s:property value='collectionItem.minScore' />" maxScore="<s:property value='collectionItem.maxScore' />"
                            			value="<s:property value='collectionItemInfoCheckTarget[0].value' />">
                            		</div>
                            		<div name="valueDisplay">
                                		<input name="value" type="text" value="<s:property value='collectionItemInfoCheckTarget[0].value' />"/>
                                	</div>
                            	</s:if>
                            	<s:else>
                            		<div class="valueSlider" name="<s:property value='id' />" 
                            			minScore="<s:property value='collectionItem.minScore' />" maxScore="<s:property value='collectionItem.maxScore' />"
                            			value="<s:property value='collectionItem.defaultScore' />">
                            		</div>
                            		<div name="valueDisplay">
                                		<input name="value" type="text" value="<s:property value='collectionItem.defaultScore' />"/>
                                	</div>
                            	</s:else>
                            </div>
                            <div name="submit">
                                <input name="submit" type="submit" class="<s:if test='collectionItemInfoCheckTarget[0] != null'>hidden</s:if>" />
                                <span name="submitSuccessMsg" class="hidden">提交成功</span>
                            </div>
                        </form>
                        <div class="clear">
                        </div>
                    </div>
                </s:iterator>
            </div>
        </div>
    </div>
    <div id="theEnd"></div>
    <div class="picFrame" id="picFrameModel" class="hidden"><img /></div>
</body>
</html>