package com.nkwh.mathilde.res;

import java.util.Locale;
import java.util.ResourceBundle;

import com.nkwh.mathilde.core.sets.EnumLang;

public final class InternationalizationManager 
{	
	private static Locale _locale;
	private static ResourceBundle _resourceBundle;
	
	static
	{
		_locale = new Locale("en");
		_resourceBundle = ResourceBundle.getBundle("com.nkwh.mathilde.res.lang",_locale);
	}
	
	public static void updateLanguage(EnumLang language)
	{
		try
		{
			if(language == null)
				return;
			_locale = new Locale(language.getLangPrefix());
			_resourceBundle = ResourceBundle.getBundle("com.nkwh.mathilde.res.lang",_locale);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static ResourceBundle getResourceBundle()
	{
		return _resourceBundle;
	}
}
