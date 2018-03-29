package com.distributie.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.distributie.adapters.MasiniAdapter;
import com.distributie.beans.Borderou;
import com.distributie.enums.EnumOperatiiBorderou;
import com.distributie.enums.EnumOperatiiSofer;
import com.distributie.listeners.BorderouriDAOListener;
import com.distributie.listeners.SoferiListener;
import com.distributie.model.BorderouriDAOImpl;
import com.distributie.model.HandleJSONData;
import com.distributie.model.OperatiiSoferi;
import com.distributie.model.UserInfo;

public final class NrMasina extends Activity implements SoferiListener, BorderouriDAOListener {

	private OperatiiSoferi opSoferi;
	private TextView textNrAuto;
	private Button btnSelectAuto;
	private ListView listViewMasini;
	private List<String> listAuto;
	private Button btnContinua;
	private String nrAutoBorderou;
	private boolean existaBorderou;
	private boolean isAltaMasina;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTheme(R.style.LRTheme);
		setContentView(R.layout.nr_masina);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Nr auto");
		actionBar.setDisplayHomeAsUpEnabled(true);

		opSoferi = new OperatiiSoferi(this);
		opSoferi.setSoferiListener(this);

		setupLayout();
		getMasinaSofer();

	}

	private void setupLayout() {

		textNrAuto = (TextView) findViewById(R.id.textNrAuto);
		btnSelectAuto = (Button) findViewById(R.id.btnSelectAuto);
		listViewMasini = (ListView) findViewById(R.id.listViewMasini);
		btnContinua = (Button) findViewById(R.id.btnContinua);
		setListenerBtnSelectAuto();
		setListMasiniListener();
		setListenerBtnContinua();

	}

	private void setListenerBtnSelectAuto() {
		btnSelectAuto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getMasiniFiliala();

			}
		});
	}

	private void setListMasiniListener() {

		listViewMasini.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String autoSel = (String) parent.getAdapter().getItem(position);

				if (!autoSel.equals(UserInfo.getInstance().getNrAuto()))
					isAltaMasina = true;
				else
					isAltaMasina = false;

				textNrAuto.setText(autoSel);
				listViewMasini.setVisibility(View.INVISIBLE);

			}
		});

	}

	private void setListenerBtnContinua() {
		btnContinua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				UserInfo.getInstance().setNrAuto(textNrAuto.getText().toString());

				getKmMasinaDeclarati();

			}
		});
	}

	private void getBorderouActiv() {
		BorderouriDAOImpl bord = BorderouriDAOImpl.getInstance(this);
		bord.setBorderouEventListener(NrMasina.this);
		bord.getBorderouri(UserInfo.getInstance().getId(), "d", "-1");

	}

	private void getKmMasinaDeclarati() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("nrMasina", textNrAuto.getText().toString());
		opSoferi.getKmMasinaDeclarati(params);
	}

	private void goToNextScreen(String isKmDeclarati) {

		Intent nextScreen = null;

		if (!existaBorderou) {
			nextScreen = new Intent(getApplicationContext(), InfoNrAuto.class);
			String infoStr = "Nu exista borderouri.";
			nextScreen.putExtra("infoStr", infoStr);
		} else if (!isNrAutoBorderou()) {
			nextScreen = new Intent(getApplicationContext(), InfoNrAuto.class);
		} else if (Boolean.valueOf(isKmDeclarati)) {
			getBorderouMasina();

		} else {
			nextScreen = new Intent(getApplicationContext(), KmMasina.class);
		}

		UserInfo.getInstance().setNrAuto(textNrAuto.getText().toString());

		startActivity(nextScreen);
		finish();

	}

	private void getBorderouMasina() {

		BorderouriDAOImpl bord = BorderouriDAOImpl.getInstance(this);
		bord.setBorderouEventListener(NrMasina.this);
		bord.getBorderouriMasina(UserInfo.getInstance().getNrAuto(), UserInfo.getInstance().getId());

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

		if (soferBorderou.equals(UserInfo.getInstance().getId())) {
			Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
			startActivity(nextScreen);
			finish();
		} else
			showInfoScreen("Nu exista borderouri.");

	}

	private void showInfoScreen(String infoMsg) {
		Intent nextScreen = new Intent(getApplicationContext(), InfoNrAuto.class);
		nextScreen.putExtra("infoStr", infoMsg);
		startActivity(nextScreen);
		finish();
	}

	private void getMasiniFiliala() {

		if (listAuto == null) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("codFiliala", UserInfo.getInstance().getFiliala());
			opSoferi.getMasiniFiliala(params);
		} else
			listViewMasini.setVisibility(View.VISIBLE);
	}

	private void getMasinaSofer() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("codSofer", UserInfo.getInstance().getId());
		opSoferi.getMasinaSofer(params);
	}

	private void afisMasinaSofer(String nrAuto) {

		if (nrAuto.trim().isEmpty()) {
			Intent nextScreen = new Intent(getApplicationContext(), InfoNrAuto.class);
			String infoStr = "Nu aveti alocata nicio masina. Contactati departamentul logistica.";
			nextScreen.putExtra("infoStr", infoStr);
			startActivity(nextScreen);
			finish();
		} else {
			textNrAuto.setText(nrAuto);
			UserInfo.getInstance().setNrAuto(nrAuto);
			getBorderouActiv();
		}

	}

	private void afisMasiniFiliala(String strAuto) {

		String[] arrayAuto = strAuto.split(",");

		listAuto = new ArrayList<String>(Arrays.asList(arrayAuto));
		MasiniAdapter masiniAdapter = new MasiniAdapter(getApplicationContext(), listAuto);
		listViewMasini.setAdapter(masiniAdapter);
		listViewMasini.setVisibility(View.VISIBLE);

	}

	private boolean isNrAutoBorderou() {

		if (!isAltaMasina)
			return textNrAuto.getText().toString().replace("-", "").replace(" ", "").equals(nrAutoBorderou);
		
		return true;

	}

	private void setNrAutoBorderou(String strBorderouri) {

		HandleJSONData objListBorderouri = new HandleJSONData(this, strBorderouri);
		ArrayList<Borderou> listBorderouri = objListBorderouri.decodeJSONBorderouri();

		if (!listBorderouri.isEmpty() && listBorderouri.get(0).getNrAuto() != "null") {
			nrAutoBorderou = listBorderouri.get(0).getNrAuto();
			existaBorderou = true;
		} else {
			existaBorderou = false;

		}

	}

	@Override
	public void operationSoferiComplete(EnumOperatiiSofer numeOperatie, Object result) {
		switch (numeOperatie) {
		case GET_MASINA:
			afisMasinaSofer((String) result);
			break;
		case GET_MASINI_FILIALA:
			afisMasiniFiliala((String) result);
			break;
		case GET_KM_MASINA_DECLARATI:
			goToNextScreen((String) result);
			break;
		default:
			break;
		}

	}

	@Override
	public void loadComplete(String result, EnumOperatiiBorderou methodName) {
		switch (methodName) {
		case GET_BORDEROURI:
			setNrAutoBorderou(result);
			break;
		case GET_BORDEROURI_MASINA:
			verificaBorderou(result);
			break;
		default:
			break;
		}

	}

}