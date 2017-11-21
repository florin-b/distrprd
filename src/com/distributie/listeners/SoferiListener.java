package com.distributie.listeners;

import com.distributie.enums.EnumOperatiiSofer;

public interface SoferiListener {
	void operationSoferiComplete(EnumOperatiiSofer numeOperatie, Object result);
}
