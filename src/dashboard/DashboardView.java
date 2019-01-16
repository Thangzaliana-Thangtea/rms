/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

import core.ReceiptStatus;
import static core.ReceiptStatus.PAID;
import static core.ReceiptStatus.PENDING;
import entities.Receipt;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.controlsfx.control.Notifications;
import screen.AbstractMainScreen;
import screen.MainScreen;

/**
 *
 * @author hatred
 */
public class DashboardView extends AbstractMainScreen {

    public static final String FXML = "/layout/dashboard.fxml";
    @FXML
    private BarChart barChart;
    @FXML
    private PieChart piechart;

    @FXML
    private Label receiptCount;
    @FXML
    private Label pendingCount;
    @FXML
    private Label todayAmount;
    @FXML
    private Label todayPending;
    DashboardPresenter presenter;
    public DashboardView(String LOCATION, MainScreen mainScreen) throws IOException {
        super(LOCATION, mainScreen);
    }
    @FXML
    private void initialize(){
        this.presenter=new DashboardPresenter(this);
        this.presenter.loadReceipt();
    }

    public void generateChart(List<Receipt> value) {
        double km = 0;
        double amount = 0;
        double hour = 0;
        double jan = 0, feb = 0, mar = 0, apr = 0, may = 0, jun = 0, jul = 0, aug = 0, sep = 0, oct = 0, nov = 0, dec = 0;
        double pjan = 0, pfeb = 0, pmar = 0, papr = 0, pmay = 0, pjun = 0, pjul = 0, paug = 0, psep = 0, poct = 0, pnov = 0, pdec = 0;

        double todayAmount=0;
        int pendingCount=0,totalCount=0,todayPending=0;
        
        double pending = 0, paid = 0, created = 0;
        for (Receipt item : value) {
         
            LocalDate date = item.getVoucherDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (date.equals(LocalDate.now())) {
                todayAmount+=item.getAmount();
                if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                    todayPending++;
                }
            }
            if (date.getYear() == LocalDate.now().getYear()) {
                switch (date.getMonth()) {
                    case JANUARY:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            jan += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            pjan += item.getAmount();
                        } 
                        break;
                    case FEBRUARY:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            feb += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            pfeb += item.getAmount();
                        } 
                        break;
                    case MARCH:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            mar += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            pmar += item.getAmount();
                        } 
                        break;
                    case APRIL:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            apr += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            papr += item.getAmount();
                        } 
                        break;
                    case MAY:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            may += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            pmay += item.getAmount();
                        } 
                        break;
                    case JUNE:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            jun += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            pjun += item.getAmount();
                        } 
                        break;
                    case JULY:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            jul += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            pjul += item.getAmount();
                        } 
                        break;
                    case AUGUST:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            aug += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            paug += item.getAmount();
                        } 
                        break;
                    case SEPTEMBER:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            sep += item.getAmount();
                        }
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            psep += item.getAmount();
                        }
                        break;
                    case OCTOBER:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            oct += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            poct += item.getAmount();
                        } 
                        break;
                    case NOVEMBER:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            nov += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            pnov += item.getAmount();
                        } 
                        break;
                    case DECEMBER:
                        if (item.getStatus().equals(ReceiptStatus.PAID)) {
                            dec += item.getAmount();
                        } 
                        if (item.getStatus().equals(ReceiptStatus.PENDING)) {
                            pdec += item.getAmount();
                        } 
                        break;
                    default:
                        break;
                }
            }
            amount += item.getAmount();
            switch (item.getStatus()) {
                case PENDING:
                    pending += item.getAmount();
                    pendingCount++;
                    break;
                case PAID:
                    paid += item.getAmount();
                    break;
                case PARTIAL_PAID:
                    created += item.getAmount();
                    break;
                default:
                    break;
            }
           

        }

        XYChart.Series series = new XYChart.Series<>();
        series.setName("Paid");
//        series.setStyle("-fx-background-color:#369;");
        series.getData().add(new XYChart.Data<>(Month.JANUARY.toString(), jan));
        series.getData().add(new XYChart.Data<>(Month.FEBRUARY.toString(), feb));
        series.getData().add(new XYChart.Data<>(Month.MARCH.toString(), mar));
        series.getData().add(new XYChart.Data<>(Month.APRIL.toString(), apr));
        series.getData().add(new XYChart.Data<>(Month.MAY.toString(), jun));
        series.getData().add(new XYChart.Data<>(Month.JULY.toString(), jul));
        series.getData().add(new XYChart.Data<>(Month.AUGUST.toString(), aug));
        series.getData().add(new XYChart.Data<>(Month.SEPTEMBER.toString(), sep));
        series.getData().add(new XYChart.Data<>(Month.OCTOBER.toString(), oct));
        series.getData().add(new XYChart.Data<>(Month.NOVEMBER.toString(), nov));
        series.getData().add(new XYChart.Data<>(Month.DECEMBER.toString(), dec));
        XYChart.Series pseries = new XYChart.Series<>();
        pseries.setName("Pending");
        pseries.getData().add(new XYChart.Data<>(Month.JANUARY.toString(), pjan));
        pseries.getData().add(new XYChart.Data<>(Month.FEBRUARY.toString(), pfeb));
        pseries.getData().add(new XYChart.Data<>(Month.MARCH.toString(), pmar));
        pseries.getData().add(new XYChart.Data<>(Month.APRIL.toString(), papr));
        pseries.getData().add(new XYChart.Data<>(Month.MAY.toString(), pjun));
        pseries.getData().add(new XYChart.Data<>(Month.JULY.toString(), pjul));
        pseries.getData().add(new XYChart.Data<>(Month.AUGUST.toString(), paug));
        pseries.getData().add(new XYChart.Data<>(Month.SEPTEMBER.toString(), psep));
        pseries.getData().add(new XYChart.Data<>(Month.OCTOBER.toString(), poct));
        pseries.getData().add(new XYChart.Data<>(Month.NOVEMBER.toString(), pnov));
        pseries.getData().add(new XYChart.Data<>(Month.DECEMBER.toString(), pdec));

        barChart.getData().addAll(series, pseries);
        barChart.getXAxis().setAnimated(true);

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("PENDING", pending),
                        new PieChart.Data("PARTIAL", created),
                        new PieChart.Data("PAID", paid)
                );

        piechart.getData().addAll(pieChartData);
        
        this.todayAmount.setText(NumberFormat.getCurrencyInstance().format(todayAmount));
        this.todayPending.setText(todayPending+"");
        this.pendingCount.setText(pendingCount+"");
        this.receiptCount.setText(value.size()+"");

    }

    void showError(String title, String msg) {
        Notifications.create().title(title)
                .text(msg).showError();
    }

}
