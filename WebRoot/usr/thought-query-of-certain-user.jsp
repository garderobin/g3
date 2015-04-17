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
	<script type="text/javascript">
		$(function()
		{
			$(".markButton").bind("click", mark);
			$(".push").bind("click", push);
			$(".operation").bind("click",showComments);
			
			if ("<s:property value="itemId" />" == "")
				$("#itemListDiv div[name='indexDiv']").addClass("current");
			else
				$("#itemList li[value='<s:property value="itemId" />']").addClass("current");
			$("#header .global-subnav li[name='thought']").addClass("current");

			$("#itemListDiv, #itemListDiv > div[name='list']").corner("left");
			$("#contents").corner("right");
		});

		function push(){
			window.location.href="controlThought.action?controlType=publish&thoughtId="+$(this).attr("value");
		}
		
		function mark(){
			window.location.href="controlThought.action?controlType=mark&controlIntParameter=" + $(this).prev().val()+"&thoughtId="+$(this).attr("value");
		}
		
		function showComments(){
			$(this).parent().parent().next().toggle();
		}
		
		
	</script>
	
</head>


<body>
    <div id="wrapper">

        <div id="contents" style="border-top-left-radius: 10px; border-bottom-left-radius: 10px; border-left-width:1px">
            	
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
										<a href="javascript:void(0);">点击查看关联Git信息</a>
									</s:if>
									<s:else>
										无关联Git信息
									</s:else>
								
									<a> 本条感想得分：<s:property value="score"/></a>
									<a class="push" value="<s:property value="id"/>"> 推送首页 </a>
									<a class="operation">查看全部评论</a>
									<input class ="markField" type="textField" />
									<button class = "markButton" value="<s:property value="id"/>" >修改得分 </button>
									
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
</body>

</html>