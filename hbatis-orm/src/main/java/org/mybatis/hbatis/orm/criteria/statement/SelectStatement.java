package org.mybatis.hbatis.orm.criteria.statement;

import java.util.List;

import org.mybatis.hbatis.core.EntityNode;
import org.mybatis.hbatis.core.FieldNode;
import org.mybatis.hbatis.orm.criteria.Orders;
import org.mybatis.hbatis.orm.criteria.PageRange;
import org.mybatis.hbatis.orm.criteria.Restrictions;
import org.mybatis.hbatis.orm.criteria.SqlSegment;
/**
 * 查询
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <E>
 */
public class SelectStatement<E> extends SqlSegment<E> {
	
	
	private List<FieldNode<E,?>> fields;
	/**
	 * 排序
	 */
	private Orders<E> orders;
	
	/**
	 * 约束
	 */
	private Restrictions<E> restrictions;
	
	
	private PageRange pageRange;
	
	private SelectStatement(EntityNode<E> n){
		super(n);
		restrictions = Restrictions.newInstance(n);
	}
	/**
	 * 实例化
	 * @param ep
	 * @return
	 */
	public static <E> SelectStatement<E> newInstance(EntityNode<E> n){
		return new SelectStatement<E>(n);
	}
	/**
	 * 排序
	 * @return
	 */
	public Orders<E> orderby() {
		if(orders == null){
			orders = Orders.newInstance(this.getEntityNode());
		}
		return orders;
	}
	/**
	 * 分页
	 * @param start
	 * @param limit
	 * @return
	 */
	public SelectStatement<E> setPageRange(int start,int limit) {
		this.pageRange = new PageRange(start,limit);
		return this;
	}
	public Restrictions<E> restrictions(){
		return this.restrictions;
	}
	
	/**
	 * 获取约束
	 * @return
	 */
	public Restrictions<E> getRestrictions() {
		return restrictions;
	}
	/**
	 * 设置约束
	 * @param restrictions
	 */
	public void setRestrictions(Restrictions<E> restrictions) {
		this.restrictions = restrictions;
	}
	public void setFields(List<FieldNode<E,?>> fields) {
		this.fields = fields;
	}
	
	public List<FieldNode<E, ?>> getFields() {
		return fields;
	}
	public <T> EntityNode<T> join(EntityNode<T> entityNode,FieldNode<T,?> fieldNode){
		return null;
	}
	public Orders<E> getOrders() {
		return orders;
	}
	public PageRange getPageRange() {
		return pageRange;
	}
	public void setPageRange(PageRange pageRange) {
		this.pageRange = pageRange;
	}
	
	
	
}
