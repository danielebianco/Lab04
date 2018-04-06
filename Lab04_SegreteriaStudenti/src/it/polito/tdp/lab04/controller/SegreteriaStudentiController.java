/**
 * Sample Skeleton for 'SegreteriaStudenti.fxml' Controller Class
 */

package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SegreteriaStudentiController {
	
	private Model model;
	List<Corso> corsi = new LinkedList<Corso>();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCorso"
    private ComboBox<String> btnCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaIscritti"
    private Button btnCercaIscritti; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="imgCA"
    private ImageView imgCA; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaCorso"
    private Button btnCercaCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="txtLog"
    private TextArea txtLog; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    @FXML
    void clickImgCA(MouseEvent event) {
    	
    	txtLog.clear();
    	txtCognome.clear();
    	txtNome.clear();
    	
    	try {
    		
    		int matricola = Integer.parseInt(txtMatricola.getText());
    		Studente studente = model.getStudente(matricola);
    		
    		if(studente==null) {
    			txtLog.setText("Attenzione: matricola inesistente.");
    			return;
    		}
    		
    		txtCognome.setText(studente.getCognome());
    		txtNome.setText(studente.getNome());
    		
    	}catch(NumberFormatException e) {
    		txtLog.setText("Inserire una matricola nel formato corretto.");
    	}catch(RuntimeException e) {
    		txtLog.setText("Attenzione! Errore di connessione al DB!");
    	}
    
    }

    @FXML
    void doReset(ActionEvent event) {	
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    	this.txtCognome.clear();
    	this.txtLog.clear();
    	this.btnCorso.getSelectionModel().clearSelection();
    }

    @FXML
    void handleCercaCorso(ActionEvent event) {
    	
    	txtLog.clear();
    	
    	try {
    		
    		int matricola = Integer.parseInt(txtMatricola.getText());
    		boolean selezionato = false;
    		Studente studente = model.getStudente(matricola);
    		
    		if(studente==null) {
    			txtLog.appendText("Attenzione: matricola inesistente!");
    			return;
    		}
    		
    		List<Corso> corsi = model.cercaCorsiDatoStudente(studente);
    			
    		Corso cor = null;
    		
    		for(Corso c : corsi) {
    			String confronto = c.toString();
    			if(confronto.equals(btnCorso.getValue())){
    				cor = c;
    			}
    		}
    		
    		if(btnCorso.getValue()!=null && !btnCorso.getValue().matches("")) {
    			selezionato = true;
    		}
    		
    		if(selezionato) {
    			if(corsi.contains(cor)) {
        			txtLog.setText("Lo studente risulta iscritto al corso.\n");
        		}
        		else {
        			txtLog.setText("Lo studente non risulta iscritto al corso.\n");
        		}
    		}
    		else {
    			
    			StringBuilder sb = new StringBuilder();
        		
        		for(Corso corso : corsi) {
        			sb.append(String.format("%-10s ", corso.getCodins()));
        			sb.append(String.format("%-4s ", corso.getNumeroCrediti()));
        			sb.append(String.format("%-45s ", corso.getNome()));
        			sb.append(String.format("%-4s ", corso.getPeriodoDidattico()));
        			sb.append("\n");
        		}
        		
        		txtLog.appendText(sb.toString());
    		}    		   
    		    		    		
    	}catch(NumberFormatException e) {
    		txtLog.setText("Inserire una matricola nel formato corretto.");
    	}catch(RuntimeException e) {
    		txtLog.setText("Attenzione! Errore di connessione al DB!");
    	}
    }

    @FXML
    void handleCercaIscritti(ActionEvent event) {
    	
    	txtLog.clear();
    	txtCognome.clear();
    	txtNome.clear();
    	
    	try {
    	
    		Corso corso = null;
    		
    		for(Corso c : corsi) {
    			String confronto = c.toString();
    			if(confronto.equals(btnCorso.getValue())){
    				corso = c;
    			}
    		}
    		
    		if(corso==null) {
    			txtLog.appendText("Attenzione: corso non selezionato.");
    			return;
    		}
    	
    		List<Studente> studenti = model.studentiIscrittiAlCorso(corso);
    		
    		StringBuilder sb = new StringBuilder();
    		
    		for(Studente studente : studenti) {
    			sb.append(String.format("%-10s ", studente.getMatricola()));
    			sb.append(String.format("%-20s ", studente.getCognome()));
    			sb.append(String.format("%-20s ", studente.getNome()));
    			sb.append(String.format("%-10s ", studente.getCds()));
    			sb.append("\n");
    		}
    		
    		txtLog.appendText(sb.toString());
    		
    	}catch(RuntimeException e) {
    		txtLog.setText("Attenzione! Errore di connessione al DB!");
    	}
    }

    @FXML
    void handleIscrivi(ActionEvent event) {
    	
    	txtLog.clear();
    	
    	try {
    		
    		if(txtMatricola.getText().isEmpty()) {
    			txtLog.setText("Inserire una matricola.");
    			return;
    		}
    		
    		if(btnCorso.getValue()==null) {
    			txtLog.setText("Selezionare un corso.");
    			return;
    		}
    		
    		int matricola = Integer.parseInt(txtMatricola.getText());
    		
    		Studente studente = model.getStudente(matricola);
    		
    		if(studente==null) {
    			txtLog.appendText("Attenzione: matricola inesistente!");
    			return;
    		}
    		
    		txtCognome.setText(studente.getCognome());
    		txtNome.setText(studente.getNome());
    		
    		Corso corso = null;
    		
    		for(Corso c : corsi) {
    			String confronto = c.toString();
    			if(confronto.equals(btnCorso.getValue())){
    				corso = c;
    			}
    		}
    		
    		if(model.isStudenteIscrittoAlCorso(studente, corso)) {
    			txtLog.appendText("Studente già isctitto al corso.");
    			return;
    		}
    		
    		if(!model.iscriviStudenteAlCorso(studente, corso)) {
    			txtLog.appendText("Errore in fase di iscrizione al corso.");
    			return;
    		}
    		
    		else {
    			txtLog.appendText("Studente iscritto al corso!");
    		}
    		    		
    	}catch(NumberFormatException e) {
    		txtLog.setText("Inserire una matricola nel formato corretto.");
    	}catch(RuntimeException e) {
    		txtLog.setText("Attenzione! Errore di connessione al DB!");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCorso != null : "fx:id=\"btnCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert imgCA != null : "fx:id=\"imgCA\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaCorso != null : "fx:id=\"btnCercaCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtLog != null : "fx:id=\"txtLog\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        
        txtLog.setStyle("-fx-font-family: monospace");
    }

	public void setModel(Model model) {
		
		this.model = model;
		
		corsi = model.getTuttiICorsi();
		Collections.sort(corsi);
		btnCorso.getItems().add("");
		
		for(Corso c : corsi) {
			btnCorso.getItems().add(c.toString());
		}
	}
	
}
