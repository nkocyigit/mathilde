package com.nkwh.mathilde.core.event.events;

public abstract class AppEvent<T> 
{
	private T _eventParam;
		
	public AppEvent(T eventParam)
	{
		this._eventParam = eventParam;
	}
	
	public AppEvent()
	{		
	}
	
	public T getEventParam() 
	{
		return _eventParam;
	}
}
