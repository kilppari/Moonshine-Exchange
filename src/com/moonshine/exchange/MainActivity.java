/** ---------------------------------------------------------------------------
 * File:        MainActivity.java
 * Author:      Pekka Mäkinen
 * Version:     2.1
 * Description: Main activity class for the application.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.io.IOException;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Main activity class for Moonshine Exchange.
 */
public class MainActivity extends ListActivity {

	/** ID for extra data when sending cocktail information through Intent. */
	public final static String EXTRA_RECIPE = "com.moonshine.exchange.RECIPE";
	/** Specifies name of the main content file. */
	public final static String CONTENT_FILE = "cocktails.json";

	/** For future use, IDs for UI tabs. */
	public final static int TYPE_COCKTAIL = 0;
	public final static int TYPE_SPIRIT = 1;
	
	public static Context appContext;
	
	private ListView m_ListView;
	private ContentParser m_ContentParser;

	/**
	 * Setups handles to UI elements, loads content parser and 
	 * shows the recipes.
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Setup view handlers
		m_ListView = ( ListView )findViewById( android.R.id.list );
		
		// Setup content parser
		Resources res = getResources();
		m_ContentParser = new ContentParser( 
			res.getStringArray( R.array.component_amount ),
			res.getStringArray( R.array.component_units ) );

		// Parse cocktail file
		try{
			m_ContentParser.loadJsonData( getAssets().open( CONTENT_FILE ) );
			m_ContentParser.sortListByName();
		}
		catch( SimpleException e ){
		    Toast.makeText( getApplicationContext(),
		    	"Type: " + e.getType() + " Value: " + e.getValue(), 
		        Toast.LENGTH_SHORT ).show();
		}
		catch ( IOException ioe )
		{
	    	Toast.makeText( getApplicationContext(), "IOException", 
		        Toast.LENGTH_SHORT ).show();
		}

		// And setup adapter for the list view with the cocktail file contents. 
		m_ListView.setAdapter( new ArrayAdapter<Cocktail>( 
			this, R.layout.main_list_item, m_ContentParser.getRecipeList() ) );

		// Setup list view items to show corresponding recipe data on click.
		m_ListView.setOnItemClickListener( new AdapterView.OnItemClickListener() 
		{
			@Override
		    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) 
			{
		        showRecipe( position );
		    }
	    });

//		final ActionBar actionBar = getActionBar();

	    // Specify that tabs should be displayed in the action bar.
		/*
	    actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );

	    // Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	        public void onTabSelected(ActionBar.Tab tab,
	                FragmentTransaction ft) { }

	        public void onTabUnselected(ActionBar.Tab tab,
	                FragmentTransaction ft) { }

	        public void onTabReselected(ActionBar.Tab tab,
	                FragmentTransaction ft) { }
	    };

	    actionBar.addTab( 
		    	actionBar.newTab().setText( "Cocktails" ).setTabListener( tabListener ) );
	    actionBar.addTab( 
		    	actionBar.newTab().setText( "Spirits" ).setTabListener( tabListener ) );
	    */

	}

	/* Inflates options menu.
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options_menu, menu);
		
		// Get the SearchView and set the searchable configuration
	    SearchManager searchManager = 
	    	( SearchManager )getSystemService( Context.SEARCH_SERVICE );
	    SearchView searchView = 
	    	( SearchView )menu.findItem( R.id.search ).getActionView();

	    searchView.setSearchableInfo( 
	    	searchManager.getSearchableInfo( getComponentName() ) );
		
		return true;
	}

	/* Triggers selected menuitems
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
	    // Handle item selection
	    switch( item.getItemId() ) {
	    /*
	        case R.id.add_recipe:
	            addItem();
	            return true;
	    */
	        default:
	            return super.onOptionsItemSelected( item );
	    }
	}

	/**
	 * Starts DisplayRecipeActivity on recipe item click.
	 * @param id Selected recipe's id
	 */
	public void showRecipe( int id ) {
		Intent intent = new Intent( this, DisplayRecipeActivity.class );
		intent.putExtra( EXTRA_RECIPE, m_ContentParser.getRecipeList().get( id ) );
		startActivity( intent );
	}

	/**
	 * Starts NewItemActivity.
	 */
	public void addItem()
	{
		Intent intent = new Intent( this, NewItemActivity.class );
		startActivity( intent );
	}
/*
	public void sendMessage( View view ) {
		Intent intent = new Intent( this, DisplayRecipeActivity.class );
		//EditText editText = ( EditText )findViewById( R.id.edit_message );
		//String message = editText.getText().toString();
		intent.putExtra( EXTRA_RECIPE, "muhahha" );
		startActivity( intent );
		
	}
*/	
}
