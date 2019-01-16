/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

import core.ReceiptExecutors;
import entities.Receipt;
import java.util.List;
import javafx.concurrent.Task;
import receipt.ReceiptService;

public class DashboardPresenter {

    private final DashboardView view;
    public DashboardPresenter(DashboardView view){
        this.view=view;
    }
    public void loadReceipt(){
        Task<List<Receipt>> task = new Task<List<Receipt>>() {
            @Override
            protected List<Receipt> call() throws Exception {
                return new ReceiptService().all();
            }
        };
        task.setOnSucceeded(ev->{
            view.generateChart(task.getValue());
        });
        task.setOnFailed(ev->{
            view.showError("Error","Unable to draw chart ");
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }
}
