package org.mybatis.hbatis.orm.sql.builder;

import java.util.List;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.mybatis.hbatis.orm.sql.AbstractSqlBuilder;
import org.mybatis.hbatis.orm.sql.ResultMapsBuilder;
import org.mybatis.hbatis.orm.sql.TableMapping;
import org.mybatis.hbatis.orm.util.SqlBuilderHelper;

public class SelectByPKSqlBuilder extends AbstractSqlBuilder {

	public SelectByPKSqlBuilder(SqlSourceBuilder sqlSourceBuilder, Class<?> clazz) {
		super(sqlSourceBuilder, clazz);
	}

	@Override
	public SqlCommandType getSqlCommandType() {
		return SqlCommandType.SELECT;
	}

	@Override
	public List<ResultMapping> getResultMappingList() {
		return this.getResultMappingList(this.mapping);
	}

	public <E> List<ResultMapping> getResultMappingList(TableMapping<E> mapping) {
		
		return ResultMapsBuilder.buildResultMappings(this.sqlSourceBuilder.getConfiguration(),mapping);
	}

	@Override
	public <E> String buildSql(TableMapping<E> mapping) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ").append(mapping.getTableColumns());
		sb.append(" from ").append(mapping.getTableName());
		sb.append(" where ").append(SqlBuilderHelper.buildPrimaryWhereSql(mapping, entityClass));
		return sb.toString();
	}

	@Override
	public Class<?> getResultType() {
		return this.entityClass;
	}

}
