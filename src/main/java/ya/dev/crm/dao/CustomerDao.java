package ya.dev.crm.dao;

import java.util.List;

import ya.dev.crm.exception.DaoException;
import ya.dev.crm.model.Customer;

public interface CustomerDao {
	
	List<Customer> getAllCustomers() throws DaoException;
	
	Customer getCustomerById(Integer id) throws DaoException;
	
	Customer getByLastname(String lastname) throws DaoException;
	
	void createCustomer(Customer customer) throws DaoException;
	
	void updateCustomer(Customer customer) throws DaoException;
	
	void deleteCustomer(Customer customer) throws DaoException;

}
