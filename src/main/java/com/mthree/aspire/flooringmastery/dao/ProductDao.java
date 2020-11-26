package com.mthree.aspire.flooringmastery.dao;

import com.mthree.aspire.flooringmastery.dto.Product;
import java.util.List;

/**
 *
 * @author barin
 */
public interface ProductDao {

    List<Product> getAllProducts() throws PersistenceException;

}
