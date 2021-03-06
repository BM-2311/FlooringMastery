package com.mthree.aspire.flooringmastery.dao;

import com.mthree.aspire.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author barin
 */
@Component
public class OrderDaoFileImpl implements OrderDao {

    private final String ORDERS_DIRECTORY;
    private final String ORDER_FILE_PREFIX;
    private final String DELIMITER = ",";
    private final String HEADER = "OrderNumber,CustomerName,State,TaxRate,"
            + "ProductType,Area,CostPerSquareFoot,LabourCostPerSquareFoot,"
            + "MaterialCost,LaborCost,Tax,Total";
    private final Map<Integer, Order> orders = new HashMap<>();

    public OrderDaoFileImpl() {
        ORDERS_DIRECTORY = "FileData/Orders/";
        ORDER_FILE_PREFIX = "Orders_";
    }

    public OrderDaoFileImpl(String directory, String filePrefix) {
        ORDERS_DIRECTORY = directory;
        ORDER_FILE_PREFIX = filePrefix;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws PersistenceException {
        loadOrdersByDate(date);
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order getOrderByIdOnDate(int id, LocalDate date) throws PersistenceException {
        loadOrdersByDate(date);
        return orders.get(id);
    }

    @Override
    public Order addOrder(Order order, LocalDate date) throws PersistenceException {
        loadOrdersByDate(date);
        Order addedOrder = orders.put(order.getOrderNumber(), order);
        writeToOrderFile(date);
        return addedOrder; // Null if new order, and old order if edited order
    }

    @Override
    public Order removeOrder(int id, LocalDate date) throws PersistenceException {
        loadOrdersByDate(date);
        Order removedOrder = orders.remove(id);
        writeToOrderFile(date);
        return removedOrder; // Order if successfully removed, Null otherwise        
    }

    private void loadOrdersByDate(LocalDate date) throws PersistenceException {
        orders.clear(); // Clears orders before loading by date
        File file = new File(ORDERS_DIRECTORY + dateToFileName(date));
        if (file.exists()) { // If file exists, read from it
            try (Scanner sc = new Scanner(
                    new BufferedReader(
                            new FileReader(file)))) {
                String currentLine = "";
                if (sc.hasNextLine()) {
                    currentLine = sc.nextLine();
                }
                Order currentOrder;
                while (sc.hasNextLine()) {
                    currentLine = sc.nextLine();
                    currentOrder = unmarshallOrder(currentLine);
                    orders.put(currentOrder.getOrderNumber(), currentOrder);
                }
            } catch (IOException e) {
                throw new PersistenceException("Could not load orders for the date "
                        + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                        + " into memory.", e);
            }
        }
    }

    private void writeToOrderFile(LocalDate date) throws PersistenceException {
        File file = new File(ORDERS_DIRECTORY + dateToFileName(date));
        int lineCounter = 0;
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println(HEADER);
            out.flush();
            lineCounter++;

            // For each order, write order to file and increment line counter
            lineCounter = orders.values().stream()
                    .map(order -> {
                        out.println(marshallOrder(order));
                        return order;
                    })
                    .map(order -> {
                        out.flush();
                        return order;
                    })
                    .map(order -> 1)
                    .reduce(lineCounter, Integer::sum);
        } catch (IOException e) {
            throw new PersistenceException("Could not write to order file for "
                    + "the date "
                    + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                    + ".", e);
        } finally {
            if (lineCounter == 1) {
                file.delete();
            }
        }
    }

    private Order unmarshallOrder(String orderAsText) {
        String[] orderTokens = orderAsText.split(DELIMITER);
        int nameTokensLength = orderTokens.length - 11;
        String customerName = "";
        if (nameTokensLength == 1) {
            customerName = orderTokens[1];
        } else {
            for (int i = 1; i < nameTokensLength; i++) {
                customerName += orderTokens[i] + DELIMITER;
            }
            customerName += orderTokens[nameTokensLength];
        }
        return new Order(Integer.parseInt(orderTokens[0]), customerName,
                orderTokens[1 + nameTokensLength],
                new BigDecimal(orderTokens[2 + nameTokensLength]),
                orderTokens[3 + nameTokensLength],
                new BigDecimal(orderTokens[4 + nameTokensLength]),
                new BigDecimal(orderTokens[5 + nameTokensLength]),
                new BigDecimal(orderTokens[6 + nameTokensLength]));
    }

    private String marshallOrder(Order order) {
        return order.toString(DELIMITER);
    }

    private String dateToFileName(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        return ORDER_FILE_PREFIX + date.format(formatter) + ".txt";
    }

}
