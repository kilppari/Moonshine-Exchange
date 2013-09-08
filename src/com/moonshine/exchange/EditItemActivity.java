package com.moonshine.exchange;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditItemActivity extends Activity 
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.edit_item );
		/*
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = 
			fragmentManager.beginTransaction();
		
		ComponentFragment fragment = new ComponentFragment();
		fragmentTransaction.add( R.id.edit_item_activity, fragment );
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
