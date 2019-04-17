package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	private PowerOutageDAO podao;
	private List<PowerOutages> tutti;
	private List<PowerOutages> soluzione;
	private int best;
	private int anni;
	private int ore;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<String> getNercList() {
		List<String> lista = new ArrayList<String>();
		List<Nerc> listaNerc = podao.getNercList();
		
		for (Nerc n : listaNerc)
			lista.add(n.getValue());
		
		return lista;
	}
	
	public String cercaEventi(String nerc, int anni, int ore) {
		String s = "";
		List<PowerOutages> parziale = new ArrayList<PowerOutages>();
		soluzione = null;
		best = 0;
		this.anni = anni;
		this.ore = ore;
		
		Nerc n = podao.getNerc(nerc);
		if (n == null)
			return "Il NERC non è stato trovato nel DB!";
		
		n.setPo(podao.getPowerOutagesList(n));
		tutti = n.getPo();
		
		ricorsione(parziale, 0);
		
		if (soluzione != null) {
			s += "Tot people affected:   "+sommaPeople(soluzione)+"\n";
			s += "Tot hours of outages:   "+sommaOre(soluzione)+"\n";
			
			for (PowerOutages po: soluzione)
				s += po.toString();
		} else 
			s = "Nessuna combinazione trovata";
		
		return s;
	}
	
	private void ricorsione(List<PowerOutages> parziale, int L) {
		
		// ad ogni livello L decido se aggiungere l'evento o no
		
		if (L == tutti.size()) {
			int somma = sommaPeople(parziale);
			if (somma > best) {
				best = somma;
				soluzione = new ArrayList<PowerOutages>(parziale);
			}
			return ;
		}
		
		ricorsione(parziale, L+1);  // non aggiungo
		
		
		parziale.add(tutti.get(L));
		
		if (sommaOre(parziale) <= ore && diffAnni(parziale) <= anni)
			ricorsione(parziale, L+1);    // aggiungo
			 
		parziale.remove(tutti.get(L));
		
	}
	
	public int sommaPeople(List<PowerOutages> soluzione) {
        int somma = 0;
		
		for (PowerOutages p: soluzione)
			somma += p.getClienti();
		
		return somma;
	}
	
	public long sommaOre(List<PowerOutages> parziale) {
		long somma = 0;
		
		for (PowerOutages p: parziale)
			somma += p.durata();
		
		return somma;
	}
	
	public int diffAnni(List<PowerOutages> parziale) {
		int min = 2020;
		int max = 0;
		
		for (PowerOutages p : parziale)
			if (p.anno() < min)
				min = p.anno();
		
		for (PowerOutages p : parziale)
			if (p.anno() > max)
				max = p.anno();
		
		return max - min;
	}
	
}
