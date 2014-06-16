package sk.rdy.rdmvcframework.model;

import java.util.ArrayList;
import java.util.List;

import sk.rdy.rdmvcframework.model.dao.IModelDao;
import sk.rdy.rdmvcframework.model.vo.IModelVo;

import android.util.Log;

/**
 * Jeho child triedy budu obsahovat metody, ktore budu pracovat
 * s Vo triedami
 * 
 * @author rdy
 * @param <U> Vo class
 * @param <T> Dao class
 *
 */
public abstract class AbstractModel<T extends IModelVo<T>, U extends IModelDao<T>>{	
	
	public interface ModelListener{
		void onModelUpdateState(AbstractModel model);
	}
	
	private static final String MODEL = "Model";
	private static final String VO_CLASS_SUFFIX = "Vo";
	private static final String DAO_CLASS_SUFFIX = "Dao";
	private static final String TAG = AbstractModel.class.getSimpleName();
		
	private final List<ModelListener> listeners = new ArrayList<AbstractModel.ModelListener>();
	private T data;
	private U dao;
	private Class<T> dataClass;
	private Class<U> daoClass;
	
	public AbstractModel(){		
		initVoDao();		
	}
	
	private void initVoDao() {
		
		String modelSimpleName = getClass().getSimpleName(); 
		String modelName = modelSimpleName.substring(0, modelSimpleName.indexOf(MODEL));
		String voSimpleName = modelName + VO_CLASS_SUFFIX;
		String daoSimpleName = modelName + DAO_CLASS_SUFFIX;
		
		try {
			
			
			dataClass = (Class<T>) Class.forName(voSimpleName);
			daoClass = (Class<U>) Class.forName(daoSimpleName);
						
			this.data = getDataInstance(dataClass);
			this.dao = getDaoInstance(daoClass);
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private U getDaoInstance(Class<U> daoClass) throws InstantiationException, IllegalAccessException {
		return daoClass.newInstance();
	}

	private T getDataInstance(Class<T> dataClass) throws InstantiationException, IllegalAccessException {
		return dataClass.newInstance();
	}

	public T getData(){
		return this.data;
	}
	
	public U getDao(){
		return this.dao;
	}
	
	public void addListener(ModelListener listener){
		synchronized (listeners) {
			listeners.add(listener);
		}
	}
	
	public void removeListener(ModelListener listener){
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	public void notifyListeners() {
		synchronized (listeners) {
			for (ModelListener listener : listeners) {
				listener.onModelUpdateState(this);
			}
		}
	}
}
