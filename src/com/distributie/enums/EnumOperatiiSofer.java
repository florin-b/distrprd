package com.distributie.enums;

public enum EnumOperatiiSofer {
	GET_SOFERI("getSoferi"), GET_MASINA("getMasinaSofer"), GET_MASINI_FILIALA("getMasiniFiliala"), GET_KM_MASINA("getKmMasina"), ADAUGA_KM_MASINA(
			"adaugaKmMasina"), GET_KM_MASINA_DECLARATI("getKmMasinaDeclarati"), VALIDEAZA_KM_MASINA("valideazaKmMasina"), GET_STARE_MASINA("getStareMasina"), GET_STARE_VALIDARE_KM(
			"verificaKmSalvati");

	private String nume;

	EnumOperatiiSofer(String nume) {
		this.nume = nume;
	}

	public String getNume() {
		return nume;
	}

}
