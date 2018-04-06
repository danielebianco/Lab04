package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.lab04.model.*;

public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel DB
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT codins, crediti, nome, pd " + "FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
								
				Corso cs = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(cs);
			}
			
			conn.close();
			return corsi;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		
		final String sql = "SELECT * FROM corso WHERE codins=?";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			
			ResultSet rs = st.executeQuery();	

			while (rs.next()) {
				corso.setNumeroCrediti(rs.getInt("crediti"));
				corso.setNome(rs.getString("nome"));
				corso.setPeriodoDidattico(rs.getInt("pd"));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}		
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public void getStudentiIscrittiAlCorso(Corso corso) {
		
		final String sql = "SELECT * FROM iscrizione, studente WHERE iscrizione.matricola=studente.matricola AND codins=?";
		
		List<Studente> studentiIscrittiAlCorso = new LinkedList<Studente>();	
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				studentiIscrittiAlCorso.add(new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("cds")));
				
			}
			
			corso.setStudenti(studentiIscrittiAlCorso);		
			conn.close();
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}

	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteAlCorso(Studente studente, Corso corso) {
		
		String sql = "INSERT INTO `iscritticorsi`.`iscrizione` (`matricola`,`codins`) VALUES(?,?)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			
			int res = st.executeUpdate();
			
			boolean returnValue = false;
			
			if(res==1)
				returnValue = true;
			
			conn.close();
			return returnValue;
		
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
}
