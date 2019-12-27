package rs.ac.uns.ftn.db.jdbc.pozoriste.dto;

public class PrikazivanjeDTO {
	int ukupan_broj_gledalaca;
	float prosecan_broj_gledalaca;
	int broj_prikazivanja;

	public PrikazivanjeDTO(int ukupan_broj_gledalaca, float prosecan_broj_gledalaca, int broj_prikazivanja) {
		super();
		this.ukupan_broj_gledalaca = ukupan_broj_gledalaca;
		this.prosecan_broj_gledalaca = prosecan_broj_gledalaca;
		this.broj_prikazivanja = broj_prikazivanja;
	}

	public int getUkupan_broj_gledalaca() {
		return ukupan_broj_gledalaca;
	}

	public void setUkupan_broj_gledalaca(int ukupan_broj_gledalaca) {
		this.ukupan_broj_gledalaca = ukupan_broj_gledalaca;
	}

	public float getProsecan_broj_gledalaca() {
		return prosecan_broj_gledalaca;
	}

	public void setProsecan_broj_gledalaca(float prosecan_broj_gledalaca) {
		this.prosecan_broj_gledalaca = prosecan_broj_gledalaca;
	}

	public int getBroj_prikazivanja() {
		return broj_prikazivanja;
	}

	public void setBroj_prikazivanja(int broj_prikazivanja) {
		this.broj_prikazivanja = broj_prikazivanja;
	}

	@Override
	public String toString() {
		return "\t\t\t\t" + ukupan_broj_gledalaca + "\t\t\t"
				+ prosecan_broj_gledalaca + "\t\t\t" + broj_prikazivanja + "\t\t\n";
	}

}
