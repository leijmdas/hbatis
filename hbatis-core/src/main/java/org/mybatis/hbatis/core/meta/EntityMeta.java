package org.mybatis.hbatis.core.meta;
/**
 * 实体元
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <T>
 */
public class EntityMeta<T> {
	/**
	 * 表名（no scheme)
	 */
	private String tableName;
	
	private Class<T> entityClass;
	
	public EntityMeta(Class<T> clazz,String tableName){
		this.tableName = tableName;
		this.entityClass = clazz;
		
	}
	public String getTableName() {
		return tableName;
	}
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
}
