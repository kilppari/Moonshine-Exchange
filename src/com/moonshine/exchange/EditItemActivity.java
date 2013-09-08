package com.moonshine.exchange;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditItemActivity extends FragmentActivity 
{
    //private FragmentTabHost m_TabHost;
    
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.edit_item );
		/*
		m_TabHost = ( FragmentTabHost )findViewById( android.R.id.tabhost );
        m_TabHost.setup( this, getSupportFragmentManager(), android.R.id.tabcontent );

        m_TabHost.addTab( m_TabHost.newTabSpec("simple").setIndicator("Ingredients"),
                ComponentFragment.class, null );
        m_TabHost.addTab(m_TabHost.newTabSpec("contacts").setIndicator("Instructions"),
                MethodFragment.class, null );


		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = 
			fragmentManager.beginTransaction();
		
		ComponentFragment fragment = new ComponentFragment();
		fragmentTransaction.add( R.id.edit_item_activity, fragment );
		//fragmentTransaction.addToBackStack(null);

		fragmentTransaction.commit();
		*/
		
		// Show the Up button in the action bar.
		//setupActionBar();
		/*
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
		*/
	}
	
	public static class ComponentFragment extends Fragment
	{
		@Override
	    public View onCreateView( LayoutInflater inflater, ViewGroup container,
	    	Bundle savedInstanceState ) 
		{
	        // Inflate the layout for this fragment
	        return inflater.inflate( R.layout.edit_component_fragment,
	        	container, false );
	    }
	}
	
	public static class MethodFragment extends Fragment
	{
		@Override
	    public View onCreateView( LayoutInflater inflater, ViewGroup container,
	    	Bundle savedInstanceState ) 
		{
	        // Inflate the layout for this fragment
	        return inflater.inflate( R.layout.edit_method_fragment,
	        	container, false );
	    }
	}
}
