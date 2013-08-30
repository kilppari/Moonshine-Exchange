/** ---------------------------------------------------------------------------
 * File:        SuggestionProvider.java
 * Author:      Pekka Mäkinen
 * Version:     0.2
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
	 * the given query (contained in Uri, all other arguments are ignored).
	 * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Cursor query( 
		Uri uri, 
		String[] projection, // Ignored
		String selection, // Ignored
		String[] selectionArgs,  // Ignored
		String sortOrder /* Ignored */ )
	{
		// Get the query in the Uri (The search text entered by user).
		String query = uri.getLastPathSegment();
		
		// Search suggestions and return them
		return getSuggestions( query );
	}
	
	/**
	 * Searches through all the cocktail recipes and returns
	 * those which name starts with the given query listed in Cursor object.
	 * @param query Search query string
	 * @return Cursor object of the results
	 */
	private Cursor getSuggestions( String query )
	{
		// Create MatrixCursor for the suggestions.
		String[] columns = new String[] { 
			BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1 };
		MatrixCursor mcursor = new MatrixCursor( columns );
		
		// Get list of all cocktails
		ArrayList< Cocktail > cocktails = m_ContentParser.getRecipeList();
		
		// And find Cocktail names that match the query
		int id = 0;
		for( int i = 0; i < cocktails.size(); i++ )
		{
			String name = cocktails.get( i ).getName();
			if( name.toLowerCase().startsWith( query.toLowerCase() ) )
			{
				mcursor.addRow( new Object[]{ id++, name } );
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
