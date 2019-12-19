package org.mybatis.hbatis.orm.criteria.support.query;

/**
 * 查询条件
 * @author zz
 *
 */
public class QueryFieldCondition {

	private String field;
	private String op = "=";
	private Object value;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOp() {
		return op.toLowerCase();
	}
	public void setOp(String op) {
		this.op = op;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
