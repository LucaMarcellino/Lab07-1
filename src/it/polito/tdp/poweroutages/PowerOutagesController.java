package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {
	
	private Model model;
	
	public void setModel(Model model) {
		this.model = model;
		cbxNerc.getItems().setAll(model.getNercList());
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private ChoiceBox<String> cbxNerc;

    @FXML
    private TextField txtYears;

    @FXML
    private TextField txtHours;

    @FXML
    private Button btnAnalize;

    @FXML
    void doAnalize(ActionEvent event) {
    	String nerc = cbxNerc.getValue();
    	int anni = 0;
    	int ore = 0;
    	
    	try {
    		anni = Integer.parseInt(txtYears.getText().trim());
    	} catch (NumberFormatException nfe) {
    		txtResult.setText("Max Years inserito in modo errato!\n");
    		return;
    	}
    	
    	try {
        	ore = Integer.parseInt(txtHours.getText().trim());
    	} catch (NumberFormatException nfe) {
    		txtResult.setText("Max Hours inserito in modo errato!\n");
    		return;
    	}
    	
    	if (nerc.compareTo("")==0) 
    		txtResult.setText("Selezionare un NERC dal menù a tendina\n");
    	else
    		txtResult.setText(model.cercaEventi(nerc, anni, ore));

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cbxNerc != null : "fx:id=\"cbxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnAnalize != null : "fx:id=\"btnAnalize\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        cbxNerc.setValue("");
    }
}
