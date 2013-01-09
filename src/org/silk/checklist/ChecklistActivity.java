package org.silk.checklist;

import java.util.Map;

import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.Group;
import org.silk.checklist.model.Item;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ChecklistActivity extends FragmentActivity implements
		ActionBar.TabListener {

	Checklist checklist;
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checklist);
		
		//get checklist
		checklist = ((MyApp)getApplication()).getChecklist();

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_checklist, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return checklist.numberOfGroups();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			
//			switch (position) {
//			case 0:
//				return getString(R.string.title_section1).toUpperCase();
//			case 1:
//				return getString(R.string.title_section2).toUpperCase();
//			case 2:
//				return getString(R.string.title_section3).toUpperCase();
//			}
//			return null;
			
			Map<String,Group> groupMap = checklist.getGroups();
			Group[] groups = groupMap.values().toArray(new Group[groupMap.size()]);
			return groups[position].getGroupName();
			
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		Checklist checklist;

		public DummySectionFragment() {
			//get checklist
			
		}

//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			// Create a new TextView and set its text to the fragment's section
//			// number argument value.
//			TextView textView = new TextView(getActivity());
//			textView.setGravity(Gravity.CENTER);
//			textView.setText(Integer.toString(getArguments().getInt(
//					ARG_SECTION_NUMBER)));
//			return textView;
//		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			/*
			checklist = ((MyApp)getActivity().getApplication()).getChecklist();
			Map<String,Group> groupMap = checklist.getGroups();
			Group[] groups = groupMap.values().toArray(new Group[groupMap.size()]);
			int index = getArguments().getInt(ARG_SECTION_NUMBER);
			Map<String, Item> itemMap = groups[index].getItems();
			
			Item[] items = itemMap.values().toArray(new Item[itemMap.size()]);
			String[] values = new String[items.length];
			for (int i = 0; i < items.length; i++) {
				values[i] = items[i].getTitle();
			}
			
			
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
			
			ListView lv = new ListView(getActivity());
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), ItemActivity.class);
					startActivity(intent);
//					Toast.makeText(getActivity(), arg2+" "+arg3, Toast.LENGTH_SHORT).show();
				}
			});
			
			return lv;
			*/
			ListView lv = new ListView(getActivity());
			int groupIndex = getArguments().getInt(ARG_SECTION_NUMBER);
			ChecklistAdapter checklistAdapter = new ChecklistAdapter(getActivity(), groupIndex);
//			checklistAdapter.setGroupIndex(groupIndex);
			lv.setAdapter(checklistAdapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long itemId) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), ItemActivity.class);
					intent.putExtra("itemId", itemId);
					startActivity(intent);
//					Toast.makeText(getActivity(), arg2 +" "+arg3, Toast.LENGTH_SHORT).show();
				}
			});
			return lv;
		}
	}
	

}
