package org.mybatis.hbatis.orm.criteria;

import org.mybatis.hbatis.core.meta.FieldMeta;

/**
 * 含数据值的元素(多用于插入、删除)
 * 
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <T>
 */
public class FieldMetadata<E, T> {

	private FieldMeta<E, T> fieldMeta;
	private T value;

	public FieldMeta<E, T> getFieldMeta() {
		return fieldMeta;
	}

	public void setFieldMeta(FieldMeta<E, T> field) {
		this.fieldMeta = field;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	public FieldMetadata(){}
	public FieldMetadata(FieldMeta<E, T> field, T value) {
		this.fieldMeta = field;
		this.value = value;
	}

}
