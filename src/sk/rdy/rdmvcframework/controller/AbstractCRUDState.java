package sk.rdy.rdmvcframework.controller;

import static sk.rdy.rdmvcframework.controller.constants.ControllerProtocol.VIEW_REQUEST_CREATE;
import static sk.rdy.rdmvcframework.controller.constants.ControllerProtocol.VIEW_REQUEST_DELETE;
import static sk.rdy.rdmvcframework.controller.constants.ControllerProtocol.VIEW_REQUEST_QUIT;
import static sk.rdy.rdmvcframework.controller.constants.ControllerProtocol.VIEW_REQUEST_READ;
import static sk.rdy.rdmvcframework.controller.constants.ControllerProtocol.VIEW_REQUEST_UPDATE;
import android.os.Message;

/**
 * Stavy, ktore rozsiruju tuto triedu implemntuju poziadavky na vsetky CRUD operacie.
 * 
 * @author rdy
 * @param <T>
 *
 */
public abstract class AbstractCRUDState<T> implements IControllerState {

	protected T controller;
	
	public AbstractCRUDState(T controller){
		this.controller = controller;
	}
	/**
	 * {@link IControllerState#handleMessage(Message)} 
	 */
	@Override
	public boolean handleMessage(Message msg) {
		
		switch (msg.what) {
		case VIEW_REQUEST_QUIT:
			onRequestQuit();
			return true;
		case VIEW_REQUEST_UPDATE:
			onRequestUpdate(msg);
			return true;
		case VIEW_REQUEST_READ:
			onRequestRead();
			return true;
		case VIEW_REQUEST_CREATE:
			onRequestCreate(msg);
			return true;
		case VIEW_REQUEST_DELETE:
			onRequestDelete(msg);
			return true;
		default:
			break;
		}
		
		return false;		
	}
	
	/**
	 * Malo by obsahovat ukoncenia vsetkych vlakien a controllera pri uzvareti aplikacie
	 */
	abstract protected void onRequestQuit();

	/**
	 * Vytvorenie noveho zaznamu
	 * 
	 * @param msg {@link Message}
	 */
	abstract protected void onRequestCreate(Message msg);
	
	/**
	 * Metoda by mala nastavit hodnoty modelu a upozronit o zmene
	 */
	abstract protected void onRequestRead();
	
	/**
	 * Uprava existujuceho zaznamu
	 * 
	 * @param msg {@link Message}
	 */
	abstract protected void onRequestUpdate(Message msg);	
	
	/**
	 * Odstránanie záznamu
	 * 
	 * @param msg {@link Message}
	 */
	abstract protected void onRequestDelete(Message msg);
}
