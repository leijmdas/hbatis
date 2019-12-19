package org.mybatis.hbatis.orm.sql.builder.mysql;

import java.util.List;

import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.mybatis.hbatis.core.metaDescriber.EntityClassDescriber;
import org.mybatis.hbatis.core.metaDescriber.EntityFieldDescriber;
import org.mybatis.hbatis.orm.sql.KeyGeneratorBuilder;
import org.mybatis.hbatis.orm.util.EntityClassDescriberHelper;

public class MysqlKeyGeneratorBuilder implements KeyGeneratorBuilder {

	@Override
	public void build(Builder statement, Class<?> clazz) {
		EntityClassDescriber<?> entityDescriber = EntityClassDescriberHelper.getEntityClassDescriber(clazz);
		List<EntityFieldDescriber> fieldDescribers = entityDescriber.getFieldDescribers();
		int index = 0;
		for (EntityFieldDescriber fd : fieldDescribers) {
			if (index > 1)
				throw new RuntimeException("AutoIncrement column must be only one");
			if (fd.isAutoIncrement()) {
				statement.keyProperty(fd.getField().getName());
				statement.keyGenerator(new Jdbc3KeyGenerator());
				index++;
			}
		}
	}

}
