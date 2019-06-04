package com.distributie.model;

import com.distributie.enums.TipBorderou;

public interface BorderouriDAO {
	public void getBorderouri(String codSofer, String tipOp, String interval);
	public void getBorderouriTEST(String codSofer, String tipOp, String interval);
	public void getFacturiBorderou(String nrBorderou, TipBorderou tipBorderou);
	public void getArticoleBorderou(String nrBorderou, String codClient, String codAdresa);
	public void getBorderouriMasina(String nrMasina, String codSofer);
	public void getBorderouriMasinaTEST(String nrMasina, String codSofer);
	public void getArticoleBorderouDistributie(String nrBorderou, String codClient, String codAdresa);
	
}
