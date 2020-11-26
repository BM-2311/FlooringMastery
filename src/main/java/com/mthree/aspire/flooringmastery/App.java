package com.mthree.aspire.flooringmastery;

import com.mthree.aspire.flooringmastery.controller.FlooringMasteryController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author barin
 */
public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext
                = new AnnotationConfigApplicationContext();
        appContext.scan("com.mthree.aspire.flooringmastery");
        appContext.refresh();
        FlooringMasteryController myController
                = appContext.getBean("flooringMasteryController",
                        FlooringMasteryController.class);
        myController.run();
    }

}
