package com.example.progtablet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
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

public class EditLineupActivity extends Activity implements OnClickListener {

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
		setContentView(R.layout.activity_editlineup);

		ActionBar actionBar = getActionBar();
		// Setta il titolo
		actionBar.setTitle("Modifica lineup");

		actionBar.setDisplayHomeAsUpEnabled(true);

		Calendar newCalendar = Calendar.getInstance();
		int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = newCalendar.get(Calendar.MINUTE);

		inserisci = (Button) findViewById(R.id.editcontinuacreafesta2);
		lista = (ListView) findViewById(R.id.editlistalineup);
		genere = (EditText) findViewById(R.id.editgenerelineup);
		orainizio = (EditText) findViewById(R.id.editoradalineup);
		orafine = (EditText) findViewById(R.id.editoraalineup);
		nomedj = (EditText) findViewById(R.id.editnomedjlineup);

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

		if (v.getId() == R.id.editlistalineup) {

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

			i = new Intent(this, ViewFestaActivity.class);
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
