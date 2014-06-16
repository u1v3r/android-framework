package sk.rdy.rdmvcframework.controller;

import java.util.ArrayList;
import java.util.List;

import sk.rdy.rdmvcframework.controller.constants.ControllerProtocol;
import sk.rdy.rdmvcframework.utils.App;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;


public abstract class AbstractController<T> {

	private static final String TAG = AbstractController.class.getSimpleName();
	private static HandlerThread inboxHandlerThread;
	private final Handler inboxHandler;
	private final List<Handler> outboxHandlers = new ArrayList<Handler>();
	
	private T model;
	
	protected IControllerState state;
	
	public AbstractController(Class<T> modelClass){		
		try {
			this.model = getModelInstance(modelClass);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		initState();
		
		inboxHandlerThread = new HandlerThread(this.getClass().getSimpleName());
		inboxHandlerThread.start();
		
		inboxHandler = new Handler(inboxHandlerThread.getLooper()){
			public void handleMessage(android.os.Message msg) {
				AbstractController.this.handleMessage(msg);
			};
		};
	}
	
	private T getModelInstance(Class<T> classOfT) throws InstantiationException, IllegalAccessException{
		return classOfT.newInstance();
	}
	
	abstract protected void initState();
	
	public final void dispose() {		
		inboxHandlerThread.getLooper().quit();
	}

	public final Handler getInboxHandler() {
		return inboxHandler;
	}

	public final void addOutboxHandler(Handler handler) {
		outboxHandlers.add(handler);
	}

	public final void removeOutboxHandler(Handler handler) {
		outboxHandlers.remove(handler);
	}
	
	public final void notifyOutboxHandlers(int what, int arg1, int arg2, Object obj) {
		if (outboxHandlers.isEmpty()) {
			Log.w(TAG, String.format("No outbox handler to handle outgoing message (%d)", what));
		} else {
			for (Handler handler : outboxHandlers) {
				Message msg = Message.obtain(handler, what, arg1, arg2, obj);
				msg.sendToTarget();
			}
		}
	}

	public final void handleMessage(Message msg) {
		if(App.DEBUG){
			Log.d(TAG, "recieved message: " + msg);
		}
		
		if(!state.handleMessage(msg)){
			Log.w(TAG, "Unknown message: " + msg);
		}
	}

	public final T getModel() {
		return this.model;
	}

	public final void quit() {
		notifyOutboxHandlers(ControllerProtocol.CONTROLLER_QUIT, 0, 0, null);
	}
	
	public final void changeState(IControllerState newState) {
		if(App.DEBUG){
			Log.d(TAG, String.format("Changing state from %s to %s", state, newState));
		}
		state = newState;
	}

}