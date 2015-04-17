<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>测试感想发布</title>
</head>

<body>
    <div>
    	<s:form action="createThought" namespace="/usr">
    		 <s:textfield name="itemId" label="itemId" ></s:textfield>
    		 <s:textarea  name="message" label="感想" cols="100" rows="10">
    		 </s:textarea>
    		 <s:submit value="发布"></s:submit>
    	</s:form>
    </div>
</body>
</html>