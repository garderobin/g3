package com.score.util;

import com.opensymphony.xwork2.ActionSupport;

public class FieldErrorGenerator 
{
	public static void addFieldError(ActionSupport action, ErrorType errorType)
	{
		String fieldName = null;
		String errorMsg = "";
		switch (errorType)
		{
		case PASSWORD_LENGTH_WRONG:
		case OLD_PASSWORD_WRONG:
		case USER_NOT_EXIST:
		case RESET_CODE_WRONG:
		case RESET_OUT_OF_DATE:
		case RESET_CODE_APPLY_SO_CLOSE:
		case NO_MAIL_REGISTERED:
			fieldName = "profile";
			break;
		case SYSTEM_STATE_NOT_SUITABLE:
		case COLLECTION_ITEM_NOT_EXIST:
		case PUBLIC_HEARINGS_ITEM_NOT_EXIST:
		case COLLECTION_ITEM_INFO_NOT_EXIST:
		case COLLECTION_ITEM_INFO_IMAGE_NOT_EXIST:
		case COLLECTION_ITEM_FILLING_IS_NOT_AT_A_PROPER_ITEM_STATE:
		case USER_IS_NOT_IN_THE_CHECKOPERATORS_LIST:
		case USER_IS_NOT_INFOPROVIDER:
		case FILE_TYPE_WRONG:
		case FILE_TOO_LARGE:
			fieldName = "system-error";
			break;
		}
		switch (errorType)
		{
		case PASSWORD_LENGTH_WRONG:
			errorMsg = "You have typed a password with wrong format.";
			break;
		case OLD_PASSWORD_WRONG:
			errorMsg = "Username or password is not correct.";
			break;
		case RESET_CODE_WRONG:
			errorMsg = "This link is out of date or you have input a wrong username.";
			break;
		case RESET_OUT_OF_DATE:
			errorMsg = "This link is out of date.";
			break;
		case RESET_CODE_APPLY_SO_CLOSE:
			errorMsg = "You have already applied to reset your password in one minute. You can try again after one minute if you still havn't received the mail at that time.";
			break;
		case NO_MAIL_REGISTERED:
			errorMsg = "You haven't registered your mail address.";
			break;
		case MAIL_EXCEPTION:
			errorMsg = "Error while send email.";
		case FILE_TYPE_WRONG:
			errorMsg = "The file you are uploading is not supported.";
			break;
		case FILE_TOO_LARGE:
			errorMsg = "The size of file that you are uploading is larger than supported.";
			break;
		}
		if (fieldName != null)
			action.addFieldError(fieldName, errorMsg);
	}
}
