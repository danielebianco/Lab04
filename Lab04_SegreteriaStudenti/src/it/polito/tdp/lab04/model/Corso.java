package it.polito.tdp.lab04.model;

import java.util.*;

public class Corso implements Comparable<Corso>{
	private List<Studente> studenti;
	private String codins;
	private int numeroCrediti;
	private String nome;
	private int periodoDidattico;
	
	public Corso(String codins, int numeroCrediti, String nome, int periodoDidattico) {
		this.codins = codins;
		this.numeroCrediti = numeroCrediti;
		this.nome = nome;
		this.periodoDidattico = periodoDidattico;
	}
	
	

	public String getCodins() {
		if(codins==null)
			return "";
		return codins;
	}

	public int getNumeroCrediti() {
		return numeroCrediti;
	}

	public String getNome() {
		if(nome==null)
			return "";
		return nome;
	}

	public int getPeriodoDidattico() {
		return periodoDidattico;
	}

	public void setCodins(String codins) {
		this.codins = codins;
	}

	public void setNumeroCrediti(int numeroCrediti) {
		this.numeroCrediti = numeroCrediti;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPeriodoDidattico(int periodoDidattico) {
		this.periodoDidattico = periodoDidattico;
	}
	
	public List<Studente> getStudenti() {
		if(studenti==null) {
			return new ArrayList<Studente>();
		}
		return studenti;
	}

	public void setStudenti(List<Studente> studenti) {
		this.studenti = studenti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Corso other = (Corso) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Corso corsoInput) {
		return this.nome.compareTo(corsoInput.nome);
	}
	
	@Override
	public String toString() {
		return codins + " - " + nome;
	}

}
