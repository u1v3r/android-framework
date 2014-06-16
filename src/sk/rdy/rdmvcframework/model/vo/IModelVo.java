package sk.rdy.rdmvcframework.model.vo;

import java.io.Serializable;

/**
 * Predstavuje value objekt. Musi obsahovat len data
 * a musi byt nemenny (Immutable).
 * 
 * @author rdy
 * @param <T>
 *
 */
public interface IModelVo<T> extends Serializable {
	void consume(T data);	
}