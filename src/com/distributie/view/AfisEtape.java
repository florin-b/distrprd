/**
 * @author florinb
 *
 */
package com.distributie.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.distributie.adapters.EtapeAdapter;
import com.distributie.beans.Borderou;
import com.distributie.beans.Etapa;
import com.distributie.beans.Factura;
import com.distributie.enums.EnumOperatiiBorderou;
import com.distributie.enums.EnumOperatiiEvenimente;
import com.distributie.enums.EnumTipEtapa;
import com.distributie.enums.TipBorderou;
import com.distributie.listeners.BorderouriDAOListener;
import com.distributie.listeners.BorderouriListener;
import com.distributie.listeners.OperatiiEtapeListener;
import com.distributie.listeners.OperatiiEvenimenteListener;
import com.distributie.model.BorderouriDAOImpl;
import com.distributie.model.CurrentStatus;
import com.distributie.model.HandleJSONData;
import com.distributie.model.OperatiiEvenimente;
import com.distributie.model.UserInfo;


public class AfisEtape extends Activity implements BorderouriDAOListener, OperatiiEvenimenteListener, OperatiiEtapeListener, BorderouriListener {

	private ListView listViewEtape;
	private OperatiiEvenimente opEvenimente;
	private List<Etapa> listEtape;
	private EtapeAdapter etapeAdapter;
	private Borderou borderouCurent;
	private TextView textInfo;

	private Timer timer;
	private TimerTask timerTask;
	private String ultimaEtapaId;
	private Date sfarsitBordDate;

	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTheme(R.style.LRTheme);
		setContentView(R.layout.etape);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Etape");
		actionBar.setDisplayHomeAsUpEnabled(true);

		opEvenimente = new OperatiiEvenimente(this);
		opEvenimente.setOperatiiEvenimenteListener(this);

		listEtape = new ArrayList<Etapa>();

		etapeAdapter = new EtapeAdapter(this, listEtape);
		etapeAdapter.setOperatiiEtapeListener(this);
		etapeAdapter.setBorderouriListener(this);

		setupLayout();

