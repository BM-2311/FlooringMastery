package com.mthree.aspire.flooringmastery.service;

import com.mthree.aspire.flooringmastery.dao.PersistenceException;
import com.mthree.aspire.flooringmastery.dto.Order;
import com.mthree.aspire.flooringmastery.dto.Product;
import com.mthree.aspire.flooringmastery.dto.State;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author barin
 */
public interface FlooringMasteryServiceLayer {

    List<Order> getOrdersByDate(LocalDate date) throws
            PersistenceException,
            NoOrdersOnDateException;

    Order getOrderByIdOnDate(int id, LocalDate date) throws
            PersistenceException,
            InvalidOrderNumberException;

    Order addOrder(Order order, LocalDate date) throws PersistenceException;

    Order removeOrder(int id, LocalDate date) throws PersistenceException;

    State getStateByAbbreviation(String abbreviation) throws
            PersistenceException,
            InvalidStateException;

    List<Product> getAllProducts() throws PersistenceException;

    void exportData() throws PersistenceException;

    void setOrderNumberCounter() throws PersistenceException;

}
