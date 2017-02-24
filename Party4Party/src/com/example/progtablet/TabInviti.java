package com.example.progtablet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.progtablet.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TabInviti extends Fragment {
	IP ip = new IP();

	int countfeste=10;

	int myid;
	int[] idfesta;

	String nome,ora,data;
	
	 String[] nomefesta;

		 String[] giornofesta;
		
		 String[] mesefesta;
		
		String[] orafesta;
		
		String[] datafesta;
		
		

		

	public TabInviti(int myid) {
this.myid=myid;		}





	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragmentpartecipo, container, false);
		List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

		Log.d("SONOQUI","SOO");
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					ip.getindirizzo()+"HomeInviti?idutente="
							+ myid);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONArray jarr = new JSONArray(risposta);
				Log.d("SONOQUI","SOO");

				nomefesta=new String[jarr.length()];
				orafesta=new String[jarr.length() ];
				giornofesta=new String[jarr.length()];
				mesefesta=new String[jarr.length()];
				idfesta = new int[jarr.length()];

				JSONObject json;
				countfeste=0;
				for (int i = 0; i < jarr.length(); i++) {
					json = jarr.getJSONObject(i);
					
					countfeste=jarr.length();
					if(jarr.length()==0){
						/*
					nomefesta=new String[0];
					orafesta=new String[0];
					giornofesta=new String[0];
					mesefesta=new String[0]*/;}
					else{
						idfesta[i] = json.optInt("id");

					nomefesta[i] = json.optString("name");
					orafesta[i] = json.optString("orainizio");
					data=json.optString("datainizio");
					giornofesta[i]=data.substring(8, 10);
					mesefesta[i]=data.substring(5, 7);
					
					}
					Log.d("ERROREINVITI", Integer.toString(jarr.length()));

					}
			} 

		} catch (Exception e) {
			Log.e("TEST", "Errore nella connessione http " + e.toString());
		}
		
		
		
		
		
		
		
		
		
		for(int i=0;i<countfeste;i++){
		    HashMap<String, String> hm = new HashMap<String,String>();
		    hm.put("nome", nomefesta[i]);
		    hm.put("dg",giornofesta[i]);
		    hm.put("mg",mesefesta[i]);
		    hm.put("ora",orafesta[i]);
		
		   
		    aList.add(hm);        
		}
		String[] from = {"nome","dg", "mg","ora","invito" };

		int[] to = { R.id.nomefesta,R.id.giornofesta, R.id.mesefesta, R.id.orarioinizio,R.id.invitato}; 
		

		
		    ListView list = (ListView)rootView.findViewById(R.id.listview);
		    
		SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.rigahome, from, to ); 
		
		    list.setAdapter(adapter);
		    
		    list.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent i = new Intent(getActivity().getBaseContext(),ViewFestaActivity.class);
					i.putExtra("idfesta", idfesta[arg2]);
					i.putExtra("id", myid);
					startActivity(i);
					
				}
		    	
		    });
		
		
		
		return rootView;
	}



	
}
