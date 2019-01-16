/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import core.BaseDialog;
import core.ClickListener;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author hatred
 */
public class DateFilterDialog extends BaseDialog<Void>{
    public interface DatesListener {

        public void select(Date from, Date to);
    }
    public static final String FXML_LOCATION="/layout/date_filter_dialog.fxml";
    @FXML
    private Label title;
    @FXML
    private JFXDatePicker from;
    @FXML
    private JFXDatePicker to;
    @FXML
    private JFXButton positiveBtn;
    @FXML
    private JFXButton negativeBtn;
    @FXML
    private Label errorLabel;

    private DatesListener listener;

    public DateFilterDialog(String resource, ClickListener listener) {
        super(resource, listener);
        this.setOnDialogOpened(ev->from.requestFocus());
        this.negativeBtn.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                from.requestFocus();
            }
        });
        this.positiveBtn.disableProperty().bind(from.valueProperty().isNull().or(to.valueProperty().isNull()));
    }
    @FXML
    private void handleFrom(ActionEvent event) {
    }

    @FXML
    private void handleTo(ActionEvent event) {
    }

    @FXML
    private void handlePositive(ActionEvent event) {
        if (from.getValue()==null) {
            errorLabel.setText("From");
            return;
        }
        if (to.getValue()==null) {
            errorLabel.setText("To");
            return;
        }
        if (from.getValue().isAfter(to.getValue())) {
            errorLabel.setText("");
            return;
        }
        listener.select(Date.from(from.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
                ,Date.from(to.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        close();
        
    }

    @FXML
    private void handleNegative(ActionEvent event) {
        close();
    }
    
    
    public static class Builder{

        private DatesListener listener;
        
        public Builder setFilterListener(DatesListener listener){
            this.listener=listener;
            return this;
        }
        public DateFilterDialog build(){
            DateFilterDialog d=new DateFilterDialog(FXML_LOCATION, null);
            d.listener=this.listener;
            return d;
        }
    }
}
