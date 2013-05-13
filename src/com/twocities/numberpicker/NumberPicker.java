package com.twocities.numberpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.twocities.numberpicker.R;

public class NumberPicker extends LinearLayout {

	private int currentValue = 0;
	private int minValue = 0;
	private int maxValue = 100;
	private Button mDecreaseButton;
	private Button mIncreaseButton;
	private EditText mEditView;
	private NumberChangeListener listener;

	public NumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.NumberPicker, 0, 0);

		try {
			// Retrieve the values from the TypedArray and store into
			// fields of this class.
			//
			// The R.styleable.PieChart_* constants represent the index for
			// each custom attribute in the R.styleable.PieChart array.
			minValue = a.getInt(R.styleable.NumberPicker_minValue, 0);
			maxValue = a.getInt(R.styleable.NumberPicker_maxValue, 100);
			currentValue = a.getInt(R.styleable.NumberPicker_currentValue, 0);
		} finally {
			// release the TypedArray so that it can be reused.
			a.recycle();
		}

		if (minValue >= maxValue) {
			throw new IllegalArgumentException(
					"Max number must bigger than min");
		}

		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.number_picker, this, true);
		mDecreaseButton = (Button) findViewById(R.id.decrease_button);
		mIncreaseButton = (Button) findViewById(R.id.increase_button);
		mEditView = (EditText) findViewById(R.id.content);

		mDecreaseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentValue > minValue) {
					mEditView.setText(String.valueOf(--currentValue));
				}
				buttonControl();
				if (listener != null) {
					listener.NumberChanged(currentValue);
				}
			}
		});

		mIncreaseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentValue < maxValue) {
					mEditView.setText(String.valueOf(++currentValue));
				}
				buttonControl();
				if (listener != null) {
					listener.NumberChanged(currentValue);
				}
			}
		});

		mEditView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (TextUtils.isDigitsOnly(s)) {
					int value = 0;
					try {
						value = Integer.parseInt(s.toString());
					} catch (NumberFormatException e) {
						value = minValue;
					}

					if (value < minValue) {
						value = minValue;
						mEditView.setText(String.valueOf(value));
						currentValue = value;
					} else if (value > maxValue) {
						value = maxValue;
						mEditView.setText(String.valueOf(value));
						currentValue = value;
					} else {
						currentValue = value;
					}
					buttonControl();
					if (listener != null) {
						listener.NumberChanged(currentValue);
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	public void setCurrentValue(int value) {
		if (value < minValue || value > maxValue) {
			return;
		}

		mEditView.setText(String.valueOf(value));
	}

	public void setValueRange(int min, int max) {
		if (min <= max) {
			throw new IllegalArgumentException(
					"Max number must bigger than min");
		}
		this.minValue = min;
		this.maxValue = max;
	}

	public int getCurrentValue() {
		return this.currentValue;
	}

	private void buttonControl() {
		if (currentValue <= minValue) {
			mDecreaseButton.setEnabled(false);
		} else if (currentValue >= maxValue) {
			mIncreaseButton.setEnabled(false);
		} else {
			mDecreaseButton.setEnabled(true);
			mIncreaseButton.setEnabled(true);
		}
	}

	public void setOnNumberChangeListener(NumberChangeListener listener) {
		this.listener = listener;
	}

	public interface NumberChangeListener {
		public void NumberChanged(int value);
	}

}