package org.mybatis.hbatis.orm.sql.builder;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.mybatis.hbatis.orm.sql.AbstractSqlBuilder;
import org.mybatis.hbatis.orm.sql.ResultMapsBuilder;
import org.mybatis.hbatis.orm.sql.TableMapping;
import org.mybatis.hbatis.orm.util.SqlBuilderHelper;
/**
 * 通过主键批量查询
 * @className: SelectByPKSSqlBuilder  
 * @description: 通过主键批量删除
 * @author zz  | www.integriti.cn
 * @date 2018年7月20日  
 *
 */
public class SelectByPKSSqlBuilder extends AbstractSqlBuilder {

	public SelectByPKSSqlBuilder(SqlSourceBuilder sqlSourceBuilder, Class<?> clazz) {
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

		return ResultMapsBuilder.buildResultMappings(this.sqlSourceBuilder.getConfiguration(), mapping);
	}

	@Override
	public BoundSql getBoundSql(Object parameter) {

		TextSqlNode node0 = new TextSqlNode("select "+mapping.getTableColumns()+" from " + mapping.getTableName());
		WhereSqlNode node1 = new WhereSqlNode(this.sqlSourceBuilder.getConfiguration(), SqlBuilderHelper
				.buildSqlNodePrimaryKeysIn(mapping, entityClass, this.sqlSourceBuilder.getConfiguration()));

		DynamicContext context = new DynamicContext(this.sqlSourceBuilder.getConfiguration(), parameter);

		MixedSqlNode mixedSqlNode = new MixedSqlNode(Arrays.asList(node0, node1));
		mixedSqlNode.apply(context);

		DynamicSqlSource sqlSource = new DynamicSqlSource(this.sqlSourceBuilder.getConfiguration(), mixedSqlNode);

		BoundSql boundSql = sqlSource.getBoundSql(parameter);

		return boundSql;

	}

	@Override
	public Class<?> getResultType() {
		return this.entityClass;
	}

	@Override
	public <E> String buildSql(TableMapping<E> mapping) {
		// TODO Auto-generated method stub
		return null;
	}

}
