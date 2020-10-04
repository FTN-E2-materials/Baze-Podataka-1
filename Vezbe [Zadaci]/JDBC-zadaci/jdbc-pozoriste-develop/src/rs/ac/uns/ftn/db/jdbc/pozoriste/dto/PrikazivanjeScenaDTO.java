package rs.ac.uns.ftn.db.jdbc.pozoriste.dto;

public class PrikazivanjeScenaDTO {
	
	int idpred;
	int suma;
	
	
	public PrikazivanjeScenaDTO(int idpred, int suma) {
		this.idpred = idpred;
		this.suma = suma;
	}
	
	public int getIdpred() {
		return idpred;
	}
	public void setIdpred(int idpred) {
		this.idpred = idpred;
	}
	public int getSuma() {
		return suma;
	}
	public void setSuma(int suma) {
		this.suma = suma;
	}
	
	

}
