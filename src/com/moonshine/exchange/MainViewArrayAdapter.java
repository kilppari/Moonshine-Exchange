/** ---------------------------------------------------------------------------
 * File:        MainViewArrayAdapter.java
 * Author:      Pekka Mäkinen
 * Version:     1.0
 * Description: Custom array adapter for the main list view.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Custom implementation for ArrayAdapter. 
 */
public class MainViewArrayAdapter extends ArrayAdapter< Cocktail >
{
	

	private final Context m_Context;
	private final ArrayList< Cocktail > m_Cocktails;
	
	/**
	 * Constructor.
	 * @param context Context for the ArrayAdapter
	 * @param objects Objects to search through.
	 */
	public MainViewArrayAdapter( Context context, List< Cocktail > objects) 
	{
		super( context, R.layout.main_list_item, objects );
		m_Context = context;
		m_Cocktails = new ArrayList< Cocktail >( objects );
	}
	
	/**
	 * Sets the view for the adapter and fills it with contents.
	 */
	public View getView( int position, View convertView, ViewGroup parent )
	{
		// Set layout
		LayoutInflater inflater = ( LayoutInflater )
			m_Context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View view = inflater.inflate( R.layout.main_list_item, parent, false );
		
		// Get handles to the text views.
		TextView main_list_item_name = 
			( TextView )view.findViewById( R.id.main_list_item_name );
		TextView main_list_item_extra =
			( TextView )view.findViewById( R.id.main_list_item_extra );
		
		// Set cocktail name as the main text
		main_list_item_name.setText( m_Cocktails.get( position ).getName() );

		// And cocktail's components as the extra text under the cocktail name.
		ArrayList< Component > components = 
			m_Cocktails.get( position ).getComponents();
		
		if( !components.isEmpty() )
		{
			Iterator< Component > it = components.iterator();
			String comp_names = it.next().getName();

			while( it.hasNext() )
			{
				comp_names += ", " + it.next().getName();
			}
			main_list_item_extra.setText( comp_names );
		}
		return view;
	}
}
