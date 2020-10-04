package rs.ac.uns.ftn.db.jdbc.pozoriste.dto;

public class PrikazivanjeDTO {
	
	int ukupan_broj_gledalaca;
	float prosecan_broj_gledalaca;
	int ukupan_broj_prikazivanja;
	
	
	
	public PrikazivanjeDTO( int ukupan_broj_gledalaca, float prosecan_broj_gledalaca, int ukupan_broj_prikazivanja) {
		
		
		this.ukupan_broj_gledalaca = ukupan_broj_gledalaca;
		this.prosecan_broj_gledalaca = prosecan_broj_gledalaca;
		this.ukupan_broj_prikazivanja = ukupan_broj_prikazivanja;
		
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



	public int getUkupan_broj_prikazivanja() {
		return ukupan_broj_prikazivanja;
	}



	public void setUkupan_broj_prikazivanja(int ukupan_broj_prikazivanja) {
		this.ukupan_broj_prikazivanja = ukupan_broj_prikazivanja;
	}






	@Override
	public String toString() {
		return String.format("%-30d %-30.2f %-30d" , ukupan_broj_gledalaca, prosecan_broj_gledalaca, ukupan_broj_prikazivanja);
	}
	
	
	
	

}
