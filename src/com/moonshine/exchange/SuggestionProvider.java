package com.moonshine.exchange;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

/**
 * Provides search result suggestions.
 */
public class SuggestionProvider extends ContentProvider
{
	String[] m_Suggestions;
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

	/* 
	 * This is called as soon as the application starts.
	 * (non-Javadoc)
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() 
	{
		m_Suggestions = new String[] {"manhattan", "daiquiri"};
		
		Log.v( "Testing", "tete" );
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Cursor query( 
		Uri uri, 
		String[] projection, // Always null
		String selection,
		String[] selectionArgs, 
		String sortOrder /* Always null */ )
	{
		// Get the query in the Uri (The search text entered by user).
		String query = uri.getLastPathSegment().toLowerCase();
		
		String[] columns = {"_ID", "SUGGEST_COLUMN_TEXT_1"};
		MatrixCursor mcursor = new MatrixCursor( columns );
		Object[] values = { 1, "Manhattan" };
		mcursor.addRow( values );
		
		
		
		// TODO Auto-generated method stub
		return mcursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
