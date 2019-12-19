package org.mybatis.hbatis.core.metaDescriber;

import java.lang.reflect.Field;
/**
 * 实体类域描述
 * @author zhen.zhang
 * @date 2014年12月5日
 */
public class EntityFieldDescriber {
	/**
	 * 域
	 */
	private Field field;
	/**
	 * 可更新
	 */
	private boolean updatable = true;
	/**
	 * 是否主键
	 */
	private boolean primaryKey;
	
	/**
	 * 是否自增
	 */
	private boolean autoIncrement;

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj instanceof EntityFieldDescriber) return false;
		EntityFieldDescriber that = (EntityFieldDescriber)obj;
		if(that.getField().getName().equals(this.getField().getName())){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode() {
		int iConstant = 37;
		int iTotal = 17;

		iTotal = iTotal * iConstant + (this.getField() == null ? 0 : this.getField().hashCode());
		return iTotal;
	}

	
}
