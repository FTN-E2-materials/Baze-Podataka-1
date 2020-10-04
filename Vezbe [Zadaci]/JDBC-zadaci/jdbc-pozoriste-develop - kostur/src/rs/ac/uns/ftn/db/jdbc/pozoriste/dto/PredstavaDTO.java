package rs.ac.uns.ftn.db.jdbc.pozoriste.dto;

public class PredstavaDTO {

	int idp;
	String nazivp;
	float prosecan_broj_gledalaca;
	
	public PredstavaDTO(int idp, String nazivp, float prosecan_broj_gledalaca) {
		super();
		this.idp = idp;
		this.nazivp = nazivp;
		this.prosecan_broj_gledalaca = prosecan_broj_gledalaca;
	}

	public int getIdp() {
		return idp;
	}

	public void setIdp(int idp) {
		this.idp = idp;
	}

	public String getNazivp() {
		return nazivp;
	}

	public void setNazivp(String nazivp) {
		this.nazivp = nazivp;
	}

	public float getProsecan_broj_gledalaca() {
		return prosecan_broj_gledalaca;
	}

	public void setProsecan_broj_gledalaca(float prosecan_broj_gledalaca) {
		this.prosecan_broj_gledalaca = prosecan_broj_gledalaca;
	}

	@Override
	public String toString() {
		return "\t" + idp + "\t\t\t" + nazivp + "\t\t\t"
				+ prosecan_broj_gledalaca ;
	}
	
	
	
	
}
