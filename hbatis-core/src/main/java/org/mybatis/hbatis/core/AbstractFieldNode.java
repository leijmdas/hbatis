package org.mybatis.hbatis.core;

import java.util.List;

import org.mybatis.hbatis.core.meta.FieldMeta;

public abstract class AbstractFieldNode<E,T>  extends FieldMeta<E,T> implements FieldNode<E,T> {
	private EntityNode<E> entityPath;
	public AbstractFieldNode(EntityNode<E> entityPath,Class<T> clazz, String propName,String columnName) {
		super(entityPath.getEntityMeta(), clazz,propName, columnName);
		this.entityPath = entityPath;
	}
	public String getSqlColumn(){
		String name = this.getColumnName();
		if(entityPath.getAlias()!=null) {
			name = entityPath.getAlias()+"."+name;
		}
		return name;
	}
	public FieldMeta<E,T> getFieldMeta(){
		return this;
	}
	@Override
	public EntityNode<E> getEntityPath(){
		return this.entityPath;
	}

	@Override
	public Criterion<E,T> eq(T value) {
		return new Criterion<E,T>(this,"=",value);
	}

	@Override
	public Criterion<E,T> ne(T value) {
		return new Criterion<E,T>(this,"<>",value);
	}

	@Override
	public Criterion<E,T> gt(T value) {
		return new Criterion<E,T>(this,">",value);
	}

	@Override
	public Criterion<E,T> ge(T value) {
		return new Criterion<E,T>(this,">=",value);
	}

	@Override
	public Criterion<E,T> lt(T value) {
		return new Criterion<E,T>(this,"<",value);
	}
	@Override
	public Criterion<E,T> le(T value) {
		return new Criterion<E,T>(this,"<=",value);
	}
	@Override
	public Criterion<E,T> isNull() {
		T t = null;
		return new Criterion<E,T>(this,"is",t);
	}

	@Override
	public Criterion<E,T> isNotNull() {
		T t = null;
		return new Criterion<E,T>(this,"is not ",t);
	}
	public Criterion<E,T> between(T value0,T value1){
		return new Criterion<E,T>(this,"", value0,value1);
	}
	public Criterion<E,T> notBetween(T value0,T value1){
		return new Criterion<E,T>(this,"not",value0,value1);
	}
	
	public Criterion<E,T> in(List<T> list){
		return new Criterion<E,T>(this,"in",list);
	}
	public Criterion<E,T> notin(List<T> list){
		return new Criterion<E,T>(this,"not in",list);
	}
	
	public Criterion<E,T> like(T value){
		return new Criterion<E,T>(this,"like",value);
	}
	
	public Criterion<E,T> notlike(T value){
		return new Criterion<E,T>(this,"not like",value);
	}
}
