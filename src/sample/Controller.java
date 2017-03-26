package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private TextField txtFile;
    @FXML Button btZgjedhFile, btKonverto;
    @FXML
    TextArea txtArea;

    StringBuilder sb = new StringBuilder();
    StringBuilder merOld = new StringBuilder();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        konverto(txtFile.getText());
                    }
                });
            }
        });

        t.setDaemon(true);

        btKonverto.setOnAction(e -> {
            t.start();
        });

    }

    //Choose file
    public void zgjedhFile(Stage stage){
        try {

            FileChooser fc = new FileChooser();
            fc.setTitle("Zgjedh dokumentin");
            File file = fc.showOpenDialog(stage);

            txtArea.appendText("Dokumenti u hap me sukses.\nKliko 'Konverto' per te filluar\n");

            txtFile.setText(file.getAbsolutePath());

        }catch (Exception e) {

        }
    }

    //Read from and convert to ASCII characters in a new file
    private void konverto(String file){
        try {
            File oldFile = new File(file);
            File newFile = new File(oldFile.getParent() + "/" + oldFile.getName().substring(0, oldFile.getName().length()-4) + "_konvertuar.txt");
            newFile.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(oldFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));

            txtArea.appendText("Duke konvertuar ne Text\n");
            String line;
            String nline = "";
            int s;

            while ((line = br.readLine()) != null)
                nline += line;

            for (int i = 0; i < nline.length()-1; i++){
                if (nline.charAt(i+1) != ' ') {
                    if (nline.charAt(i) == '0' && nline.charAt(i+1) == 'a')
                        merOld.append("\n\r");
                    merOld.append((char) (ktheNeDec(nline.charAt(i) + "" + nline.charAt(i+1))));
                }else i++;
            }

            br.close();

            txtArea.appendText("Duke shkruar dokumentin e ri ...\n");
            bw.write(merOld.toString());
            txtArea.appendText("Dokumenti u konvertua me sukses.\nMund ta gjesh ne lokacionin e njejte me dokumentin e vjeter.\n" + newFile.getAbsolutePath());
            bw.close();

        }catch (Exception e) {

        }
    }


    private String ktheNeHex(int n){
        int rem = 0;
        while (n > 0) {
            rem = n % 16;
            n /= 16;

            switch (rem) {
                case 10:
                    sb.append("A");
                    break;
                case 11:
                    sb.append("B");
                    break;
                case 12:
                    sb.append("C");
                    break;
                case 13:
                    sb.append("D");
                    break;
                case 14:
                    sb.append("E");
                    break;
                case 15:
                    sb.append("F");
                    break;
                default:
                    sb.append(rem);
                    break;
            }
        }

        return sb.reverse().toString();
    }

    //Convert from Hex to ASCII
    private int ktheNeDec(String n){
        int j = 0;
        int digit = 0;
        for (int i = n.length()-1; i >= 0; i--) {

            switch (n.charAt(i)) {
                case 'A':
                case 'a':
                    digit += 10 * (int) Math.pow(16, j);
                    break;
                case 'B':
                case 'b':
                    digit += 11 * (int) Math.pow(16, j);
                    break;
                case 'C':
                case 'c':
                    digit += 12 * (int) Math.pow(16, j);
                    break;
                case 'D':
                case 'd':
                    digit += 13 * (int) Math.pow(16, j);
                    break;
                case 'E':
                case 'e':
                    digit += 14 * (int) Math.pow(16, j);
                    break;
                case 'F':
                case 'f':
                    digit += 15 * (int) Math.pow(16, j);
                    break;
                default:
                    digit += Character.getNumericValue(n.charAt(i)) * (int)Math.pow(16, j);
                    break;
            }
            j++;
        }

        return digit;
    }


}
