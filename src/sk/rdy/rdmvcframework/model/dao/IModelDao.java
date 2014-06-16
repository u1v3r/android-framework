package sk.rdy.rdmvcframework.model.dao;

import java.util.List;

import sk.rdy.rdmvcframework.model.vo.IModelVo;


public interface IModelDao<T extends IModelVo<T>> {	
	public List<T> getAll();
	public T get(long id);
	public long insert(T data);
	public long update(T data);
	public void delete(long id);
}
