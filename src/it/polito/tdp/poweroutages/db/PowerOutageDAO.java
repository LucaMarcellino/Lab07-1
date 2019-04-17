package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;

public class PowerOutageDAO {

	public List<Nerc> getNercList() {

		String sql = "SELECT * FROM nerc";
		List<Nerc> nercList = new ArrayList<Nerc>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				nercList.add(new Nerc(res.getInt("id"), res.getString("value")));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public Nerc getNerc(String value) {
		String sql = "SELECT * FROM nerc WHERE value = ?";
		Nerc n = null;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, value);
			ResultSet res = st.executeQuery();

			if (res.next()) 
				n = new Nerc(res.getInt("id"), res.getString("value"));

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return n;
	}
	
	public List<PowerOutages> getPowerOutagesList(Nerc n) {

		String sql = "SELECT * FROM poweroutages WHERE nerc_id = ? ORDER BY date_event_began ASC";
		List<PowerOutages> poList = new ArrayList<PowerOutages>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				Nerc nerc = n;
				int clienti = res.getInt("customers_affected");
				LocalDateTime inizio = res.getTimestamp("date_event_began").toLocalDateTime();
				LocalDateTime fine = res.getTimestamp("date_event_finished").toLocalDateTime();
				
				poList.add(new PowerOutages(id, nerc, clienti, inizio, fine));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return poList;
	}

}