		getBorderouri();

	}

	private void setupLayout() {
		listViewEtape = (ListView) findViewById(R.id.listEtape);
		listViewEtape.setVisibility(View.GONE);

		textInfo = (TextView) findViewById(R.id.textInfo);
		textInfo.setVisibility(View.GONE);

	}

	private void startTimerTask() {
		timer = new Timer();
		initializeTimerTask();
		timer.schedule(timerTask, 300000, 300000);

	}

	public void stopTimerTask() {

		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public void initializeTimerTask() {

		timerTask = new TimerTask() {

			public void run() {
				handler.post(new Runnable() {
					public void run() {
						listEtape.clear();
						getBorderouri();

					}
				});
			}
		};
	}

	public void getBorderouri() {

		checkBordScanningTime();

		BorderouriDAOImpl bord = BorderouriDAOImpl.getInstance(this);
		bord.setBorderouEventListener(AfisEtape.this);
		bord.getBorderouri(UserInfo.getInstance().getId(), "d", "-1");

	}

	private void checkBordScanningTime() {
		if (sfarsitBordDate != null) {
			Date currentDate = new Date();
			long diff = currentDate.getTime() - sfarsitBordDate.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;

			if (diffMinutes > 10)
				System.exit(0);

		}
	}

	private void getSfarsitIncarcareEv(String codBorderou) {

		if (CurrentStatus.getInstance().getNrBorderou() != "") {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("document", codBorderou);
			params.put("codSofer", UserInfo.getInstance().getId());
			opEvenimente.getSfarsitIncarcare(params);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == 0)
			System.exit(0);

		return super.onOptionsItemSelected(item);

	}

	private void CreateMenu(Menu menu) {
		MenuItem mnu1 = menu.add(0, 0, 0, "IESIRE");
		mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

	}

	private void populateListBorderouri(String borderouri) {

		HandleJSONData objListBorderouri = new HandleJSONData(this, borderouri);
		ArrayList<Borderou> listBorderouri = objListBorderouri.decodeJSONBorderouri();

		if (listBorderouri.size() > 0) {

			listViewEtape.setVisibility(View.VISIBLE);
			textInfo.setVisibility(View.GONE);

			borderouCurent = listBorderouri.get(0);
			getSfarsitIncarcareEv(listBorderouri.get(0).getNumarBorderou());
		} else {
			listViewEtape.setVisibility(View.GONE);
			textInfo.setVisibility(View.VISIBLE);

		}

	}

	private void handleSfarsitIncarcareEvent(String result) {

		boolean isEtapaSfarsit = false;

		Etapa etapa = new Etapa();
		etapa.setTipEtapa(EnumTipEtapa.SFARSIT_INCARCARE);
		etapa.setNume("Sfarsit incarcare");
		etapa.setPos(listEtape.size());
		etapa.setDocument(borderouCurent.getNumarBorderou());
		etapa.setFactura(new Factura());

		if (result.contains("SOF")) {
			etapa.setSalvata(true);
			isEtapaSfarsit = true;
		}

		else if (result.contains("LOG")) {
			isEtapaSfarsit = false;
		} else if (!result.contains("-1")) {
			isEtapaSfarsit = true;
		}

		if (borderouCurent.getStandardTipBorderou() == TipBorderou.DISTRIBUTIE && isEtapaSfarsit) {
			listEtape.add(etapa);

		}

		etapa = new Etapa();
		etapa.setTipEtapa(EnumTipEtapa.START_BORD);
		etapa.setNume("Inceput cursa");
		etapa.setPos(listEtape.size());
		etapa.setDocument(borderouCurent.getNumarBorderou());
		etapa.setFactura(new Factura());

		listEtape.add(etapa);

		getFacturiBorderou();

	}

	private void getFacturiBorderou() {

		BorderouriDAOImpl borderouri = new BorderouriDAOImpl(this);
		borderouri.setBorderouEventListener(this);
		borderouri.getFacturiBorderou(borderouCurent.getNumarBorderou(), borderouCurent.getStandardTipBorderou());

	}

	private void populateListFacturi(String facturi) {

		HandleJSONData objListFacturi = new HandleJSONData(AfisEtape.this, facturi);
		ArrayList<Factura> listFacturi = objListFacturi.decodeJSONFacturiBorderou();

		if (listFacturi.size() > 0) {

			sfarsitBordDate = null;
			stopTimerTask();

			for (int i = 0; i < listFacturi.size(); i++) {
				Etapa etapa = new Etapa();
				etapa.setFactura(listFacturi.get(i));
				etapa.setPos(listEtape.size());
				etapa.setTipBorderou(borderouCurent.getStandardTipBorderou());
				etapa.setDocument(borderouCurent.getNumarBorderou());
				etapa.setTipEtapa(EnumTipEtapa.SOSIRE);
				listEtape.add(etapa);
			}

			// actualizare start cursa
			for (Etapa etapa : listEtape) {
				if (etapa.getTipEtapa() == EnumTipEtapa.START_BORD) {
					etapa.setSalvata(!listFacturi.get(0).getDataStartCursa().equals(""));
					break;
				}
			}

			listEtape.get(listEtape.size() - 1).setTipEtapa(EnumTipEtapa.STOP_BORD);
			listEtape.get(listEtape.size() - 1).setObservatii("Sfarsit cursa");

			if (ultimaEtapaId == null)
				ultimaEtapaId = listEtape.get(listEtape.size() - 1).getCodClient();
			else {
				if (!ultimaEtapaId.equals(listEtape.get(listEtape.size() - 1).getCodClient()))
					stopTimerTask();
			}

			etapeAdapter.setListEtape(listEtape);
			listViewEtape.setAdapter(etapeAdapter);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		CreateMenu(menu);
		return true;
	}

	@Override
	public void loadComplete(String result, EnumOperatiiBorderou methodName) {
		switch (methodName) {
		case GET_BORDEROURI:
			populateListBorderouri(result);
			break;
		case GET_FACTURI_BORDEROU:
			populateListFacturi(result);
			break;
		default:
			break;
		}

	}

	@Override
	public void opEventComplete(String result, EnumOperatiiEvenimente methodName) {
		switch (methodName) {

		case SET_SFARSIT_INC:
		case GET_SFARSIT_INC:
			handleSfarsitIncarcareEvent(result);
			break;
		default:
			break;
		}

	}

	@Override
	public void borderouTerminat() {

		getBorderouri();
		sfarsitBordDate = new Date();
		if (timer == null) {
			startTimerTask();
		}

	}

	@Override
	public void verificaBordeoruri() {
		if (timer == null)
			startTimerTask();

	}

}