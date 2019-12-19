package org.mybatis.hbatis.orm.criteria.support.query;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author zz
 *
 */
public class QueryRuleParam {

	private List<QueryConditionGroup> groups = new ArrayList<QueryConditionGroup>();
	
	private String relation = "and";

	public List<QueryConditionGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<QueryConditionGroup> groups) {
		this.groups = groups;
	}

	public String getRelation() {
		return relation.toLowerCase();
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	
}
