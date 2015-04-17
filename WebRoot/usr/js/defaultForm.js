// JavaScript Document

function defaultForm($form, $info)
{
	$form.each(function()
	{
		if ($(this).attr("value") == "")
			$(this).attr("value", $info);
	});
	$form.bind("focus", function()
	{
		if ($(this).attr("value") == $info)
		{
			$(this).attr("value", "");
		}
	});
	$form.bind("blur", function()
	{
		if ($(this).attr("value") == "")
		{
			$(this).attr("value", $info);
		}
	});
}