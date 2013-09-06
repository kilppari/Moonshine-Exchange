package com.moonshine.exchange;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;

public class MainViewModeListener implements MultiChoiceModeListener {

	MainViewModeListener(){}
	
	@Override
	public boolean onActionItemClicked(ActionMode arg0, MenuItem arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCreateActionMode( ActionMode mode, Menu menu )
	{
		// Inflate menu
		MenuInflater inflater = mode.getMenuInflater();
		// TODO Proper menu, 'new_item' is just for test. 
		inflater.inflate(R.menu.contextual_menu, menu );
		return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode arg0, int arg1, long arg2,
			boolean arg3) {
		// TODO Auto-generated method stub

	}

}
