package com.keepworks.eventcontents;

import android.os.Parcel;
import android.os.Parcelable;

public class Events implements Parcelable {

	private String mEventName;
	private String mEventPlace;
	private String mEventEntryType;
	private int mEventImageResId;

	public Events(String eventName, String eventPlace, String eventEntryType, 
			int eventImageResId) {
		this.mEventName = eventName;
		this.mEventPlace = eventPlace;
		this.mEventEntryType = eventEntryType;
		this.mEventImageResId = eventImageResId;
	}

	public String getEventName() {
		return mEventName;
	}

	public void setEventName(String eventName) {
		this.mEventName = eventName;
	}

	public String getEventPlace() {
		return mEventPlace;
	}

	public void setEventPlace(String eventPlace) {
		this.mEventPlace = eventPlace;
	}

	public String getEventEntryType() {
		return mEventEntryType;
	}

	public void setEventEntryType(String eventEntryType) {
		this.mEventEntryType = eventEntryType;
	}

	public int getEventImageResId() {
		return mEventImageResId;
	}

	public void setEventImageResId(int eventImageResId) {
		this.mEventImageResId = eventImageResId;
	}

	public Events(Parcel par) {
		mEventName = par.readString();
		mEventPlace = par.readString();
		mEventEntryType = par.readString();
		mEventImageResId = par.readInt();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mEventName);
		dest.writeString(mEventPlace);
		dest.writeString(mEventEntryType);
		dest.writeInt(mEventImageResId);
	}

	public static final Parcelable.Creator<Events> CREATOR = new Parcelable.Creator<Events>() {

		@Override
		public Events createFromParcel(Parcel parcel) {
			return new Events(parcel);
		}

		@Override
		public Events[] newArray(int size) {
			return new Events[size];
		}
	};
}
