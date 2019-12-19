package org.mybatis.hbatis.core;

import java.util.List;

import org.mybatis.hbatis.core.meta.EntityMeta;
/**
 * 实体路径
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <T>
 */
public interface EntityNode<T> {
	/**
	 * 实体元
	 * @return
	 */
	EntityMeta<T> getEntityMeta();
	/**
	 * 别名
	 * @return
	 */
	String getAlias();
	/**
	 * 含别名的sql table
	 * @return
	 */
	String getSqlTable();
	
	/**
	 * 列
	 * @return
	 */
	List<FieldNode<T,?>> getFieldNodes();
	
	String getSqlColumns();
}
