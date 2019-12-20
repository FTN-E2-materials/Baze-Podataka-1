package rs.ac.uns.ftn.db.jdbc.exam.model;

public class Projekat {
	private int spr;
	private int ruk;
	private String nap;
	private String nar;

	public Projekat() {
		super();
	}

	public Projekat(int spr, int ruk, String nap, String nar) {
		super();
		this.spr = spr;
		this.ruk = ruk;
		this.nap = nap;
		this.nar = nar;
	}

	public int getSpr() {
		return spr;
	}

	public void setSpr(int spr) {
		this.spr = spr;
	}

	public int getRuk() {
		return ruk;
	}

	public void setRuk(int ruk) {
		this.ruk = ruk;
	}

	public String getNap() {
		return nap;
	}

	public void setNap(String nap) {
		this.nap = nap;
	}

	public String getNar() {
		return nar;
	}

	public void setNar(String nar) {
		this.nar = nar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nap == null) ? 0 : nap.hashCode());
		result = prime * result + ((nar == null) ? 0 : nar.hashCode());
		result = prime * result + ruk;
		result = prime * result + spr;
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
		Projekat other = (Projekat) obj;
		if (nap == null) {
			if (other.nap != null)
				return false;
		} else if (!nap.equals(other.nap))
			return false;
		if (nar == null) {
			if (other.nar != null)
				return false;
		} else if (!nar.equals(other.nar))
			return false;
		if (ruk != other.ruk)
			return false;
		if (spr != other.spr)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-4d %-4d %-30s %-30s", spr, ruk, nap, nar);
	}

	public static String getFormattedHeader() {
		return String.format("%-4s %-4s %-30s %-30s", "SPR", "RUK", "NAP", "NAR");
	}

}
