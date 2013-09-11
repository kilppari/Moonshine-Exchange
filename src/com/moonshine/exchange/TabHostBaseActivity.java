/** ---------------------------------------------------------------------------
 * File:        TabHostBaseActivity.java
 * Author:      Pekka Mäkinen
 * Description: Abstract base class for tab activity with swipe gesture support.
 * 				This is a modification of an example listed in here:
 *              http://thepseudocoder.wordpress.com/2011/10/13/android-
 *              tabs-viewpager-swipe-able-tabs-ftw/
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

abstract class TabHostBaseActivity extends FragmentActivity
	implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener 
	{

	protected TabHost m_TabHost;
	protected PagerAdapter m_PagerAdapter;
	private ViewPager m_ViewPager;
	private String m_CurrentTab;
	
	protected PagerAdapter getPagerAdapter() { return m_PagerAdapter; }
	
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.edit_item );
		
		initTabHost();
		
		if( savedInstanceState != null )
		{
			
			m_TabHost.setCurrentTabByTag( m_CurrentTab );
				//savedInstanceState.getString( "tab" ) );
		}
		
		initViewPager();
		
	}
	
	/**
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(
	 * android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState( Bundle out_state )
	{
		//out_state.putString( "tab", m_TabHost.getCurrentTabTag() );
		m_CurrentTab = m_TabHost.getCurrentTabTag();
		super.onSaveInstanceState( out_state );
	}
	
    /**
     * A simple factory that returns dummy views to the Tabhost
     */
    class TabFactory implements TabContentFactory
    {
        private final Context m_Context;
 
        /**
         * @param context
         */
        public TabFactory( Context context ) { m_Context = context; }
 
        /** (non-Javadoc)
         * @see android.widget.TabHost.
         * TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent( String tag )
        {
            View v = new View( m_Context );
            v.setMinimumWidth( 0 );
            v.setMinimumHeight( 0 );
            return v;
        }
 
    }
	
	/**
	 * Simple adapter for ViewPager. PagerAdapter is responsible for returning
	 * appropriate fragments.
	 */
	protected class PagerAdapter extends FragmentPagerAdapter
	{
		private List< Fragment > m_Fragments;
		
		public PagerAdapter( FragmentManager fm, List< Fragment > fragments ) 
		{
			super( fm );
			m_Fragments = fragments;
		}
		
		/**
		 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem( int pos ) { return m_Fragments.get( pos ); }
		
		/**
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() { return m_Fragments.size(); }
		
	}
	
	/**
	 * Initialises ViewPager by setting its <code>Adapter</code> and 
	 * <Code>OnPageChangeListener</code>.
	 */
	private void initViewPager()
	{

		m_PagerAdapter = new PagerAdapter(
			getSupportFragmentManager(), setupFragments() );
		
		//Get the ViewPager from the layout.
		m_ViewPager = ( ViewPager )findViewById( R.id.viewpager );
		
		m_ViewPager.setAdapter( m_PagerAdapter );
		m_ViewPager.setOnPageChangeListener( this );
		
	}

	/**
	 * Initialises the <code>TabHost</code>.
	 */
	private void initTabHost()
	{
		m_TabHost = ( TabHost )findViewById( android.R.id.tabhost );
		m_TabHost.setup();
		
		setupTabs();

		m_TabHost.setOnTabChangedListener( this );

	}
	
	/**
	 * Creates a list of fragments that shall be used as the tab contents.
	 * Order in the list determines tab order on device.
	 * @return List< Fragment > List of fragments
	 */
	protected abstract List< Fragment > setupFragments();
	
	/**
	 * Abstract function for adding tabs (<code>TabSpec</code>s) to the 
	 * activity's <code>TabHost</code>.<p> NOTE: This function should only call
	 * <code>addTab</code> for each tab to be added.
	 * @see com.moonshine.exchange.addTab
	 */
	protected abstract void setupTabs();

	/**
	 * Adds tab content to the <code>TabHost</code>.
	 * @param activity <code>Activity</code> for the content
	 * @param tabhost <code>TabHost</code> in which content is added
	 * @param tabspec <code>TabSpec</code> of the tab content
	 */
	protected static void addTab( TabHostBaseActivity activity, 
		TabHost tabhost, TabHost.TabSpec tabspec )
	{
		tabspec.setContent( activity.new TabFactory( activity ) );
		tabhost.addTab( tabspec );
	}
	
	/**
	 * @see android.support.v4.view.ViewPager.
	 * OnPageChangeListener#onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged( int state ) { }

	/**
	 * @see android.support.v4.view.ViewPager.
	 * OnPageChangeListener#onPageScrolled(int, float, int)
	 */
	@Override
	public void onPageScrolled( int pos, float pos_offset, 
		int pos_offset_pixels ) { }

	/**
	 * @see android.support.v4.view.ViewPager.
	 * OnPageChangeListener#onPageSelected(int)
	 */
	@Override
	public void onPageSelected( int pos )  { m_TabHost.setCurrentTab( pos ); }

	/**
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(
	 * java.lang.String)
	 */
	@Override
	public void onTabChanged( String tag ) 
	{
		m_ViewPager.setCurrentItem( m_TabHost.getCurrentTab() );
	}

}
