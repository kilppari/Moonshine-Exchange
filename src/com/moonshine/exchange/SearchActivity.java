package com.moonshine.exchange;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

public class SearchActivity extends Activity {

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
        setContentView( R.layout.search_result );
	    // Get the intent, verify the action and get the query
	    
	    Uri uri = getIntent().getData();
	    MatrixCursor mcursor = ( MatrixCursor)getContentResolver().query( 
	    	uri, null, null, null, null );
	    
	    if( mcursor == null )
	    {
	    	finish();
	    }
	    else
	    {
	    	mcursor.moveToFirst();
	    	
	    	TextView result_view = ( TextView )findViewById( 
	    		R.id.searchresult );
	    	
	    	int result_idx = mcursor.getColumnIndexOrThrow( "" );
	    	
	    	result_view.setText( mcursor.getString( result_idx ) );
	    	
	    }
	    

/*
		MatrixCursor mcursor;
		// Queries possible suggestions and returns results
		mcursor = getContentResolver().query(
		    UserDictionary.Words.CONTENT_URI,   // The content URI of the words table
		    mProjection,                        // The columns to return for each row
		    mSelectionClause                    // Selection criteria
		    mSelectionArgs,                     // Selection criteria
		    mSortOrder);  
		    	    
	    if( Intent.ACTION_SEARCH.equals( intent.getAction() ) ) 
	    {
	        String query = intent.getStringExtra( SearchManager.QUERY );
	    }
		*/
	}
	
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) 
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.options_menu, menu );

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB )
        {
            SearchManager searchManager = 
            	( SearchManager )getSystemService( Context.SEARCH_SERVICE );
            SearchView searchView = 
            	( SearchView )menu.findItem( R.id.search ).getActionView();
            searchView.setSearchableInfo( 
            	searchManager.getSearchableInfo( getComponentName() ) );
            searchView.setIconifiedByDefault( false );
        }
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) 
    {
        switch( item.getItemId() ) 
        {
            case R.id.search:
                onSearchRequested();
                return true;
            case android.R.id.home:
                Intent intent = new Intent( this, MainActivity.class );
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                return true;
            default:
                return false;
        }
    }

}
