/** ---------------------------------------------------------------------------
 * File:        NewItemActivity.java
 * Author:      Pekka Mäkinen
 * Version:     0.1
 * Description: Activity for setting up a form for a new cocktail recipe.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.moonshine.exchange.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

/**
 * Activity for setting up a form for a new cocktail recipe.
 */
public class NewItemActivity extends Activity {
	
	
	private int m_CurrentItemType = 0;
	private LinearLayout m_ComponentLayout;
	//private int m_ComponentCount = 0;
	//private ArrayList< ComponentView > m_ComponentList;
//	private List< ComponentView > m_ComponentViewRefs;

	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.new_recipe );
		// Show the Up button in the action bar.
		setupActionBar();
		
		m_ComponentLayout = ( LinearLayout )findViewById( 
			R.id.component_layout );
		
		ImageButton button = ( ImageButton )findViewById(
			R.id.button_add_component_form );
		

		
		button.setOnClickListener( new ImageButton.OnClickListener()
		{
			@Override
			public void onClick( View view ) {
				addComponentForm();
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled( true );

	}

	@Override
	protected void onSaveInstanceState( Bundle outState ) {
	    super.onSaveInstanceState( outState );

		ArrayList< Component > components = getComponents();
		
	    if( !components.isEmpty() )
	    {
	    	outState.putParcelableArrayList( "COMPONENTS", components );
	    }
	    
	}

	@Override
	protected void onRestoreInstanceState( Bundle savedInstanceState ) {
	    super.onRestoreInstanceState( savedInstanceState );
	    //get the views back...
	    /* Get any stored views */
	    ArrayList< Component > components = 
	    	savedInstanceState.getParcelableArrayList( "COMPONENTS" );
	    
	    if( components != null )
	    {
		    //add the views back to Activity
			LayoutInflater inflater = ( LayoutInflater )
				getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		    for( int i = 0; i < components.size(); i++ )
		    {
				LinearLayout component_view = ( LinearLayout )inflater.inflate(
				    R.layout.input_form_component, null );
				
				( ( EditText )component_view.getChildAt( 0 ) ).setText( 
					components.get( i ).getName() );
				( ( Spinner )component_view.getChildAt( 1 ) ).setSelection( 
					components.get( i ).getAmountIdx() );			
				( ( Spinner )component_view.getChildAt( 2 ) ).setSelection( 
					components.get( i ).getUnitIdx() );
				
				m_ComponentLayout.addView( component_view );
		    }
	    }
	    
	}


	void addComponentForm()
	{	
		/* Use LayoutInflater to make a custom ViewGroup for a 
		 * cocktail component input form. 
		 */
		LayoutInflater inflater = ( LayoutInflater )
			getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View component_view = inflater.inflate(
        	R.layout.input_form_component, null );
		

		
		m_ComponentLayout.addView( component_view );
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.new_item, menu );
		return true;
	}

	private String getName()
	{
		return( ( EditText )findViewById( 
			R.id.new_cocktail_name ) ).getText().toString();
	}
	
	private String getBaseSpirit()
	{
		return( ( EditText )findViewById( 
			R.id.new_cocktail_base_spirit ) ).getText().toString();
	}

	private ArrayList< Component > getComponents()
	{
		ArrayList< Component > components = new ArrayList< Component >();
	    
		/* Save the dynamically created component forms and the data of those forms */
	    for( int i = 1; i < m_ComponentLayout.getChildCount(); i++ )
	    {
	    	LinearLayout component = ( LinearLayout )m_ComponentLayout.getChildAt( i );
	
	    	/* Get child views based on 'input_form_component' layout */
	    	EditText name_view = ( EditText )component.getChildAt( 0 );
	    	Spinner amount_view = ( Spinner )component.getChildAt( 1 );
	    	Spinner unit_view = ( Spinner )component.getChildAt( 2 );
	    	
	    	Component component_data = new Component(
	    		name_view.getText().toString(),
	    		amount_view.getSelectedItemPosition(),
	    		unit_view.getSelectedItemPosition() 
	    		);
	    	components.add( component_data );
	    }
	    return components;
	}

	private String getMethod()
	{
		return( ( EditText )findViewById( 
			R.id.new_cocktail_method ) ).getText().toString();		
	}
/*	

	private String getDescription()
	{
		return( ( EditText )findViewById(
			R.id.new_cocktail_description ) ).getText().toString();
	}
*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_save:
			if( MainActivity.TYPE_COCKTAIL == m_CurrentItemType )
			{

				Cocktail cocktail = new Cocktail(
					getName(),
					getBaseSpirit(),
					getComponents(),
					getMethod() );
					
				Resources res = getResources();

				ContentParser contentParser = new ContentParser( 
					res.getStringArray( R.array.component_amount ),
					res.getStringArray( R.array.component_units ) );
					
				File content_file = new File(getExternalFilesDir(null), MainActivity.CONTENT_FILE );
				try{
					contentParser.writeJsonCocktail( content_file, cocktail );
				}catch( IOException e ){}
				/*
				Recipe recipe = new Recipe( 0,
					,
					( ( EditText )findViewById( 
						R.id.new_cocktail_base_spirit ) ).getText().toString(),
						null, null );
					*/
				//Recipe cocktail = new Recipe(m_CurrentItemType, null, null, null, null);
			}
			else if( MainActivity.TYPE_SPIRIT == m_CurrentItemType )
			{
				/* Do nothing for now. */
			}
			return true;
			
		case R.id.menu_cancel:
			NavUtils.navigateUpFromSameTask( this );
			return true;
			
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask( this );
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
