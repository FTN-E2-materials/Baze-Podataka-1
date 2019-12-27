package rs.ac.uns.ftn.db.jdbc.pozoriste.dao;

import java.sql.SQLException;

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
