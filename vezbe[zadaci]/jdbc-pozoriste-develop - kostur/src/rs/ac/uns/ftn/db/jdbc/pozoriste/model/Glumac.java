package rs.ac.uns.ftn.db.jdbc.pozoriste.model;

import java.util.Date;

public class Glumac {
	private int mbg;
	private String imeg;
	private Date datumr;
	private boolean status;
	private double plata;
	private double dodatak;
	private int idpoz;

	public Glumac() {
	}
	
	public Glumac(int mbg, String imeg, Date datumr, boolean status, double plata, double dodatak, int idpoz) {
		this.mbg = mbg;
		this.imeg = imeg;
		this.datumr = datumr;
		this.status = status;
		this.plata = plata;
		this.dodatak = dodatak;
		this.idpoz = idpoz;
	}
	

	public int getMbg() {
		return mbg;
	}

	public void setMbg(int mbg) {
		this.mbg = mbg;
	}

	public String getImeg() {
		return imeg;
	}

	public void setImeg(String imeg) {
		this.imeg = imeg;
	}

	public Date getDatumr() {
		return datumr;
	}

	public void setDatumr(Date datumr) {
		this.datumr = datumr;
	}

	public double getPlata() {
		return plata;
	}

	public void setPlata(double plata) {
		this.plata = plata;
	}

	public double getDodatak() {
		return dodatak;
	}

	public void setDodatak(double dodatak) {
		this.dodatak = dodatak;
	}

	public int getIdpoz() {
		return idpoz;
	}

	public void setIdpoz(int idpoz) {
		this.idpoz = idpoz;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datumr == null) ? 0 : datumr.hashCode());
		long temp;
		temp = Double.doubleToLongBits(dodatak);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + idpoz;
		result = prime * result + ((imeg == null) ? 0 : imeg.hashCode());
		result = prime * result + mbg;
		temp = Double.doubleToLongBits(plata);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (status ? 1231 : 1237);
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
		Glumac other = (Glumac) obj;
		if (datumr == null) {
			if (other.datumr != null)
				return false;
		} else if (!datumr.equals(other.datumr))
			return false;
		if (Double.doubleToLongBits(dodatak) != Double.doubleToLongBits(other.dodatak))
			return false;
		if (idpoz != other.idpoz)
			return false;
		if (imeg == null) {
			if (other.imeg != null)
				return false;
		} else if (!imeg.equals(other.imeg))
			return false;
		if (mbg != other.mbg)
			return false;
		if (Double.doubleToLongBits(plata) != Double.doubleToLongBits(other.plata))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Glumac [mbg=" + mbg + ", imeg=" + imeg + ", datumr=" + datumr + ", status=" + status + ", plata="
				+ plata + ", dodatak=" + dodatak + ", idpoz=" + idpoz + "]";
	}

}
