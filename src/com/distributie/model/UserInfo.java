/**
 * @author florinb
 *
 */
package com.distributie.model;

import java.util.Date;

public class UserInfo {

	private String nume;
	private String filiala;
	private String id;
	private String unitLog;
	private String status;
	private String departament;
	private String tipAcces;
	private boolean dti;
	private String nrAuto;
	private String kmStart;
	private Date logonDate;

	private static UserInfo instance = new UserInfo();

	private UserInfo() {
	}

	public static UserInfo getInstance() {
		return instance;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getFiliala() {
		return filiala;
	}

	public void setFiliala(String filiala) {
		this.filiala = filiala;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitLog() {
		return unitLog;
	}

	public void setUnitLog(String unitLog) {
		this.unitLog = unitLog;
	}

	public String getLogonStatus() {
		return status;
	}

	public void setLogonStatus(String logonStatus) {
		this.status = logonStatus;
	}

	public String getDepartament() {
		return departament;
	}

	public void setDepartament(String departament) {
		this.departament = departament;
	}

	public String getTipAcces() {
		return tipAcces;
	}

	public void setTipAcces(String tipAcces) {
		this.tipAcces = tipAcces;
	}

	public boolean isDti() {
		return dti;
	}

	public void setDti(boolean dti) {
		this.dti = dti;
	}

	public String getNrAuto() {
		return nrAuto;
	}

	public void setNrAuto(String nrAuto) {
		this.nrAuto = nrAuto;
	}

	public String getKmStart() {
		return kmStart;
	}

	public void setKmStart(String kmStart) {
		this.kmStart = kmStart;
	}

	public Date getLogonDate() {
		return logonDate;
	}

	public void setLogonDate(Date logonDate) {
		this.logonDate = logonDate;
	}

	@Override
	public String toString() {
		return "UserInfo [nume=" + nume + ", filiala=" + filiala + ", id=" + id + ", unitLog=" + unitLog + ", status=" + status + ", departament="
				+ departament + ", tipAcces=" + tipAcces + ", dti=" + dti + "]";
	}

}
