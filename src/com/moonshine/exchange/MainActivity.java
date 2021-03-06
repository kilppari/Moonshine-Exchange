/** ---------------------------------------------------------------------------
 * File:        MainActivity.java
 * Author:      Pekka M�kinen
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
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Main activity class for Moonshine Exchange.
 */
public class MainActivity extends ListActivity {

	/** ID for extra data when sending cocktail information through Intent. */
	public final static String EXTRA_RECIPE = "com.moonshine.exchange.RECIPE";
	
	public final static String EXTRA_POSITION = 
		"com.moonshine.exchange.POSITION";
	
	/** Specifies name of the main content file. */
	public final static String CONTENT_FILE = "cocktails.json";

	/** For future use, IDs for UI tabs. */
	public final static int TYPE_COCKTAIL = 0;
	public final static int TYPE_SPIRIT = 1;
	
	public static Context appContext;
	
	private ListView m_ListView;
	private ContentParser m_ContentParser;
	
	private int m_CurrentCheckedItemPos;

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
		m_ContentParser = ContentParser.getInstance();
		m_ContentParser.setAmounts( 
			res.getStringArray( R.array.component_amount ) );
		m_ContentParser.setUnits(
			res.getStringArray( R.array.component_units ) );

		// Parse cocktail file
		try{
			m_ContentParser.loadJsonData( getAssets().open( CONTENT_FILE ) );
			m_ContentParser.sortListByName();
		}
		catch( SimpleException e )
		{
		    Toast.makeText( getApplicationContext(),
		    	"Type: " + e.getType() + " Value: " + e.getValue(), 
		        Toast.LENGTH_SHORT ).show();
		}
		catch ( IOException ioe )
		{
	    	Toast.makeText( getApplicationContext(), "IOException", 
		        Toast.LENGTH_SHORT ).show();
		}
		
		// Create and fill the main list view.
		setupListView();
		m_ListView.setChoiceMode( ListView.CHOICE_MODE_MULTIPLE_MODAL );
		m_ListView.setMultiChoiceModeListener( new MainViewModeListener() );

		//m_ListView.setSelector( R.drawable.main_view_selector );
	}
	
	/**
	 * Sets the main list view layout and fills it with contents.
	 */
	private void setupListView()
	{
		m_ListView.setAdapter( new MainViewArrayAdapter( 
			this, m_ContentParser.getRecipeList() ) );
		
		// Setup list view items to show corresponding recipe data on click.
		m_ListView.setOnItemClickListener( new AdapterView.OnItemClickListener() 
		{
			@Override
		    public void onItemClick( 
		        AdapterView< ? > parent, final View view, int position, long id)
			{
		        showRecipe( position );
		    }
	    });
	}

	/**
	 * This activity's launchmode is 'singleTop' so this function is called
	 * on a new intent instead of making a new instance of the activity.
	 * @see android.app.Activity#onNewIntent(android.content.Intent)
	 */
	@Override
	protected void onNewIntent( Intent intent )
	{
        /* Handles a click on a search suggestion; 
         * launches activity to show cocktail recipe.
         */
        if (Intent.ACTION_VIEW.equals( intent.getAction() ) )
        {
        	// Get the URi which has the search data from ContentProvider
            Uri uri = intent.getData();
            
            /* Uri's last segment refers to the chosen suggested recipe's index 
             * from the full recipe list.
             */
    		int index = Integer.parseInt( uri.getLastPathSegment() );
			showRecipe( index );
        }
		/* Handles a direct search query, i.e., user has typed search text and 
		 * clicked search button.
		 */
        else if( Intent.ACTION_SEARCH.equals( intent.getAction() ) )
		{
			// Get search query.
			String query = intent.getStringExtra( SearchManager.QUERY );

			// If query matches a name of one of the cocktails, show it.
			int i = m_ContentParser.getCocktailIndexByName( query );
			
			if( i != -1 )
			{
				// Cocktail found, show it.
				showRecipe( i );
			}
		}
	}

	/* Inflates options menu.
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.options_menu, menu );
		
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
	public boolean onOptionsItemSelected( MenuItem item ) 
	{
	    // Handle item selection
	    switch( item.getItemId() ) 
	    {
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
		try
		{
			Intent intent = new Intent( this, DisplayRecipeActivity.class );
			intent.putExtra( EXTRA_RECIPE, 
				m_ContentParser.getRecipeList().get( id ) );
			startActivity( intent );
		}
		catch( IndexOutOfBoundsException e)
		{
	    	Toast.makeText( getApplicationContext(),
	    		"Oops, could not find the selected recipe.", 
			    Toast.LENGTH_SHORT ).show();
		}
	}

	/**
	 * Starts EditItemActivity.
	 */
	public void editItem( int pos )
	{
		Intent intent = new Intent( this, EditItemActivity.class );
		intent.putExtra( EXTRA_POSITION, pos );
		//Intent intent = new Intent( this, TabHostBaseActivity.class );
		startActivity( intent );
	}
	
	/**
	 * Listener for contextual action mode.
	 */
	public class MainViewModeListener implements MultiChoiceModeListener 
	{
		
		@Override
		public boolean onActionItemClicked( ActionMode mode, MenuItem item )
		{
			switch( item.getItemId() )
			{
			case R.id.menu_edit:
				editItem( m_CurrentCheckedItemPos );
				break;
				
			default:
				// Do nothing..
				break;
					
			}
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean onCreateActionMode( ActionMode mode, Menu menu )
		{
			/* Setup contextual menu. Edit -option is under 
			 * single target -group so that when user selects multiple
			 * items from the list, the 'single target' options can be
			 * disable.
			 */
			menu.add( R.id.menu_group_single_target, R.id.menu_edit, 
				Menu.NONE, R.string.menu_title_edit );
			
			menu.add( Menu.NONE, R.id.menu_delete, Menu.NONE, 
				R.string.menu_title_delete );
			
			mode.setTitle("Select Items");
	        mode.setSubtitle("One item selected");
	        
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
			boolean checked ) 
		{
			int select_count = m_ListView.getCheckedItemCount();

	        switch( select_count ) 
	        {
	        case 1:
	            mode.setSubtitle("One item selected");
	            // Enable 'single target' menu options.
	            mode.getMenu().setGroupVisible( R.id.menu_group_single_target, true );
	            // Get the actual checked position and store it
	            long[] pos = m_ListView.getCheckedItemIds();
	            m_CurrentCheckedItemPos = ( int )pos[ 0 ];
	            
	            break;
	        default:
	            mode.setSubtitle("" + select_count + " items selected");
	            // Multiple items selected, disable 'single target' options.
	            mode.getMenu().setGroupVisible( R.id.menu_group_single_target, false );
	            break;
	        }
		}

	}


}
