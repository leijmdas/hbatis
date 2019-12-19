package org.mybatis.hbatis.orm.criteria.support.query;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.hbatis.core.EntityNode;
import org.mybatis.hbatis.core.FieldNode;
/**
 * 排序
 * @author zz
 *
 * @param <E>
 */
public class SortOrders<E> extends ArrayList<SortOrder<E>> {

	private static final long serialVersionUID = 1L;
	
	private EntityNode<E> entityNode;
	
	public SortOrders() {
		
	}
	public SortOrders(EntityNode<E> entityNode) {
		this.entityNode = entityNode;
	}
	
	public EntityNode<E> getEntityNode() {
		return entityNode;
	}
	public SortOrders<E> addDesc(FieldNode<E,?> field) {
		this.add(new SortOrder<E>(field,"desc"));
		return this;
	}
	public SortOrders<E> addAsc(FieldNode<E,?> field) {
		this.add(new SortOrder<E>(field,"asc"));
		return this;
	}
	public static <E> SortOrders<E> transFromString(EntityNode<E> entityNode,String orderBys) {
		SortOrders<E> sortOrders = new SortOrders<E>(entityNode);
		if(orderBys == null || orderBys.trim().equals("")) {
			return sortOrders;
		}
		String[] orderByArr = orderBys.split(",");
		
		for(String orderBy:orderByArr) {
			String[] tmpArr = orderBy.split("\\|");
			String sortType = tmpArr[1];
			String sortField = tmpArr[0];
			List<FieldNode<E,?>> nodes = sortOrders.getEntityNode().getFieldNodes();
			for(FieldNode<E,?> tmp:nodes) {
				if(tmp.getFieldMeta().getPropertyName().equals(sortField)) {
					SortOrder<E> order = new SortOrder<E>(tmp,sortType.equals("asc")?"asc":"desc");
					sortOrders.add(order);
					break;
				}
			}
		}
		return sortOrders;
	}
}
