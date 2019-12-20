package rs.ac.uns.ftn.db.jdbc.pozoriste.model;

import java.util.Date;

public class Prikazivanje {
	private int rbr;
	private Date datumpri;
	private Date vremepri;
	private int brojgled;
	private int idpred;
	private int idsce;
	
	
	public Prikazivanje() {
	}

	public Prikazivanje(int brojgled, int idpred) {
		this.brojgled = brojgled;
		this.idpred = idpred;
	}

	public Prikazivanje(int rbr, Date datumpri, Date vremepri, int brojgled, int idpred, int idsce) {
		this.rbr = rbr;
		this.datumpri = datumpri;
		this.vremepri = vremepri;
		this.brojgled = brojgled;
		this.idpred = idpred;
		this.idsce = idsce;
	}
	
	
	public int getRbr() {
		return rbr;
	}

	public void setRbr(int rbr) {
		this.rbr = rbr;
	}

	public Date getDatumpri() {
		return datumpri;
	}

	public void setDatumpri(Date datumpri) {
		this.datumpri = datumpri;
	}

	public Date getVremepri() {
		return vremepri;
	}

	public void setVremepri(Date vremepri) {
		this.vremepri = vremepri;
	}

	public int getBrojgled() {
		return brojgled;
	}

	public void setBrojgled(int brojgled) {
		this.brojgled = brojgled;
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
		result = prime * result + brojgled;
		result = prime * result + ((datumpri == null) ? 0 : datumpri.hashCode());
		result = prime * result + idpred;
		result = prime * result + idsce;
		result = prime * result + rbr;
		result = prime * result + ((vremepri == null) ? 0 : vremepri.hashCode());
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
		Prikazivanje other = (Prikazivanje) obj;
		if (brojgled != other.brojgled)
			return false;
		if (datumpri == null) {
			if (other.datumpri != null)
				return false;
		} else if (!datumpri.equals(other.datumpri))
			return false;
		if (idpred != other.idpred)
			return false;
		if (idsce != other.idsce)
			return false;
		if (rbr != other.rbr)
			return false;
		if (vremepri == null) {
			if (other.vremepri != null)
				return false;
		} else if (!vremepri.equals(other.vremepri))
			return false;
		return true;
	}

	public int getIdsce() {
		return idsce;
	}

	public void setIdsce(int idsce) {
		this.idsce = idsce;
	}

	@Override
	public String toString() {
		return String.format("%-6d %-20.20s %-20.20s %-8d %-8d %-8d", rbr, datumpri.toString(), vremepri.toString(), brojgled, idpred, idsce);
	}
	
	public static String getFormattedHeader() {
		return String.format("%-6s %-20s %-20s %-8s %-8s %-8s", "RBR", "DATUMPRI", "VREMEPRI", "BROJ_GLED" , "ID_PRED" , "ID_SCE");
	}

}
