package org.mybatis.hbatis.orm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.mybatis.hbatis.core.EntityNode;
import org.mybatis.hbatis.core.annotation.Column;
import org.mybatis.hbatis.core.annotation.Table;
import org.mybatis.hbatis.core.exception.UnknownTableException;
import org.mybatis.hbatis.core.metaDescriber.EntityClassDescriber;
import org.mybatis.hbatis.core.metaDescriber.EntityFieldDescriber;

/**
 * 实体类描述
 * 
 * @author zhen.zhang
 * @date 2014年12月5日
 */
public class EntityClassDescriberHelper {
	private EntityClassDescriberHelper() {
	}

	/**
	 * 缓存
	 */
	private static Map<Class<?>, EntityClassDescriber<?>> holder = new HashMap<Class<?>, EntityClassDescriber<?>>();
	protected static ObjectFactory objectFactory = new DefaultObjectFactory();
	protected static ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

	public static MetaObject getSysMetaObject(Object obj) {
		return MetaObject.forObject(obj, objectFactory, objectWrapperFactory,new DefaultReflectorFactory());
	}

	@SuppressWarnings("unchecked")
	public static <E> EntityClassDescriber<E> getEntityClassDescriber(Class<E> entityClass) {
		EntityClassDescriber<E> d = (EntityClassDescriber<E>) holder.get(entityClass);
		if (d == null) {
			EntityNode<E> m = (EntityNode<E>) getEntityNode(entityClass);
			List<EntityFieldDescriber> fieldDescribers = getFieldDescribers(entityClass);
			d = new EntityClassDescriber<E>(entityClass);
			d.setEntityNode(m);
			d.setFieldDescribers(fieldDescribers);
			holder.put(entityClass, d);
			return d;
		}
		return d;
	}

	/**
	 * 获取不可更新字段
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> List<EntityFieldDescriber> getFieldDescribers(Class<T> clazz) {
		List<EntityFieldDescriber> result = new ArrayList<EntityFieldDescriber>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {

			Column col = f.getAnnotation(Column.class);

			if (col != null) {
				EntityFieldDescriber fd = new EntityFieldDescriber();
				fd.setField(f);
				fd.setAutoIncrement(col.autoIncrement());
				fd.setPrimaryKey(col.primaryKey());
				fd.setUpdatable(col.updatable());
				result.add(fd);
			}
		}
		if (!clazz.getSuperclass().equals(Object.class)) {
			result.addAll(getFieldDescribers(clazz.getSuperclass()));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> EntityNode<T> getEntityNode(Class<T> clazz) {
		Table entity = clazz.getAnnotation(Table.class);
		if (entity == null)
			throw new UnknownTableException(clazz + " must configure table annotation");
		Field[] fields = entity.value().getFields();
		for (Field f : fields) {

			if (EntityNode.class.isAssignableFrom(f.getType()) && (f.getModifiers() & 8) == Modifier.STATIC) {

				try {
					EntityNode<T> en = (EntityNode<T>) (f.get(null));
					return en;
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}

			}

		}
		throw new RuntimeException("no static EntityPath field for class:" + entity.value());
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<EntityFieldDescriber> getPrimaryKeys(Class<?> entityClass){
		EntityClassDescriber entityDescriber = getEntityClassDescriber(entityClass);
		List<EntityFieldDescriber> fieldDescribers = entityDescriber.getFieldDescribers();
		List<EntityFieldDescriber> fdKeys = new ArrayList<EntityFieldDescriber>();
		
		for(EntityFieldDescriber fd:fieldDescribers){
			if(fd.isPrimaryKey()) {
				fdKeys.add(fd);
			}
		}
		if(fdKeys.isEmpty()) {
			throw new RuntimeException("None Primary Key");
		}
		return fdKeys;
	}
}
