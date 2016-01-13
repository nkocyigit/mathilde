package com.nkwh.mathilde.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.nkwh.mathilde.core.event.events.AppEventLangUpdate;
import com.nkwh.mathilde.core.injector.CoreModuleInjector;
import com.nkwh.mathilde.res.AppConstants;
import com.nkwh.mathilde.res.InternationalizationManager;
import com.nkwh.mathilde.ui.designcanvas.CanvasProjectDesign;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public final class UIMainController implements Initializable,ILangChangeUpdate
{
	private Stage _stagePreferences;
	
	private final EventBus _eventBus;
	
	@FXML
	private Menu mnFile,mnEdit,mnHelp;
	@FXML
	private MenuItem mnItemClose,mnItemPreferences,mnItemAbout;
	@FXML
	private AnchorPane paneDesignArea;
	
	@Inject
	public UIMainController(Provider<EventBus> eventBusProvider)
	{
		this._eventBus = eventBusProvider.get();
		_eventBus.register(this);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		mnFile.setText(InternationalizationManager.getResourceBundle().getString("str_file"));
		mnEdit.setText(InternationalizationManager.getResourceBundle().getString("str_edit"));
		mnHelp.setText(InternationalizationManager.getResourceBundle().getString("str_help"));
		mnItemClose.setText(InternationalizationManager.getResourceBundle().getString("str_close"));
		mnItemPreferences.setText(InternationalizationManager.getResourceBundle().getString("str_preferences"));
		mnItemAbout.setText(InternationalizationManager.getResourceBundle().getString("str_about"));
		
		CanvasProjectDesign canvasProjectDesign = new CanvasProjectDesign();
		AnchorPane.setTopAnchor(canvasProjectDesign, 0d);
		AnchorPane.setBottomAnchor(canvasProjectDesign, 0d);
		AnchorPane.setLeftAnchor(canvasProjectDesign, 0d);
		AnchorPane.setRightAnchor(canvasProjectDesign, 0d);
		paneDesignArea.getChildren().add(canvasProjectDesign);
		canvasProjectDesign.widthProperty().bind(paneDesignArea.widthProperty());
		canvasProjectDesign.heightProperty().bind(paneDesignArea.heightProperty());
		paneDesignArea.setVisible(true);
		canvasProjectDesign.drawShapes();
	}
	
	public void menuItemPreferencesClicked()
	{
		try
		{
			final Injector injector = CoreModuleInjector.getInjector();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nkwh/mathilde/ui/preferences/UIPreferences.fxml"));
			
			loader.setControllerFactory(new Callback<Class<?>, Object>() 
			{				
				public Object call(Class<?> type) 
				{
					return injector.getInstance(type);
				}
			});
			
			BorderPane root = loader.<BorderPane>load();
			
			Scene scene = new Scene(root,600,400);
			_stagePreferences = new Stage();
			_stagePreferences.initModality(Modality.APPLICATION_MODAL);
			_stagePreferences.setScene(scene);
			_stagePreferences.getIcons().add(new Image(
					UIMainController.class.getResourceAsStream(AppConstants.APP_ICON_PATH)));
			_stagePreferences.setTitle(InternationalizationManager.getResourceBundle().getString("str_preferences"));
			_stagePreferences.setMinWidth(600);
			_stagePreferences.setMinHeight(400);
			_stagePreferences.setMaxWidth(600);
			_stagePreferences.setMaxHeight(400);
			_stagePreferences.setMaximized(false);
			_stagePreferences.setResizable(false);
			_stagePreferences.show();
		}
		catch(Exception e)
		{		
			e.printStackTrace();
		}
	}
	
	public void menuItemCloseClicked()
	{
		Platform.exit();
	    System.exit(0);
	}
	
	@Override
	public void updateUIControlsLanguage() 
	{
		_stagePreferences.setTitle(InternationalizationManager.getResourceBundle().getString("str_preferences"));
		mnFile.setText(InternationalizationManager.getResourceBundle().getString("str_file"));
		mnEdit.setText(InternationalizationManager.getResourceBundle().getString("str_edit"));
		mnHelp.setText(InternationalizationManager.getResourceBundle().getString("str_help"));
		mnItemClose.setText(InternationalizationManager.getResourceBundle().getString("str_close"));
		mnItemPreferences.setText(InternationalizationManager.getResourceBundle().getString("str_preferences"));
		mnItemAbout.setText(InternationalizationManager.getResourceBundle().getString("str_about"));
	}
	
	@Subscribe
	public void appLangChanged(AppEventLangUpdate e)
	{
		updateUIControlsLanguage();
	}
}
