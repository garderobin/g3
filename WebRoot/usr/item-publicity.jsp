<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>项目设定-评分系统</title>
    <link rel="stylesheet" href="css/common.css" />
    <link rel="stylesheet" href="css/item-optional-frame.css" />
    <link rel="stylesheet" href="css/item-publicity.css" />
    <link href="../plugins/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="../plugins/jquery-jqplot/jquery.jqplot.min.css" />
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="../plugins/jquery-ui/js/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script language="javascript" type="text/javascript" src="../plugins/jquery-jqplot/jquery.jqplot.min.js"></script>
	<script language="javascript" type="text/javascript" src="../plugins/jquery-jqplot/plugins/jqplot.pieRenderer.min.js"></script>
    <script src="../plugins/jquery-corner/jquery.corner.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/list-move.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function()
		{
			$("#itemList li[name='allScore']").bind("click", itemChooseA);
			$("#itemList li[name='collectionItem']").bind("click", itemChooseC);
			$("#itemList li[name='publicHearingsItem']").bind("click", itemChooseP);
			$("#itemListDiv div[name='indexDiv']").bind("click", itemChooseI);

			$nowEditingId = null;
			$("#itemListDiv div[name='indexDiv']").addClass("current");
			$("#header .global-subnav li[name='item-publicity']").addClass("current");
			
			$("#itemListDiv, #itemListDiv > div[name='list']").corner("left");
			$("#contents").corner("right");
		});

		function itemChooseI()
		{
			itemChoose.call(this, -1);
		}

		function itemChooseA()
		{
			itemChoose.call(this, 0);
		}
 
		function itemChooseC()
		{
			itemChoose.call(this, 1);
		}
		
		function itemChooseP()
		{
			itemChoose.call(this, 2);
		}
		
		function itemChoose(type)
		{
			$("#itemList li").removeClass("current");
			$("#itemListDiv div[name='indexDiv']").removeClass("current");
			$nowEditingId = $(this).attr("value");
			if (type == -1)
				$nowEditingType = null;
			else if (type == 0)
				$nowEditingType = 0;
			else if (type == 1)
				$nowEditingType = 1;
			else if (type == 2)
				$nowEditingType = 2;
			if ($nowEditingId > 0 || $nowEditingType == 0)
			{
				$.get(
					'queryScoreByItemId.action', 
					{type:$nowEditingType, itemId: $nowEditingId}, 
					setResult, 
					'json'
				);
			}
			else
				setResult();
			$(this).addClass("current");
		}

		function setResult(data)
		{
			var $scoreDiv = $("#itemDiv div[name='scoreDiv']");
			var $scoreSpan = $("#itemDiv div[name='scoreDiv'] span[name='score']");
			var $rankDiv = $("#itemDiv div[name='rankDiv']");
			var $rankSpan = $("#itemDiv div[name='rankDiv'] span[name='rank']");
			if (data == null)
			{
				$scoreDiv.addClass("hidden");
				$rankDiv.addClass("hidden");
				return;
			}
			$scoreDiv.removeClass("hidden");
			var score, rank, total;
			if (data.type == 0)
			{
				score = data.totalScore.value;
				rank = data.totalScore.rank;
			}
			else if (data.type == 1)
			{
				score = data.collectionItemScore.value;
				rank = data.collectionItemScore.rank;
			}
			else if (data.type == 2)
			{
				score = data.publicHearingsItemScore.value;
				rank = data.publicHearingsItemScore.rank;
			}
			total = data.totalUserSize;
			score = Math.round(score * 100)/100;
			$scoreSpan.text(score);
			if (rank != null)
			{
				$rankDiv.removeClass("hidden");
				var line = [['people in front of you', rank - 1], ['people after you', total - rank]];
				$.jqplot('rankChart', [line], {
					//title:'pieRenderer ',//设置饼状图的标题
					seriesDefaults: 
					{
						fill: true, 
						showMarker: false, 
						shadow: false,
						renderer:$.jqplot.PieRenderer,
						rendererOptions:
						{
							diameter: undefined,
							padding: 20,
							sliceMargin: 9,
							fill:true,
							shadow:true,
							shadowOffset: 2,
							shadowDepth: 5,
							shadowAlpha: 0.07
						}
					},
					legend:
					{
						show: true,
						location: 's'
					}     
				});
			}
			else
				$rankDiv.addClass("hidden");
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
                    	<li name="allScore" > 
                            	总成绩
                        </li>
                        <s:iterator value="publicHearingsItemList" id="itemsP">
                        	<s:if test="type==0">
                            <li value=<s:property value="id" /> name="publicHearingsItem" > 
                                <s:property value="itemId" />&nbsp;:&nbsp;<s:property value="name" />
                            </li>
                            </s:if>
                        </s:iterator>
                        <s:iterator value="collectionItemList" id="itemsC">
                            <li value=<s:property value="id" /> name="collectionItem" > 
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
            <div id="markDiv" class="hidden">
            </div>
            <div id="itemDiv">
                <div name="scoreAndRankDiv">
                	<div name="scoreDiv" class="hidden">
                    	<span>您的分数是：</span><span name="score"></span>
                    </div>
                    <div name="rankDiv" class="hidden">
                        <div id="rankChart">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
	<div id="saveErrorDialog" title="消息框" class="hidden">
		<span>失败</span>
	</div>
</body>
</html>