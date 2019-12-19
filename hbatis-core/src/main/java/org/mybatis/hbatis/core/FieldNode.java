package org.mybatis.hbatis.core;

import org.mybatis.hbatis.core.meta.FieldMeta;
/**
 * 元素路径
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <T> 元组类型
 * @param <E>	实体
 */
public interface FieldNode<E,T> extends Restriction<E,T>{
	
	public FieldMeta<E,T> getFieldMeta();
	
	public EntityNode<E> getEntityPath();
	
	public String getSqlColumn();
}
