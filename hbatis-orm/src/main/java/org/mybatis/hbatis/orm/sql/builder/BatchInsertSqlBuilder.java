package org.mybatis.hbatis.orm.sql.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.mybatis.hbatis.core.meta.FieldMeta;
import org.mybatis.hbatis.orm.sql.AbstractSqlBuilder;
import org.mybatis.hbatis.orm.sql.TableMapping;

public class BatchInsertSqlBuilder extends AbstractSqlBuilder {

	
	public BatchInsertSqlBuilder(SqlSourceBuilder sqlSourceBuilder, Class<?> clazz) {
		super(sqlSourceBuilder, clazz);
	}
	public <E> SqlNode buildSqlNode(TableMapping<E> tableMapping) {
		String tableName = tableMapping.getEntityMeta().getTableName();
		List<FieldMeta<E, ?>> fields = tableMapping.getFieldMetas();
	    
	    List<SqlNode> sqlNodes = new ArrayList<SqlNode>();
	    //获取全部列
	    StringBuilder sqlColumnSb = new StringBuilder();
	    for(FieldMeta<?, ?> f : fields){
	    	sqlColumnSb.append(",").append(f.getColumnName());
	    }
	  //静态的sql部分:insert into table values (,,,,)
	    sqlNodes.add(new StaticTextSqlNode("insert into "+tableName+"("+sqlColumnSb.substring(1)+") values "));
	    List<SqlNode> columnNodes = new ArrayList<SqlNode>();
	    columnNodes.add(new StaticTextSqlNode("("));
	    int index = 0;
		for (FieldMeta<?, ?> f : fields) {
			String columnSql = "#{item."+f.getPropertyName()+"}";
			 if(index!=fields.size()-1){
				 columnSql+=",";
			 }
			columnNodes.add(new StaticTextSqlNode(columnSql));
			index++;
		}
		columnNodes.add(new StaticTextSqlNode(")"));
		sqlNodes.add(new ForEachSqlNode(this.sqlSourceBuilder.getConfiguration(), new MixedSqlNode(columnNodes), "list", "index", "item", null, null, ","));
	    
	    return new MixedSqlNode(sqlNodes);
	}
	public BoundSql getBoundSql(Object parameter){
		SqlNode sqlNode = this.buildSqlNode(this.mapping);
		DynamicSqlSource sqlSource = new DynamicSqlSource(this.sqlSourceBuilder.getConfiguration(),sqlNode);
		BoundSql boundSql = sqlSource.getBoundSql(parameter);
		return boundSql;
		
	}
	@Override
	public SqlCommandType getSqlCommandType() {
		return SqlCommandType.INSERT;
	}

	@Override
	public List<ResultMapping> getResultMappingList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getResultType() {
		return Integer.class;
	}
	@Override
	public <E> String buildSql(TableMapping<E> mapping) {
		// TODO Auto-generated method stub
		return null;
	}

}
