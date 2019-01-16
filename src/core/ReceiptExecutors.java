/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author hatred
 */
public class ReceiptExecutors {
    
    
    
    private ExecutorService executor;
    
    private ReceiptExecutors() {
        if (executor==null) {
            executor=Executors.newFixedThreadPool(6);
        }
    }
    
    public static ReceiptExecutors getInstance() {
        return PonosExecutorHolder.INSTANCE;
    }
    
    private static class PonosExecutorHolder {

        private static final ReceiptExecutors INSTANCE = new ReceiptExecutors();
    }
    
    public ExecutorService getExecutor(){
        return executor;
    }
}
