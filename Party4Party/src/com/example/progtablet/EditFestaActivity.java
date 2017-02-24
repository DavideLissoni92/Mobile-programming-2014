package com.example.progtablet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditFestaActivity extends Activity implements OnClickListener{
	EditText giornoda, giornoa, orada, oraa,nome;
	EditText indirizzo, generi;
	int year, month, day;
	Button continua;
	int myid,idfesta;
	IP ip = new IP();
	String orai,oraf,datai,dataf,nomefesta,genere,luogo;
	private TimePickerDialog fromTimePickerDialog;
	private TimePickerDialog toTimePickerDialog;
	private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    
    
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editfesta);
		Bundle bundle = getIntent().getExtras();
		myid = bundle.getInt("id");
		datai=bundle.getString("datai");
		dataf=bundle.getString("dataf");
		orai=bundle.getString("orai");
		oraf=bundle.getString("oraf");
		genere=bundle.getString("genere");
		luogo=bundle.getString("luogo");
nomefesta=bundle.getString("nomefesta");
idfesta=bundle.getInt("idfesta");
		
		
		
		
		ActionBar actionBar= getActionBar();
		
		
		actionBar.setTitle("Modifica info festa");
		 
		
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		nome=(EditText)findViewById(R.id.editnomefesta);
		generi =(EditText)findViewById(R.id.editgeneri);
		indirizzo = (EditText)findViewById(R.id.editindirizzo);
		giornoa = (EditText) findViewById(R.id.editgiornoa);
		giornoda = (EditText) findViewById(R.id.editgiornoda);
		oraa = (EditText) findViewById(R.id.editoraa);
		orada = (EditText) findViewById(R.id.editorada);
		continua = (Button) findViewById(R.id.confermaeditfesta);
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);
		Calendar newCalendar = Calendar.getInstance();
		int year = newCalendar.get(Calendar.YEAR);
		int month =newCalendar.get(Calendar.MONTH);
		int day = newCalendar.get(Calendar.DAY_OF_MONTH);
		int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = newCalendar.get(Calendar.MINUTE);
		
		generi.setText(genere);
		indirizzo.setText(luogo);
		nome.setText(nomefesta);
		
		giornoa.setOnClickListener(this);
		giornoda.setOnClickListener(this);
		oraa.setOnClickListener(this);
		orada.setOnClickListener(this);
		continua.setOnClickListener(this);
		
		
		
		
		
		fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
			public void onDateSet (DatePicker view, int year, int monthOfYear, int dayOfMonth){
				Calendar newDate = Calendar.getInstance();
	            newDate.set(year, monthOfYear, dayOfMonth);
	            giornoda.setText(dateFormatter.format(newDate.getTime()));
			}
		},year, month, day);
		
		toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
			public void onDateSet (DatePicker view, int year, int monthOfYear, int dayOfMonth){
				Calendar newDate = Calendar.getInstance();
	            newDate.set(year, monthOfYear, dayOfMonth);
	            giornoa.setText(dateFormatter.format(newDate.getTime()));
			}
		},year, month, day);
		
		fromTimePickerDialog = new TimePickerDialog (this, new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				
				
		        orada.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
				
			}
		}, hour, minute, true);

		toTimePickerDialog = new TimePickerDialog (this, new TimePickerDialog.OnTimeSetListener() {
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		
		
        oraa.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
		
	}
}, hour, minute, true);
		
	

}


	@Override
	public void onClick(View v) {
		if(v == giornoda) {
			fromDatePickerDialog.show();
		} else if(v== giornoa) {
			toDatePickerDialog.show();
		}else if(v== oraa) {
			toTimePickerDialog.show();
		}else if(v== orada) {
			fromTimePickerDialog.show();
		}
		
		if(v==continua){
			cambia();
			Intent i = new Intent(this,ViewFestaActivity.class);
			i.putExtra("id", myid);
			i.putExtra("idfesta", idfesta);
			startActivity(i);
		}
		
	}
	
	public void cambia(){
		String nomenuovo = nome.getText().toString();
		String indirizzonuovo = indirizzo.getText().toString();
		String generinuovo = generi.getText().toString();
String datainizio= giornoda.getText().toString();
String datafine=giornoa.getText().toString();
String orainizio=orada.getText().toString();
String orafine=oraa.getText().toString();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
StrictMode.setThreadPolicy(policy);
try {
	HttpClient httpclient = new DefaultHttpClient();
	HttpPost httppost = new HttpPost(
			ip.getindirizzo()+"ModificaFesta?idadmin="
					+ myid +"&idfesta="+idfesta+ "&nome=" + nomenuovo + "&datainizio=" + datainizio
					+ "&datafine=" + datafine + "&orainizio=" + orainizio
					+ "&orafine=" + orafine + "&luogo="
					+ indirizzonuovo+"&genere="+generinuovo);
	HttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();
Log.d("ciaoiaosaodsa","ciao");
	if (entity != null) {
		Log.d("ciaoiaosaodsa","ciao");
		String risposta = EntityUtils.toString(entity);
		JSONObject json = new JSONObject(risposta);
		//controllo = json.optString("modifica");
	}


	}catch (Exception e) {
		Log.e("TEST", "Errore nella connessione http " + e.toString());
	}
	}
	
	
public void confronta(){
		
		String nomenuovo = nome.getText().toString();
		String indirizzonuovo = indirizzo.getText().toString();
		String generinuovo = generi.getText().toString();
String datainizio= giornoda.getText().toString();
String datafine=giornoa.getText().toString();
String orainizio=orada.getText().toString();
String orafine=oraa.getText().toString();


		if (nomenuovo.equals("")) {
			 Toast.makeText(this,
					 "Il campo Nome non puo essere vuoto", Toast.LENGTH_LONG).show();
		} else if (indirizzonuovo.equals("")) {
			Toast.makeText(this,
					 "Il campo Indirizzo non puo essere vuoto", Toast.LENGTH_LONG).show();
		} else if (generinuovo.equals("")) {
			Toast.makeText(this,
					 "Il campo Generi non puo essere vuoto", Toast.LENGTH_LONG).show();			
		}else if (datainizio.equals("")) {
			Toast.makeText(this,
					 "Il campo Data Inizio non puo essere vuoto", Toast.LENGTH_LONG).show();	
		}else if (datafine.equals("")) {
			Toast.makeText(this,
					 "Il campo Data Fine non puo essere vuoto", Toast.LENGTH_LONG).show();			
		}else if (orafine.equals("")) {
			Toast.makeText(this,
					 "Il campo Ora Fine non puo essere vuoto", Toast.LENGTH_LONG).show();		
		}else if (orainizio.equals("")) {	
			Toast.makeText(this,
					 "Il campo Ora Inizio non puo essere vuoto", Toast.LENGTH_LONG).show();		
			} else {
			cambia();
			return;
		}
	}

}