package ya.dev.crm.dao;

import java.util.List;

import ya.dev.crm.exception.DaoException;
import ya.dev.crm.model.User;

public interface UserDao {
	List<User> getAll() throws DaoException;

	User getById(Integer id) throws DaoException;

	User getByUsername(String username) throws DaoException;

	void createUser(User user) throws DaoException;

	void udpateUser(User user) throws DaoException;

	void deleteUser(User user) throws DaoException;
}
