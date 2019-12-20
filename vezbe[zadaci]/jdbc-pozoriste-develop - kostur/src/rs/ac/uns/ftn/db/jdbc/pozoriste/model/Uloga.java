package rs.ac.uns.ftn.db.jdbc.pozoriste.model;

public class Uloga {
	private String imeulo;
	private String pol;
	private String vrstaulo;
	private int idpred;

	public Uloga() {
	}

	public Uloga(String imeulo, String pol, String vrstaulo, int idpred) {
		this.imeulo = imeulo;
		this.pol = pol;
		this.vrstaulo = vrstaulo;
		this.idpred = idpred;
	}

	public String getImeulo() {
		return imeulo;
	}

	public void setImeulo(String imeulo) {
		this.imeulo = imeulo;
	}

	public String isPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getVrstaulo() {
		return vrstaulo;
	}

	public void setVrstaulo(String vrstaulo) {
		this.vrstaulo = vrstaulo;
	}

	public int getIdpred() {
		return idpred;
	}

	public void setIdpred(int idpred) {
		this.idpred = idpred;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idpred;
		result = prime * result + ((imeulo == null) ? 0 : imeulo.hashCode());
		result = prime * result + ((vrstaulo == null) ? 0 : vrstaulo.hashCode());
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
		Uloga other = (Uloga) obj;
		if (idpred != other.idpred)
			return false;
		if (imeulo == null) {
			if (other.imeulo != null)
				return false;
		} else if (!imeulo.equals(other.imeulo))
			return false;
		if (pol != other.pol)
			return false;
		if (vrstaulo == null) {
			if (other.vrstaulo != null)
				return false;
		} else if (!vrstaulo.equals(other.vrstaulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%10s %-10s %-10s %-10d", imeulo, pol, vrstaulo, idpred);
	}
	
	public static String getFormattedHeader() {
		return String.format("%-10s %-10s %-10s %-10s", "IME", "POL", "VRSTA", "IDPRED");
	}
	
}
