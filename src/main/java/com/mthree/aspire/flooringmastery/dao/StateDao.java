package com.mthree.aspire.flooringmastery.dao;

import com.mthree.aspire.flooringmastery.dto.State;

/**
 *
 * @author barin
 */
public interface StateDao {

    State getStateByAbbreviation(String abbreviation) throws PersistenceException;

}
