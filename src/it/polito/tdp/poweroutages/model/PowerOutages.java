package it.polito.tdp.poweroutages.model;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class PowerOutages {
	
	private int id;
	private Nerc nerc;
	private int clienti;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	
	public PowerOutages(int id, Nerc nerc, int clienti, LocalDateTime inizio, LocalDateTime fine) {
		this.id = id;
		this.nerc = nerc;
		this.clienti = clienti;
		this.inizio = inizio;
		this.fine = fine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public int getClienti() {
		return clienti;
	}

	public void setClienti(int clienti) {
		this.clienti = clienti;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}
	
	public int anno() {
		return inizio.getYear();
	}
	
	public long durata() {
		return ChronoUnit.HOURS.between(inizio, fine);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		PowerOutages other = (PowerOutages) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return anno()+"   "+inizio+"   "+fine+"   "+durata()+"   "+clienti+"\n";
	}

}
