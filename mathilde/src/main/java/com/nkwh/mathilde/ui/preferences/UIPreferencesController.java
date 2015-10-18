package com.nkwh.mathilde.ui.preferences;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.nkwh.mathilde.core.event.events.AppEventLangUpdate;
import com.nkwh.mathilde.core.injector.CoreModuleInjector;
import com.nkwh.mathilde.res.InternationalizationManager;
import com.nkwh.mathilde.ui.ILangChangeUpdate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class UIPreferencesController extends BorderPane implements Initializable,ILangChangeUpdate
{
	@FXML
	private TreeView<String> treePreferences;
	
	@FXML
	private BorderPane borderPanePreferences;
	
	private TreeItem<String> itemGeneralRoot,itemGeneralLang;
	
	private final EventBus _eventBus;
	
	@Inject
	public UIPreferencesController(Provider<EventBus> eventBusProvider)
	{
		this._eventBus = eventBusProvider.get();
		_eventBus.register(this);
	}

	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		initTrees();			
	}
	
	private void initTrees()
	{
		try
		{		
			final TreeItem<String> itemDummyRoot = new TreeItem<String>("");
			itemGeneralRoot = new TreeItem<String>
				(InternationalizationManager.getResourceBundle().getString("str_general"));
		    itemGeneralLang = new TreeItem<String>
		    	(InternationalizationManager.getResourceBundle().getString("str_lang"));
		    itemGeneralRoot.getChildren().add(itemGeneralLang);		    
		    treePreferences.setRoot(itemDummyRoot);		    
		    
		    itemDummyRoot.getChildren().addAll(itemGeneralRoot);
		    
		    treePreferences.setShowRoot(false);
		    
		    treePreferences.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() 
		    {
				public void changed(ObservableValue observable, Object oldValue, Object newValue) 
		    	{
					TreeItem<String> selectedItem = (TreeItem<String>) newValue;
					System.out.println("Selected Text : " + selectedItem.getValue());
				}		    	
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void addLangSets()
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
			pane.setPadding(borderPanePreferences.getInsets());
			borderPanePreferences.setCenter(pane);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void updateControlsLanguage() 
	{
		itemGeneralRoot.setValue(InternationalizationManager.getResourceBundle().getString("str_general"));
		itemGeneralLang.setValue(InternationalizationManager.getResourceBundle().getString("str_lang"));
	}
	
	@Subscribe
	public void appLangChanged(AppEventLangUpdate e)
	{
		updateControlsLanguage();
	}
}
