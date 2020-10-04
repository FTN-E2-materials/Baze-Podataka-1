package rs.ac.uns.ftn.db.jdbc.pozoriste.model;

import java.util.Date;

public class Podela {
	private double honorar;
	private Date datumd;
	private Date datump;
	private String imeulo;
	private int idpred;
	private int mbg;

	public Podela() {
	}

	public Podela(double honorar, Date datumd, Date datump, String imeulo, int idpred, int mbg) {
		this.honorar = honorar;
		this.datumd = datumd;
		this.datump = datump;
		this.imeulo = imeulo;
		this.idpred = idpred;
		this.mbg = mbg;
	}

	public double getHonorar() {
		return honorar;
	}

	public void setHonorar(double honorar) {
		this.honorar = honorar;
	}

	public Date getDatumd() {
		return datumd;
	}

	public void setDatumd(Date datumd) {
		this.datumd = datumd;
	}

	public Date getDatump() {
		return datump;
	}

	public void setDatump(Date datump) {
		this.datump = datump;
	}

	public String getImeulo() {
		return imeulo;
	}

	public void setImeulo(String imeulo) {
		this.imeulo = imeulo;
	}

	public int getIdpred() {
		return idpred;
	}

	public void setIdpred(int idpred) {
		this.idpred = idpred;
	}

	public int getMbg() {
		return mbg;
	}

	public void setMbg(int mbg) {
		this.mbg = mbg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datumd == null) ? 0 : datumd.hashCode());
		result = prime * result + ((datump == null) ? 0 : datump.hashCode());
		long temp;
		temp = Double.doubleToLongBits(honorar);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + idpred;
		result = prime * result + ((imeulo == null) ? 0 : imeulo.hashCode());
		result = prime * result + mbg;
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
		Podela other = (Podela) obj;
		if (datumd == null) {
			if (other.datumd != null)
				return false;
		} else if (!datumd.equals(other.datumd))
			return false;
		if (datump == null) {
			if (other.datump != null)
				return false;
		} else if (!datump.equals(other.datump))
			return false;
		if (Double.doubleToLongBits(honorar) != Double.doubleToLongBits(other.honorar))
			return false;
		if (idpred != other.idpred)
			return false;
		if (imeulo == null) {
			if (other.imeulo != null)
				return false;
		} else if (!imeulo.equals(other.imeulo))
			return false;
		if (mbg != other.mbg)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Podela [honorar=" + honorar + ", datumd=" + datumd + ", datump=" + datump + ", imeulo=" + imeulo
				+ ", idpred=" + idpred + ", mbg=" + mbg + "]";
	}

}
