<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Git版本信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
    <link href="css/git.css" media="all" rel="stylesheet" type="text/css">
    <link href="css/git2.css" media="all" rel="stylesheet" type="text/css">
	
  </head>
  
 <body class="logged_out  windows vis-public env-production  ">
 	<div id="slider">
    			<div class="frame-meta">  
					<p class="history-link js-history-link-replace">
            			<s:property value="count" /> commits</p>
					<div class="breadcrumb">
						<a href="javascript:showCode('','',1,2);">StudentManagement@g3.git</a>
        			</div>
    			</div>
				<table class="tree-browser css-truncate" cellpadding="0" cellspacing="0">
					<thead>
        			</thead>
        			
        			<tbody class="tree-entries js-deferred-content">
    				<s:iterator value="versions" id="vers-it">
        				<tr>
							<td class="content">
								<a class="version" href="javascript:showCode('<s:property value='name' />','',1,2);"><s:property value="name" /></a>
							</td>
							<td class="age"><s:property value="commitTimeStamp" /></td>

							<td class="message">
								<s:property value="commitMessage" />
								[<s:property value="committerName" />(<s:property value="committerEmail" />)]
							</td>
        				</tr>
        			</s:iterator>
        			</tbody>
        		</table>
    </div>
  </body>
</html>
