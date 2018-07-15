package com.sinoservices.codehelper.util;

import com.sinoservices.codehelper.model.ColumnModel;
import com.sinoservices.codehelper.model.TableModel;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
	public static List<TableModel> getTablesByConn(Connection conn)
			throws SQLException {
		List list = new ArrayList();
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet rs = dbmd.getTables(null, null, null,
				new String[] { "TABLE" });
		try {
			String name = null;
			String comment = null;
			while (rs.next()) {
				TableModel tableModel = new TableModel();
				name = rs.getString("TABLE_NAME");
				comment = rs.getString("REMARKS");
				if ((comment == null) || (comment.length() < 1)) {
					comment = name;
				}
				tableModel.setName(name);
				tableModel.setType(rs.getString("TABLE_TYPE"));
				tableModel.setComment(comment);
				list.add(tableModel);
			}
		} finally {
			com.sinoservices.xframework.core.utils.DBUtils.close(rs);
		}
		return list;
	}

	public static List<ColumnModel> getColumnsByTable(Connection conn,
			String tableName) throws SQLException {
		DatabaseMetaData dbmd = conn.getMetaData();

		ResultSet rsPk = dbmd.getPrimaryKeys(null, null, tableName);
		String pkName = "";
		while (rsPk.next()) {
			pkName = rsPk.getString("COLUMN_NAME");
		}

		List list = new ArrayList();
		ResultSet rs = dbmd.getColumns(null, null, tableName, "%");
		try {
			String name = null;
			String comment = null;
			while (rs.next()) {
				name = rs.getString("COLUMN_NAME");
				comment = rs.getString("REMARKS");
				if ((comment == null) || (comment.length() < 1)) {
					comment = name;
				}
				ColumnModel columnModel = new ColumnModel();
				columnModel.setName(name);
				columnModel.setType(rs.getString("TYPE_NAME"));
				columnModel.setComment(comment);

				if (name.equals(pkName))
					columnModel.setIsPk(Boolean.valueOf(true));
				else {
					columnModel.setIsPk(Boolean.valueOf(false));
				}

				list.add(columnModel);
			}
		} finally {
			com.sinoservices.xframework.core.utils.DBUtils.close(rs);
		}
		return list;
	}

	public static List<ColumnModel> getColumnsBySQL(Connection conn, String sql) {
		List list = new ArrayList();
		ResultSet rs = null;
		try {
			rs = com.sinoservices.xframework.core.utils.DBUtils.queryNamedSql(
					conn, sql, null);
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i < cols; i++) {
				ColumnModel columnModel = new ColumnModel();
				columnModel.setName(rsmd.getColumnName(i));
				columnModel.setType(rsmd.getColumnTypeName(i));
				columnModel.setComment(rsmd.getColumnName(i));
				list.add(columnModel);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			com.sinoservices.xframework.core.utils.DBUtils.close(rs);
		}
		return list;
	}

	public void resolveSQL(String sql) {
	}

	public static void close(Connection conn) {
		com.sinoservices.xframework.core.utils.DBUtils.close(conn);
	}
}
