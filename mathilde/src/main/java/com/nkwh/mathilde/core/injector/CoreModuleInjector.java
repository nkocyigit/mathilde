package com.nkwh.mathilde.core.injector;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

public final class CoreModuleInjector extends AbstractModule
{
	private final EventBus _appEventBus;
	
	private static final Injector injector = Guice.createInjector(new CoreModuleInjector());
	
	private CoreModuleInjector() 
	{
		_appEventBus = new EventBus();
	}
	
	@Override
	protected void configure() 
	{		
	}
	
	@Provides
	private EventBus provideEventBus() 
	{
		return _appEventBus;
	}
	
	public static Injector getInjector()
	{
		return injector;
	}
}
