package com.distributie.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public final class InfoNrAuto extends Activity {

	private Button btnInchide;
	private TextView textInfo;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTheme(R.style.LRTheme);
		setContentView(R.layout.info_nr_auto);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Info");
		actionBar.setDisplayHomeAsUpEnabled(true);

		setupLayout();
	}

	private void setupLayout() {
		btnInchide = (Button) findViewById(R.id.btnInchide);
		setListenerBtnInchide();

		textInfo = (TextView) findViewById(R.id.textInfo);

		if (getIntent().getStringExtra("infoStr") != null)
			textInfo.setText(getIntent().getStringExtra("infoStr"));
	}

	private void setListenerBtnInchide() {
		btnInchide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				System.exit(0);

			}
		});

	}

}