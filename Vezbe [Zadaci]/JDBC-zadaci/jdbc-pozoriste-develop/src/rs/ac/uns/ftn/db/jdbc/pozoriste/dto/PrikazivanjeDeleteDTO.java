package rs.ac.uns.ftn.db.jdbc.pozoriste.dto;

import java.sql.Date;

public class PrikazivanjeDeleteDTO {
	
	String nazivsce;
	int brojsed;
	int pozoriste_idpoz;
	int idsce;
	int rbr;
	Date datumpri;
	Date vremepri;
	int brojgled;
	int predstava_idpred;
	
	public PrikazivanjeDeleteDTO(){
		
	}
	
	public PrikazivanjeDeleteDTO(String nazivsce, int brojsed, int pozoriste_idpoz, int idsce, int rbr, Date datumpri,
			Date vremepri, int brojgled, int predstava_idpred) {
		this.nazivsce = nazivsce;
		this.brojsed = brojsed;
		this.pozoriste_idpoz = pozoriste_idpoz;
		this.idsce = idsce;
		this.rbr = rbr;
		this.datumpri = datumpri;
		this.vremepri = vremepri;
		this.brojgled = brojgled;
		this.predstava_idpred = predstava_idpred;
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
	public int getPozoriste_idpoz() {
		return pozoriste_idpoz;
	}
	public void setPozoriste_idpoz(int pozoriste_idpoz) {
		this.pozoriste_idpoz = pozoriste_idpoz;
	}
	public int getIdsce() {
		return idsce;
	}
	public void setIdsce(int idsce) {
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
	public int getPredstava_idpred() {
		return predstava_idpred;
	}
	public void setPredstava_idpred(int predstava_idpred) {
		this.predstava_idpred = predstava_idpred;
	}
	
	
	@Override
	public String toString() {
		return String.format("%-20s %-10d %-6d %-6d %-6d %-10s %-10s %-6d %-6d", nazivsce, brojsed, pozoriste_idpoz, idsce,rbr,datumpri.toString(),vremepri.toString(),brojgled,predstava_idpred);
	}

	public static String getFormattedHeader() {
		return String.format("%-20s %-10s %-6s %-6s %-6s %-10s %-10s %-6s %-6s", "NAZIVSCE", "BROJSED", "IDPOZ", "IDSCE","RBR","DATUMPRI","VREMEPRI","BROJGLED","IDPRED");
	}
}
