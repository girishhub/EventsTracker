package com.keepworks.eventtracker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.keepworks.eventcontents.Events;

public class EventListFragment extends Fragment {

	SwitchMechanism mSwitchMechanism;
	ArrayList<Events> mEventsList;

	/**
	 * interface to switch between two fragments 
	 */
	public interface SwitchMechanism {
		void showEventDetails(ArrayList<Events> eventsList, int position);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mSwitchMechanism = (SwitchMechanism) activity;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setEventsInList();
	}

	/**
	 * inflates events list fragment view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event_items, container, false);

		ListView listView = (ListView) view.findViewById(R.id.event_listview);
		EventsAdapter eventsAdapter = new EventsAdapter(getActivity(), mEventsList);
		listView.setAdapter(eventsAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				mSwitchMechanism.showEventDetails(mEventsList, position);
			}
		});

		return view;
	}

	/**
	 * set all the events in list 
	 */
	private void setEventsInList() {

		mEventsList = new ArrayList<Events>();
		mEventsList.add(new Events(getString(R.string.metallica_concert), getString(R.string.palace_grounds),
				getString(R.string.paid), getResources().getIdentifier("metallica", "drawable",
						getActivity().getPackageName())));

		mEventsList.add(new Events(getString(R.string.saree_exhibition), getString(R.string.malleswaram_grounds),
				getString(R.string.free), getResources().getIdentifier("saree", "drawable",
						getActivity().getPackageName())));

		mEventsList.add(new Events(getString(R.string.wine_tasting_event), getString(R.string.links_brewery),
				getString(R.string.paid), getResources().getIdentifier("wine", "drawable",
						getActivity().getPackageName())));

		mEventsList.add(new Events(getString(R.string.startups_meet), getString(R.string.kanteerava),
				getString(R.string.paid), getResources().getIdentifier("startup", "drawable",
						getActivity().getPackageName())));

		mEventsList.add(new Events(getString(R.string.summer_noon_party), getString(R.string.kumara_park),
				getString(R.string.paid), getResources().getIdentifier("summer_party", "drawable",
						getActivity().getPackageName())));

		mEventsList.add(new Events(getString(R.string.rock_and_roll_nights), getString(R.string.sarjapur_road),
				getString(R.string.paid), getResources().getIdentifier("rock_n_roll", "drawable",
						getActivity().getPackageName())));

		mEventsList.add(new Events(getString(R.string.barbecue_fridays), getString(R.string.whitefield),
				getString(R.string.paid), getResources().getIdentifier("barbecue", "drawable",
						getActivity().getPackageName())));

		mEventsList.add(new Events(getString(R.string.summer_workshop), getString(R.string.indiranagar),
				getString(R.string.free), getResources().getIdentifier("workshop", "drawable",
						getActivity().getPackageName())));

		mEventsList.add(new Events(getString(R.string.impressions_expressions), getString(R.string.mg_road),
				getString(R.string.free), getResources().getIdentifier("impressions", "drawable",
						getActivity().getPackageName())));

		mEventsList.add(new Events(getString(R.string.italian_carnival), getString(R.string.electronic_city),
				getString(R.string.free), getResources().getIdentifier("carnival", "drawable",
						getActivity().getPackageName())));
	}

	public class EventsAdapter extends BaseAdapter {

		ArrayList<Events> eventList;
		LayoutInflater layoutInflater;
		Context ctx;

		public EventsAdapter(Context context, ArrayList<Events> eventList) {
			ctx = context;
			this.eventList = eventList;
			layoutInflater = LayoutInflater.from(ctx);
		}

		@Override
		public int getCount() {
			return eventList.size();
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
			eventImage.setImageResource(eventList.get(position).getEventImageResId());

			TextView eventName = (TextView) convertView.findViewById(R.id.event_name);
			eventName.setText(eventList.get(position).getEventName());

			TextView eventPlace = (TextView) convertView.findViewById(R.id.event_place);
			eventPlace.setText(eventList.get(position).getEventPlace());

			TextView eventEntry = (TextView) convertView.findViewById(R.id.event_entry);
			eventEntry.setText(eventList.get(position).getEventEntryType());

			return convertView;
		}

	}

}
