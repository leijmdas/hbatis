package org.mybatis.hbatis.orm.criteria.support.query;

import java.util.ArrayList;
import java.util.List;
/**
 * 查询条件分组
 * @author zz
 *
 */
public class QueryConditionGroup {

	private List<QueryFieldCondition> fieldConditions = new ArrayList<QueryFieldCondition>();
	
	private String relation = "and";
	/**
	 * 暂时不用
	 */
	List<QueryConditionGroup> childrens;


	public List<QueryFieldCondition> getFieldConditions() {
		return fieldConditions;
	}

	public void setFieldConditions(List<QueryFieldCondition> fieldConditions) {
		this.fieldConditions = fieldConditions;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public QueryConditionGroup addFieldCondition(String field,String op,Object val){
		QueryFieldCondition fc = new QueryFieldCondition();
		fc.setField(field);
		fc.setOp(op);
		fc.setValue(val);
		
		this.getFieldConditions().add(fc);
		return this;
	}
	
}
