package org.mybatis.hbatis.orm.criteria.statement;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.hbatis.core.EntityNode;
import org.mybatis.hbatis.core.FieldNode;
import org.mybatis.hbatis.orm.criteria.FieldMetadata;
import org.mybatis.hbatis.orm.criteria.Restrictions;
import org.mybatis.hbatis.orm.criteria.SqlSegment;

/**
 * 更新
 * 
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <E>
 */
public class UpdateStatement<E> extends SqlSegment<E> {

	protected UpdateStatement(EntityNode<E> n) {
		super(n);
		this.restrictions = Restrictions.newInstance(n);
		this.fieldMetadatas = new ArrayList<FieldMetadata<E, ?>>();
	}

	public static <T> UpdateStatement<T> newInstance(EntityNode<T> entityNode) {
		return new UpdateStatement<T>(entityNode);
	}

	private List<FieldMetadata<E, ?>> fieldMetadatas;

	private Restrictions<E> restrictions;

	/**
	 * 获取属性元组列表
	 * 
	 * @return
	 */
	public List<FieldMetadata<E, ?>> getFieldMetadataList() {
		return fieldMetadatas;
	}

	/**
	 * 设置属性元组列表
	 * 
	 * @param fieldList
	 */
	public void setFieldMetadataList(List<FieldMetadata<E, ?>> fieldList) {
		this.fieldMetadatas = fieldList;
	}

	/**
	 * 获取约束
	 * 
	 * @return
	 */
	public Restrictions<E> getRestrictions() {
		return restrictions;
	}

	public Restrictions<E> restrictions() {
		return restrictions;
	}

	/**
	 * 设置约束
	 * 
	 * @param restrictions
	 */
	public void setRestrictions(Restrictions<E> restrictions) {
		this.restrictions = restrictions;
	}

	public <V> UpdateStatement<E> addField(FieldNode<E, V> fieldNode, V val) {
		FieldMetadata<E, V> uf = new FieldMetadata<E, V>();
		uf.setFieldMeta(fieldNode.getFieldMeta());
		uf.setValue(val);
		this.fieldMetadatas.add(uf);
		return this;
	}
}
