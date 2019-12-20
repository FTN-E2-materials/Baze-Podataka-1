package rs.ac.uns.ftn.db.jdbc.exam.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Radnik {
	private int mbr;
	private String ime;
	private String prz;
	private int sef;
	private double plt;
	private double pre;
	private Date god;

	public Radnik() {
		super();
	}

	public Radnik(int mbr, String ime, String prz, int sef, double plt, double pre, Date god) {
		super();
		this.mbr = mbr;
		this.ime = ime;
		this.prz = prz;
		this.sef = sef;
		this.plt = plt;
		this.pre = pre;
		this.god = god;
	}

	public int getMbr() {
		return mbr;
	}

	public void setMbr(int mbr) {
		this.mbr = mbr;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrz() {
		return prz;
	}

	public void setPrz(String prz) {
		this.prz = prz;
	}

	public int getSef() {
		return sef;
	}

	public void setSef(int sef) {
		this.sef = sef;
	}

	public double getPlt() {
		return plt;
	}

	public void setPlt(double plt) {
		this.plt = plt;
	}

	public double getPre() {
		return pre;
	}

	public void setPre(double pre) {
		this.pre = pre;
	}

	public Date getGod() {
		return god;
	}

	public void setGod(Date god) {
		this.god = god;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((god == null) ? 0 : god.hashCode());
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result + mbr;
		long temp;
		temp = Double.doubleToLongBits(plt);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pre);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((prz == null) ? 0 : prz.hashCode());
		result = prime * result + sef;
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
		Radnik other = (Radnik) obj;
		if (god == null) {
			if (other.god != null)
				return false;
		} else if (!god.equals(other.god))
			return false;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (mbr != other.mbr)
			return false;
		if (Double.doubleToLongBits(plt) != Double.doubleToLongBits(other.plt))
			return false;
		if (Double.doubleToLongBits(pre) != Double.doubleToLongBits(other.pre))
			return false;
		if (prz == null) {
			if (other.prz != null)
				return false;
		} else if (!prz.equals(other.prz))
			return false;
		if (sef != other.sef)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		return String.format("%-4d %-20s %-20s %-4d %-12.2f %-9.2f %-10s", mbr, ime, prz, sef, plt, pre, simpleDateFormat.format(god));
	}

	public static String getFormattedHeader() {
		return String.format("%-4s %-20s %-20s %-4s %-12s %-9s %-10s", "MBR", "IME", "PRZ", "SEF", "PLT", "PRE", "GOD");
	}

}
