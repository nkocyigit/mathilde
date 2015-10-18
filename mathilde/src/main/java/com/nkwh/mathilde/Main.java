package com.nkwh.mathilde;

import com.google.inject.Injector;
import com.nkwh.mathilde.core.injector.CoreModuleInjector;
import com.nkwh.mathilde.res.AppConstants;
import com.nkwh.mathilde.res.InternationalizationManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application 
{
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		try 
		{
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/com/nkwh/mathilde/ui/UIMain.fxml"));
			
			final Injector injector = CoreModuleInjector.getInjector();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nkwh/mathilde/ui/UIMain.fxml"));
			
			loader.setControllerFactory(new Callback<Class<?>, Object>() 
			{				
				public Object call(Class<?> type) 
				{
					return injector.getInstance(type);
				}
			});
			
			BorderPane root = loader.<BorderPane>load();
			
			Scene scene = new Scene(root,800,600);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.getIcons().add(
					   new Image(
					      Main.class.getResourceAsStream(AppConstants.APP_ICON_PATH)));
			primaryStage.setTitle("Mathilde");
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		InternationalizationManager.getResourceBundle();
		
		launch(args);
	}
}
