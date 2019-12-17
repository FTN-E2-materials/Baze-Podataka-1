package rs.ac.uns.ftn.db.jdbc.callable_statement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example01_ExecuteFunction {

	public static void main(String[] args) {

		// spr and brc values for which we are counting workers
		int spr = 30;
		int brc = 5;

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				CallableStatement callableStatement = connection.prepareCall("{? = call F_SEL_RadprojCnt(?, ?) }")) {
			callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStatement.setLong(2, spr);
			callableStatement.setLong(3, brc);

			callableStatement.execute();

			int result = (Integer) callableStatement.getObject(1);
			System.out.printf("%d", result);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
