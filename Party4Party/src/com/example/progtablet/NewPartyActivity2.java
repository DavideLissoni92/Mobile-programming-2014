package com.example.progtablet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewPartyActivity2 extends Activity implements OnClickListener {
String controllo;
	int myid,idfesta;
	IP ip = new IP();
	private Button inserisci;
	private ListView lista;
	private EditText genere, orainizio, orafine, nomedj;
	private TimePickerDialog fromTimePickerDialog;
	private TimePickerDialog toTimePickerDialog;
	static int selected;
	String orario, generi, dj;
	SimpleAdapter mSchedule;
	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map = new HashMap<String, String>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newparty2);
		Bundle bundle = getIntent().getExtras();
		myid= bundle.getInt("id");
		idfesta= bundle.getInt("idfesta");
		
		ActionBar actionBar = getActionBar();
		// Setta il titolo
		actionBar.setTitle("Crea festa");

		actionBar.setDisplayHomeAsUpEnabled(true);

		Calendar newCalendar = Calendar.getInstance();
		int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = newCalendar.get(Calendar.MINUTE);

		inserisci = (Button) findViewById(R.id.continuacreafesta2);
		lista = (ListView) findViewById(R.id.listalineup);
		genere = (EditText) findViewById(R.id.generelineup);
		orainizio = (EditText) findViewById(R.id.oradalineup);
		orafine = (EditText) findViewById(R.id.oraalineup);
		nomedj = (EditText) findViewById(R.id.nomedjlineup);

		orainizio.setOnClickListener(this);
		orafine.setOnClickListener(this);

		map.put("orari", "Orario");
		map.put("generi", "Generi");
		map.put("dj", "DJ");
		mylist.add(map);

		fromTimePickerDialog = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {

						orainizio.setText(Integer.toString(hourOfDay) + ":"
								+ Integer.toString(minute));

					}
				}, hour, minute, true);

		toTimePickerDialog = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {

						orafine.setText(Integer.toString(hourOfDay) + ":"
								+ Integer.toString(minute));

					}
				}, hour, minute, true);

		inserisci.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(nomedj.getWindowToken(), 0);

				
				String nome = nomedj.getText().toString();
    			String gen = genere.getText().toString();
    	
    	String orada=orainizio.getText().toString();
    	String oraa=orafine.getText().toString();
    			
    			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
    			.permitAll().build();
    	StrictMode.setThreadPolicy(policy);
    	try {
    		HttpClient httpclient = new DefaultHttpClient();
    		HttpPost httppost = new HttpPost(
    				ip.getindirizzo()+"InserisciDj?nome="
    						+ nome + "&idutente=" + myid + "&idfesta=" + idfesta
    						+  "&orainizio=" + orada+"&orafine=" + oraa+"&genere=" + gen);
    		HttpResponse response = httpclient.execute(httppost);
    		HttpEntity entity = response.getEntity();
    		Log.d("SONOQUI","QUI3");

    		if (entity != null) {
    			String risposta = EntityUtils.toString(entity);
    			JSONObject json = new JSONObject(risposta);
    			controllo = json.optString("creazione");
    			idfesta=json.optInt("inseriscidj");
    		}
    		Log.d("json", controllo);

    		
    	} catch (Exception e) {
    		Log.e("TEST", "Errore nella connessione http " + e.toString());
    	}
    	
    	
				
				orario = orainizio.getText().toString() + " - "
						+ orafine.getText().toString();
				generi = genere.getText().toString();
				dj = nomedj.getText().toString();

				map = new HashMap<String, String>();
				map.put("orari", orario);
				map.put("generi", generi);
				map.put("dj", dj);
				mylist.add(map);

				genere.setText("");
				nomedj.setText("");

			}

		});

		mSchedule = new SimpleAdapter(this, mylist, R.layout.listlineupitem,
				new String[] { "orari", "generi", "dj" }, new int[] {
						R.id.orariolineup, R.id.generilineup, R.id.djlineup });
		lista.setAdapter(mSchedule);

		lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v, int pos,
					long id) {
				selected = pos;
				if (selected == 0) {
					return true;
				}

				registerForContextMenu(lista);
				return false;
			}
		});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		if (v.getId() == R.id.listalineup) {

			String menuItem2 = "Elimina riga " + selected;

			menu.add(menuItem2);

		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		mylist.remove(selected);
		mSchedule.notifyDataSetChanged();
		mSchedule.notifyDataSetInvalidated();

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.newpartymenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		Intent i;
		switch (item.getItemId()) {

		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_accept:

			i = new Intent(this, HomeActivity.class);
	    	i.putExtra("id", myid);
			startActivity(i);
			return true;

		}
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == orafine) {
			toTimePickerDialog.show();
		} else if (v == orainizio) {
			fromTimePickerDialog.show();
		}

	}

}
