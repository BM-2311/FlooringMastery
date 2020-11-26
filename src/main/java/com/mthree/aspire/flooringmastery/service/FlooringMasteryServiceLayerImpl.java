package com.mthree.aspire.flooringmastery.service;

import com.mthree.aspire.flooringmastery.dao.ExportDao;
import com.mthree.aspire.flooringmastery.dao.OrderDao;
import com.mthree.aspire.flooringmastery.dao.PersistenceException;
import com.mthree.aspire.flooringmastery.dao.ProductDao;
import com.mthree.aspire.flooringmastery.dao.StateDao;
import com.mthree.aspire.flooringmastery.dto.Order;
import com.mthree.aspire.flooringmastery.dto.Product;
import com.mthree.aspire.flooringmastery.dto.State;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author barin
 */
@Component
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    private final ProductDao productDao;
    private final StateDao stateDao;
    private final ExportDao exportDao;
    private final OrderDao orderDao;

    @Autowired
    public FlooringMasteryServiceLayerImpl(ProductDao productDao, StateDao stateDao, ExportDao exportDao, OrderDao orderDao) {
        this.productDao = productDao;
        this.stateDao = stateDao;
        this.exportDao = exportDao;
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws
            PersistenceException,
            NoOrdersOnDateException {
        List<Order> ordersOnDate = orderDao.getOrdersByDate(date);
        if (ordersOnDate.isEmpty()) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            throw new NoOrdersOnDateException("There are no orders on "
                    + formattedDate + ".");
        } else {
            return ordersOnDate;
        }
    }

    @Override
    public Order getOrderByIdOnDate(int id, LocalDate date) throws
            PersistenceException,
            InvalidOrderNumberException {
        Order returnedOrder = orderDao.getOrderByIdOnDate(id, date);
        if (returnedOrder == null) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            throw new InvalidOrderNumberException("There is no order with the "
                    + "order number " + id + " on the date " + formattedDate + ".");
        } else {
            return returnedOrder;
        }
    }

    @Override
    public Order addOrder(Order order, LocalDate date) throws PersistenceException {
        return orderDao.addOrder(order, date);
    }

    @Override
    public Order removeOrder(int id, LocalDate date) throws PersistenceException {
        return orderDao.removeOrder(id, date);
    }

    @Override
    public State getStateByAbbreviation(String abbreviation) throws
            PersistenceException,
            InvalidStateException {
        State returnedState = stateDao.getStateByAbbreviation(abbreviation);
        if (returnedState == null) {
            throw new InvalidStateException("The state " + abbreviation
                    + " is not in our system.");
        } else {
            return returnedState;
        }
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        return productDao.getAllProducts();
    }

    @Override
    public void exportData() throws PersistenceException {
        exportDao.exportData();
    }

    @Override
    public void setOrderNumberCounter() throws PersistenceException {
        exportDao.setOrderNumberCounter();
    }
}
