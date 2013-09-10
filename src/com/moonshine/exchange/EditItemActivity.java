/** ---------------------------------------------------------------------------
 * File:        EditItemActivity.java
 * Author:      Pekka Mäkinen
 * Description: Activity for editing cocktail information.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.util.List;
import java.util.Vector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends TabHostBaseActivity
{
    ContentParser m_ContentParser;
    Cocktail m_Cocktail;
    List< Fragment > m_Fragments;
    
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		
		/* Get the position of the item (of the main listview) 
		 * that is to be edited.
		 */
		Intent intent = getIntent();
		int pos = intent.getIntExtra( MainActivity.EXTRA_POSITION, -1 );
		if( pos == -1 )
		{
			/* This intent should not be called without proper extra.
			 * Just show some info-text and finish the activity for now. 
			 */
			Toast.makeText( getApplicationContext(),
		    	"Oops, could not find the selected item.", 
				Toast.LENGTH_LONG ).show();
			finish();
		}
		
		// Get the Recipe we are editing and set title for the activity.
		m_ContentParser = ContentParser.getInstance();
		m_Cocktail = m_ContentParser.getRecipeList().get( pos );
		setTitle( "Edit " + m_Cocktail.getName() + "..." );
		
		//MethodFragment mf = ( MethodFragment )m_Fragments.get( 1 ); //.m_EditText.setText( m_Cocktail.getMethod() );
		//mf.m_EditText.setText( m_Cocktail.getMethod() );
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
		public EditText m_EditText;
		
		@Override
	    public View onCreateView( LayoutInflater inflater, ViewGroup container,
	    	Bundle savedInstanceState ) 
		{
	        // Inflate the layout for this fragment
			View v = inflater.inflate( R.layout.edit_method_fragment,
		        container, false );
			
			m_EditText = ( EditText )v.findViewById( R.id.edit_cocktail_method );

	        return v;
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
		
		m_Fragments = new Vector< Fragment >();
		m_Fragments.add( Fragment.instantiate( 
			this, ComponentFragment.class.getName() ) );
		m_Fragments.add( Fragment.instantiate(
			this, MethodFragment.class.getName() ) );
		
		return m_Fragments;
	}

}
