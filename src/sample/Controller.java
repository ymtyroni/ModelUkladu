package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.chart.XYChart;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {
    float u;
    float s;
    public  double czas;

    @FXML
    private TextField tfk;

    @FXML
    private TextField tfA;

    @FXML
    private TextField tfa;

    @FXML
    private TextField tfb;

    @FXML
    private Button output;

    @FXML
    private Button ConfirmBtn;

    @FXML
    private Button LiniePierwiastkowe;

    @FXML
    private ToggleButton Jedynka;

    @FXML
    private ToggleGroup Grupa1;

    @FXML
    private ToggleButton Prostokat;

    @FXML
    private ToggleButton Sinusoida;

    @FXML
    private LineChart<Double, Double> plot;

    @FXML
    private LineChart<Double, Double> plot1;
    @FXML
    private LineChart<Double, Double> Locus;

    @FXML
    private TextField SampleAmount;


    @FXML
    public double wspK( )
    {
        CharSequence wspk = tfk.getCharacters();
        double k = Float.parseFloat(wspk.toString());
       // System.out.println(k);
        return k;


    }
    @FXML
    public double wspA( )
    {
        CharSequence wspk = tfA.getCharacters();
        double A = Double.parseDouble(wspk.toString());
        //System.out.println(A);
        return A;

    }
    @FXML
    public double wspa( )
    {
        CharSequence wspk = tfa.getCharacters();
        double a = Double.parseDouble(wspk.toString());
        //System.out.println(a);
        return a;

    }
    @FXML
    public double wspb( )
    {
        CharSequence wspk = tfb.getCharacters();
        double b = Double.parseDouble(wspk.toString());
        //System.out.println(b);
        return b;

    }

    @FXML
    public int SampleAmount() {
        CharSequence wspk = SampleAmount.getCharacters();
        int b = Integer.parseInt(wspk.toString());
       // System.out.println(b);
        return b;

    }
/*
    public void RysujLocus(ActionEvent e) throws IOException
    {
        Parent locus = FXMLLoader.load(getClass().getResource("LiniePierwiastkowe.fxml"));
        Scene scenaLocus = new Scene(locus);



        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(scenaLocus);
        window.show();

    }
*/
    public void LiniePierwiastkowe()
    {

    }
    public double reTransmitancja(double w)
    {
        double Gp = 0;

        Gp=(-wspA()*(w*w)+wspA()*wspa()*wspb())/((w*w*w*w)-(2*wspa()*wspb()+wspa()+wspb())*(w*w)+(wspa()*wspa())*(wspb()*wspb()));

        return Gp;
    }
    public double imTransmitancja(double w)
    {
        double Gp;
        Gp= (-wspA()*(wspa()*wspb()))/((w*w*w*w)-(2*wspa()*wspb()+wspa()+wspb())*(w*w)+(wspa()*wspa())*(wspb()*wspb()));

        return Gp;
    }
    public void RysujLocus()
    {
        double k=0;
        double omega=-10;
        double Gp=0;

        XYChart.Series<Double,Double> locus = new XYChart.Series<Double,Double>();

        for (int i=0;i<SampleAmount();i++)
        {
            for(int j=0; j<50;j++)
            {
                System.out.println("weszlo");
                if(reTransmitancja(omega)==-1/k)
                {
                    System.out.println("dodalo");
                    locus.getData().add(new XYChart.Data<Double,Double>(reTransmitancja(omega),imTransmitancja(omega)));
                }
                k=k+0.1;

            }

            omega=0.08;
        }
        Locus.getData().add(locus);
    }

    public boolean Stabilny()
    {
        if (!(wspa()+ wspb()>0) || !(((wspa()* wspb())+(wspK()* wspA()))>0))
        {
            return false;
        }else return true;
    }
    @FXML
    public void PopUp()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Błędne dane wejściowe");
        alert.setHeaderText("Uwaga!");
        alert.setContentText("Nie wprowadziłeś wszystkich danych, bądź wprowadzone dane są nieprawidłowe");
        alert.setHeight(400);

        alert.showAndWait();
    }
    @FXML

    public void PopUp2()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stabilność");
        alert.setHeaderText("Uwaga!");
        alert.setContentText("Wprowadzone dane nie zapewniają stabilności układu");
        alert.setHeight(400);

        alert.showAndWait();
    }

    @FXML
    public void Confirm()
    {
        if (((tfA.getText() == null || tfA.getText().trim().isEmpty())
                ||  (tfa.getText() == null || tfa.getText().trim().isEmpty())
                ||  (tfb.getText() == null || tfb.getText().trim().isEmpty())
                ||  (tfk.getText() == null || tfk.getText().trim().isEmpty())
                || (SampleAmount.getText() == null || SampleAmount.getText().trim().isEmpty())) || Grupa1.getSelectedToggle() == null)
        {
            PopUp();
        }
        else if (!Stabilny())
        {
            PopUp2();
        }
        else
        {
            ObliczanieWykresow(czas);
        }
    }


    public int KtoreWejscie(ToggleGroup Grupa1)
    {
        if(Grupa1.getSelectedToggle() == Jedynka){
            System.out.println("Jedynka");
            return 1;
        }
        else if(Grupa1.getSelectedToggle() == Prostokat){
            System.out.println("Prostokat");
            return 2;
        }
        else if(Grupa1.getSelectedToggle() == Sinusoida){
            System.out.println("Sinusoida");
            return 3;
        }
        else return 0;
    }




    public double FunkcjaProstokatna(double czas)
    {
        int z = (int)czas;
        if (z%2==0 ) {return (double)1;}
        else return (double)0;
    }


    public double Wejscie(double czas)
    {
        switch(KtoreWejscie(Grupa1))
        {
            case 0: System.out.println("zle wejscie");
            case 1: return (double)1;
            case 2: return FunkcjaProstokatna(czas);
            case 3: return (double)Math.sin((double)czas);
        }
        return 0;
    }

    


   /* public void policzWyjscie(float Ts)
    {
        float y;
        XYChart.Series<Float,Float> floatFloatSeries = new XYChart.Series<Float,Float>();
        for(double x =0; x<=20; x= x + 0.01){
            float z = (float)x;
            floatFloatSeries.getData().add(new XYChart.Data<Float,Float>(z,Depresja(z)));
        }
        plot.getData().add(floatFloatSeries);
    }*/

    public double ObliczanieWykresow(double czas)
    {
        double x1,x2,dx1,dx2,u,y;
        double forx1=0,forx2=0,fordx1=0,fordx2=0;
        double foru= Wejscie(0);
        double Ts=0.008;
         czas=0;
        

        XYChart.Series<Double,Double> doubleDoubleSeries = new XYChart.Series<Double,Double>();
        XYChart.Series<Double,Double> uchyb = new XYChart.Series<Double,Double>();
        for(int i=0;i<SampleAmount();i++)
        {
            System.out.println("Iteracja nr "+ i);
            System.out.println(" Ts=" +Ts);
            System.out.println("k="+wspK());
            System.out.println("A="+wspA());
            System.out.println("a="+wspa());
            System.out.println("b="+wspb());
            System.out.println("Wartosc forx2="+forx2);
            System.out.println("Wartosc forx1="+forx1);
            System.out.println("Wartosc fordx2="+fordx2);
            System.out.println("Wartosc fordx1="+fordx1);




            dx2=(foru-((wspa()+wspb())*forx2)-((wspa()*wspb())+(wspK()*wspA()))*forx1)*Ts;

            dx1=forx2*Ts;

            x2=forx2+dx2;

            x1=forx1+dx1;

            y=wspK()*wspA()*x1;

            doubleDoubleSeries.getData().add(new XYChart.Data<Double,Double>(czas,y));
            uchyb.getData().add(new XYChart.Data<Double,Double>(czas,dx2));

            System.out.println("Wartosc foru="+foru);
            System.out.println("Wartosc dx2="+dx2);
            System.out.println("Wartosc dx1="+dx1);
            System.out.println("Wartosc x2="+x2);
            System.out.println("Wartosc x1="+x1);
            System.out.println("Wartosc y="+y);

            forx1=x1;
            forx2=x2;
            fordx1=dx1;
            fordx2=dx2;
            foru=Wejscie(czas);
            czas= czas+(double)0.008;
            System.out.println("Nowy czas=" +czas);

        }
        plot.getData().add(doubleDoubleSeries);
        plot1.getData().add(uchyb);
        return 0;
    }

    void Initialize()
    {

    }

}
