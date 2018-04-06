package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.lab04.model.*;

public class StudenteDAO {
	
	public boolean isStudenteIscrittoAlCorso(Studente studente, Corso corso) {

		final String sql = "SELECT * FROM iscrizione WHERE codins=? AND matricola=?";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			st.setInt(2, studente.getMatricola());
			
			ResultSet rs = st.executeQuery();
			
			boolean returnValue = false;
			
			if(rs.next())
				returnValue = true;
			
			conn.close();
			return returnValue;
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
	public void getCorsiFromStudente(Studente studente) {
		
		final String sql = "SELECT * FROM iscrizione, corso WHERE iscrizione.codins=corso.codins AND matricola=?";
		
		List<Corso> corsi = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso corso = new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				corsi.add(corso);
			}
			studente.setCorsi(corsi);
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public Studente getStudente(int matricola) {
		
		final String sql = "SELECT * FROM studente WHERE matricola=?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();
			
			Studente studente = null;
			
			if(rs.next()) {
				
				studente = new Studente(matricola, rs.getString("cognome"), rs.getString("nome"), rs.getString("cds"));
				
			}
			conn.close();
			return studente;
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
}
