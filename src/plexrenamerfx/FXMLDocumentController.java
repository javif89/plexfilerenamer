/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plexrenamerfx;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author javif89
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label dirLabel, dirLabelSeries;
    @FXML
    private TextField seriesText, seasonText, seriesText2;
    @FXML
    private TextArea logText;
    @FXML
    private ProgressBar progressBar;
    
    private double xOffset = 0, yOffset = 0;
    private File directory,directorySeries;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Season Folder");
        directory = dirChooser.showDialog(null);
        if(directory != null)
        {
            dirLabel.setText(directory.getPath());
            seriesText.setText(directory.getParentFile().getName());
            Pattern p = Pattern.compile(" [0-9]{1,3} ?");
            Matcher m = p.matcher(directory.getName());
            if(m.find())
            {
                int seasonNum = Integer.parseInt(m.group().trim());
                if(seasonNum < 10)
                {
                    seasonText.setText("0"+seasonNum);
                }
                else
                {
                    seasonText.setText(m.group().trim());
                }
            }
        }
    }
    
    @FXML
    private void renameSeriesChooseFolder(ActionEvent event)
    {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Series Folder");
        directorySeries = dirChooser.showDialog(null);
        if(directorySeries != null)
        {
            dirLabelSeries.setText(directorySeries.getPath());
            seriesText2.setText(directorySeries.getName());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void closeWindowAction(ActionEvent event)
    {
        Platform.exit();
    }
    
    @FXML
    private void minimizeWindowAction(ActionEvent event)
    {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    private void setOffsetsAction(MouseEvent event)
    {
        Stage primaryStage = (Stage)((AnchorPane)event.getSource()).getScene().getWindow();
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
    
    @FXML
    private void dragWindowAction(MouseEvent event)
    {
        Stage primaryStage = (Stage)((AnchorPane)event.getSource()).getScene().getWindow();
        primaryStage.setX(event.getScreenX() - xOffset);
        primaryStage.setY(event.getScreenY() - yOffset);
    }
    
    @FXML
    private void renameFilesAction(ActionEvent event)
    {
        FileRenamer fr = new FileRenamer();
        fr.renameInDirectory(directory, seasonText.getText(), seriesText.getText(), logText, progressBar);
    }
    
    @FXML
    private void renameSeriesAction(ActionEvent event)
    {
        FileRenamer fr = new FileRenamer();
        fr.renameSeries(directorySeries, seriesText2.getText(), logText, progressBar);
    }
}
