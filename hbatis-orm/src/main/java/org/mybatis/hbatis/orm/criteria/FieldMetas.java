package org.mybatis.hbatis.orm.criteria;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.hbatis.core.EntityNode;
import org.mybatis.hbatis.core.meta.FieldMeta;
/**
 * 元组集合
 * @author zz
 * @date 2014年10月11日
 * @param <E>
 */
public class FieldMetas<E> extends SqlSegment<E> {
	
	public FieldMetas(EntityNode<E> entityNode) {
		super(entityNode);
	}
	public static <E> FieldMetas<E> newInstance(EntityNode<E> entityNode){
		return new FieldMetas<E>(entityNode);
	}
	private List<FieldMeta<E,?>> metaList = new ArrayList<FieldMeta<E,?>>();
	/**
	 * 获取元组集合
	 * @return
	 */
	public List<FieldMeta<E, ?>> getMetaList() {
		return metaList;
	}
	
}
