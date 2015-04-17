<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>用户系统-评分系统</title>
    <link rel="stylesheet" href="css/common.css" />
    <link rel="stylesheet" href="css/index.css" />
        <link rel="stylesheet" href="css/index_temp.css"/>
    <link rel="stylesheet" href="css/thought.css"/>
    <link href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function()
		{
			$(".operation").bind("click",showComments);
			$("#contents").corner();
		});
		
		function showComments(){
			$(this).parent().parent().next().toggle();
		}
	</script>
</head>

<body>
    <%@ include file="header.jsp" %>
	<div id="wrapper">
        <div id="contents" name="index">
        		<div id="context">
				<div id="thoughtsdiv">
					<div name="title">
						<h1>
							感想分享
						</h1>
					</div>
					<ul class="thoughts">
					   <s:iterator var = "thought" value="thoughtList">
						<li class="text">
							<div class="msgbox" id=<s:property value = "id"/>>
								<p>
									<a href=“javascript:void(0);”><s:property value="infoProvider.username"/> </a>：<em> <s:property value="message"/></em>
								</p>
								<div class="appendinfo">
								
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
					</ul>
				</div>
			</div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>