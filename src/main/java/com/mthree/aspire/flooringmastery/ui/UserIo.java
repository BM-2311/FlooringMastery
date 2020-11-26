package com.mthree.aspire.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author barin
 */
public interface UserIo {

    void print(String message);

    String readString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    BigDecimal readBigDecimal(String prompt, BigDecimal min);

    LocalDate readLocalDate();

    LocalDate readLocalDateInFuture();

}
