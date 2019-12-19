package org.mybatis.hbatis.orm.sql;

import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;

public interface SqlBuilder extends SqlSource {

	SqlCommandType getSqlCommandType();
	
	List<ResultMapping> getResultMappingList();
	
	Class<?> getResultType();
}
