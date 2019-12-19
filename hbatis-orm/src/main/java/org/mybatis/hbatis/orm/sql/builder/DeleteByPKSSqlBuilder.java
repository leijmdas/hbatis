package org.mybatis.hbatis.orm.sql.builder;

import java.util.Arrays;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.mybatis.hbatis.orm.sql.AbstractSqlBuilder;
import org.mybatis.hbatis.orm.sql.TableMapping;
import org.mybatis.hbatis.orm.util.SqlBuilderHelper;
/**
 * 通过主键批量删除
 * @className: SelectByPKSSqlBuilder  
 * @description: 通过主键批量查询 
 * @author zz  | www.integriti.cn
 * @date 2018年7月20日  
 *
 */
public class DeleteByPKSSqlBuilder extends AbstractSqlBuilder {

	public DeleteByPKSSqlBuilder(SqlSourceBuilder sqlSourceBuilder, Class<?> clazz) {
		super(sqlSourceBuilder, clazz);
	}

	@Override
	public SqlCommandType getSqlCommandType() {
		return SqlCommandType.DELETE;
	}


	@Override
	public BoundSql getBoundSql(Object parameter) {

		TextSqlNode node0 = new TextSqlNode("delete from " + mapping.getTableName());
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
	public <E> String buildSql(TableMapping<E> mapping) {
		// TODO Auto-generated method stub
		return null;
	}

}
