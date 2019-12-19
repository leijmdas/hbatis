package org.mybatis.hbatis.core.meta;

import org.mybatis.hbatis.core.EntityNode;

/**
 * 属性域元
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <T>
 */
public class FieldMeta<E,T>  {
	/**
	 * 实体
	 */
	private EntityMeta<E> entityMeta;
	/**
	 * 属性域类型
	 */
	private Class<T> fieldType;
	/**
	 * 列名
	 */
	private String columnName;
	
	/**
	 * 属性名
	 */
	private String propertyName;
	/**
	 * Jdbc类型
	 */
	private String jdbcType;
	
	public FieldMeta(EntityMeta<E> entityMeta, Class<T> clazz,String propName,String columnName) {

		this.entityMeta = entityMeta;
		this.fieldType = clazz;
		this.columnName = columnName;
		this.propertyName = propName;
	}

	public EntityMeta<?> getEntityMeta() {
		return entityMeta;
	}

	
	public Class<T> getFieldType() {
		return fieldType;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public EntityNode<E> getEntityPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
