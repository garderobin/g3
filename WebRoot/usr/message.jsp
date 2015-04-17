<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<!--STATUS OK-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>收信页</title>
<meta name="description" content=""/>
<meta name="keywords" content=""/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen"/>
	<link href="css/bootstrap-responsive.css" rel="stylesheet" media="screen"/>
    <link rel="stylesheet" href="css/common.css" />
    <link rel="stylesheet" href="css/item-optional-frame.css" />
    <link rel="stylesheet" href="css/message-write.css" type="text/css"/>
	<link rel="stylesheet" href="css/msgbase_datauri.css" type="text/css" />
	<link rel="stylesheet" href="css/boot_msgpublish_datauri.css" type="text/css" />
	<link rel="stylesheet" href="css/boot_msgpublish.css" type="text/css"/>
	<link rel="stylesheet" href="css/boot_msghome_datauri.css"  type="text/css"/>
	<link rel="stylesheet" href="css/boot_msghome.css"  type="text/css"/>
	<link rel="stylesheet" href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" type="text/css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-form/jquery.form.js" type="text/javascript" ></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/list-move2.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function()
		{
			var bootstrapButton = $.fn.button.noConflict();
			$.fn.bootstrapBtn = bootstrapButton;		

			$("#commentArea").click(function(){
				$("#inputPromp").addClass("hidden");
			});
			
			$("#commentArea").keyup(checkCommentArea);
			
			$("#messageText").keyup(checkMessageText);
			
			$("#send").mouseover(function(){
				$(this).addClass("hover");
			});
			
			$("#send").mouseout(function(){
				$(this).removeClass("hover");
			});
			
			$(".mod-editor .editor-submit-btn .enable").mouseover(function(){
				$(this).css("background","url(../images/icon_buttons_me.png) no-repeat 0 -58px;")
			});
			
			$("#modReceiverLabel").off("data-api");
			$(".dropdown-toggle").dropdown();

			$(".msgli").mouseover(function(){
				$(this).addClass("msg-hover");
			});
			
			$(".msgli").mouseout(function(){
				$(this).removeClass("msg-hover");
			});
					
			$(".msgli").click(inbox);  
			
			$(".blockli").mouseover(function(){
				$(this).addClass("block-hover");
			});
						
			$(".blockli").mouseout(function(){
				$(this).removeClass("block-hover");
			});
			$(".blockli").click(outbox);	
			
			$(".publishli").mouseover(function(){
				$(this).addClass("publish-hover");
			});
			
			$(".publishli").mouseout(function(){
				$(this).removeClass("publish-hover");
			});
			
			$(".publishli").click(writeMessage);
			
			$.ajax({
				type:"GET", 
				dataType:"json",
				url:'queryMessageByInfoTarget.action',
				success: function(data, textStatus)
				{	
					var $messageDiv = $("#inboxListDiv");
					
					$messageDiv.children().remove();
					 $.each(data.messageList, function()
					{
						var $id = this.id;
						var $message = this.message;	
						var $title = this.title;	
						var $provider = this.infoProvider.name; 	
              			var $element = $("<div class='talkitem' id='t_" + $id + "'>"+
						"<div class='talk-checkbox'><div class='qelm-checkbox qelm-checkbox-normal'>"+
                    "<input type='checkbox' name='talkcheck ' value='t_4854469012' class='qelm-checkbox-real hide'/>"+
                  "</div></div>"+
                "<a href='http://msg.baidu.com/#' class='tools'></a>"+
                "<div class='time'>4月23日 3:01</div>"+
                "<div class='st-mark'></div>"+
                "<div class='portrait-con'><img src='images/a66043686978696e6f31b328.jpg' data-src='a66043686978696e6f31b328' class='border-radius-5 border-shadow-img portraitSmall' alt='Chixino1'/>"+
                  "<div class='portrait-online'></div></div>"+
                "<div class='portrait-con-selected'><a target='_blank' href='http://www.baidu.com/p/Chixino1?from=msg'><img src='images/a66043686978696e6f31b328.jpg' data-src='a66043686978696e6f31b328' class='border-radius-5 border-shadow-img portraitSmall' alt='Chixino1'/>"+
                 " <div class='portrait-online'></div>"+
                 " </a></div>"+
               " <div class='msgcontent clearfix'>"+
                  "<div class='uname-row'>"+
                    "<div class='uname' title=''>" + $provider+"：</div>"+
                   " <div class='uname-selected' title=''><a target='_blank' href='http://www.baidu.com/p/Chixino1?from=msg' class='a-person'>" + $provider+"</a>：</div>"+
                  "</div>"+
                  "<div class='abstract-row' id='title_" + $id + "'>"+$title+"</div>"+
                   "<div class='messageText hidden' id='msgText_t_" + $id + "'>"+$message+"</div>"+
               " </div>"+
              "</div>");
						$messageDiv.append($element); 
						$element.bind("click", itemChoose); 
		
					});  
				},
				 error: function(XMLHttpRequest, textStatus, errorThrown)
				{
					alert("私信列表加载成功");
				}  
			
			});
			
			$.ajax({
				type:"GET", 
				dataType:"json",
				url:'queryMessageByInfoProvider.action',
				success: function(data, textStatus)
				{	
					var $messageDiv = $("#outboxListDiv");	
					$messageDiv.children().remove();
					 $.each(data.messageList, function()
					{
						var $id = this.id;
						var $message = this.message;	
						var $title = this.title;	
						var $provider = this.infoTarget.name; 	
              			var $element = $("<div class='talkitem' id='t_" + $id + "'>"+
						"<div class='talk-checkbox'><div class='qelm-checkbox qelm-checkbox-normal'>"+
                    "<input type='checkbox' name='talkcheck ' value='t_4854469012' class='qelm-checkbox-real hide'/>"+
                  "</div></div>"+
                "<a href='http://msg.baidu.com/#' class='tools'></a>"+
                "<div class='time'>4月23日 3:01</div>"+
                "<div class='st-mark'></div>"+
                "<div class='portrait-con'><img src='images/a66043686978696e6f31b328.jpg' data-src='a66043686978696e6f31b328' class='border-radius-5 border-shadow-img portraitSmall' alt='Chixino1'/>"+
                  "<div class='portrait-online'></div></div>"+
                "<div class='portrait-con-selected'><a target='_blank' href='http://www.baidu.com/p/Chixino1?from=msg'><img src='images/a66043686978696e6f31b328.jpg' data-src='a66043686978696e6f31b328' class='border-radius-5 border-shadow-img portraitSmall' alt='Chixino1'/>"+
                 " <div class='portrait-online'></div>"+
                 " </a></div>"+
               " <div class='msgcontent clearfix'>"+
                  "<div class='uname-row'>"+
                    "<div class='uname' title=''>" + $provider+"：</div>"+
                   " <div class='uname-selected' title=''><a target='_blank' href='http://www.baidu.com/p/Chixino1?from=msg' class='a-person'>" + $provider+"</a>：</div>"+
                  "</div>"+
                  "<div class='abstract-row' id='title_" + $id + "'>"+$title+"</div>"+
                   "<div class='messageText hidden' id='msgText_t_" + $id + "'>"+$message+"</div>"+
               " </div>"+
              "</div>");
						$messageDiv.append($element); 
						$element.bind("click", itemChoose); 
		
					});  
				},
				 error: function(XMLHttpRequest, textStatus, errorThrown)
				{
					alert("私信列表加载成功");
				}  
			
			});
			
			$.ajax({
				type:"GET", 
				dataType:"json",
				url:'queryAllUser.action',
				success: function(data, textStatus)
				{	
					var $targetUl = $("#targetUl");
					$targetUl.children().remove();
					$.each(data.users, function()
					{
						var $id = this.id;
						var $name = this.name;	 	
						var $username = this.username;
              			var $element = $("<li class='userLi' role='menuitem' tableindex='-1' href='#' id='u_" + $id + "' title='"+$username+"'>"
              			+ $name + "("+ $username +")</li>");
						$targetUl.append($element); 
						$element.bind("click", targetChoose); 
		
					});  
				},
				 error: function(XMLHttpRequest, textStatus, errorThrown)
				{
					alert("私信列表加载成功");
				}  
			
			});
			
			$("#newMessageForm").ajaxForm({
				url: 'createMessage.action',
				type: 'GET',
				dataType: 'json',
				beforeSubmit: function(formData, jqForm, options){
					var $ifErr = false;
					var $errMsg = "<strong>有如下错误，请检查后重新提交。</strong><br />";
					var $temp;
					$temp = formData[0].value;
					
					if ($temp.length <1){
						$ifErr = true;
						$errMsg += "收件人不能为空！<br />";
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
				 	mailAjax(data.messageText, data.messageTitle, data.infoTargetUsername, hour.value);										
					alert("私信发送成功！");
					window.top.location.reload();  
					outbox();
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("私信发送成功！");
				}
			}); 
		});
		
		
 		function mailAjax(messageText, messageTitle, infoTargetUsername, hour)
 		{
 			$.ajax({
				type:"GET", 
				dataType:"json",
				url:'sendMail.action',
				data:{messageTitle:messageTitle, messageText:messageText, infoTargetUsername:infoTargetUsername},
				beforeSend: function(data){				
					if(hour>0){
						alert("提醒已设置！收件人如果在"+hour+"小时内没有回复您则会收到来自系统的邮件提醒。");
						return false;
					}
					else if(hour<0){
						return false;
					}
				},
				success: function(data, textStatus)
				{	
					alert("提醒邮件已发送！");
				},
				 error: function(XMLHttpRequest, textStatus, errorThrown)
				{
					alert("邮件发送成功！");
				}  
			
			});
 		}
		
		function writeMessage()
		{
			$(".publishli").addClass("publish-selected");
			$(".msgli").removeClass("msg-selected");
			$(".blockli").removeClass("block-selected");
			$("#inboxListDiv").addClass("hidden");
			$("#inbox-content").addClass("hidden");
			$("#outboxListDiv").addClass("hidden");
			$("#write-content").removeClass("hidden");
		}	
		
		function outbox()
		{
			$(".blockli").addClass("block-selected");
			$(".publishli").removeClass("publish-selected");
			$(".msgli").removeClass("msg-selected");
			$("#write-content").addClass("hidden");
			$(".inbox").addClass("hidden");
			$("#inboxListDiv").addClass("hidden");
			$("#inbox-content").removeClass("hidden");
			$("#outboxListDiv").removeClass("hidden");
			$("#myitemList").css("margin-top", 0);
			$("#myitemListDiv").removeClass("hidden");
		}
		
		function inbox(){
				$(".msgli").addClass("msg-selected");
				$(".publishli").removeClass("publish-selected");
				$(".blockli").removeClass("block-selected");
				$("#write-content").addClass("hidden");
				$("#outboxListDiv").addClass("hidden");
				$(".inbox").removeClass("hidden");
				$("#inboxListDiv").removeClass("hidden");
				$("#inbox-content").removeClass("hidden");
				$("#myitemList").css("margin-top", 0);
			}
		
        function strlen(input) 
        {
            var str =(input + '').replace(/(\s+)$/g, '').replace(/^\s+/g, '');
            var len = 0;
            for (var i = 0; i < str.length; i++) {
                len += str.charCodeAt(i) > 0 && str.charCodeAt(i) < 255 ? 0.5 : 1;
            }
            return len;
        }

		function checkCommentArea()
		{
			var $len = strlen($("#commentArea").val());
			if($len >= 300){
					$("#modEditorLength").css("color", "red");
					$("#modEditorSubmit").attr("disabled", "disabled");
					$("#modEditorSubmit").removeClass("enable");
			}
			else if($len>0){
				$("#modEditorSubmit").addClass("enable");
			}
			$("#modEditorLength").text($len+"/300");
		}
			
		
		function checkMessageText(){
			var $len1 = strlen($("#messageText").val());
			var $len0 = strlen($("#infoTarget").val());
			if($len0>0 && $len1>0){
				$(".editor-submit-btn").removeClass("disable");
				$(".editor-submit-btn").addClass("hover");
			}
			
		}
			
		function itemChoose()
		{
		    $("#myitemList div[class='talkitem talkitem-selected']").removeClass("talkitem-selected");
		    $(this).addClass("talkitem-selected");
			var $id = this.id; 
			var $message = document.getElementById("msgText_"+$id).innerText;
			document.getElementById("contentP").innerHTML=$message;
		}
		
 		function targetChoose()
 		{
 			var $username = $(this).attr("title");
 			$("#write-content input[name='infoTargetUsername']").attr("value", $username);
 		}
 		
 		function sendMail(hour)
 		{
 			$("#hour").attr("value", hour);
 			if(hour>0){
 				$("#email").text(hour+"h");
 			}
 		}
 		
 		function publishThought()
 		{
 			var $initText = $("#contentP").text();
 			var $comment = $("#commentArea").val();
 			var $message = $comment + "（以下来自私信原文：）"+$initText;
 			$.ajax({
				type:"POST", 
				dataType:"json",
				url:'createThought.action',
				data:{message:$message, publish:"true"},
				beforeSend: function(data){				
					//alert($message);
				},
				success: function(data, textStatus)
				{	
					alert("私信成功推荐到首页！");
				},
				 error: function(XMLHttpRequest, textStatus, errorThrown)
				{
					alert("私信成功推荐到首页！");
				}  
			
			});
 		}
 		
	</script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="mod-topbar" id="modTopbar">
  <div class="mod-topbar-pseudo-real">
      <div class="wrapper-box clearfix">
    </div>
  </div>
