package ya.dev.crm.dao;

import java.sql.SQLException;
import java.util.List;

import ya.dev.crm.exception.DaoException;
import ya.dev.crm.model.Order;

public interface OrderDao {

	List<Order> getAllOrders() throws DaoException;

	/**
	 * Get all the orders
	 * 
	 * @return List<Order>
	 * @throws SQLException
	 */
	Order getById(Integer id) throws DaoException;

	void createOrder(Order order) throws DaoException;

	void updateOrder(Order order) throws DaoException;

	void deleteOrder(Order order) throws DaoException;
}
