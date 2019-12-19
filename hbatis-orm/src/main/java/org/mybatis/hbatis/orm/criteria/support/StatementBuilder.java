package org.mybatis.hbatis.orm.criteria.support;

import org.mybatis.hbatis.core.EntityNode;
import org.mybatis.hbatis.orm.criteria.Restrictions;
import org.mybatis.hbatis.orm.criteria.statement.InsertStatement;
import org.mybatis.hbatis.orm.criteria.statement.SelectStatement;
import org.mybatis.hbatis.orm.criteria.statement.UpdateStatement;

/**
 * 声明构构建
 * 
 * @author zz
 * @date 2015年5月12日
 */
public class StatementBuilder {

	private StatementBuilder() {

	}


	/**
	 * 构建查询声明
	 * 
	 * @param n
	 * @return
	 */
	public static <E> SelectStatement<E> buildSelect(EntityNode<E> n) {
		return SelectStatement.newInstance(n);
	}
	/**
	 * 构建插入声明
	 * 
	 * @param n
	 * @return
	 */
	public static <E> InsertStatement<E> buildInsert(EntityNode<E> n) {
		return InsertStatement.newInstance(n);
	}

	/**
	 * 构建更新声明
	 * 
	 * @param n
	 * @return
	 */
	public static <E> UpdateStatement<E> buildUpdate(EntityNode<E> n) {
		return UpdateStatement.newInstance(n);
	}

	/**
	 * 构建实体非空属性插入声明
	 * 
	 * @param entity
	 * @return
	 */
	public static <E> InsertStatement<E> buildInsertSelective(E entity) {
		return EntityStatementBuilder.buildInsertSelective(entity);
	}

	/**
	 * 构建实体非空属性更新声明
	 * 
	 * @param entity
	 * @return
	 */
	public static <E> UpdateStatement<E> buildUpdateSelective(E entity) {
		return EntityStatementBuilder.buildUpdateSelective(entity);
	}

	/**
	 * 构建实体非空属性查询声明
	 * 
	 * @param entity
	 * @return
	 */
	public static <E> SelectStatement<E> buildSelectSelective(E entity) {
		return EntityStatementBuilder.buildSelectSelective(entity);
	}
	/**
	 * 构建约束
	 * @param entityNode
	 * @return
	 */
	public static <E> Restrictions<E> buildRestrictions(EntityNode<E> entityNode){
		return Restrictions.newInstance(entityNode);
	}
	/**
	 * 构建约束(通过实体)
	 * @param entity
	 * @return
	 */
	public static <E> Restrictions<E> buildRestrictions(E entity){
		return EntityStatementBuilder.buildRestrictions(entity);
	}
}
