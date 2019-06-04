package com.distributie.enums;

public enum EnumOperatiiBorderou {
	GET_BORDEROURI("getBorderouri"), GET_FACTURI_BORDEROU("getFacturiBorderou"), GET_ARTICOLE_BORDEROU("getArticoleBorderou"), GET_BORDEROURI_MASINA(
			"getBorderouriMasina"), GET_ARTICOLE_BORDEROU_DISTRIB("getArticoleBorderouDistributie"), GET_BORDEROURI_MASINA_TEST("getBorderouriMasinaTEST"), GET_BORDEROURI_TEST(
			"getBorderouriTEST");

	private String numeComanda;

	EnumOperatiiBorderou(String numeComanda) {
		this.numeComanda = numeComanda;
	}

	public String getNumeComanda() {
		return numeComanda;
	}

}
