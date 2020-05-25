package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LiniePierwiastkowe {

    @FXML
    private LineChart<Double, Double> RootLocus;

    @FXML
    private Button OkButton;

    @FXML
    public void CloseWindow(ActionEvent e) throws IOException
    {
        Parent locus = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scenaLocus = new Scene(locus);

        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(scenaLocus);
        window.show();
        System.out.println("penis " );

    }
}
