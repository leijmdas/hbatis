package org.mybatis.hbatis.orm.criteria;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.hbatis.core.EntityNode;
import org.mybatis.hbatis.core.FieldNode;
import org.mybatis.hbatis.core.meta.FieldMeta;
/**
 * 排序
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <E>
 */
public class Orders<E> extends SqlSegment<E>{
	
	private Orders(EntityNode<E> n) {
		super(n);
	}


	private List<Order> orderList = new ArrayList<Order>();
	
	/**
	 * 
	 * @param entityPath
	 * @return
	 */
	public static <E> Orders<E> newInstance(EntityNode<E> n){
		return new Orders<E>(n);
	}
	/**
	 * 降序
	 * @param fn
	 * @return
	 */
	public Orders<E> desc(FieldNode<E,?> fn) {
		order(fn,"desc");
		return this;
	}
	/**
	 * 升序
	 * @param fn
	 * @return
	 */
	public Orders<E> asc(FieldNode<E,?> fn) {
		order(fn,"asc");
		return this;
	}
	private Orders<E> order(FieldNode<E,?> fn,String type) {
		FieldMeta<E,?> fm = fn.getFieldMeta();
		orderList.add(new Order(fn.getEntityPath().getAlias(),fm.getColumnName(),type));
		return this;
	}
	
	
	public List<Order> getOrderList() {
		return orderList;
	}
	public String toString() {
		List<Order> list = this.getOrderList();
		if( list == null || list.isEmpty()) return null;
		StringBuilder sb = new StringBuilder();
		for(Order o:list){
			sb.append(",");
			if(o.getPrefix() != null){
				sb.append(o.getPrefix()).append(".");
			}
			sb.append(o.getColumnName()).append(" ").append(o.getOrderType());
		}
		return sb.substring(1);
	}


	public static class Order {
		private String prefix;
		private String columnName;
		private String orderType;
		public Order(String prefix,String columnName,String orderType){
			this.prefix = prefix;
			this.columnName = columnName;
			this.orderType = orderType;
		}
		public String getPrefix() {
			return prefix;
		}
		public String getColumnName() {
			return columnName;
		}
		public String getOrderType() {
			return orderType;
		}
		
	}
}
