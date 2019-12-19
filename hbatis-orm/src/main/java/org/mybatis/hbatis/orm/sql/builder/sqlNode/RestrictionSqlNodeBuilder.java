package org.mybatis.hbatis.orm.sql.builder.sqlNode;

import java.util.Arrays;

import org.apache.ibatis.scripting.xmltags.ChooseSqlNode;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;

@Deprecated
public class RestrictionSqlNodeBuilder  {

	public static ChooseSqlNode build() {
		TextSqlNode tn1 = new TextSqlNode("${r.fieldNode.sqlColumn} ${r.oper} #{r.value}");
		TextSqlNode tn2 = new TextSqlNode("${r.fieldNode.sqlColumn} ${r.oper} between #{r.value}\r\n" + 
				"				and #{r.secondValue}");
		TextSqlNode tn3 = new TextSqlNode("${r.fieldNode.sqlColumn} ${r.oper} #{r.value}");
		
		SqlNode w1 = new IfSqlNode(tn1,"r.singleValue");
		SqlNode w2 = new IfSqlNode(tn2,"r.betweenValue");
		ChooseSqlNode chooseSqlNode = new ChooseSqlNode(Arrays.asList(w1,w2),w1);
		return chooseSqlNode;
	}
	

}
