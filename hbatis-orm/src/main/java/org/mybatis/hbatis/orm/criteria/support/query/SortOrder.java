package org.mybatis.hbatis.orm.criteria.support.query;

import org.mybatis.hbatis.core.FieldNode;
/**
 * 排序
 * @author zz
 *
 * @param <E>
 */
public class  SortOrder<E> {

	private FieldNode<E,?> sortField;
	private String sortType;
	public SortOrder(FieldNode<E,?> field,String sortType) {
		this.sortField = field;
		this.sortType = sortType;
	}
	public FieldNode<E,?> getSortField() {
		return sortField;
	}
	public String getSortType() {
		return sortType;
	}
	
}
