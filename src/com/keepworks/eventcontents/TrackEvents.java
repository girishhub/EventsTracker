package com.keepworks.eventcontents;

public class TrackEvents {

	private String mUserName;
	private String mTrackedEventName;
	private String mTrackedEventPlace;
	private String mTrackedEventEntry;
	private int mTrackedEventImageResId;

	public TrackEvents() {
	}

	public TrackEvents(String userName, String trackedEventName, String trackedEventPlace, String trackedEventEntry,
			int trackedEventImageResId) {
		this.mUserName = userName;
		this.mTrackedEventName = trackedEventName;
		this.mTrackedEventPlace = trackedEventPlace;
		this.mTrackedEventEntry = trackedEventEntry;
		this.mTrackedEventImageResId = trackedEventImageResId;
	}

	public String getTrackedEventName() {
		return mTrackedEventName;
	}

	public void setTrackedEventName(String trackedEventName) {
		this.mTrackedEventName = trackedEventName;
	}

	public String getTrackedEventPlace() {
		return mTrackedEventPlace;
	}

	public void setTrackedEventPlace(String trackedEventPlace) {
		this.mTrackedEventPlace = trackedEventPlace;
	}

	public String getTrackedEventEntry() {
		return mTrackedEventEntry;
	}

	public void setTrackedEventEntry(String trackedEventEntry) {
		this.mTrackedEventEntry = trackedEventEntry;
	}

	public int getTrackedEventImageResId() {
		return mTrackedEventImageResId;
	}

	public void setTrackedEventImageResId(int trackedEventImageResId) {
		this.mTrackedEventImageResId = trackedEventImageResId;
	}

	public String getUserName() {
		return mUserName;
	}

	public void setUserName(String userName) {
		this.mUserName = userName;
	}
}