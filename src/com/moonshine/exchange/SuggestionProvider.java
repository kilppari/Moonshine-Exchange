/** ---------------------------------------------------------------------------
 * File:        SuggestionProvider.java
 * Author:      Pekka Mäkinen
 * Version:     1.0
 * Description: Content provider implementation for giving result suggestions 
 *              for the search functionality.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Provides search result suggestions.
 */
public class SuggestionProvider extends ContentProvider
{
	/** Instance to this application's ContentParser. */
	private ContentParser m_ContentParser;
	
	/** 
	 * Constructor. Gets the instance to the ContentParser.
	 * (non-Javadoc)
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() 
	{
		m_ContentParser = ContentParser.getInstance();
		return true;
	}

	/**
	 * Searches the list of cocktails and returns suggestions that match 
	 * the given query.
	 * @see android.content.ContentProvider#query(android.net.Uri, 
	 * java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Cursor query( 
		Uri uri, 
		String[] projection, // Ignored
		String selection, // Ignored
		String[] selectionArgs,  // Search query
		String sortOrder /* Ignored */ )
	{
		// Check if search query is present and get suggestions if it is.
		String query = uri.getLastPathSegment();
		if( query.equals( SearchManager.SUGGEST_URI_PATH_QUERY ) )
		{
			// Suggestions triggered
			return getSuggestions( selectionArgs[ 0 ] );
		}
		return null;
	}
	
	/**
	 * Searches through all the cocktail recipes and returns
	 * those which name starts with the given query listed in Cursor object.
	 * @param query Search query string
	 * @return Cursor object of the results
	 */
	private Cursor getSuggestions( String query )
	{
		// Create column names for the cursor's data table
		String[] columns = new String[] { 
			BaseColumns._ID, // suggestion ID
			SearchManager.SUGGEST_COLUMN_TEXT_1, // Suggestion text field
			SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID // Intent data field
			};
		
		// Create MatrixCursor for the suggestions.
		MatrixCursor mcursor = new MatrixCursor( columns );
		
		/* And find Cocktails that match the query and add each 
		 * to matrix cursor as a new data row.
		 */
		ArrayList< Cocktail > cocktails = m_ContentParser.getRecipeList();
		int id = 0;
		for( int i = 0; i < cocktails.size(); i++ )
		{
			String name = cocktails.get( i ).getName();
			if( name.toLowerCase().startsWith( query.toLowerCase() ) )
			{
				mcursor.addRow( new Object[]{ id++, name, i } );
			}
		}
		mcursor.close();
		return ( Cursor )mcursor;
	}

	/* Mandatory overrides */
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
