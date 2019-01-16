/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receipt;

import core.JasperViewerFX;
import core.ReceiptExecutors;
import core.ReceiptStatus;
import entities.Receipt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import util.ReceiptGenerator;
import util.TbcPreferences;

/**
 *
 * @author hatred
 */
public class NewReceiptPresenter {

    NewReceiptView view;
    ObservableList<ReceiptRow> receiptItems;
    ObservableList<ReceiptStatus> status;
    ReceiptService service;

    public NewReceiptPresenter(NewReceiptView view) {
        this.view = view;
        this.service = new ReceiptService();
        this.receiptItems = FXCollections.observableArrayList();
        this.status = FXCollections.observableArrayList(
                ReceiptStatus.PENDING,ReceiptStatus.PARTIAL_PAID,ReceiptStatus.PAID
        );
    }

    void createReceipt(Receipt receipt,boolean print) {
        Task<Receipt> task = new Task<Receipt>() {
            @Override
            protected Receipt call() throws Exception {
                return service.create(receipt);
            }
        };
        task.setOnSucceeded(ev -> {
            view.showInfo("Result","New receipt is created");
            if (print) {
                Stage owner = (Stage) view.rootLayout.getScene().getWindow();
                int choice = new TbcPreferences().getTemplate();
                JasperViewerFX viewer;
                if (choice == 1) {
                    viewer = ReceiptGenerator.generateSmall(owner, task.getValue());
                } else {
                    viewer = ReceiptGenerator.generateLarge(owner, task.getValue());
                }
                viewer.show();
            }
            
        });
        task.setOnFailed(ev -> {
           task.getException().printStackTrace();
           view.showError("Result",task.getException().getMessage());
        });

        ReceiptExecutors.getInstance().getExecutor().submit(task);
    }

    void updateReceipt(Receipt receipt, boolean selected) {
        Task<Receipt> task = new Task<Receipt>() {
            @Override
            protected Receipt call() throws Exception {
                return service.update(receipt);
            }
        };
        task.setOnSucceeded(ev->{
            view.showInfo("Edit result", "Receipt info is updated successfully");
            view.handleReset(new ActionEvent());
            Stage owner = (Stage) view.rootLayout.getScene().getWindow();
            if (selected) {
                int choice = new TbcPreferences().getTemplate();
                JasperViewerFX viewer;
                if (choice == 1) {
                    viewer = ReceiptGenerator.generateSmall(owner, task.getValue());
                } else {
                    viewer = ReceiptGenerator.generateLarge(owner, task.getValue());
                }
                viewer.show();
            }
        });
        task.setOnFailed(ev->task.getException().printStackTrace());
        ReceiptExecutors.getInstance().getExecutor().submit(task);

    }

}
