package org.mybatis.hbatis.orm.sql.builder;

import java.util.List;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.SqlCommandType;
import org.mybatis.hbatis.core.meta.FieldMeta;
import org.mybatis.hbatis.core.metaDescriber.EntityClassDescriber;
import org.mybatis.hbatis.core.metaDescriber.EntityFieldDescriber;
import org.mybatis.hbatis.orm.sql.AbstractSqlBuilder;
import org.mybatis.hbatis.orm.sql.TableMapping;
import org.mybatis.hbatis.orm.util.EntityClassDescriberHelper;
import org.mybatis.hbatis.orm.util.SqlBuilderHelper;

/**
 * 更新
 * 
 * @author zz
 * @date 2014年10月3日
 * @email zhen.zhang@vipshop.com
 */
public class UpdateSqlBuilder extends AbstractSqlBuilder {
	public UpdateSqlBuilder(SqlSourceBuilder sqlSourceBuilder, Class<?> clazz) {
		super(sqlSourceBuilder, clazz);
	}

	@SuppressWarnings("unchecked")
	public <E> String buildSql(TableMapping<E> tableMapping) {
		StringBuilder sb = new StringBuilder();
		String tableName = tableMapping.getEntityMeta().getTableName();
		sb.append("update ").append(tableName).append(" set ");

		EntityClassDescriber<E> d = (EntityClassDescriber<E>) EntityClassDescriberHelper.getEntityClassDescriber(this.entityClass);
		List<EntityFieldDescriber> fieldDescribers = d.getFieldDescribers();

		List<FieldMeta<E, ?>> fields = tableMapping.getFieldMetas();
		for (FieldMeta<E, ?> fm : fields) {
			for (EntityFieldDescriber fd : fieldDescribers) {
				if (fd.isUpdatable() && fd.getField().getName().equals(fm.getPropertyName())) {
					sb.append(fm.getColumnName()).append("=#{").append(fm.getPropertyName()).append(",jdbcType=").append(fm.getJdbcType()).append("}").append(",");
					break;
				}
			}
		}

		sb = sb.deleteCharAt(sb.length() - 1);
		// append where
		sb.append(" where ").append(SqlBuilderHelper.buildPrimaryWhereSql(mapping, entityClass));
		return sb.toString();
	}

	@Override
	public SqlCommandType getSqlCommandType() {
		return SqlCommandType.UPDATE;
	}

}
