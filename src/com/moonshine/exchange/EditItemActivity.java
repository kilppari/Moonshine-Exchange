/** ---------------------------------------------------------------------------
 * File:        EditItemActivity.java
 * Author:      Pekka M�kinen
 * Description: Activity for editing cocktail information.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.util.List;
import java.util.Vector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class EditItemActivity extends FragmentActivity
{
    ContentParser m_ContentParser;
    private Cocktail m_Cocktail;
    List< FragmentPageBase > m_Fragments;
    private int m_EditMode;
    
    private final static int NEW_ITEM = 0;
    private final static int EXISTING_ITEM = 1;
    
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
        setContentView( R.layout.edit_item );
		setupFragments();
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
			m_EditMode = NEW_ITEM;
		}
		else
		{
			// Get the Recipe we are editing and set title for the activity.
			m_ContentParser = ContentParser.getInstance();
			m_Cocktail = m_ContentParser.getRecipeList().get( pos );
			setTitle( "Edit " + m_Cocktail.getName() + "..." );
			m_EditMode = EXISTING_ITEM;
			ViewPager viewpager = ( ViewPager ) findViewById( R.id.pager );
			CustomPageAdapter adapter = new CustomPageAdapter( m_Fragments.size() );
			viewpager.setAdapter( adapter );
		}
	}
	
	public class CustomPageAdapter extends FragmentPagerAdapter {
		private int m_PageCount;
		  
		public CustomPageAdapter( int page_count ) {
			super( getSupportFragmentManager() );
			m_PageCount = page_count;
		}
		  
		@Override
		public int getCount() {
			return m_PageCount;
		}
		  
		@Override
		public Fragment getItem(int position) {
			return m_Fragments.get( position );
		}
		 
		@Override
		public CharSequence getPageTitle(int position) {
			return m_Fragments.get( position ).getTitle();
        }
		 
		public void setPageCount( int count ) {
			m_PageCount = count;
		}
	}
	
	public abstract static class FragmentPageBase extends Fragment {
		private String m_Title;
		
		public void setTitle( String title ) {
			m_Title = title;
		}
		public String getTitle() {
			return m_Title;
		}
	}

	/**
	 * Returns the editmode of this activity.
	 * @return Activity's edit mode, either <code>NEW_ITEM</code>
	 * or <code>EXISTING_ITEM</code>
	 */
	public int getEditMode() { return m_EditMode; }
	
	public static class ComponentFragment extends FragmentPageBase
	{
		private LinearLayout m_ComponentLayout;
		private EditText m_EditText;
		
		@Override
	    public View onCreateView( LayoutInflater inflater, ViewGroup container,
	    	Bundle savedInstanceState ) 
		{
	        // Inflate the layout for this fragment
	        View v = inflater.inflate( R.layout.edit_component_fragment,
	        	container, false );
	        m_ComponentLayout = 
	        	( LinearLayout )v.findViewById( R.id.component_layout );
	        m_EditText = ( EditText )v.findViewById( R.id.edit_cocktail_method );
	        
	        // If editing existing item, set the component and method values.
			EditItemActivity activity = ( EditItemActivity )getActivity();
			if( activity.getEditMode() == EXISTING_ITEM )
			{
				List< Component > components = activity.m_Cocktail.getComponents();

				for( int i = 0; i < components.size(); i++ )
				{
					LinearLayout comp_view = addComponentForm( inflater );
						
					( ( EditText )comp_view.getChildAt( 0 ) ).setText(
						components.get( i ).getName() ); 
					( ( Spinner )comp_view.getChildAt( 1 ) ).setSelection( 
						components.get( i ).getAmountIdx() );			
					( ( Spinner )comp_view.getChildAt( 2 ) ).setSelection( 
						components.get( i ).getUnitIdx() );
				}
				
				m_EditText.setText( activity.m_Cocktail.getMethod() );
			}
			else // Assume NEW_ITEM
			{
				// At least 2 components are always required for a new item.
				addComponentForm( inflater );
				addComponentForm( inflater );
			}
	        return v;
	    }
		
		public LinearLayout addComponentForm( LayoutInflater inflater )
		{	
			/* Use LayoutInflater to make a custom view for a 
			 * cocktail component input form. 
			 */
			LinearLayout comp_view = ( LinearLayout )inflater.inflate(
	        	R.layout.input_form_component, null );
			m_ComponentLayout.addView( comp_view );
			
			Spinner spinner = ( Spinner )comp_view.getChildAt( 1 );
			
			// Set possible values for the 'amount' -spinner
			spinner.setAdapter( 
				new ArrayAdapter< String >( 
					getActivity(), 
					android.R.layout.simple_spinner_item, 
					Component.COMPONENT_AMOUNTS ) );
	
			return comp_view;
		}
		
	}
	
	
	public static class MethodFragment extends FragmentPageBase
	{
		private EditText m_EditText;
		
		@Override
	    public View onCreateView( LayoutInflater inflater, ViewGroup container,
	    	Bundle savedInstanceState ) 
		{
	        // Inflate the layout for this fragment
			View v = inflater.inflate( R.layout.edit_method_fragment,
		        container, false );
			m_EditText = ( EditText )v.findViewById( R.id.edit_cocktail_method );
			
			/* If editing existing item, set the item's method value 
			 * into the EditText field.
			 */
			EditItemActivity activity = ( EditItemActivity )getActivity();
			if( activity.getEditMode() == EXISTING_ITEM )
			{
				m_EditText.setText( activity.m_Cocktail.getMethod() );
			}

	        return v;
	    }
		
		/**
		 * Returns the method-text user has typed.
		 * @return text of the <code>EditText</code> field
		 */
		public String getMethodText() 
		{
			return m_EditText.getText().toString();
		}
		
	}

	public static class DescriptionFragment extends FragmentPageBase
	{
		private EditText m_EditText;
		
		@Override
	    public View onCreateView( LayoutInflater inflater, ViewGroup container,
	    	Bundle savedInstanceState ) 
		{
	        // Inflate the layout for this fragment
			View v = inflater.inflate( R.layout.edit_method_fragment,
		        container, false );
			m_EditText = ( EditText )v.findViewById( R.id.edit_cocktail_method );
			
			/* If editing existing item, set the item's method value 
			 * into the EditText field.
			 */
			EditItemActivity activity = ( EditItemActivity )getActivity();
			if( activity.getEditMode() == EXISTING_ITEM )
			{
				m_EditText.setText( activity.m_Cocktail.getDescription() );
			}

	        return v;
	    }
		
		/**
		 * Returns the method-text user has typed.
		 * @return text of the <code>EditText</code> field
		 */
		public String getDescriptionText() 
		{
			return m_EditText.getText().toString();
		}
		
	}
	
	public static class ReferencesFragment extends FragmentPageBase
	{
		private EditText m_EditText;
		
		@Override
	    public View onCreateView( LayoutInflater inflater, ViewGroup container,
	    	Bundle savedInstanceState ) 
		{
	        // Inflate the layout for this fragment
			View v = inflater.inflate( R.layout.edit_method_fragment,
		        container, false );
			m_EditText = ( EditText )v.findViewById( R.id.edit_cocktail_method );
			
			/* If editing existing item, set the item's method value 
			 * into the EditText field.
			 */
			EditItemActivity activity = ( EditItemActivity )getActivity();
			if( activity.getEditMode() == EXISTING_ITEM )
			{
				m_EditText.setText( activity.m_Cocktail.getReferences().get( 0 ) );
			}

	        return v;
	    }
		
		/**
		 * Returns the method-text user has typed.
		 * @return text of the <code>EditText</code> field
		 */
		public String getReferencesText() 
		{
			return m_EditText.getText().toString();
		}
		
	}
