package com.nkwh.mathilde.ui.preferences;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.nkwh.mathilde.core.event.events.AppEventLangUpdate;
import com.nkwh.mathilde.core.sets.EnumLang;
import com.nkwh.mathilde.res.InternationalizationManager;
import com.nkwh.mathilde.ui.ILangChangeUpdate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public final class UIPLangSetsController implements Initializable,ILangChangeUpdate
{
	@FXML
	private ComboBox<EnumLang> cmbLangs;
	
	@FXML
	private Label lblLang;
	
	private final EventBus _eventBus;
	
//	@Inject
//	public UIPLangSetsController(EventBus eventBus)
//	{
//		this._eventBus = eventBus;
//		_eventBus.register(this);
//	}
	
	@Inject
	public UIPLangSetsController(Provider<EventBus> eventBusProvider)
	{
		this._eventBus = eventBusProvider.get();
		_eventBus.register(this);
	}

	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		initControls();
	}
	
	private void initControls() 
	{
		lblLang.setText(InternationalizationManager.getResourceBundle().getString("str_lang"));
		cmbLangs.setPromptText(InternationalizationManager.getResourceBundle().getString("str_lang"));
		cmbLangs.getItems().setAll(EnumLang.values());

		cmbLangs.valueProperty().addListener(new ChangeListener<EnumLang>() 
		{
			public void changed(ObservableValue observable, EnumLang oldValue, EnumLang newValue) 
			{
				InternationalizationManager.updateLanguage(newValue);
				_eventBus.post(new AppEventLangUpdate());
			}
		});
	}

	@Override
	public void updateUIControlsLanguage() 
	{
		lblLang.setText(InternationalizationManager.getResourceBundle().getString("str_lang"));
		cmbLangs.setPromptText(InternationalizationManager.getResourceBundle().getString("str_lang"));
		cmbLangs.getItems().setAll(EnumLang.values());
	}
	
	@Subscribe
	public void appLangChanged(AppEventLangUpdate e)
	{
		updateUIControlsLanguage();
	}	
}