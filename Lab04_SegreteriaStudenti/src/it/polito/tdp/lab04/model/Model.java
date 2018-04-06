package it.polito.tdp.lab04.model;

import java.util.*;
import it.polito.tdp.lab04.DAO.*;

public class Model {
	
	CorsoDAO cdao;
	StudenteDAO sdao;
		
	public Model() {
		this.cdao = new CorsoDAO();
		this.sdao = new StudenteDAO();
	}

	public Studente getStudente(int matricola) {
		return sdao.getStudente(matricola);
	}
	
	public List<Studente> studentiIscrittiAlCorso(Corso corso) {
		cdao.getStudentiIscrittiAlCorso(corso);
		return corso.getStudenti();
	}

	public List<Corso> cercaCorsiDatoStudente(Studente studente) {
		sdao.getCorsiFromStudente(studente);
		return studente.getCorsi();
	}
	
	public boolean isStudenteIscrittoAlCorso(Studente studente, Corso corso) {
		return sdao.isStudenteIscrittoAlCorso(studente, corso);
	}
	
	public boolean iscriviStudenteAlCorso(Studente studente, Corso corso) {
		return cdao.inscriviStudenteAlCorso(studente, corso);
	}
	
	public List<Corso> getTuttiICorsi() {
		return cdao.getTuttiICorsi();
	}
	
}
