package rs.ac.uns.ftn.db.jdbc.pozoriste.model;

public class Pozoriste {
	private int id;
	private String naziv;
	private String adresa;
	private String sajt;
	private String mesto;

	public Pozoriste() {
	}

	public Pozoriste(int id, String naziv, String adresa, String sajt,String mesto) {
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.sajt = sajt;
		this.mesto = mesto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getSajt() {
		return sajt;
	}

	public void setSajt(String sajt) {
		this.sajt = sajt;
	}
	
	
	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresa == null) ? 0 : adresa.hashCode());
		result = prime * result + id;
		result = prime * result + ((mesto == null) ? 0 : mesto.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		result = prime * result + ((sajt == null) ? 0 : sajt.hashCode());
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
		Pozoriste other = (Pozoriste) obj;
		if (adresa == null) {
			if (other.adresa != null)
				return false;
		} else if (!adresa.equals(other.adresa))
			return false;
		if (id != other.id)
			return false;
		if (mesto == null) {
			if (other.mesto != null)
				return false;
		} else if (!mesto.equals(other.mesto))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (sajt == null) {
			if (other.sajt != null)
				return false;
		} else if (!sajt.equals(other.sajt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-6d %-30.30s %-30.30s %-30.30s %-30.30s", id, naziv, adresa, sajt, mesto);
	}
	
	public static String getFormattedHeader() {
		return String.format("%-6s %-30.30s %-30.30s %-30.30s %-30.30s", "IDPOZ", "NAZIVPOZ", "ADRESAPOZ", "SAJT", "MESTO");
	}

}
