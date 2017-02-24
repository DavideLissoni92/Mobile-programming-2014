package com.example.progtablet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;


public class HomeActivity extends FragmentActivity implements
		ActionBar.TabListener {
	IP ip = new IP();

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	NumberPicker risposta;
	String[] nomefesta;

	// Tab titles
	private String[] tabs = { "Partecipo", "Inviti", "Non partecipo" };
int myid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Bundle bundle = getIntent().getExtras();
		myid = bundle.getInt("id");
		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		 // Setta il titolo
		actionBar.setTitle("Party4Party");
		 
		
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager(),myid);

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ip.getindirizzo()
					+ "NotificheInvito?idutente=" + myid);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONArray jarr = new JSONArray(risposta);
				nomefesta=new String[jarr.length()];
				JSONObject json;

				for (int i = 0; i < jarr.length(); i++) {
					json = jarr.getJSONObject(i);
					nomefesta[i]=json.optString("nomefesta");
					Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			    	   NotificationCompat.Builder n  = new NotificationCompat.Builder(this).setContentTitle("Arrivato nuovo messaggio!!")
			    			   .setContentText("Autore: DavideLIssoni")
			    			   .setSmallIcon(android.R.drawable.ic_dialog_alert)
			    			   .setSound(sound)	;
			    	   NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    		        notificationManager.notify(0, n.build());
				
				}
				
			}}catch (Exception e) {
				Log.e("TEST", "Errore nella connessione http " + e.toString());
			}
			
		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	
	
	}
	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.homemenu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
		
		Intent i;
	    switch (item.getItemId()) {
	    case R.id.action_newparty: 
	    	 i = new Intent(this,NewPartyActivity.class);
	    	 i.putExtra("id", myid);
			startActivity(i);
	    	return true;
	    
	        
	        
	        	case R.id.action_cerca: i = new Intent(this,RicercaActivity.class);
	        	int controlo=0;
	        	i.putExtra("id", myid);
	        	i.putExtra("controllo", controlo);
	        	i.putExtra("nome", "");
	        	i.putExtra("cognome", "");
	        	i.putExtra("nickname", "");
	        	i.putExtra("ruolo", "Tutti");
	        	i.putExtra("oggetto", "Qualsiasi");
				startActivity(i); return true;
	        	
	        	case R.id.action_viewprofilo:  i = new Intent(this,ViewUtenteActivity.class);
	        	i.putExtra("id", myid);
	        	i.putExtra("idutente", myid);
	        	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i); return true;
	        	
	        	case R.id.action_preferiti: i = new Intent(this,ListaPreferitiActivity.class);
				startActivity(i);
	        		return true;
	        	
	        	case R.id.action_esci: i = new Intent(this,LoginActivity.class);
				startActivity(i);
        		return true;
	        	
	        	}
	            return true;
	        
	    
	}
	
	
}

