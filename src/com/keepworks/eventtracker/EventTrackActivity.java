package com.keepworks.eventtracker;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.keepworks.database.TrackEventDatabase;
import com.keepworks.eventcontents.Events;
import com.keepworks.eventcontents.TrackEvents;
import com.keepworks.utils.DataStorage;

public class EventTrackActivity extends Activity {

	ArrayList<Events> mEventList;
	ArrayList<TrackEvents> mTrackList;
	EventTrackAdapter mEventTrackAdapter;
	TrackEventDatabase mTrackEventDatabase;
	int mIndex;
	Events mEvents;
	ListView mTrackListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_track);

		initializeComponents();
		checkEventsInTracker();
		listViewItemActions();
	}

	/**
	 * initialize all activity related components
	 */
	private void initializeComponents() {
		setTitle(getString(R.string.tracked_events) + " " + DataStorage.getUserName(this));
		mTrackEventDatabase = new TrackEventDatabase(this);
		mTrackList = mTrackEventDatabase.getListOfEvents();
		mEventList = getIntent().getParcelableArrayListExtra("event");
		mTrackListView = (ListView) findViewById(R.id.event_track_listview);
	}

	/**
	 * check event list size
	 */
	private void checkTrackListSize() {
		if (mTrackList.size() == 0) {
			Toast.makeText(this, getString(R.string.no_events), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * check no. of events present in tracker
	 * 
	 * and add accordingly
	 */
	private void checkEventsInTracker() {
		if (mEventList != null) {
			mIndex = getIntent().getExtras().getInt("index");

			mEvents = mEventList.get(mIndex);

			addEventToTracker();
		} else {
			checkTrackListSize();
		}
	}

	/**
	 * actions such as event details and 
	 * 
	 * remove event are performed
	 */
	private void listViewItemActions() {
		mEventTrackAdapter = new EventTrackAdapter(this, mTrackList);
		mTrackListView.setAdapter(mEventTrackAdapter);

		mTrackListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				sendIntentValues(position);
			}
		});

		mTrackListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
				showDeleteDialog(mTrackList.get(position).getTrackedEventName(), position);
				return true;
			}
		});
		mEventTrackAdapter.notifyDataSetChanged();
	}

	/**
	 * send the required values through intent
	 * 
	 * for EventsActivity activity
	 */
	private void sendIntentValues(int position) {
		try {
			boolean key = getIntent().getExtras().getBoolean("from_menu");
			if (key) {
				Intent intent = new Intent(EventTrackActivity.this, EventsActivity.class);
				intent.putExtra("from_menu", true);
				intent.putExtra("index", position);
				startActivity(intent);
				finish();
			} else {
				Intent intent = new Intent();
				intent.putExtra("index", position);
				setResult(RESULT_OK, intent);
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * method where tracked events are filtered & added
	 */
	private void addEventToTracker() {
		boolean isSameEvent = true;
		String userName, eventName, eventPlace, eventEntry;
		int eventImageResId;

		userName = DataStorage.getUserName(this);
		eventName = mEvents.getEventName();
		eventPlace = mEvents.getEventPlace();
		eventEntry = mEvents.getEventEntryType();
		eventImageResId = mEvents.getEventImageResId();

		if (mTrackList.size() == 0) {
			mTrackList.add(new TrackEvents(userName, eventName, eventPlace, eventEntry, eventImageResId));
			mTrackEventDatabase.addEvent(new TrackEvents(userName, eventName, eventPlace, eventEntry, eventImageResId));
		} else {

			for (int i = 0; i < mTrackList.size(); i++) {
				if (!eventName.equals(mTrackList.get(i).getTrackedEventName())) {
					isSameEvent = false;
				} else {
					isSameEvent = true;
					break;
				}
			}

			if (!isSameEvent) {
				mTrackList.add(new TrackEvents(userName, eventName, eventPlace, eventEntry, eventImageResId));
				mTrackEventDatabase.addEvent(new TrackEvents(userName, eventName, eventPlace, eventEntry,
						eventImageResId));
			}
		}
	}

	/**
	 * to remove event from track list
	 */
	private void showDeleteDialog(final String eventName, final int position) {
		String[] event = { getString(R.string.remove_event) };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(eventName);
		builder.setItems(event, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int index) {
				dialog.cancel();
				mTrackEventDatabase.deleteTrackedEvent(eventName);
				mTrackList.remove(position);
				mEventTrackAdapter.notifyDataSetChanged();
				checkTrackListSize();
			}
		}).show();
	}

	public class EventTrackAdapter extends BaseAdapter {

		ArrayList<TrackEvents> trackList;
		LayoutInflater layoutInflater;
		Context ctx;

		public EventTrackAdapter(Context context, ArrayList<TrackEvents> trackList) {
			ctx = context;
			this.trackList = trackList;
			layoutInflater = LayoutInflater.from(ctx);
		}

		@Override
		public int getCount() {
			return trackList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.event_items_list, null);
			}

			ImageView eventImage = (ImageView) convertView.findViewById(R.id.event_image);
			eventImage.setImageResource(trackList.get(position).getTrackedEventImageResId());

			TextView eventName = (TextView) convertView.findViewById(R.id.event_name);
			eventName.setText(trackList.get(position).getTrackedEventName());

			TextView eventPlace = (TextView) convertView.findViewById(R.id.event_place);
			eventPlace.setText(trackList.get(position).getTrackedEventPlace());

			TextView eventEntry = (TextView) convertView.findViewById(R.id.event_entry);
			eventEntry.setText(trackList.get(position).getTrackedEventEntry());

			return convertView;
		}
	}
}
