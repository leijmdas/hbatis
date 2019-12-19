package org.mybatis.hbatis.orm.util;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;
import org.mybatis.hbatis.core.meta.FieldMeta;
import org.mybatis.hbatis.core.metaDescriber.EntityFieldDescriber;
import org.mybatis.hbatis.orm.sql.TableMapping;
import org.mybatis.hbatis.orm.sql.TableMappingUtil;

public class SqlBuilderHelper {

	private SqlBuilderHelper() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String buildPrimaryWhereSql(TableMapping mapping, Class<?> entityClass) {
		StringBuilder sb = new StringBuilder();
		List<EntityFieldDescriber> keyFields = EntityClassDescriberHelper.getPrimaryKeys(entityClass);
		int keyIndex = 0;
		for (EntityFieldDescriber fd : keyFields) {
			if (keyIndex > 0) {
				sb.append(" and ");
			}
			String prop = fd.getField().getName();

			FieldMeta fm = (FieldMeta) TableMappingUtil.getFieldMeta(mapping, prop);
			sb.append(fm.getColumnName()).append("=");
			sb.append("#{").append(prop).append(",jdbcType=").append(fm.getJdbcType()).append("}");
			keyIndex++;
		}
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SqlNode buildSqlNodePrimaryKeysIn(TableMapping mapping, Class<?> entityClass, Configuration cfg) {
		String primaryKeyCnd = null;
		TextSqlNode forEachItemNode = new TextSqlNode("#{r}");

		List<EntityFieldDescriber> keyFields = EntityClassDescriberHelper.getPrimaryKeys(entityClass);
		if (keyFields.size() == 1) {
			EntityFieldDescriber keyField = keyFields.get(0);

			String prop = keyField.getField().getName();
			FieldMeta fm = (FieldMeta) TableMappingUtil.getFieldMeta(mapping, prop);
			primaryKeyCnd = fm.getColumnName();

		} else {
			StringBuilder sb = new StringBuilder();
			StringBuilder itemSb = new StringBuilder();
			for (EntityFieldDescriber fd : keyFields) {
				String prop = fd.getField().getName();
				FieldMeta fm = (FieldMeta) TableMappingUtil.getFieldMeta(mapping, prop);
				sb.append(fm.getColumnName()).append(",");

				itemSb.append("#{r.").append(prop).append("},");

			}

			primaryKeyCnd = "(" + sb.deleteCharAt(sb.length() - 1) + ")";

			forEachItemNode = new TextSqlNode("(" + itemSb.deleteCharAt(itemSb.length() - 1) + ")");

		}

		ForEachSqlNode forEach = new ForEachSqlNode(cfg, forEachItemNode, "list", "index", "r", "(", ")", ",");

		MixedSqlNode whereRootNode = new MixedSqlNode(Arrays.asList(new TextSqlNode(primaryKeyCnd + " in "), forEach));

		return whereRootNode;

	}
}
