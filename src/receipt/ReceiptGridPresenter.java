/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receipt;

import core.BackupService;
import core.ReceiptExecutors;
import entities.Payment;
import entities.Receipt;
import java.io.File;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class ReceiptGridPresenter {

    ReceiptService service;
    ObservableList<Receipt> items;
    ReceiptGridView view;

    public ReceiptGridPresenter(ReceiptGridView baseView) {
        this.items = FXCollections.observableArrayList();
        this.service = new ReceiptService();
        this.view = baseView;
    }

    public void all() {
        Task<List<Receipt>> task = new Task<List<Receipt>>() {
            @Override
            protected List<Receipt> call() throws Exception {
                return service.all();
            }
        };
        view.bar.visibleProperty().bind(task.runningProperty());
        task.setOnSucceeded(ev -> {
            items.clear();
            items.addAll(task.getValue());
            view.bar.visibleProperty().unbind();
        });
        task.setOnFailed(ev -> {
            view.bar.visibleProperty().unbind();
            task.getException().printStackTrace();
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
        task.setOnSucceeded(ev -> {
            view.showInfo("Task result", "Receipt is deleted");
            view.gridView.getItems().remove(task.getValue());
        });
        task.setOnFailed(ev -> {
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);

    }

    void createPayment(Receipt r, Payment item) {
        Task<Payment> task = new Task<Payment>() {
            @Override
            protected Payment call() throws Exception {
                return service.createPayment(r, item);
            }
        };
        task.setOnSucceeded(ev -> {
            view.showInfo("Task result", "New payment is created");
            all();
        });
        task.setOnFailed(ev -> {
            view.showerror("Task result", task.getException().getMessage());
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    public void search(String text) {
        Task<List<Receipt>> task = new Task<List<Receipt>>() {
            @Override
            protected List<Receipt> call() throws Exception {
                return service.search(text);
            }
        };
        task.setOnSucceeded(ev -> {
            view.gridView.getItems().clear();
            view.gridView.getItems().addAll(task.getValue());
            view.gridView.requestFocus();
        });
        task.setOnFailed(ev -> {
            task.getException().printStackTrace();
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void updateReceipt(Receipt item) {
        Task<Receipt> task = new Task<Receipt>() {
            @Override
            protected Receipt call() throws Exception {
                return service.updatePayment(item);
            }
        };
        task.setOnSucceeded(ev -> {
            view.showInfo("Task result", "Receipt info is updated");
            all();
        });
        task.setOnFailed(ev -> {
            task.getException().printStackTrace();
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void sortLatest() {
        Task<List<Receipt>> task = new Task<List<Receipt>>() {
            @Override
            protected List<Receipt> call() throws Exception {
                return service.latest();
            }
        };
        view.bar.visibleProperty().bind(task.runningProperty());
        task.setOnSucceeded(ev -> {
            items.clear();
            items.addAll(task.getValue());
            view.bar.visibleProperty().unbind();
        });
        task.setOnFailed(ev -> {
            view.bar.visibleProperty().unbind();
            task.getException().printStackTrace();
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void sortOldest() {
        Task<List<Receipt>> task = new Task<List<Receipt>>() {
            @Override
            protected List<Receipt> call() throws Exception {
                return service.oldest();
            }
        };
        view.bar.visibleProperty().bind(task.runningProperty());
        task.setOnSucceeded(ev -> {
            items.clear();
            items.addAll(task.getValue());
            view.bar.visibleProperty().unbind();
        });
        task.setOnFailed(ev -> {
            view.bar.visibleProperty().unbind();
            task.getException().printStackTrace();
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void exportAsExl(File loc, Receipt... data) {
        Task<File> task = new Task<File>() {
            @Override
            protected File call() throws Exception {
                return new BackupService().toExcel(loc, data);
            }
        };
        view.bar.visibleProperty().bind(task.runningProperty());
        task.setOnSucceeded(ev -> {
            view.bar.visibleProperty().unbind();
            view.showInfo("Export result", data.length + " items is exported");
        });
        task.setOnFailed(ev -> {
            view.bar.visibleProperty().unbind();
            task.getException().printStackTrace();
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void exportXml(File f, Receipt... receipts) {
        Task<File> task = new Task<File>() {
            @Override
            protected File call() throws Exception {
                return new BackupService().toXML(f, receipts);
            }
        };
        view.bar.visibleProperty().bind(task.runningProperty());
        task.setOnSucceeded(ev -> {
            view.bar.visibleProperty().unbind();
            view.showInfo("Export result", receipts.length + " items is exported");
        });
        task.setOnFailed(ev -> {
            view.bar.visibleProperty().unbind();
            task.getException().printStackTrace();
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void imports(File file) {
        Task<List<Receipt>> task = new Task<List<Receipt>>() {
            @Override
            protected List<Receipt> call() throws Exception {
                return new BackupService().fromXml(file);
            }
        };
        view.bar.visibleProperty().bind(task.runningProperty());
        task.setOnSucceeded(ev -> {
            items.clear();
            items.addAll(task.getValue());
            view.bar.visibleProperty().unbind();
            view.showInfo("Import result", items.size() + " items is imported");
        });
        task.setOnFailed(ev -> {
            view.bar.visibleProperty().unbind();
            task.getException().printStackTrace();
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void updateReceiptStatus(Receipt receipt) {
        Task<Receipt> task = new Task<Receipt>() {
            @Override
            protected Receipt call() throws Exception {
                return service.update(receipt);
            }
        };
        task.setOnSucceeded(ev -> {
            view.showInfo("Receipt update", "Receipt status is changed");
            all();
        });
        task.setOnFailed(ev -> {
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);

    }

    void filter(Date from, Date to) {
        Task<List<Receipt> > task = new Task<List<Receipt> >() {
            @Override
            protected List<Receipt> call() throws Exception {
                return service.filter(from,to);
            }
        };
        task.setOnSucceeded(ev -> {
            items.clear();
            items.addAll(task.getValue());
            view.bar.visibleProperty().unbind();
        });
        task.setOnFailed(ev -> {
        });
        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

}
