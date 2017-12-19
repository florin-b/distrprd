package com.distributie.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.distributie.beans.Borderou;
import com.distributie.beans.StareValidareKm;
import com.distributie.enums.EnumOperatiiBorderou;
import com.distributie.enums.EnumOperatiiSofer;
import com.distributie.listeners.BorderouriDAOListener;
import com.distributie.listeners.SoferiListener;
import com.distributie.model.BorderouriDAOImpl;
import com.distributie.model.HandleJSONData;
import com.distributie.model.OperatiiSoferi;
import com.distributie.model.UserInfo;

public final class KmMasina extends Activity implements SoferiListener, BorderouriDAOListener {

	private Button btnOne;
	private Button btnTwo;
	private Button btnThree;
	private Button btnFour;
	private Button btnFive;
	private Button btnSix;
	private Button btnSeven;
	private Button btnEight;
	private Button btnNine;
	private Button btnZero;
	private Button btnClear;
	private TextView textKm;
	private Button btnContinua;

	private OperatiiSoferi opSoferi;

	private int initKm;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTheme(R.style.LRTheme);
		setContentView(R.layout.km_masina);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Km bord");
		actionBar.setDisplayHomeAsUpEnabled(true);

		opSoferi = new OperatiiSoferi(this);
		opSoferi.setSoferiListener(this);

		setupLayout();
		getKmMasina();

	}

	private void getKmMasina() {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("nrMasina", UserInfo.getInstance().getNrAuto());

		opSoferi.getKmMasina(params);

	}

	private void setupLayout() {

		textKm = (TextView) findViewById(R.id.textKm);

		btnContinua = (Button) findViewById(R.id.btnContinua);
		setListenerBtnContinua();

		btnOne = (Button) findViewById(R.id.btnOne);
		btnOne.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setKmValue("1");

			}
		});

		btnTwo = (Button) findViewById(R.id.btnTwo);
		btnTwo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setKmValue("2");

			}
		});

		btnThree = (Button) findViewById(R.id.btnThree);
		btnThree.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setKmValue("3");

			}
		});

		btnFour = (Button) findViewById(R.id.btnFour);
		btnFour.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setKmValue("4");

			}
		});

		btnFive = (Button) findViewById(R.id.btnFive);
		btnFive.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setKmValue("5");

			}
		});

		btnSix = (Button) findViewById(R.id.btnSix);
		btnSix.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setKmValue("6");

			}
		});

		btnSeven = (Button) findViewById(R.id.btnSeven);
		btnSeven.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setKmValue("7");

			}
		});

		btnEight = (Button) findViewById(R.id.btnEight);
		btnEight.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setKmValue("8");

			}
		});

		btnNine = (Button) findViewById(R.id.btnNine);
		btnNine.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setKmValue("9");

			}
		});

		btnZero = (Button) findViewById(R.id.btnZero);
		btnZero.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setKmValue("0");

			}
		});

		btnClear = (Button) findViewById(R.id.btnClear);
		btnClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				clearKmValue();

			}
		});

	}

	private void setListenerBtnContinua() {

		btnContinua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				saveKmMasina();

			}
		});

	}

	private void saveKmMasina() {

		if (isKmEmpty()) {
			Toast.makeText(getApplicationContext(), "Completati km din bord", Toast.LENGTH_LONG).show();
			return;
		}

		
		valideazaKmIntrodusi();

	}

	private void getBorderouMasina() {

		BorderouriDAOImpl bord = BorderouriDAOImpl.getInstance(this);
		bord.setBorderouEventListener(KmMasina.this);
		bord.getBorderouriMasina(UserInfo.getInstance().getNrAuto(), UserInfo.getInstance().getId());

	}

	private boolean isKmEmpty() {
		return textKm.getText().toString().isEmpty();
	}



	private void valideazaKmIntrodusi() {
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("nrAuto", UserInfo.getInstance().getNrAuto());
		params.put("kmNoi", textKm.getText().toString());
		opSoferi.verificaKmMasina(params);

	}

	private void adaugaKmMasina() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("codAngajat", UserInfo.getInstance().getId());
		params.put("nrAuto", UserInfo.getInstance().getNrAuto());
		params.put("km", textKm.getText().toString());

		opSoferi.adaugaKmMasina(params);

	}

	private void setKmValue(String kmValue) {

		if (textKm.getText().toString().length() > 7)
			return;

		if (!kmValue.equals("0") || !textKm.getText().toString().isEmpty()) {
			textKm.setText(textKm.getText() + kmValue);

		}

	}

	private void setInitKm(String kmValue) {
		initKm = Integer.valueOf(kmValue);
	}

	

	private void valideazaKmSalvati(String result) {
		
		HandleJSONData decodeJson = new HandleJSONData(getApplicationContext());
		
		StareValidareKm stareValidare = decodeJson.decodeStareValidare(result);
		
		if (stareValidare.isKmValid()) {
			salveazaKmMasina();
		} else {
			
			if (stareValidare.getStatusId() == 3)
				showConfirmDialog(stareValidare.getStatusMsg());
			else
				showInfoDialog(stareValidare.getStatusMsg());
				
		}
		

	}

	private void salveazaKmMasina() {
		UserInfo.getInstance().setKmStart(textKm.getText().toString());
		adaugaKmMasina();
	}

	private void showConfirmDialog(String strMessage) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setTitle("Atentie");

		alertDialogBuilder.setMessage(strMessage).setCancelable(false).setPositiveButton("DA", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				salveazaKmMasina();

			}
		}).setNegativeButton("NU", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	
	
	private void showInfoDialog(String strMessage) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setTitle("Atentie");

		alertDialogBuilder.setMessage(strMessage).setCancelable(true).setPositiveButton("Inchide", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();

			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	

	private void verificaBorderou(String strBorderouri) {
		HandleJSONData objListBorderouri = new HandleJSONData(this, strBorderouri);
		ArrayList<Borderou> listBorderouri = objListBorderouri.decodeJSONBorderouri();

		if (!listBorderouri.isEmpty()) {
			valideazaSoferBorderou(listBorderouri.get(0).getCodSofer());
		} else {
			showInfoScreen("Nu exista borderouri.");
		}

	}

	private void valideazaSoferBorderou(String soferBorderou) {

		if (soferBorderou.equals(UserInfo.getInstance().getId()))
			gotoNextScreen();
		else
			showInfoScreen("Nu exista borderouri.");

	}

	private void gotoNextScreen() {
		Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
		startActivity(nextScreen);
		finish();
	}

	private void showInfoScreen(String infoMsg) {
		Intent nextScreen = new Intent(getApplicationContext(), InfoNrAuto.class);
		nextScreen.putExtra("infoStr", infoMsg);
		startActivity(nextScreen);
		finish();
	}

	private void clearKmValue() {
		if (textKm.getText().length() > 0)
			textKm.setText(textKm.getText().toString().substring(0, textKm.getText().length() - 1));
	}

	@Override
	public void operationSoferiComplete(EnumOperatiiSofer numeOperatie, Object result) {
		switch (numeOperatie) {
		case GET_KM_MASINA:
			setKmValue((String) result);
			setInitKm((String) result);
			break;
		case ADAUGA_KM_MASINA:
			getBorderouMasina();
			break;
		case GET_STARE_VALIDARE_KM:
			valideazaKmSalvati((String) result);
			break;
		default:
			break;
		}

	}

	@Override
	public void loadComplete(String result, EnumOperatiiBorderou methodName) {
		switch (methodName) {
		case GET_BORDEROURI_MASINA:
			verificaBorderou(result);
			break;
		default:
			break;
		}

	}

}