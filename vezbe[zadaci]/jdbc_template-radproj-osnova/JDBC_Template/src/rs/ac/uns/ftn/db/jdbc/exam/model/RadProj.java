package rs.ac.uns.ftn.db.jdbc.exam.model;

public class RadProj {
	private int mbr;
	private int spr;
	private int brc;

	public RadProj() {
		super();
	}

	public RadProj(int mbr, int spr, int brc) {
		super();
		this.mbr = mbr;
		this.spr = spr;
		this.brc = brc;
	}

	public int getMbr() {
		return mbr;
	}

	public void setMbr(int mbr) {
		this.mbr = mbr;
	}

	public int getSpr() {
		return spr;
	}

	public void setSpr(int spr) {
		this.spr = spr;
	}

	public int getBrc() {
		return brc;
	}

	public void setBrc(int brc) {
		this.brc = brc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + brc;
		result = prime * result + mbr;
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
		RadProj other = (RadProj) obj;
		if (brc != other.brc)
			return false;
		if (mbr != other.mbr)
			return false;
		if (spr != other.spr)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-4d %-4d %-4d", spr, mbr, brc);
	}

	public static String getFormattedHeader() {
		return String.format("%-4s %-4s %-4s", "SPR", "MBR", "BRC");
	}

}
