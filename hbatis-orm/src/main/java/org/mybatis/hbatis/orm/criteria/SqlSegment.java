package org.mybatis.hbatis.orm.criteria;

import org.mybatis.hbatis.core.EntityNode;
/**
 * 声明片段
 * @author zz
 * @date 2014年10月11日
 * @param <T>
 */
public abstract class SqlSegment<T> {
	private EntityNode<T> entityNode;
	
	protected SqlSegment(EntityNode<T> n){
		this.entityNode = n;
	}

	public EntityNode<T> getEntityNode() {
		return this.entityNode;
	}
	
}
