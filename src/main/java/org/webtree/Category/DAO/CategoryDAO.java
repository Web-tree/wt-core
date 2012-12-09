package org.webtree.Category.DAO;

import org.webtree.Base.DAO.SqlDAO;
import org.webtree.Category.Model.CategoryModel;
import org.webtree.System.Exception.WebTreeException;
import org.webtree.System.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lucifer
 *         Date: 11.07.12
 *         Time: 9:10
 */
public class CategoryDAO extends SqlDAO {
	public CategoryModel get(int id) throws CategoryDAOException {
		try {
			ResultSet result = getStatement("SELECT * FROM category WHERE \"categoryId\" = ?").executeQuery();
			result.next();

			CategoryModel category = new CategoryModel();
			category.setId(result.getInt("categoryId"));
			category.setTitle(result.getString("title"));

			return category;
		} catch (SQLException e) {
			Log.getInst().error("CategoryDAO sql exception", e);
			throw new CategoryDAOException();
		}
	}

	public static class CategoryDAOException extends WebTreeException {
	}
}
