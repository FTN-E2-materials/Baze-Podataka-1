package rs.ac.uns.ftn.db.jdbc.pozoriste.dao;

import java.sql.SQLException;
/*
 * Genericki interfejs koji treba da se implementira za odredjeni model
 * Svaki model ima svoj reprezentativan DAO u koji ce smestati podatke
 * 
 * Za kolokvijum je potrebno znati implementirati ( svaku metodu Overide-ovati ) DAO
 * za jedan model u potpunosti
 */


public interface CRUDDao<T, ID> {

	int count() throws SQLException;

	void delete(T entity) throws SQLException;

	void deleteAll() throws SQLException;

	void deleteById(ID id) throws SQLException;

	boolean existsById(ID id) throws SQLException;

	Iterable<T> findAll() throws SQLException;

	Iterable<T> findAllById(Iterable<ID> ids) throws SQLException;

	T findById(ID id) throws SQLException;

	void save(T entity) throws SQLException;

	void saveAll(Iterable<T> entities) throws SQLException;
}
