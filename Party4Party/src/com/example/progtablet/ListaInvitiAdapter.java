package com.example.progtablet;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;
public class ListaInvitiAdapter extends BaseExpandableListAdapter{

	public ArrayList<String> groupItem, tempChild;
	 public ArrayList<Object> Childtem = new ArrayList<Object>();
	 public LayoutInflater minflater;
	 public Activity activity;
	 List<Utente>utentilist;
	 List<Utente>preferitilist;
		IP ip = new IP();

	 public int numero,idfesta;
	 Context context;
	 public ListaInvitiAdapter(ArrayList<String> grList, ArrayList<Object> childItem,List<Utente>utentilist,List<Utente>preferitilist,int idfesta,Context context) {
		 this.context=context;
		  groupItem = grList;
		  this.Childtem = childItem;
		  this.preferitilist=preferitilist;
		  this.utentilist=utentilist;
		  this.idfesta=idfesta;
		 }
	 
	 public void setInflater(LayoutInflater mInflater, Activity act) {
		  this.minflater = mInflater;
		  activity = act;
		 }
	 
	 
	 @Override
	 public Object getChild(int groupPosition, int childPosition) {
	  return null;
	 }

	 @Override
	 public long getChildId(int groupPosition, int childPosition) {
	  return 0;
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 @Override
	 public View getChildView(int groupPosition, final int childPosition,
	   boolean isLastChild, View convertView, ViewGroup parent) {
	  tempChild = (ArrayList<String>) Childtem.get(groupPosition);
	  TextView text = null;
	  numero=groupPosition;
	  if (convertView == null) {
	   convertView = minflater.inflate(R.layout.ricercalistachild, null);
	  }
	  text = (TextView) convertView.findViewById(R.id.textView1);
	  text.setText(tempChild.get(childPosition));
	  convertView.setOnClickListener(new OnClickListener() {
	   @Override
	   public void onClick(View v) {
		   
		  
			int iduscio;
		   try {
				if (numero==0){
					Toast toast = Toast.makeText(context,"Utente invitato",Toast.LENGTH_LONG);
					toast.show();
					iduscio=preferitilist.get(childPosition).getId();
					
				}else{iduscio=utentilist.get(childPosition).getId();
				Toast toast = Toast.makeText(context,"Utente invitato",Toast.LENGTH_LONG);
				toast.show();
				}
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						ip.getindirizzo()+"InvitaFesta?idutente="
								+ iduscio+"&idfesta="+idfesta);
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();}catch(Exception e){}

	   }
	  });
	  return convertView;
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 @SuppressWarnings("unchecked")
		@Override
		 public int getChildrenCount(int groupPosition) {
		  return ((ArrayList<String>) Childtem.get(groupPosition)).size();
		 }

		 @Override
		 public Object getGroup(int groupPosition) {
		  return null;
		 }

		 @Override
		 public int getGroupCount() {
		  return groupItem.size();
		 }

		 @Override
		 public void onGroupCollapsed(int groupPosition) {
		  super.onGroupCollapsed(groupPosition);
		 }

		 @Override
		 public void onGroupExpanded(int groupPosition) {
		  super.onGroupExpanded(groupPosition);
		 }

		 @Override
		 public long getGroupId(int groupPosition) {
		  return 0;
		 }

		 
		 
		 
		 
		 
		 
		 
		 
		 
		 

		 @Override
		 public View getGroupView(int groupPosition, boolean isExpanded,
		   View convertView, ViewGroup parent) {
		  if (convertView == null) {
		   convertView = minflater.inflate(R.layout.invitalistaheader, null);
		  }
		  ((CheckedTextView) convertView).setText(groupItem.get(groupPosition));
		  ((CheckedTextView) convertView).setChecked(isExpanded);
		  return convertView;
		 }
	@Override
	public boolean hasStableIds() {
		
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		
		return true;
	}
	
	
	}