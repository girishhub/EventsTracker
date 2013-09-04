package com.keepworks.eventtracker;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keepworks.database.TrackEventDatabase;
import com.keepworks.eventcontents.Events;
import com.keepworks.eventcontents.TrackEvents;
import com.keepworks.eventtracker.EventListFragment.SwitchMechanism;

public class EventsActivity extends FragmentActivity implements SwitchMechanism {

	private ArrayList<Events> mEventsList;
	int mPosition;
	boolean mFromMenu;

	ImageView mEventImage;
	TextView mEventName, mEventPlace, mEventEntry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_events);

		initializeViews();

		fetchIntentValues();
	}

	/**
	 * initialize all views of the layout
	 */
	private void initializeViews() {
		mEventImage = (ImageView) findViewById(R.id.event_detail_image);
		mEventName = (TextView) findViewById(R.id.event_detail_name);
		mEventPlace = (TextView) findViewById(R.id.event_detail_place);
		mEventEntry = (TextView) findViewById(R.id.event_detail_entry);
	}

	/**
	 * get values from intent of EventTrackActivity
	 */
	private void fetchIntentValues() {
		try {
			mFromMenu = getIntent().getExtras().getBoolean("from_menu");

			if (mFromMenu) {
				int index = getIntent().getExtras().getInt("index");
				showTrackedEventDetails(index);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * method of SwitchMechanism interface
	 */
	@Override
	public void showEventDetails(ArrayList<Events> eventsList, int position) {
		this.mEventsList = eventsList;
		this.mPosition = position;

		openEventDetailsFragment();

		mEventImage.setImageResource(eventsList.get(position).getEventImageResId());
		mEventName.setText(eventsList.get(position).getEventName());
		mEventPlace.setText(eventsList.get(position).getEventPlace());
		mEventEntry.setText(eventsList.get(position).getEventEntryType());
	}

	/**
	 * open event details fragment
	 */
	private void openEventDetailsFragment() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment listFragment = (EventListFragment) getSupportFragmentManager().findFragmentById(
				R.id.event_list_fragment);
		ft.hide(listFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	/**
	 * open EventTrackActivity activity
	 */
	public void openEventTrackActivity(View v) {
		Intent trackIntent = new Intent(this, EventTrackActivity.class);
		trackIntent.putParcelableArrayListExtra("event", mEventsList);
		trackIntent.putExtra("index", mPosition);
		startActivityForResult(trackIntent, 5);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 5) {
			if (resultCode == RESULT_OK) {
				int index = data.getExtras().getInt("index");
				showTrackedEventDetails(index);
			}
		}
	}

	/**
	 * common method for showing event details
	 * 
	 * from both list of events and menu option
	 */
	private void showTrackedEventDetails(int index) {
		TrackEventDatabase trackEventDatabase = new TrackEventDatabase(this);
		ArrayList<TrackEvents> trackList = trackEventDatabase.getListOfEvents();

		if (mFromMenu) {
			openEventDetailsFragment();
		}

		mEventName.setText(trackList.get(index).getTrackedEventName());
		mEventPlace.setText(trackList.get(index).getTrackedEventPlace());
		mEventEntry.setText(trackList.get(index).getTrackedEventEntry());
		mEventImage.setImageResource(trackList.get(index).getTrackedEventImageResId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_favorites: {
			Intent intent = new Intent(this, EventTrackActivity.class);
			intent.putExtra("from_menu", true);
			startActivity(intent);
		}
			break;

		default:
			break;
		}
		return true;
	}
}
