package com.nkwh.mathilde.core.sets;

import com.nkwh.mathilde.res.InternationalizationManager;

public enum EnumLang 
{
	LANG_TR("tr"),
	LANG_EN("en");
	
	private final String _langPrefix;

	private EnumLang(final String langPrefix)
	{
		this._langPrefix = langPrefix;		
	}

	public String getLangPrefix()
	{
		return _langPrefix;
	}
	
	@Override
	public String toString() 
	{
		return InternationalizationManager.getResourceBundle().getString("str_lang_"+_langPrefix);
	}
}
