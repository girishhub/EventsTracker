package com.keepworks.eventtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.keepworks.utils.DataStorage;

public class MainActivity extends Activity implements OnClickListener, TextWatcher {

	private EditText mNameText;
	String mUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeViews();
	}

	/**
	 * initialize views of this layout
	 */
	private void initializeViews() {
		mNameText = (EditText) findViewById(R.id.person_name);
		Button continueButton = (Button) findViewById(R.id.continue_button);
		continueButton.setOnClickListener(this);
		mNameText.addTextChangedListener(this);
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		mNameText.setError(null);
	}

	/**
	 * method to check whether user has entered the name
	 */
	private boolean validate() {
		boolean isNameGiven = false;
		View focusView = null;

		mUserName = mNameText.getText().toString().trim();

		if (TextUtils.isEmpty(mUserName)) {
			mNameText.setError(getString(R.string.blank_name));
			focusView = mNameText;
		} else {
			isNameGiven = true;
		}

		if (focusView != null) {
			focusView.requestFocus();
		}

		return isNameGiven;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.continue_button:
			if (validate()) {
				DataStorage.setUserName(this, mUserName);
				startActivity(new Intent(this, EventsActivity.class));
				finish();
			}
			break;

		default:
			break;
		}
	}
}
