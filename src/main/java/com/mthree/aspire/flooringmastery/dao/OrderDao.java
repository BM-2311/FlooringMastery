package com.mthree.aspire.flooringmastery.dao;

import com.mthree.aspire.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author barin
 */
public interface OrderDao {

    List<Order> getOrdersByDate(LocalDate date) throws PersistenceException;

    Order getOrderByIdOnDate(int id, LocalDate date) throws PersistenceException;

    Order addOrder(Order order, LocalDate date) throws PersistenceException;

    Order removeOrder(int id, LocalDate date) throws PersistenceException;

}
