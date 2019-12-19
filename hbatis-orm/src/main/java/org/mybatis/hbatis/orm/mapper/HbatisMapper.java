package org.mybatis.hbatis.orm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.hbatis.orm.annotation.EnableEntityResultMapping;
import org.mybatis.hbatis.orm.criteria.Restrictions;
import org.mybatis.hbatis.orm.criteria.statement.InsertStatement;
import org.mybatis.hbatis.orm.criteria.statement.SelectStatement;
import org.mybatis.hbatis.orm.criteria.statement.UpdateStatement;

public interface HbatisMapper<T,PK> {
	
	
	/**
	 * 新增
	 * @param entity
	 * @return
	 */
	int insert(T entity);
	
	/**
	 * 修改
	 * @param entity
	 * @return
	 */
	int update(T entity);
	/**
	 * 查询
	 * @param pk
	 * @return
	 */
	T selectByPK(PK pk);
	/**
	 * 根据主键列表查询
	 * @param pks
	 * @return
	 */
	List<T> selectByPKS(List<PK> pks);
	/**
	 * 删除
	 * @param entity
	 * @return
	 */
	int deleteByPK(PK pk);
	
	/**
	 * 根据主键批量删除
	 * @param pks
	 * @return
	 */
	int deleteByPKS(List<PK> pks);
	/**
	 * 按声明新增
	 * @param statement
	 * @return
	 */
	int insertByStatement(@Param("st") InsertStatement<T> statement);
	/**
	 * 按声明更新
	 * @param statement
	 * @return
	 */
	int updateByStatement(@Param("st") UpdateStatement<T> statement);
	/**
	 * 按约束删除
	 * @param restrictions
	 * @return
	 */
	int deleteByRestrictions(@Param("restrictions") Restrictions<T> restrictions);
	
	/**
	 * 按声明查询
	 * @param statement
	 * @return
	 */
	@EnableEntityResultMapping
    List<T> selectByStatement(@Param("st") SelectStatement<T> statement);
    /**
     * 按约束统计
     * @param restrictions
     * @return
     */
    int countByRestrictions(@Param("restrictions") Restrictions<T> restrictions);
    /**
     * 批量插入
     * @param entities
     * @return
     */
    int batchInsert(List<T> list);
    
}
