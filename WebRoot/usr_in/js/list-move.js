// JavaScript Document
function listUp()
{
	var height = $("#itemList").position().top + $("#itemList").height();
	var capacity = $("#itemListDiv > div[name='list']").height() - 65;
	$("#itemList").animate({top:"-=80px"}, 300);
	var topP = $("#itemList").position().top - 80;
	if (height - 80 < capacity)
	{
		topP = capacity - $("#itemList").height();
	}
	if (topP > 0)
	{
		topP = 0;
	}
	$("#itemList").animate({top:topP + "px"}, 100);
}

function listDown()
{
	var top = $("#itemList").position().top;
	$("#itemList").animate({top:"+=80px"}, 300);
	if (top + 80 > 0)
	{
		$("#itemList").animate({top:"0px"}, 100);
	}
}