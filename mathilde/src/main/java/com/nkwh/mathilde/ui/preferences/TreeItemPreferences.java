package com.nkwh.mathilde.ui.preferences;

import com.nkwh.mathilde.ui.commands.IUICommand;

import javafx.scene.control.TreeItem;

public final class TreeItemPreferences extends TreeItem <String>
{
	private final IUICommand _uiCommand;
	
	public TreeItemPreferences(String value, IUICommand uiCommand) 
	{
		super(value);
		this._uiCommand = uiCommand;
	}

	public IUICommand getUICommand() 
	{
		return _uiCommand;
	}
}
