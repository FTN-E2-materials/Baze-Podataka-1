package rs.ac.uns.ftn.db.jdbc.pozoriste.dto;

public class PredstavaDTO {
	int idpred;
	String naziv_predstave;
	double prosecan_broj_gledalaca;
	
	
	
	public PredstavaDTO(int idpred, String naziv_predstave, double prosecan_broj_gledalaca) {
		super();
		this.idpred = idpred;
		this.naziv_predstave = naziv_predstave;
		this.prosecan_broj_gledalaca = prosecan_broj_gledalaca;
	}
	public int getIdpred() {
		return idpred;
	}
	public void setIdpred(int idpred) {
		this.idpred = idpred;
	}
	public String getNaziv_predstave() {
		return naziv_predstave;
	}
	public void setNaziv_predstave(String naziv_predstave) {
		this.naziv_predstave = naziv_predstave;
	}
	public double getProsecan_broj_gledalaca() {
		return prosecan_broj_gledalaca;
	}
	public void setProsecan_broj_gledalaca(double prosecan_broj_gledalaca) {
		this.prosecan_broj_gledalaca = prosecan_broj_gledalaca;
	}
	
	
	@Override
	public String toString() {
		return String.format("%-6d %-10s %-8f ", idpred, naziv_predstave, prosecan_broj_gledalaca);
	}
	
}
