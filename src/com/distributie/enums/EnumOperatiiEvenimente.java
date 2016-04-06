package com.distributie.enums;

public enum EnumOperatiiEvenimente {
	SAVE_NEW_EVENT("saveNewEvent"), GET_DOC_EVENTS("getDocEvents"), CHECK_BORD_STARTED("getStartBorderou"), SAVE_NEW_STOP("saveStop"), CHECK_STOP(
			"getEvenimentStop"), SAVE_ARCHIVED_OBJECTS("saveArchivedObjects"), SET_SFARSIT_INC("saveEvenimentStopIncarcare"), GET_SFARSIT_INC(
			"getEvenimentStopIncarcare");

	private String numeComanda;

	EnumOperatiiEvenimente(String numeComanda) {
		this.numeComanda = numeComanda;
	}

	public String getNumeComanda() {
		return numeComanda;
	}

	public String toString() {
		return numeComanda;
	}

}
