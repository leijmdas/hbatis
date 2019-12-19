package org.mybatis.hbatis.orm.sql;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.hbatis.core.EntityNode;
import org.mybatis.hbatis.core.FieldNode;
import org.mybatis.hbatis.core.meta.FieldMeta;
import org.mybatis.hbatis.core.metaDescriber.EntityClassDescriber;
import org.mybatis.hbatis.orm.util.EntityClassDescriberHelper;

/**
 * 表映射工具类
 * 
 * @author zhen.zhang
 * @date 2014年12月1日
 */
public class TableMappingUtil {

	private TableMappingUtil() {

	}
	
	public static <T> TableMapping<T> getEntityMapping(Class<T> entityClass) {
		EntityClassDescriber<T> d = EntityClassDescriberHelper.getEntityClassDescriber(entityClass);
		return getEntityMapping(d.getEntityNode());
	}

	@SuppressWarnings("unchecked")
	private static <T> TableMapping<T> getEntityMapping(EntityNode<T> en) {
		TableMapping<T> entityMapping = new TableMapping<T>();
		entityMapping.setEntityMeta(en.getEntityMeta());
		MetaObject metaThis = EntityClassDescriberHelper.getSysMetaObject(en);
		Field[] fields = en.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (FieldNode.class.isAssignableFrom(f.getType())) {

				FieldNode<T, ?> fp = (FieldNode<T, ?>) metaThis.getValue(f.getName());

				entityMapping.getFieldMetas().add(fp.getFieldMeta());
			}
		}
		return entityMapping;
	}

	public static <E> FieldMeta<E, ?> getFieldMeta(TableMapping<E> entityMapping, String propName) {
		List<FieldMeta<E, ?>> fields = entityMapping.getFieldMetas();
		for (FieldMeta<E, ?> f : fields) {
			if (f.getPropertyName().equals(propName)) {
				return f;
			}
		}
		return null;
	}
	
	
}
