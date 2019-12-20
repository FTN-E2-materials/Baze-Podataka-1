package rs.ac.uns.ftn.db.jdbc.pozoriste.model;

public class Predstava {
	private int idpred;
	private String nazivpred;
	private String trajanje;
	private int godinapre;

	public Predstava() {
	}
	
	public Predstava(int idpred){
		this.idpred = idpred;
	}

	public Predstava(int idpred, String nazivpred, String trajanje, int godinapre) {
		this.idpred = idpred;
		this.nazivpred = nazivpred;
		this.trajanje = trajanje;
		this.godinapre = godinapre;
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

	public String getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(String trajanje) {
		this.trajanje = trajanje;
	}

	public int getGodinapre() {
		return godinapre;
	}

	public void setGodinapre(int godinapre) {
		this.godinapre = godinapre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + godinapre;
		result = prime * result + idpred;
		result = prime * result + ((nazivpred == null) ? 0 : nazivpred.hashCode());
		
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
		Predstava other = (Predstava) obj;
		if (godinapre != other.godinapre)
			return false;
		if (idpred != other.idpred)
			return false;
		if (nazivpred == null) {
			if (other.nazivpred != null)
				return false;
		} else if (!nazivpred.equals(other.nazivpred))
			return false;
		if (trajanje != other.trajanje)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("%-6d %-30.30s %-10.10s %-10d", idpred, nazivpred, trajanje, godinapre);
	}
	
	public static String getFormattedHeader() {
		return String.format("%-6s %-30.30s %-10.10s %-10s", "IDPRED", "NAZIVPRED", "TRAJANJE", "GODINAPRE");
	}

}
