package rs.ac.uns.ftn.db.jdbc.pozoriste.dto;

public class PredstavaDTO {
	
	int idpred;
	String nazivpred;
	double prosecan_broj_gledalaca;
	
	
	
	public PredstavaDTO(int idpred, String nazivpred, double prosecan_broj_gledalaca) {
		this.idpred = idpred;
		this.nazivpred = nazivpred;
		this.prosecan_broj_gledalaca = prosecan_broj_gledalaca;
	}
	public int getIdpred() {
		return idpred;
	}
	public void setIdpred(int idpred) {
		this.idpred = idpred;
	}
	public String getNazivpred() {
		return nazivpred;
	}
	public void setNazivpred(String nazivpred) {
		this.nazivpred = nazivpred;
	}
	public double getProsecan_broj_gledalaca() {
		return prosecan_broj_gledalaca;
	}
	public void setProsecan_broj_gledalaca(double prosecan_broj_gledalaca) {
		this.prosecan_broj_gledalaca = prosecan_broj_gledalaca;
	}
	@Override
	public String toString() {
		return String.format("%-6d %-10s %-8f ", idpred, nazivpred, prosecan_broj_gledalaca);
	}
	
	
	
	

}
