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

public class NewPartyActivity extends Activity implements OnClickListener{
	EditText giornoda, giornoa, orada, oraa,editnome,editindirizzo,editgeneri,editdescrizione;
	int year, month, day,myid,idfesta;
	Button continua;
	String controllo="false";
	IP ip = new IP();
	
	private TimePickerDialog fromTimePickerDialog;
	private TimePickerDialog toTimePickerDialog;
	private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newparty);
		ActionBar actionBar= getActionBar();
		Bundle bundle = getIntent().getExtras();
		myid= bundle.getInt("id");
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		giornoa = (EditText) findViewById(R.id.giornoa);
		giornoda = (EditText) findViewById(R.id.giornoda);
		oraa = (EditText) findViewById(R.id.oraa);
		orada = (EditText) findViewById(R.id.orada);
		continua = (Button) findViewById(R.id.continuacreafesta);
		editnome=(EditText) findViewById(R.id.Nome);
		editindirizzo=(EditText) findViewById(R.id.Indirizzo);
		editgeneri=(EditText) findViewById(R.id.Generi);
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);
		editdescrizione=(EditText)findViewById(R.id.descrizionefesta);
		Calendar newCalendar = Calendar.getInstance();
		int year = newCalendar.get(Calendar.YEAR);
		int month =newCalendar.get(Calendar.MONTH);
		int day = newCalendar.get(Calendar.DAY_OF_MONTH);
		int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = newCalendar.get(Calendar.MINUTE);
		
		
		giornoa.setOnClickListener(this);
		giornoda.setOnClickListener(this);
		oraa.setOnClickListener(this);
		orada.setOnClickListener(this);
		continua.setOnClickListener(this);
		
		/*continua.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			confronta();	
			}
		});*/
		
			
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
		confronta();
			
		}
		
		
	}
	
	
	public void confronta(){
		
		String nome = editnome.getText().toString();
		String indirizzo = editindirizzo.getText().toString();
		String generi = editgeneri.getText().toString();
String datainizio= giornoda.getText().toString();
String datafine=giornoa.getText().toString();
String orainizio=orada.getText().toString();
String orafine=oraa.getText().toString();


		if (nome.equals("")) {
			 Toast.makeText(this,
					 "Il campo Nome non puo essere vuoto", Toast.LENGTH_LONG).show();
		} else if (indirizzo.equals("")) {
			Toast.makeText(this,
					 "Il campo Indirizzo non puo essere vuoto", Toast.LENGTH_LONG).show();
		} else if (generi.equals("")) {
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
			registrazione();
			return;
		}
	}

	public void registrazione(){
		
		String nome = editnome.getText().toString();
		String indirizzo = editindirizzo.getText().toString();
		String generi = editgeneri.getText().toString();
String datainizio= giornoda.getText().toString();
String datafine=giornoa.getText().toString();
String orainizio=orada.getText().toString();
String orafine=oraa.getText().toString();
String descrizione=editdescrizione.getText().toString();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
StrictMode.setThreadPolicy(policy);
try {
	HttpClient httpclient = new DefaultHttpClient();
	HttpPost httppost = new HttpPost(
			ip.getindirizzo()+"CreateFesta?nome="
					+ nome + "&idadmin=" + myid + "&datainizio=" + datainizio
					+ "&datafine=" + datafine + "&orainizio=" + orainizio+"&orafine=" + orafine+"&luogo=" + indirizzo+"&generi=" + generi+"&descrizione="+descrizione);
	HttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();
	Log.d("SONOQUI","QUI3");

	if (entity != null) {
		String risposta = EntityUtils.toString(entity);
		JSONObject json = new JSONObject(risposta);
		controllo = json.optString("creazione");
		idfesta=json.optInt("idfesta");
	}
	Log.d("json", Integer.toString(idfesta));
	

	if (controllo.equals("true")) {
		
		final Intent intent = new Intent(NewPartyActivity.this,
				NewPartyActivity2.class);
		intent.putExtra("id", myid);
		intent.putExtra("idfesta", idfesta);
		startActivity(intent);
	} else {
		Toast toast6 = Toast.makeText(this, "Errore",
				Toast.LENGTH_LONG);
		toast6.show();
	}

} catch (Exception e) {
	Log.e("TEST", "Errore nella connessione http " + e.toString());
}
		
	}

	
	}       
        
