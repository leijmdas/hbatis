package org.mybatis.hbatis.core;

import java.util.List;

/**
 * SQL约束
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <T>
 * @param <E>
 */
public final class Criterion<E,T> {
	
	private FieldNode<E,T> fieldNode = null;
	/**
	 * 操作符
	 */
	private String oper ;
	/**
	 * 多值
	 */
	private List<T> values;
	/**
	 * 单值
	 */
	private T value;
	/**
	 * 两个值(用于between)
	 */
	private T secondValue;
	private boolean singleValue;
	private boolean betweenValue;
	private boolean listValue;
	
	
	private Criterion(FieldNode<E,T> fp,String oper){
		this.fieldNode = fp;
		this.oper = oper;
		
	}
	public Criterion(FieldNode<E,T> fp,String oper,T value) {
		this(fp,oper);
		this.value = value;
		this.singleValue = true;
		
	}

	protected Criterion(FieldNode<E,T> fp, String oper, T value0, T value1) {
		this(fp,oper);
		this.value = value0;
		this.secondValue = value1;
		this.betweenValue = true;
	}

	protected Criterion(FieldNode<E,T> fp, String oper, List<T> list) {
		this(fp,oper);
		this.values = list;
		this.listValue = true;
	}

	public FieldNode<E,T> getFieldNode() {
		return fieldNode;
	}

	public String getOper() {
		return oper;
	}

	public List<T> getValues() {
		return values;
	}

	public T getValue() {
		return value;
	}

	public boolean isSingleValue() {
		return singleValue;
	}

	public boolean isBetweenValue() {
		return betweenValue;
	}

	public boolean isListValue() {
		return listValue;
	}

	public T getSecondValue() {
		return secondValue;
	}
	

	
}
