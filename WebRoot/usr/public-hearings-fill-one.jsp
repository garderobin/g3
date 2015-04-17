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
			$("#saveErrorDialog").dialog({
				autoOpen: false,
				width: 300,
				resizable: false,
				buttons: {
					好的: function() {
						$(this).dialog("close");
					}
				}
			});
			
			$(".item textarea, .item input").bind("keyup", function()
			{
				var $div = $(this).parent().parent().parent();
				$(".item[name=" + $div.attr("name") + "] > form input[name='submit']").removeClass("hidden"); 
			});

			$("form.itemForm").ajaxForm({
				type: 'post',
				dataType: 'json',
				beforeSubmit: function(formData, jqForm, options)
				{
					var $ifErr = false;
					var $errMsg = "<strong>有如下错误，请检查后重新提交。</strong><br />";
					var $temp;
					$temp = formData[2].value;
					if ($temp.length > 255){
						$ifErr = true;
						$errMsg += "建议不能超255个字符<br />";
					}
					if ($ifErr)
					{
						$("#saveErrorDialog").html($errMsg);
						$("#saveErrorDialog").dialog("open");
					}
				},
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
					}
				});
			}); 
			parent.subFrameLoaded();
		});	
		
		function getTotalHeight(){
			return document.getElementById("theEnd").offsetTop;
		}
		
	</script>
</head>

<body>
	<div id="wrapper">
        <div id="contents">
        	<div id="itemsDiv">
        		<div name="item-contents">
                	<span><s:property value="publicHearingsItem.contents" /></span>
            	</div>
            	<s:iterator value="users">
                	<div class="item" name="<s:property value='id' />">
                    	<div name="user">
                    		<h1>	
                            	<s:property value='name' />
                            	<span class="noClass <s:if test='publicHearingsItemInfoTarget[0] != null'>hidden</s:if> <s:if test='publicHearingsItemInfoTextTarget[0] != null'>hidden</s:if>">
                            		(未评价)
                            	</span>
                            </h1>
                        </div>
                        <form class="itemForm" action="
                        	<s:if test='publicHearingsItem.type == 0'>
                        		fillPublicHearingsItemInfo.action
                        	</s:if><s:else>
                        	<s:if test="publicHearingsItem.type == 1">
                        		fillPublicHearingsItemInfoText.action
                        	</s:if></s:else>">
                        	<input type="hidden" name="itemId" value="<s:property value='itemId' />" />
                            <input type="hidden" name="infoTarget" value="<s:property value='id' />" />
                            <s:if test="publicHearingsItem.type == 1">
	                            <div name="text">
		                        	<textarea name="text"><s:if test='publicHearingsItemInfoTextTarget[0] != null'><s:property value='publicHearingsItemInfoTextTarget[0].text' /></s:if></textarea>
		                        </div>
		                    </s:if>
		                    <s:else>
		                    <s:if test="publicHearingsItem.type == 0">
	                            <div name="checkValue">
	                            	<s:if test="publicHearingsItemInfoTarget[0] != null">
	                            		<div class="valueSlider" name="<s:property value='id' />" 
	                            			minScore="<s:property value='publicHearingsItem.minScore' />" maxScore="<s:property value='publicHearingsItem.maxScore' />"
	                            			value="<s:property value='publicHearingsItemInfoTarget[0].value' />">
	                            		</div>
	                            		<div name="valueDisplay">
	                                		<input name="value" type="text" value="<s:property value='publicHearingsItemInfoTarget[0].value' />"/>
	                                	</div>
	                            	</s:if>
	                            	<s:else>
	                            		<div class="valueSlider" name="<s:property value='id' />" 
	                            			minScore="<s:property value='publicHearingsItem.minScore' />" maxScore="<s:property value='publicHearingsItem.maxScore' />"
	                            			value="<s:property value='publicHearingsItem.defaultScore' />">
	                            		</div>
	                            		<div name="valueDisplay">
	                                		<input name="value" type="text" value="<s:property value='publicHearingsItem.defaultScore' />"/>
	                                	</div>
	                            	</s:else>
	                            </div>
	                        </s:if>
	                        </s:else>
                            <div name="submit">
                                <input name="submit" type="submit" class="noClass <s:if test='publicHearingsItemInfoTarget[0] != null'>hidden</s:if> <s:if test='publicHearingsItemInfoTextTarget[0] != null'>hidden</s:if>" />
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
    <div id="saveErrorDialog" title="消息框" class="hidden">
		<span>失败</span>
	</div>
</body>
</html>