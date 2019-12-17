package rs.ac.uns.ftn.db.jdbc.pozoriste.model;

public class Scena {
	private int idsce;
	private String nazivsce;
	private int brojsed;
	private int idpoz;
	
	public Scena() {
	}
	
	

	public Scena(int idsce, String nazivsce, int brojsed, int idpoz) {
		this.idsce = idsce;
		this.nazivsce = nazivsce;
		this.brojsed = brojsed;
		this.idpoz = idpoz;
		
	}



	public Scena(String nazivsce, int brojsed, int idpoz) {
		this.nazivsce = nazivsce;
		this.brojsed = brojsed;
		this.idpoz = idpoz;
	}
	
	public Scena(String nazivsce, int brojsed) {
		this.nazivsce = nazivsce;
		this.brojsed = brojsed;
	}

	public String getNazivsce() {
		return nazivsce;
	}

	public void setNazivsce(String nazivsce) {
		this.nazivsce = nazivsce;
	}

	public int getBrojsed() {
		return brojsed;
	}

	public void setBrojsed(int brojsed) {
		this.brojsed = brojsed;
	}

	public int getIdpoz() {
		return idpoz;
	}

	public void setIdpoz(int idpoz) {
		this.idpoz = idpoz;
	}
	
	

	public int getIdsce() {
		return idsce;
	}



	public void setIdsce(int idsce) {
		this.idsce = idsce;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + brojsed;
		result = prime * result + idpoz;
		result = prime * result + ((nazivsce == null) ? 0 : nazivsce.hashCode());
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
		Scena other = (Scena) obj;
		if (brojsed != other.brojsed)
			return false;
		if (idpoz != other.idpoz)
			return false;
		if (nazivsce == null) {
			if (other.nazivsce != null)
				return false;
		} else if (!nazivsce.equals(other.nazivsce))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-6d %-20.20s %-8d %-6d", idsce, nazivsce, brojsed, idpoz);
	}
	
	public static String getFormattedHeader() {
		return String.format("%-6s %-20s %-8s %-6s", "IDSCE", "NAZIVSCE", "BROJSED", "IDPOZ");
	}

}
