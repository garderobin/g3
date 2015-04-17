<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>数据提交-评分系统</title>
    <link rel="stylesheet" href="css/common.css" />
    <link rel="stylesheet" href="css/item-only-frame.css" />
    <link rel="stylesheet" href="css/item-seperate-item.css" />
    <link href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-form/jquery.form.js" type="text/javascript" ></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/defaultForm.js" type="text/javascript"></script>
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
			
			$("#uploadImgDialog").dialog({
				autoOpen: false,
				width: 400,
				resizable: false,
				buttons: {
					关闭: function() {
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
				url: 'fillCollectionItemInfo.action',
				type: 'post',
				dataType: 'json',
				beforeSubmit: function(formData, jqForm, options){
					var $ifErr = false;
					var $errMsg = "<strong>有如下错误，请检查后重新提交。</strong><br />";
					var $temp;
					$temp = formData[1].value;
					if ($temp.length > 255){
						$ifErr = true;
						$errMsg += "解释不能超255个字符<br />";
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
					$(".item[name=" + data.itemId + "] textarea[name='text']").attr("value", data.text);
					$(".item[name=" + data.itemId + "] input[name='value']").attr("value", data.value);
					var $submitButton = $(".item[name=" + data.itemId + "] div[name='submit'] > input[name='submit']");
					var $submitSuccessMsg = $(".item[name=" + data.itemId + "] div[name='submit'] > span");
					$submitButton.addClass("hidden");
					$submitSuccessMsg.fadeIn(800).fadeOut();
				}
			});
			
			$("form.itemImageForm").ajaxForm({
				 url: "fillCollectionItemInfoImage.action",
				 type: "post",
				 dataType: "json",
				 beforeSubmit: function(formData, jqForm, options){
					var $ifErr = false;
					var $errMsg = "<strong>有如下错误，请检查后重新提交。</strong><br />";
					var $temp;
					$temp = $("form.itemImageForm > input[name='upload']").attr("value");
					if ($temp == null || $temp == "")
					{
						$ifErr = true;
						$errMsg += "您未选择图片<br />";
					}
					if ($ifErr)
					{
						$("#saveErrorDialog").html($errMsg);
						$("#saveErrorDialog").dialog("open");
						return false;
					}
					$("#uploadImgDialog div[name='loadingBar']").removeClass("hidden");
					$("form.itemImageForm").addClass("hidden");
				},
				success: function(data)
				{
					addOnePicFrame(data.itemId, data.id);
					alert("上传成功");
					$("#uploadImgDialog").dialog("close");
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

			defaultForm($(".item textarea[name='text']"), "请输入您的解释");
			
			$("#contents").corner();

			$("#itemsDiv").each(function()
			{
				$(this).corner();
			});
			
			$("#header .global-subnav li[name='item-fill']").addClass("current");
		});	
		
		function showPic(itemId)
		{
			$.ajax({
				type: "GET",
				url: "queryCollectionItemInfoImage.action",
				dataType: "json",
				data: {itemId: itemId},
				success: function(data)
				{
					$(".item[name=" + data.itemId + "] div[name='picDiv'] div.picFrame").remove();
					$.each(data.itemInfoImage, function(n, value){
						addOnePicFrame(data.itemId, value.id);
					});
					$(".item[name=" + data.itemId + "] div[name='picDiv']").slideDown();
				}
			});	
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
			$(".item[name=" + itemName + "] div[name='picDiv'] div[name=" + id + "] a").attr("href", "javascript:delPic(" + id + ");");
		}
		
		function addPic(i)
		{
			$("#uploadImgDialog div[name='loadingBar']").addClass("hidden");
			$("form.itemImageForm").removeClass("hidden");
			$("form.itemImageForm > input[name='itemId']").attr("value", i);
			$("#uploadImgDialog").dialog("open");
		}
		
		function delPic(i)
		{
			$.ajax({
				url:"deleteCollectionItemInfoImage.action",
				type:"get",
				dataType:"json",
				data:{id:i},
				success:function(data)
				{
					$(".item div[name='picDiv'] div.picFrame[name='" + data.id + "']").remove();
					alert("删除成功");
				}
			});
		}

	</script>
</head>

<body>
    <%@ include file="header.jsp" %>
	<div id="wrapper">
        <div id="contents">
        	<div id="itemsDiv">
                <s:iterator value="collectionItemList">
                	<s:if test="ifUserSubmitText == 1 || ifUserSubmitPic == 1 || ifCollection == 1">
	                    <div class="item" name="<s:property value='id' />">
	                    	<div name="title">
	                        	<h1>
	                      			<s:property value="itemId" /> : <s:property value="name" />
	                            </h1>
	                        </div>
	                        <div name="contents">
	                      		<s:property value="contents" />
	                        </div>
	                        <form class="itemForm">
	                        	<input type="hidden" name="itemId" value="<s:property value='id' />" />
	                            <s:if test="ifUserSubmitText==1">
	                                <div name="text">
	                                    <textarea name="text"><s:if test='collectedItemInfo != null'><s:property value='collectedItemInfo[0].text' /></s:if></textarea>
	                                </div>
	                            </s:if>
	                            <div name="value">
	                                <s:if test="ifCollection==1">
	                                	<s:if test='collectedItemInfo[0] != null'>
	                                    	<div class="valueSlider" name="<s:property value='id' />"  
	                                        	minScore="<s:property value='minScore' />" maxScore="<s:property value='maxScore' />"
	                            				value="<s:property value='collectedItemInfo[0].value' />">
	                            			</div>
	                            			<div name="valueDisplay">
                                				<input name="value" type="text" value="<s:property value='collectedItemInfo[0].value' />"/>
                                			</div>
	                            		</s:if><s:else>
	                            			<div class="valueSlider" name="<s:property value='id' />"  
	                                        	minScore="<s:property value='minScore' />" maxScore="<s:property value='maxScore' />"
	                            				value="<s:property value='defaultScore' />">
	                            			</div>
	                            			<div name="valueDisplay">
                                				<input name="value" type="text" value="<s:property value='defaultScore' />" />
                                			</div>
	                            		</s:else>
	                                </s:if>
	                            </div>
                                <s:if test="ifUserSubmitText == 1 || ifCollection == 1">
                                    <div name="submit">
                                        <input name="submit" type="submit" class="hidden" />
                                        <span name="submitSuccessMsg" class="hidden">提交成功</span>
                                    </div>
                                </s:if>
	                        </form>
                            <s:if test="ifUserSubmitPic==1">
                                <div class="clear">
                                </div>
                                <div name="showPicDiv">
                                	<a href="javascript:showPic(<s:property value='id' />);">这个项目可以提交图片。</a>
                                </div>
                                <div name="picDiv" class="hidden">
                                    <div class="picFrameNew">
                                    	<a href="javascript:addPic(<s:property value='id' />);">添加</a>
                                    </div>
                                    <div class="clear">
                                    </div>
                                </div>
                            </s:if>
	                        <div class="clear">
	                        </div>
	                    </div>
                    </s:if>
                </s:iterator>
            </div>
        </div>
    </div>
    <div id="theEnd"></div>
    <%@ include file="footer.jsp" %>
    <div class="picFrame" id="picFrameModel" class="hidden"><img /><br /><a>删除</a></div>
    <div id="saveErrorDialog" title="消息框" class="hidden">
		<span>失败</span>
	</div>
    <div id="uploadImgDialog" title="上传图片" class="hidden">
    	<span>上传过程中请不要关闭窗口，以免上传失败。如果长时间无反应，请按F5刷新后重试。<br />
        	请注意：只接受jpg,png,gif格式且大小在200KB以下在图片。</span>
        <div name="loadingBar" class="hidden">
        </div>
        <form enctype="multipart/form-data" class="itemImageForm">
            <input type="hidden" name="itemId" /> 
            <input name="upload" type="file" />
            <input type="submit" value="上传" />
        </form>
    </div>
</body>
</html>