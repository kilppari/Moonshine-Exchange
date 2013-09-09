/** ---------------------------------------------------------------------------
 * File:        EditItemActivity.java
 * Author:      Pekka Mäkinen
 * Description: Activity for editing cocktail information.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditItemActivity extends TabHostBaseActivity
{
    
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setTitle( "Edit.." );
	}
	
	public static class ComponentFragment extends Fragment
	{
		@Override
	    public View onCreateView( LayoutInflater inflater, ViewGroup container,
	    	Bundle savedInstanceState ) 
		{
	        // Inflate the layout for this fragment
	        return inflater.inflate( R.layout.edit_component_fragment,
	        	container, false );
	    }
	}
	
	public static class MethodFragment extends Fragment
	{
		@Override
	    public View onCreateView( LayoutInflater inflater, ViewGroup container,
	    	Bundle savedInstanceState ) 
		{
	        // Inflate the layout for this fragment
	        return inflater.inflate( R.layout.edit_method_fragment,
	        	container, false );
	    }
	}
	

	@Override
	public void setupTabs() {
		// TODO Auto-generated method stub
		addTab( this, m_TabHost, m_TabHost.newTabSpec("Tab1").setIndicator( "Components" ) );
		addTab( this, m_TabHost, m_TabHost.newTabSpec("Tab2").setIndicator( "Instructions" ) );
	}

	@Override
	protected List<Fragment> setupFragments() {
		
		List< Fragment > fragments = new Vector< Fragment >();
		fragments.add( Fragment.instantiate( 
			this, ComponentFragment.class.getName() ) );
		fragments.add( Fragment.instantiate(
			this, MethodFragment.class.getName() ) );
		
		return fragments;
	}

}
