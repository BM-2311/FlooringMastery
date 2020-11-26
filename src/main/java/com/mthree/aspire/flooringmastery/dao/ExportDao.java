package com.mthree.aspire.flooringmastery.dao;

/**
 *
 * @author barin
 */
public interface ExportDao {

    void exportData() throws PersistenceException;

    public void setOrderNumberCounter() throws PersistenceException;

}
