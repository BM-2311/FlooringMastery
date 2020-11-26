package com.mthree.aspire.flooringmastery.service;

import com.mthree.aspire.flooringmastery.dao.StateDao;
import com.mthree.aspire.flooringmastery.dto.State;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 *
 * @author barin
 */
@Component
public class StateDaoStub implements StateDao {

    @Override
    public State getStateByAbbreviation(String abbreviation) {
        if (abbreviation.equals("TX")) {
            return new State("TX", "Texas", new BigDecimal("4.45"));
        } else {
            return null;
        }
    }

}