/*
	@Override
	public void setupTabs() {
		// TODO Auto-generated method stub
		addTab( this, m_TabHost, m_TabHost.newTabSpec("Tab1").setIndicator( "Components" ) );
		addTab( this, m_TabHost, m_TabHost.newTabSpec("Tab2").setIndicator( "Instructions" ) );
		addTab( this, m_TabHost, m_TabHost.newTabSpec("Tab3").setIndicator( "Description" ) );
		addTab( this, m_TabHost, m_TabHost.newTabSpec("Tab4").setIndicator( "References" ) );
	}*/

	//@Override
	protected List< FragmentPageBase > setupFragments() {
		
		m_Fragments = new Vector< FragmentPageBase >();
		
		m_Fragments.add( (FragmentPageBase) Fragment.instantiate( 
			this, ComponentFragment.class.getName() ) );
		m_Fragments.get( 0 ).setTitle( 
			getResources().getString( R.string.fragment_tab_title_recipe ) );
		m_Fragments.add( (FragmentPageBase) Fragment.instantiate(
			this, DescriptionFragment.class.getName() ) );
		m_Fragments.get( 1 ).setTitle( 
			getResources().getString( R.string.fragment_tab_title_description ) );
		m_Fragments.add( (FragmentPageBase) Fragment.instantiate(
			this, ReferencesFragment.class.getName() ) );
		m_Fragments.get( 2 ).setTitle( 
			getResources().getString( R.string.fragment_tab_title_references ) );
		
		return m_Fragments;
	}

}
