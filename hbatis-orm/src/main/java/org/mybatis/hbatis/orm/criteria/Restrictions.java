package org.mybatis.hbatis.orm.criteria;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.hbatis.core.Criterion;
import org.mybatis.hbatis.core.EntityNode;
/**
 * 约束
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <E>
 */
public class Restrictions<E> extends SqlSegment<E>{
	
	private Restrictions(EntityNode<E> entityNode) {
		super(entityNode);
	}

	private List<Criterion<E,?>> criterionList = new ArrayList<Criterion<E,?>>();
	

	public static <E> Restrictions<E> newInstance(EntityNode<E> entityNode) {
		return new Restrictions<E>(entityNode);
	}
	
	public Restrictions<E> add(Criterion<E,?> rs){
		criterionList.add(rs);
		return this;
	}

	public List<Criterion<E,?>> getCriterionList() {
		return criterionList;
	}


}
