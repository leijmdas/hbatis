package org.mybatis.hbatis.orm.sql.builder;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.SqlCommandType;
import org.mybatis.hbatis.orm.sql.AbstractSqlBuilder;
import org.mybatis.hbatis.orm.sql.TableMapping;
import org.mybatis.hbatis.orm.util.SqlBuilderHelper;

/**
 * 删除
 * 
 * @author zz
 * @date 2014年10月3日
 * @email zhen.zhang@vipshop.com
 */
public class DeleteSqlBuilder extends AbstractSqlBuilder {

	public DeleteSqlBuilder(SqlSourceBuilder sqlSourceBuilder, Class<?> clazz) {
		super(sqlSourceBuilder, clazz);

	}

	@Override
	public SqlCommandType getSqlCommandType() {
		return SqlCommandType.DELETE;
	}

	@Override
	public <E> String buildSql(TableMapping<E> mapping) {
		StringBuilder sb = new StringBuilder();

		String tableName = mapping.getEntityMeta().getTableName();
		sb.append("delete from ").append(tableName);

		// append where
		sb.append(" where ").append(SqlBuilderHelper.buildPrimaryWhereSql(mapping, entityClass));
		return sb.toString();
	}

}
