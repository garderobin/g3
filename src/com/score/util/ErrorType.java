package com.score.util;

public enum ErrorType 
{
	/* PASSWORD */
	PASSWORD_LENGTH_WRONG,
	OLD_PASSWORD_WRONG,
	USER_NOT_EXIST,
	RESET_CODE_WRONG,
	RESET_OUT_OF_DATE,
	RESET_CODE_APPLY_SO_CLOSE,
	NO_MAIL_REGISTERED,
	/* SYSTEM ERROR */
	SYSTEM_STATE_NOT_SUITABLE,
	COLLECTION_ITEM_NOT_EXIST,
	PUBLIC_HEARINGS_ITEM_NOT_EXIST,
	COLLECTION_ITEM_INFO_NOT_EXIST,
	COLLECTION_ITEM_INFO_IMAGE_NOT_EXIST,
	COLLECTION_ITEM_FILLING_IS_NOT_AT_A_PROPER_ITEM_STATE,
	USER_IS_NOT_IN_THE_CHECKOPERATORS_LIST,
	USER_IS_NOT_INFOPROVIDER,
	USER_IS_NOT_IN_THE_CHECKOPERATORS_LIST_NOR_INFORPROVIDER,
	MAIL_EXCEPTION,
	GIT_PATH_NOT_REGISTERED,
	/* PARAMETER */
	PARAMETER_NO_GIVEN,
	FILE_TYPE_WRONG,
	FILE_TOO_LARGE,
	/* NO ERROR */
	NO_ERROR,
}
