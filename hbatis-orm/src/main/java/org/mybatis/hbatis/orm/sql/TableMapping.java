package org.mybatis.hbatis.orm.sql;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.hbatis.core.meta.EntityMeta;
import org.mybatis.hbatis.core.meta.FieldMeta;
/**
 * 表映射
 * @author zz
 * @date 2014年10月3日
 * @email zhen.zhang@vipshop.com
 * @param <E>
 */
public class TableMapping<E> {
	/**
	 * 实体
	 */
	private EntityMeta<E> entityMeta;
	/**
	 * 属性域
	 */
	private List<FieldMeta<E,?>> fieldMetas = new ArrayList<FieldMeta<E,?>>();
	
	public EntityMeta<?> getEntityMeta() {
		return entityMeta;
	}

	public void setEntityMeta(EntityMeta<E> entityMeta) {
		this.entityMeta = entityMeta;
	}

	public List<FieldMeta<E,?>> getFieldMetas() {
		return fieldMetas;
	}

	public String getTableName() {
		return entityMeta.getTableName();
	}
	
	public String getTableColumns() {
		if(fieldMetas.isEmpty()) {
			throw new RuntimeException("None Define FieldMeta");
		}
		StringBuilder sb = new StringBuilder();
		for(FieldMeta<E,?> fm:fieldMetas) {
			sb.append(fm.getColumnName()).append(",");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
}
