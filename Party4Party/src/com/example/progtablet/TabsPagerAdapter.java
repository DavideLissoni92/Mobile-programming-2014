package com.example.progtablet;

import com.example.progtablet.TabInviti;
import com.example.progtablet.TabNonPartecipo;
import com.example.progtablet.TabPartecipo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	int myid;
	public TabsPagerAdapter(FragmentManager fragmentManager,int myid) {
		super(fragmentManager);
		this.myid=myid;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new TabPartecipo(myid);
		case 1:
			// Games fragment activity
			return new TabInviti(myid);
		case 2:
			// Movies fragment activity
			return new TabNonPartecipo(myid);
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
