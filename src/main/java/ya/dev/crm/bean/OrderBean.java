package ya.dev.crm.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.ServletContext;

import ya.dev.crm.dao.CustomerDao;
import ya.dev.crm.dao.OrderDao;
import ya.dev.crm.dao.impl.DaoFactory;
import ya.dev.crm.exception.DaoException;
import ya.dev.crm.model.Customer;
import ya.dev.crm.model.Order;

@Named
@SessionScoped
public class OrderBean implements Serializable{

	private static final long serialVersionUID = -2798830177567575283L;

	private CustomerDao customerDao;

	private OrderDao orderDao;

	private Order order;

	private Customer customer;

	private String customerChoice; 

	private String customerId;

	// Constructor
	public OrderBean() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		this.orderDao = ((DaoFactory) servletContext.getAttribute("daoFactory")).getOrderDao();
		this.customerDao = ((DaoFactory) servletContext.getAttribute("daoFactory")).getCustomerDao();
		this.order = new Order();
		this.customer = new Customer();
		this.customerChoice = "Yes";	
	}

	public void createOrder() {
		System.out.println("createOrder !");
		try {
			if(customerChoice.equals("Yes")) {
				customerDao.createCustomer(this.customer);
				FacesMessage msgCustomer = new FacesMessage("Customer creation OK!");
				FacesContext.getCurrentInstance().addMessage(null, msgCustomer);
			}else {
				customer = customerDao.getCustomerById(Integer.valueOf(customerId));
			}

			order.setCustomer(this.customer);

			orderDao.createOrder(order);
			FacesMessage msgOrder = new FacesMessage("Order creation OK!");
			FacesContext.getCurrentInstance().addMessage(null, msgOrder);
			order = new Order();

		}catch(DaoException e) {
			FacesMessage msg = new FacesMessage("Order creation error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	 /**
     * Get the order
     * @return
     */
    public Order getOrder() {
        return order;
    }

    public Customer getCustomer() {
        return customer;
    }
    
    /**
     * Get list of orders
     * @return
     */
    public List<Order> getOrders() {
    	List<Order> orders = orderDao.getAllOrders();
    	return orders;
    }
    
    /**
     * Get list of customers
     * @return
     */
    public List<Customer> getCustomers() {
    	List<Customer> customers = customerDao.getAllCustomers();
    	return customers;
    }
    
    /**
     * Get choice
     * @return
     */
    public String getCustomerChoice() {
    	return customerChoice;
    }
 
    public void setCustomerChoice(String customerChoice) {
		this.customerChoice = customerChoice;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
     * Get customer Id
     * @return
     */
    public String getCustomerId() {
    	return customerId;
    }
    
    /**
     * Delete a customer
     * @param customer
     */
    public void delete(Order order) {
    	System.out.println("deleteOrder !");
    	try {
    		orderDao.deleteOrder(order);
	        FacesMessage message = new FacesMessage("Suppression de la commande OK !");
	        FacesContext.getCurrentInstance().addMessage(null, message);
    	} catch (DaoException e) {
    		FacesMessage message = new FacesMessage("Erreur lors de la suppression de la commande : " + e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, message);
    	}
    }
    /**
     * Specific Phone validation
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void validatePhone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	String inputValue = (String) value;
        if (!inputValue.matches("^\\d+$")) {
            FacesMessage msg = new FacesMessage("Mauvais format : le numéro de téléphone doit contenir uniquement des chiffres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        } else if (inputValue.length() < 4) {
        	FacesMessage msg = new FacesMessage("Mauvais format : le numéro de téléphone doit contenir au moins 4 chiffres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
