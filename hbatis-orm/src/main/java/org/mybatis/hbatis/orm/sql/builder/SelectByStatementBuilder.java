package org.mybatis.hbatis.orm.sql.builder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.mybatis.hbatis.orm.criteria.statement.SelectStatement;
import org.mybatis.hbatis.orm.sql.AbstractSqlBuilder;
import org.mybatis.hbatis.orm.sql.TableMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Deprecated
public class SelectByStatementBuilder extends AbstractSqlBuilder {
	public SelectByStatementBuilder(SqlSourceBuilder sqlSourceBuilder, Class<?> clazz) {
		super(sqlSourceBuilder, clazz);
	}
//	@Override
//	public <E> String buildSql(TableMapping<E> mapping) {
//		String sql="select * from ${st.entityNode.sqlTable}" + 
//				"		<where>" + 
//				"			<foreach collection=\"st.restrictions.criterionList\" item=\"r\" separator=\"and\">" + 
//				"				<include refid=\"Restriction\" />" + 
//				"			</foreach>" + 
//				"		</where>" + 
//				"		<if test=\"st.orders!=null\">" + 
//				"			order by ${st.orders}" + 
//				"		</if>" + 
//				"		<if test=\"st.pageRange!=null\">" + 
//				"			limit ${st.pageRange.start},${st.pageRange.limit}" + 
//				"		</if>";
//		return sql;
//	}
	private String getScript() {
		String sql="select * from ${st.entityNode.sqlTable}" + 
		"		<where>" + 
		"			<foreach collection=\"st.restrictions.criterionList\" item=\"r\" separator=\"and\">" + 
		"				<include refid=\"Restriction\" />" + 
		"			</foreach>" + 
		"		</where>" + 
		"		<if test=\"st.orders!=null\">" + 
		"			order by ${st.orders}" + 
		"		</if>" + 
		"		<if test=\"st.pageRange!=null\">" + 
		"			limit ${st.pageRange.start},${st.pageRange.limit}" + 
		"		</if>";
		
		sql="<doc>select * from ${st.entityNode.sqlTable}" + 
				"		<where>" + 
				"			<foreach collection=\"st.restrictions.criterionList\" item=\"r\" separator=\"and\">" + 
				"				1=1" + 
				"			</foreach>" + 
				"		</where>" + 
				"		<if test=\"st.orders!=null\">" + 
				"			order by ${st.orders}" + 
				"		</if></doc>" ;
		
		return sql;
		
	}
	@Override
	public BoundSql getBoundSql(Object parameter){
		BoundSql boundSql = null;
		
		InputStream is = new ByteArrayInputStream(this.getScript().getBytes());
		DocumentBuilder doc;
		Node node = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document d= doc.parse(is);
			node = d.getFirstChild();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XPathParser xpathParser = new XPathParser("<div></div>");
		
		Node n = null;
		
		XNode xnode = new XNode(null,n, new Properties());
		XMLScriptBuilder scriptBuilder = new XMLScriptBuilder(this.sqlSourceBuilder.getConfiguration(),xnode,SelectStatement.class);
		
		boundSql = scriptBuilder.parseScriptNode().getBoundSql(parameter);
		
//		DynamicSqlSource ss = new DynamicSqlSource(this.sqlSourceBuilder.getConfiguration());
//		
		
		
//		ForEachSqlNode forEach = new ForEachSqlNode(this.sqlSourceBuilder.getConfiguration(),RestrictionSqlNodeBuilder.build(),"st.restrictions.criterionList","index","r","(",")","and");
//		
//		TextSqlNode node0 = new TextSqlNode("select * from ${st.entityNode.sqlTable}");
//		WhereSqlNode node1 = new WhereSqlNode(this.sqlSourceBuilder.getConfiguration(),forEach);
//		
//		
//		DynamicContext context = new DynamicContext(this.sqlSourceBuilder.getConfiguration(), parameter);
//		
//		MixedSqlNode mixedSqlNode = new MixedSqlNode(Arrays.asList(node0,node1));
//		mixedSqlNode.apply(context);
//		
//		DynamicSqlSource sqlSource = new DynamicSqlSource(this.sqlSourceBuilder.getConfiguration(),mixedSqlNode);
//		
//		BoundSql boundSql = sqlSource.getBoundSql(parameter);
//		
//		System.out.println(boundSql.getSql());
//		
		System.out.println(boundSql.getSql());
		return boundSql;
		
//		SqlSource sqlSource =  scriptBuilder.parseScriptNode();
//		return sqlSourceBuilder.parse(sqlSource.toString(), SelectStatement.class, map).getBoundSql(parameter);

		
	}

	@Override
	public SqlCommandType getSqlCommandType() {
		return SqlCommandType.SELECT;
	}
	@Override
	public <E> String buildSql(TableMapping<E> mapping) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Class<?> getResultType() {
		return this.entityClass;
	}

}
