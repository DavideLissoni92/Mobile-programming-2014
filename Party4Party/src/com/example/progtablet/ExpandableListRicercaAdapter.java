package com.example.progtablet;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableListRicercaAdapter extends BaseExpandableListAdapter {

int myid,idutente;
	public ArrayList<String> groupItem, tempChild;
	 public ArrayList<Object> Childtem = new ArrayList<Object>();
	 public LayoutInflater minflater;
	 public Activity activity;
	 Context contextactivity;
	 List<Utente> utentilist;
	 List<Utente>preferitilist;
	public int posgruppo;
	 public ExpandableListRicercaAdapter(ArrayList<String> grList, ArrayList<Object> childItem,Context context,int myid,int idutente,List<Utente> utentilist,List<Utente>preferitilist) {
		 this.contextactivity=context;
		 this.myid=myid;
		 this.idutente=idutente;
		  groupItem = grList;
		  this.Childtem = childItem;
		  this.utentilist=utentilist;
		  this.preferitilist=preferitilist; 
		 
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
	  posgruppo=groupPosition;
	  if (convertView == null) {
	   convertView = minflater.inflate(R.layout.ricercalistachild, null);
	  }
	  text = (TextView) convertView.findViewById(R.id.textView1);
	  text.setText(tempChild.get(childPosition));
	  
	  convertView.setOnClickListener(new OnClickListener() {
	   @Override
	   public void onClick(View v) {
		   int iduscio1;
		 if (posgruppo==0){iduscio1=preferitilist.get(childPosition).getId();}else{
		   iduscio1=utentilist.get(childPosition).getId();
		 }
		   
		   
		   
		   Intent i = new Intent(contextactivity, ViewUtenteActivity.class);
		   
			i.putExtra("id", myid);
			i.putExtra("idutente", iduscio1);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			contextactivity.startActivity(i);
	    Toast.makeText(activity, tempChild.get(childPosition),
	      Toast.LENGTH_SHORT).show();
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
	   convertView = minflater.inflate(R.layout.ricercalistaheader, null);
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
	 public boolean isChildSelectable(int groupPosition, int childPosition) {
	  return false;
	 }

	}
	
	
	
	
