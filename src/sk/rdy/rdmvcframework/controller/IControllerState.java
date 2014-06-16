package sk.rdy.rdmvcframework.controller;

import android.os.Message;

/**
 * 
 * @author rdy
 *
 */
public interface IControllerState {
	/**
	 * Metoda prijima a obsluhuje {@link Message}.
	 */
	public boolean handleMessage(Message msg);
}
