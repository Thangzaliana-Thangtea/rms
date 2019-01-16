/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.jasper;

import javafx.concurrent.Task;


public class WarningToaster extends Task<Void> {

    public WarningToaster() {
        super();
        updateProgress(1.0d, 1.0d);

    }

    @Override
    protected Void call() throws Exception {
        updateProgress(1.0, 1.0);
        Thread.sleep(500);
        for (double d = 1.0d; d >= -0.0d; d = d - .01d) {
            updateProgress(d, 1.0d);
            Thread.sleep(20);
        }
        return null;
    }

}
