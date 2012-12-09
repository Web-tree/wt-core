package org.webtree.Page.DAO;

import org.webtree.Base.DAO.SqlDAO;
import org.webtree.Page.Model.PageModel;
import org.webtree.System.Exception.ItemNotFound;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lucifer
 *         Date: 28.04.12
 *         Time: 19:40
 */
public class PageDAO extends SqlDAO {

	protected String getFirstPage = "SELECT * FROM page ORDER BY \"pageId\" LIMIT 1";

	public PageModel getFirstPage() throws SQLException, ItemNotFound {

		PreparedStatement statement = getStatement(getFirstPage);

		ResultSet resultSet = statement.executeQuery();

		if (!resultSet.next()) {
			throw new ItemNotFound();
		}

		PageModel pageModel = new PageModel();
		pageModel.setId(resultSet.getInt("pageId"));
		pageModel.setHeader(resultSet.getString("header"));
		pageModel.setContent(resultSet.getString("content"));

		return pageModel;
	}
}
