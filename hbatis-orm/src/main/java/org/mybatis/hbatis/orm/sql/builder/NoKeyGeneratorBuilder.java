package org.mybatis.hbatis.orm.sql.builder;

import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.mybatis.hbatis.orm.sql.KeyGeneratorBuilder;

public class NoKeyGeneratorBuilder implements KeyGeneratorBuilder {

	@Override
	public void build(Builder statement, Class<?> clazz) {
		
	}

}