</div>
<section class="mod-page-body clearfix">
  <div class="mod-page-main wordwrap clearfix">
    <div class="x-page-container">
      <div class="mod-main grid-98 border-shadow-box clearfix">
        <div class="mod-nav grid-7 clearfix">
          <div class="nav-inner height-msg-inner">
            <ul class="nav-top">
              <li class="msg-selected msgli">
                <div class="navli-inner">
                  <div class="unread-tip hide"></div>
                  <div class="msg-button"></div>
                  <div class="msg-nav-title">收信</div>
                </div>
              </li>
              <li class="block-normal blockli">
                <div class="navli-inner">
                  <div class="block-button"></div>
                  <div class="msg-nav-title">已发</div>
                </div>
              </li>
              <li class="publish-normal publishli">
                <div class="navli-inner">
                  <div class="publish-button"></div>
                  <div class="msg-nav-title">写信</div>
                </div>
              </li>              
            </ul>
            <ul class="nav-bottom ">              
            </ul>
          </div>
        </div>
        <div class="mod-content grid-91 clearfix" id="inbox-content">
          <div class="mod-notalk-page" style="display:none">
            <div class="notalk-icon"></div>
            <div class="notalk-tips">您还没收到私信哦！</div>
            <a href="http://msg.baidu.com/msg/writing" class="notalk-button a-button">马上写私信</a></div>
          <div id="myitemListDiv" class="mod-talklist clearfix inbox" style="display:block">
            <div class="loadMask" style="display: none;"></div>
            <div name="mylist">
            <div class="list-container" id="myitemList">
              <div id="inboxListDiv">
              <div class="talkitem talkitem-selected" id="t_4854469012" title="haha">
                <div class="talk-checkbox">
                  <div class="qelm-checkbox qelm-checkbox-normal">
                    <input type="checkbox" name="talkcheck " value="t_4854469012" class="qelm-checkbox-real hide"/>
                  </div>
                </div>
                <div class="time">4月23日 3:01</div>
                <div class="st-mark"></div>
                <div class="portrait-con"><img src="images/a66043686978696e6f31b328.jpg" data-src="a66043686978696e6f31b328" class="border-radius-5 border-shadow-img portraitSmall" alt="Chixino1"/>
                  <div class="portrait-online"></div>
                </div>
                <div class="portrait-con-selected"><a target="_blank" href="#"><img src="images/a66043686978696e6f31b328.jpg" data-src="a66043686978696e6f31b328" class="border-radius-5 border-shadow-img portraitSmall" alt="Chixino1"/>
                  <div class="portrait-online"></div>
                  </a></div>
                <div class="msgcontent clearfix">
                  <div class="uname-row">
                    <div class="uname" title="">Chixino1：</div>
                    <div class="uname-selected" title=""><a target="_blank" href="#" class="a-person">Chixino1</a>：</div>
                  </div>
                  <div class="abstract-row">兄弟，你是不是有 奥尼尔...</div>
                </div>
              </div>
              </div>
              <div id="outboxListDiv" class="hidden">
              <div class="talkitem talkitem-selected" id="t_4854469012" title="haha">
                <div class="talk-checkbox">
                  <div class="qelm-checkbox qelm-checkbox-normal">
                    <input type="checkbox" name="talkcheck " value="t_4854469012" class="qelm-checkbox-real hide"/>
                  </div>
                </div>
                <a href="http://msg.baidu.com/#" class="tools"></a>
                <div class="time">4月23日 3:01</div>
                <div class="st-mark"></div>
                <div class="portrait-con"><img src="images/a66043686978696e6f31b328.jpg" data-src="a66043686978696e6f31b328" class="border-radius-5 border-shadow-img portraitSmall" alt="Chixino1"/>
                  <div class="portrait-online"></div>
                </div>
                <div class="portrait-con-selected"><a target="_blank"><img src="images/a66043686978696e6f31b328.jpg" data-src="a66043686978696e6f31b328" class="border-radius-5 border-shadow-img portraitSmall" alt="Chixino1"/>
                  <div class="portrait-online"></div>
                  </a></div>
                <div class="msgcontent clearfix">
                  <div class="uname-row">
                    <div class="uname" title="">Chixino1：</div>
                    <div class="uname-selected" title=""><a target="_blank" href="#" class="a-person">Chixino1</a>：</div>
                  </div>
                  <div class="abstract-row">兄弟，你是不是有 奥尼尔...</div>
                </div>
              </div>
              </div>
            </div>
          </div> 
            <div class="mod-pager clearfix">
              <div id="mod_talk_paper" class="tang-pager">
                <div name="control">
                    <a name="up" href="javascript:listUp();">up</a>
                    <a name="down" href="javascript:listDown();">down</a>
                </div>
              </div>
            </div>
          </div>
          <div class="mod-msglist clearfix" style="display:block">
            <div class="loadMask" style="display: none;"></div>
            <div class="msg-unopen-page" id="msg_unopen_page" style="display: none;">
              <div class="unopen-icon"></div>
              <div class="unopen-tips">您还没有打开任何私信</div>
            </div>
            <div class="mod-msgcontainer" id="mod_msgcontainer" style="display: block;">
              <div class="msgtoolbar clearfix">
                <div class="ptitle">
                  <div id="normal-msgtoolbar" class="">
                  <div id="sys-msgtoolbar" class="hide">来自<span class="fb uname-bg" id="editor_talker_sys" title="该帐号为官方认证账号"></span>的通知<span class="num">（共条）</span></div>
                </div>
                <div class="tools" id="talk_tools" href="#"></div>
                <div class="toolbox" id="talk_toolbox"> <a id="talk_toolbox_del" href="#" class="toolitem">删除私信</a> <a id="talk_toolbox_block" href="#" class="toolitem" style="display: block;">屏蔽此人</a> <a id="talk_toolbox_tousu" href="3" target="_blank" class="toolitem last">投诉</a> </div>
              </div>
              <div class="no-msg-tip" id="no_msg_tip" style="display: none;"></div>
              <ul class="msg-container" id="msg_container" style="display: block;">
                <li id="4854469012">
                  <div class="msgitem leftitem clearfix">
                    <div class="portrait-con left"><a target="_blank" href="#"><img src="images/a66043686978696e6f31b328.jpg" data-src="a66043686978696e6f31b328" class="border-radius-5 border-shadow-img portrait" alt="Chixino1"/></a></div>
                    <div class="msg-content-header left"></div>
                    <div class="msg-content-body left"><a class="close" href="#" style="display: none;"></a>
                      <div class="content">
                        <p class="abstract" id="contentP">hello</p>
                        <p class="msgertime"><span class="time">4月23日 3:01</span><a href="#" class="reply hide">回复</a></p>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div id="modEditor" class="mod-editor clearfix inactive" style="display: block;">
              <div class="editor-bg"></div>
              <div class="editor-inner clearfix">
                <div class="editor-textbox">
                  <div class="editor-textarea" id="msgTextareaCon">
                    <div class="qelm-msgeditor clearfix">
                      
                      <div class="qelm-msgeditor-editorbox">
                        <label for="msgcontent" id="inputPromp" class="msglabel" style="display: block;">输入私信内容</label>
                        <textarea name="msgcontent" class="msgtextarea" id="commentArea"></textarea>
                      </div>
                    </div>
                  </div>
                  <div class="editor-console">
                    <input type="button" id="modEditorSubmit" class="editor-submit-btn" onclick="javascript:publishThought()" value="推首页"/>
                    <div id="modEditorLength" class="editor-length-count">0/1000</div>
                    <div class="editor-sending"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

	  <div class="mod-content grid-91 clearfix hidden" id="write-content">
		<div class="mod-publish grid-68">
			<div class="inner">
				<form id="newMessageForm" action="createMessage.action">       		
					<!-- <input type="hidden" name="" id=""> -->
					<div class="mes-row">
						<!-- <div class="labelword">  -->
							<table class=email>
       							 <tbody>
       								 <tr>
          								<td>
          								<div class="labelword dropdown">
          									<a id="to_btn" class="dropdown-toggle" data-toggle="dropdown" data-target="#">收件人</a>
          									<ul id="targetUl" class="dropdown-menu" role="menu" area-labelledby="to_btn">
          									</ul>
          								</div>
          								</td>
          								<td>
          								<div class="con-input">									
											<input type="text" name="infoTargetUsername" id="infoTarget" class="input-box" />
										</div>
                                        <div class="divtxt" id="divtxt" name="infoTargetUsername"></div>
											
										</td>
									</tr>
								</tbody>
	  						</table>
					</div>
					<div class="mes-row" id="titleDiv">
						<div class="labelword">标题</div>
						<div class="con-input">
							<input type="text" name="messageTitle" id="titleText"  class="input-box"/>
						</div>
					</div>
					<div class="mes-row" id="textDiv">
						<div class="labelword">内容</div>
						<div class="con-input clear-fix">
							<textarea name="messageText" id="messageText"  class="input-box"></textarea>
						</div>
					</div>
					<div class="mes-row btn-group" id="remindDiv">
						<div class="labelword">提醒</div>
						<button name="email" id="email" value="设置邮件" class="dropdown-toggle editor-submit-btn disable" data-toggle="dropdown">设置邮件</button>
						<input type="hidden" id="hour" name="hour" value="-1"/>
							<ul id="emailUl" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">	
								<li><a tableindex="-1" href="javascript:sendMail(0)">即刻发送</a></li>
								<li><a tableindex="-1" href="javascript:sendMail(6)">6小时</a></li>
								<li><a tableindex="-1" href="javascript:sendMail(12)">12小时</a></li>
								<li><a tableindex="-1" href="javascript:sendMail(24)">24小时</a></li>
								<li><a tableindex="-1" href="javascript:sendMail(48)">2天</a></li> 
								
							</ul>
						
					</div>
					<div class="mes-row" id="submitDiv">
						<input type="submit" name="submit" id="send" value="发送" class="editor-submit-btn disable"></input>						
					</div>
        		</form>
			</div>
		  </div>
		  <div class="mod-friends"></div> 
		</div>
	  </div>
	</div>
  </div>
</section>
 <%@ include file="footer.jsp" %>
    <div id="saveSuccessDialog" title="消息框" class="hidden">
		<span>保存成功！</span>
	</div>
	<div id="deleteSuccessDialog" title="消息框" class="hidden">
		<span>删除成功！</span>
	</div> 

</body>
</html>
