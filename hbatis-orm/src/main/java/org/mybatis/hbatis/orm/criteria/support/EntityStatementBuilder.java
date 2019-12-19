package org.mybatis.hbatis.orm.criteria.support;

import java.util.List;

import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.hbatis.core.FieldNode;
import org.mybatis.hbatis.core.metaDescriber.EntityClassDescriber;
import org.mybatis.hbatis.core.metaDescriber.EntityFieldDescriber;
import org.mybatis.hbatis.orm.criteria.Restrictions;
import org.mybatis.hbatis.orm.criteria.statement.InsertStatement;
import org.mybatis.hbatis.orm.criteria.statement.SelectStatement;
import org.mybatis.hbatis.orm.criteria.statement.UpdateStatement;
import org.mybatis.hbatis.orm.util.EntityClassDescriberHelper;

/**
 * 实体声明
 * 
 * @author zhen.zhang
 * @date 2014年12月5日
 */
public class EntityStatementBuilder {
	
	private EntityStatementBuilder(){}
	/**
	 * 构建实体非空属性插入声明
	 * 
	 * @param entity
	 * @return
	 */
	public static <E> InsertStatement<E> buildInsertSelective(E entity) {
		return EntityInsertSelectiveStatementBuilder.build(entity);
	}

	/**
	 * 构建实体非空属性更新声明
	 * 
	 * @param entity
	 * @return
	 */
	public static <E> UpdateStatement<E> buildUpdateSelective(E entity) {
		return EntityUpdateSelectiveStatementBuilder.build(entity);
	}

	/**
	 * 构建实体非空属性查询声明
	 * 
	 * @param entity
	 * @return
	 */
	public static <E> SelectStatement<E> buildSelectSelective(E entity) {
		return EntitySelectSelectiveStatementBuilder.build(entity);
	}
	
	/**
	 * 构建实体非空属性约束声明
	 * 
	 * @param entity
	 * @return
	 */
	public static <E> Restrictions<E> buildRestrictions(E entity) {
		return EntitySelectSelectiveStatementBuilder.build(entity).restrictions();
	}


	private static class EntityUpdateSelectiveStatementBuilder {

		@SuppressWarnings("unchecked")
		public static <E, V> UpdateStatement<E> build(E entity) {
			EntityClassDescriber<E> d = (EntityClassDescriber<E>) EntityClassDescriberHelper.getEntityClassDescriber(entity.getClass());
			UpdateStatement<E> st = UpdateStatement.newInstance(d.getEntityNode());
			MetaObject metaObject = EntityClassDescriberHelper.getSysMetaObject(entity);
			List<FieldNode<E, ?>> fieldPaths = d.getEntityNode().getFieldNodes();
			List<EntityFieldDescriber> fieldDescribers = d.getFieldDescribers();
			for (FieldNode<E, ?> fpTmp : fieldPaths) {
				String propName = fpTmp.getFieldMeta().getPropertyName();
				FieldNode<E, V> fp = (FieldNode<E, V>) fpTmp;
				Object val = metaObject.getValue(propName);
				V v = null;
				if (val != null) {
					v = (V) val;
				}
				for (EntityFieldDescriber fd : fieldDescribers) {
					if (fd.getField().getName().equals(fp.getFieldMeta().getPropertyName())) {
						if (v != null && fd.isUpdatable() && !fd.isPrimaryKey()) {
							st.addField(fp, v);
						}
						if (fd.isPrimaryKey()) {
							st.restrictions().add(fp.eq(v));
						}
						break;
					}
				}

			}
			if (st.getFieldMetadataList().isEmpty()) {
				throw new IllegalArgumentException("No property value set for entity:" + entity.getClass());
			}
			return st;
		}
	}

	private static class EntitySelectSelectiveStatementBuilder {

		@SuppressWarnings("unchecked")
		public static <E, V> SelectStatement<E> build(E entity) {
			EntityClassDescriber<E> d = (EntityClassDescriber<E>) EntityClassDescriberHelper.getEntityClassDescriber(entity.getClass());
			SelectStatement<E> st = SelectStatement.newInstance(d.getEntityNode());
			MetaObject metaObject = EntityClassDescriberHelper.getSysMetaObject(entity);
			List<FieldNode<E, ?>> fieldPaths = d.getEntityNode().getFieldNodes();
			List<EntityFieldDescriber> fieldDescribers = d.getFieldDescribers();
			for (FieldNode<E, ?> fpTmp : fieldPaths) {
				String propName = fpTmp.getFieldMeta().getPropertyName();
				FieldNode<E, V> fp = (FieldNode<E, V>) fpTmp;
				Object val = metaObject.getValue(propName);
				V v = null;
				if (val != null) {
					v = (V) val;
				}
				for (EntityFieldDescriber fd : fieldDescribers) {
					if (fd.getField().getName().equals(fp.getFieldMeta().getPropertyName())) {
						if (v != null) {
							st.restrictions().add(fp.eq(v));
						}
						break;
					}
				}

			}
			return st;
		}
	}

	private static class EntityInsertSelectiveStatementBuilder {

		@SuppressWarnings("unchecked")
		public static <E, V> InsertStatement<E> build(E entity) {
			EntityClassDescriber<E> d = (EntityClassDescriber<E>) EntityClassDescriberHelper.getEntityClassDescriber(entity.getClass());
			InsertStatement<E> st = InsertStatement.newInstance(d.getEntityNode());
			MetaObject metaObject = EntityClassDescriberHelper.getSysMetaObject(entity);
			List<FieldNode<E, ?>> fieldPaths = d.getEntityNode().getFieldNodes();
			List<EntityFieldDescriber> fieldDescribers = d.getFieldDescribers();
			for (FieldNode<E, ?> fpTmp : fieldPaths) {
				String propName = fpTmp.getFieldMeta().getPropertyName();
				FieldNode<E, V> fp = (FieldNode<E, V>) fpTmp;
				Object val = metaObject.getValue(propName);
				V v = null;
				if (val != null) {
					v = (V) val;
				}
				for (EntityFieldDescriber fd : fieldDescribers) {
					if (fd.getField().getName().equals(fp.getFieldMeta().getPropertyName())) {
						if (v != null) {
							st.addField(fp, v);
						}
						break;
					}
				}

			}
			if (st.getFieldMetadataList().isEmpty()) {
				throw new IllegalArgumentException("No property value set for entity:" + entity.getClass());
			}
			return st;
		}
	}

}
