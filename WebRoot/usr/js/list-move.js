// JavaScript Document
function listUp()
{	
	var height = $("#myitemList").position().margin-top + $("#myitemList").height();
	var capacity = $("#myitemListDiv > div[name='mylist']").height() - 500;	
	alert("height-1024="+height + "; capacity=" + capacity);
	$("#myitemList").animate({"margin-top":"-=512px"}, 300);
	var topP = $("#myitemList").position().margin-top - 512;
	
	if (height - 512 < capacity)
	{
		topP = capacity - $("#myitemList").height();
		
	}
	if (topP > 0)
	{
		topP = 0;
	}

	$("#myitemList").animate({"margin-top":topP + "px"}, 100);
	//$("#myitemList").animate({top:-512 + "px"}, 100);
}

function listDown()
{
	var top = $("#myitemList").position().margin-top;
	$("#myitemList").animate({"margin-top":"+=512px"}, 300);
	if (top + 512 > 0)
	{
		$("#myitemList").animate({"margin-top":"0px"}, 100);
	}
}