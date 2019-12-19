package org.mybatis.hbatis.core;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.hbatis.core.exception.UnknownJdbcTypeException;
import org.mybatis.hbatis.core.meta.EntityMeta;
import org.mybatis.hbatis.core.type.JdbcType;
import org.mybatis.hbatis.core.util.DefaultJdbcTypeRegistry;

public abstract class AbstractEntityNode<E> extends EntityMeta<E> implements EntityNode<E> {

	private String alias;
	private List<FieldNode<E, ?>> fieldPaths = new ArrayList<FieldNode<E, ?>>();

	public AbstractEntityNode(Class<E> entityClass, String tableName, String alias) {
		super(entityClass, tableName);
		this.alias = alias;
	}

	protected <K> FieldNode<E, K> createFieldNode(String propName, String columnName, Class<K> clazz) {
		JdbcType jdbcType = DefaultJdbcTypeRegistry.getDefaultJdbcType(clazz);
		if (jdbcType == null) {
			throw new UnknownJdbcTypeException(clazz);
		}
		return createFieldNode(propName, columnName, clazz, jdbcType);
	}

	protected <K> FieldNode<E, K> createFieldNode(String propName, String columnName, Class<K> clazz,
			JdbcType jdbcType) {
		FieldNode<E, K> fp = new AbstractFieldNode<E, K>(this, clazz, propName, columnName) {
		};
		fp.getFieldMeta().setJdbcType(jdbcType.name());
		fieldPaths.add(fp);

		return fp;
	}

	public EntityMeta<E> getEntityMeta() {
		return this;
	}

	public String getSqlTable() {
		String name = this.getEntityMeta().getTableName();
		if (this.getAlias() != null) {
			name = name + " " + this.getAlias();
		}
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public List<FieldNode<E, ?>> getFieldNodes() {
		return fieldPaths;
	}

	@Override
	public String getSqlColumns() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = this.fieldPaths.size(); i < len; i++) {

			FieldNode<E, ?> n = this.fieldPaths.get(i);
			sb.append(n.getSqlColumn()).append(" ").append(n.getFieldMeta().getPropertyName());
			if (i < len - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

}
