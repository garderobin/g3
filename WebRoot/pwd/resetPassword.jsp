<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>密码修改-评分系统</title>
    <link rel="stylesheet" href="css/common.css" />
    <link rel="stylesheet" href="css/password.css" />
    <link href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function()
		{
			$("#contents").corner();
		});
		
		function submit()
		{
			var $newPassword = $("#password-div input[name='newPassword']").attr("value");
			if ($newPassword != $("#password-div input[name='newPassword-confirm']").attr("value"))
			{
				alert("两次输入的密码不一致");
			}
			else if ($newPassword.length < 5 || $newPassword.length > 16)
			{
				alert("密码长度应在5-16间");
			}
			else
			{
				$("#password-form").submit();
			}
		}
	</script>
</head>

<body>
	<div id="wrapper">
        <div id="contents">
        	<div id="password-div">
            	<table>
                	<s:if test="errorCode != null">
                	<tr name="error">
                    	<td colspan="2">
                        	<span>
                            	<s:if test="errorCode == 4">
                                	该链接已经失效
                                </s:if>
                            </span>
                        </td>
                    </tr>
                    </s:if>
                	<form id="password-form" action="resetPassword.action" method="post">
                    	<input type="hidden" name="resetCode" value="<s:property value='resetCode' />" />
                	<tr>
                    	<td name="name">
                        	用户名：
                        </td>
                        <td name="input">
                			<input name="username" type="text" />
                        </td>
                    </tr>
                    <tr>
                    	<td>
                        	新密码：
                        </td>
                        <td>
                        	<input name="newPassword" type="password" />
                        </td>
                    </tr>
                    <tr>
                    	<td>
                        	确认密码：
                        </td>
                        <td>
                        	<input name="newPassword-confirm" type="password" />
                        </td>
                    </tr>
                    <tr>
                    	<td colspan="2">
                        	<div name="submit">
                        		<a name="submit" href="javascript:submit();">修改</a>
                            </div>
                        </td>
                    </tr>
                    </form>
                </table>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>