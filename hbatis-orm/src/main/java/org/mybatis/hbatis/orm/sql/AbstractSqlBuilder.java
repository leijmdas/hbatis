package org.mybatis.hbatis.orm.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * 抽象SqlBuilder
 * 
 * @author zhen.zhang
 * @date 2014年10月23日
 */
public abstract class AbstractSqlBuilder implements SqlBuilder {
	protected SqlSourceBuilder sqlSourceBuilder;

	protected Class<?> entityClass;

	protected TableMapping<?> mapping;
	/**
	 * SQL模板
	 */
	protected String sqlTpl;

	public AbstractSqlBuilder(SqlSourceBuilder sqlSourceBuilder, Class<?> clazz) {
		this.entityClass = clazz;
		this.sqlSourceBuilder = sqlSourceBuilder;
		mapping = TableMappingUtil.getEntityMapping(clazz);
		this.sqlTpl = this.buildSql(mapping);

	}

	@Override
	public BoundSql getBoundSql(Object parameter) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_parameter", parameter);

		return sqlSourceBuilder.parse(sqlTpl, parameter.getClass(), map).getBoundSql(parameter);

	}

	public void setSqlSourceBuilder(SqlSourceBuilder sqlSourceBuilder) {
		this.sqlSourceBuilder = sqlSourceBuilder;
	}

	public abstract <E> String buildSql(TableMapping<E> mapping);

	@Override
	public abstract SqlCommandType getSqlCommandType();

	@Override
	public List<ResultMapping> getResultMappingList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getResultType() {
		return null;
	}
}
