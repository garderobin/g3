<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	
<link href="css/git.css" media="all" rel="stylesheet" type="text/css">
<link href="css/git2.css" media="all" rel="stylesheet" type="text/css">

<script type="text/javascript">
	String.prototype.endWith = function(str){     
	      var reg = new RegExp(str + "$");     
	      return reg.test(this);        
	};
	
	$(function() {
		var $filename = "<s:property value='name' />";
		if ($filename.endWith("html") || $filename.endWith("jsp"))
			$("#content").addClass("brush-html");
		else if ($filename.endWith("js"))
			$("#content").addClass("brush-javascript");
		else if ($filename.endWith("css"))
			$("#content").addClass("brush-css");
		else if ($filename.endWith("java"))
			$("#content").addClass("brush-ooc");
		else
			alert ("not html");
		$.syntax();
	});
</script>
  
<div id="slider">
	<div class="frame-meta">  
		<p class="history-link js-history-link-replace">
       		<s:property value="log.committerName" />[<s:property value="log.committerEmail" />]<br />
		</p>
		<div class="breadcrumb">
			<s:property value="currentPath" /> in <a href="javascript:showCode('','',1,2);"><s:property value="log.name" /></a> <br/>
				return to <a href="javascript:showCode('<s:property value='log.name' />','<s:property value='currentpath' />',1,2);"><s:property value="currentpath" /></a>
		</div>
	</div>
	<pre id="content" class="syntax"><s:property value="content" />
	</pre>
</div>