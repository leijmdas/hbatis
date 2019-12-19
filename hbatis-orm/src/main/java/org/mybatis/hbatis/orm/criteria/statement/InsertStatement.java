package org.mybatis.hbatis.orm.criteria.statement;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.hbatis.core.EntityNode;
import org.mybatis.hbatis.core.FieldNode;
import org.mybatis.hbatis.orm.criteria.FieldMetadata;
import org.mybatis.hbatis.orm.criteria.SqlSegment;
/**
 * 插入操作
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 * @param <E>
 */
public class InsertStatement<E> extends SqlSegment<E> {
	
	private InsertStatement(EntityNode<E> entityNode) {
		super(entityNode);
		this.fieldList = new ArrayList<FieldMetadata<E,?>>();
	}
	
	/**
	 * 实类化声明
	 * @param entityPath
	 * @return
	 */
	public static <E> InsertStatement<E> newInstance(EntityNode<E> entityNode){
		return new InsertStatement<E>(entityNode);
	}
	

	private List<FieldMetadata<E,?>> fieldList ;
	
	/**
	 * 获取属性元组列表
	 * @return
	 */
	public List<FieldMetadata<E,?>> getFieldMetadataList() {
		return fieldList;
	}

	public void setFieldMetadataList(List<FieldMetadata<E,?>> fieldList) {
		this.fieldList = fieldList;
	}
	
	public  <T> InsertStatement<E> addField(FieldNode<E,T> fieldNode,T val){
		FieldMetadata<E,T> uf = new FieldMetadata<E,T>();
		uf.setFieldMeta(fieldNode.getFieldMeta());
		uf.setValue(val);
		this.fieldList.add(uf);
		return this;
	}
	
	
}
