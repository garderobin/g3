<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>感想查询</title>
    <link rel="stylesheet" href="css/common.css"/>
    <link rel="stylesheet" href="css/item-optional-frame.css" />
    <link href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="css/thought.css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function()
		{
			$("#itemList li").bind("click", itemChoose);
			$(".operation").bind("click",showComments);
			
			if ("<s:property value="itemId" />" == "")
				$("#itemListDiv div[name='indexDiv']").addClass("current");
			else
				$("#itemList li[value='<s:property value="itemId" />']").addClass("current");
			$("#header .global-subnav li[name='thought']").addClass("current");

			$("#itemListDiv, #itemListDiv > div[name='list']").corner("left");
			$("#contents").corner("right");
			
			$("#codeDialog").dialog({
				autoOpen: false,
				width: 800,
				resizable: false,
				buttons: {
					"关闭": function() {
						$(this).dialog("close");
					}
				}
			});
		});
		
		function itemChoose(){
			window.location.href="queryThought.action?itemId=" + $(this).attr("value");
		}
		
		function showComments(){
			$(this).parent().parent().next().toggle();
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
					$("td.content > a.version").bind("click", function(){
						$("#gitIdentifier").attr("value", $(this).text());
						$("#codeDialog").dialog("close");
						return false;
					});
				}
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
         	<s:if test="itemId != null">
        		<div id="shout_box">
				<s:form action="createThought.action" namespace="/usr">
					<input type="hidden" name="itemId" value="<s:property value="itemId" />" />
					<s:textarea id="broadcast_area" name="message"></s:textarea>
					<input type="text" id="gitIdentifier" name="gitIdentifier" />
					<a href="javascript:showCode('','',<s:property value='itemId' />);">查看Git信息</a>
					<s:submit value="发布"></s:submit>
				</s:form>
			</div>
       		</s:if>
         	
			<div id="thoughtListDiv">
				<ol id="talk_list">
					<s:iterator var = "thought" value="thoughtList">
						<li class="text">
							<div class="msgbox" id=<s:property value = "id"/>>
								<p>
									<a href=“javascript:void(0);”><s:property value="infoProvider.username"/> </a>：<em> <s:property value="message"/></em>
								</p>
								<div class="appendinfo">
								
									<s:if test="gitIdentifier != null">
										<a href="javascript:showCode('<s:property value="gitIdentifier" />', '', <s:property value="itemId" />);">点击查看关联Git信息</a>
									</s:if>
									<s:else>
										无关联Git信息
									</s:else>
								
									<a> 本条感想得分：<s:property value="score"/></a>
									<a class="operation">查看全部评论</a>
									
								</div>
							</div>
							<div class="comment" id="all_comment" >
								<div class="comment_box" id="comment">
									<s:form action = "controlThought?controlType=addComment">
										<input type="hidden" name="thoughtId" value="<s:property value ='id'/>"/>
										<s:textarea name="controlStringParameter" cols="80" rows="5" ></s:textarea>
										<s:submit value="评论"/>										
									</s:form>
								</div>
								
								<ul  class="comment_list">
									<s:iterator var="comment" value="#thought.comments" >
									<li>
										<a href="javascript:void(0);"><s:property value="#comment.infoProvider.username"/></a>：<s:property value="#comment.message"/>
									</li> 									
									</s:iterator>
								</ul>
							</div>
						</li>
					</s:iterator>				
				</ol>
   			</div>
        </div>
    </div>
    <div id="codeDialog" title="查看代码" class="hidden">
	</div>
    <%@ include file="footer.jsp" %>
</body>

</html>