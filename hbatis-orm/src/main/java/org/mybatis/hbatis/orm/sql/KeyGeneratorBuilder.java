package org.mybatis.hbatis.orm.sql;


import org.apache.ibatis.mapping.MappedStatement;

public interface KeyGeneratorBuilder {
	
	void build(MappedStatement.Builder statement, Class<?> clazz);
}
