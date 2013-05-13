package com.twocities.numberpicker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.twocities.numberpicker.NumberPicker.NumberChangeListener;

public class MainActivity extends Activity implements NumberChangeListener{

	private NumberPicker mPicker;
	private TextView mResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mPicker = (NumberPicker) findViewById(R.id.picker);
		mResult = (TextView) findViewById(R.id.result);
		
		mPicker.setOnNumberChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void NumberChanged(int value) {
		mResult.setText(String.valueOf(value));
	}
}
