
package receipt;

import core.ReceiptExecutors;
import entities.Payment;
import entities.Receipt;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;


public class ReceiptHistoryPresenter  {
   
    ReceiptHistoryView view;

    ReceiptService service;
    public ObservableList<Receipt> itemList;
    public ReceiptHistoryPresenter(ReceiptHistoryView view) {
        this.view = view;
        this.service=new ReceiptService();
        this.itemList=FXCollections.observableArrayList();
    }

    public void loadAll() {
        Task<List<Receipt>> task = new Task<List<Receipt>>() {
            @Override
            protected List<Receipt> call() throws Exception {
                List<Receipt> all=new ArrayList<>();
                for (int i = 0; i < 11000; i++) {
                    Receipt r=new Receipt();
                    r.setId(i);
                    r.setCustomerName("customer name "+i);
                    r.setAmount(1000+i);
                    all.add(r);
                }
                return all;
            }
        };
        task.setOnSucceeded(ev->{
            itemList.clear();
            itemList.addAll(task.getValue());
        });
        task.setOnFailed(ev->{
            view.showError("Error",task.getException().getMessage());
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void search(String param) {
        Task<List<Receipt>> task = new Task<List<Receipt>>() {
            @Override
            protected List<Receipt> call() throws Exception {
                return service.search(param);
            }
        };
        task.setOnSucceeded(ev->{
            itemList.clear();
            itemList.addAll(task.getValue());
        });
        task.setOnFailed(ev->{
            view.showError("Error",task.getException().getMessage());
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void createPayment(Receipt r,Payment item) {
        Task<Payment> task = new Task<Payment>() {
            @Override
            protected Payment call() throws Exception {
                return service.createPayment(r,item);
            }
        };
        task.setOnSucceeded(ev->{
            view.paymentTable.getItems().add(item);
            view.showInfo("Task Result","New payment is created");
        });
        task.setOnFailed(ev->{
            view.showError("Error", task.getException().getMessage());
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void delete(Receipt receipt) {
        Task<Receipt> task = new Task<Receipt>() {
            @Override
            protected Receipt call() throws Exception {
                return service.delete(receipt);
            }
        };
        task.setOnSucceeded(ev->{
            itemList.remove(task.getValue());
            view.receiptListView.refresh();
            view.showInfo("Delete Result", "Receipt info is deleted");
        });
        task.setOnFailed(ev->{
            view.showError("Error", task.getException().getMessage());
        });
    }
    
    
    
    
}
