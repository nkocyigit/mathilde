package com.nkwh.mathilde.ui.commands.impl;

import java.io.IOException;

import com.google.inject.Injector;
import com.nkwh.mathilde.core.injector.CoreModuleInjector;
import com.nkwh.mathilde.ui.commands.IUICommand;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public final class UICommandShowLangPreferences implements IUICommand 
{
	private final BorderPane _preferencesBorderPane;
	
	public UICommandShowLangPreferences(BorderPane preferencesBorderPane) 
	{
		this._preferencesBorderPane = preferencesBorderPane;
	}
	
	@Override
	public void runCommand() 
	{
		try 
		{
			final Injector injector = CoreModuleInjector.getInjector();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nkwh/mathilde/ui/preferences/UIPLangSets.fxml"));
			
			loader.setControllerFactory(new Callback<Class<?>, Object>() 
			{				
				public Object call(Class<?> type) 
				{
					return injector.getInstance(type);
				}
			});
						
			HBox pane = loader.<HBox>load();
			pane.setPadding(_preferencesBorderPane.getInsets());
			_preferencesBorderPane.setCenter(pane);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}	
}
