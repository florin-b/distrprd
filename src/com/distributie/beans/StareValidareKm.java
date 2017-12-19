package com.distributie.beans;

public class StareValidareKm {

	private boolean isKmValid;
	private String statusMsg;
	private int statusId;

	public boolean isKmValid() {
		return isKmValid;
	}

	public void setKmValid(boolean isKmValid) {
		this.isKmValid = isKmValid;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "StareValidareKm [isKmValid=" + isKmValid + ", statusMsg=" + statusMsg + ", statusId=" + statusId + "]";
	}

}
