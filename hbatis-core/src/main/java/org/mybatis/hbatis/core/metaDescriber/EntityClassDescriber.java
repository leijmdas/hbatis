package org.mybatis.hbatis.core.metaDescriber;

import java.util.List;

import org.mybatis.hbatis.core.EntityNode;
/**
 * 实体类描述
 * @author zhen.zhang
 * @date 2014年12月5日
 * @param <T>
 */
public class EntityClassDescriber<T> {
	/**
	 * 默认entitypath
	 */
	private EntityNode<T> entityNode;
	/**
	 * 域描述
	 */
	private List<EntityFieldDescriber> fieldDescribers;
	
	private Class<T> entityClass;
	public EntityClassDescriber(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public EntityNode<T> getEntityNode() {
		return entityNode;
	}

	public void setEntityNode(EntityNode<T> entityNode) {
		this.entityNode = entityNode;
	}


	public List<EntityFieldDescriber> getFieldDescribers() {
		return fieldDescribers;
	}

	public void setFieldDescribers(List<EntityFieldDescriber> fieldDescribers) {
		this.fieldDescribers = fieldDescribers;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	
}
