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
                <s:iterator value="publicHearingsItemInfoTextList">
                	<div class="wordItem" name="<s:property value='id' />">
                    	<div name="text">
                        	<span><s:property value="text" /></span>
                        </div>
                        <div name="clear">
                        </div>
                    </div>
                </s:iterator>
            </div>
        </div>
    </div>
    <div id="theEnd"></div>
</body>
</html>