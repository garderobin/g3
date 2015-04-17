<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>git directory</title>
    
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
  
  <body>
 	<div id="slider">
    			<div class="frame-meta">  
					<p class="history-link js-history-link-replace">
            			<s:property value="log.committerName" />[<s:property value="log.committerEmail" />]
            		</p>
					<div class="breadcrumb">
						<a href="javascript:showCode('','',1,2);"><s:property value="log.name" /></a>
        			</div>
    			</div>
				<table class="tree-browser css-truncate" cellpadding="0" cellspacing="0">
					<thead>
        			</thead>
        			
        			<tbody class="tree-entries js-deferred-content">
        			<s:if test="!currentpath.equals('')">
        			<tr>
        				<td class="icon">
        					<img src="images/upper.jpg" />
        				</td>
        				<td>
        					<a href="javascript:showCode('<s:property value='log.name' />','<s:property value='upperpath' />',1,2);" >../</a>
        				</td>
        			</tr>
        			</s:if>
    				<s:iterator value="files" id="vers-it">
        				<tr>
        						<s:if test="isfile == true">
        							<td class="icon">
        								<img src="images/file.png" />
        							</td>
        							<td>
    									<a href="javascript:showContent('<s:property value="log.name" />','<s:property value="path" />','<s:property value="name" />',1,2);"><s:property value="name" /></a>
    								</td>
    							</s:if>
        						<s:if test="isfile == false">
        							<td class="icon">
        								<img src="images/directory.png" />
        							</td>
        							<td>
        							<s:if test="path.equals('')">
    									<a href="javascript:showCode('<s:property value='log.name' />','<s:property value='name' />',1,2);"><s:property value="name" /></a>
        							</s:if>
        							<s:if test="!path.equals('')">
    									<a href="javascript:showCode('<s:property value='log.name' />','<s:property value='path' />/<s:property value='name' />',1,2);"><s:property value="name" /></a>
        							</s:if>
        							</td>
    							</s:if>
        				</tr>
        			</s:iterator>
        			</tbody>
        		</table>
    </div>
</html>
