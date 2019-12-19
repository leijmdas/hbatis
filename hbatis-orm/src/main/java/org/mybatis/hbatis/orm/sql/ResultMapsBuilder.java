package org.mybatis.hbatis.orm.sql;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.mybatis.hbatis.core.meta.FieldMeta;
/**
 * ResultMapping builder
 * @author zz
 * @date 2014年11月28日
 * @email zhen.zhang@vipshop.com
 */
public class ResultMapsBuilder {
	
	public static <E> List<ResultMapping> buildResultMappings(Configuration configuration,TableMapping<E> mapping) {
		List<ResultMapping> list = new ArrayList<ResultMapping>();
		List<FieldMeta<E, ?>> fields = mapping.getFieldMetas();
		for (FieldMeta<E, ?> field : fields) {
			String columnName = field.getColumnName();

			list.add((new ResultMapping.Builder(configuration, field.getPropertyName(), columnName, field.getFieldType())).build());

		}
		return list;
	}
}
