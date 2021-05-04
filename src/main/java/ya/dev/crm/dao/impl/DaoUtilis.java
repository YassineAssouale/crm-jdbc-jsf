package ya.dev.crm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoUtilis {
	
	public static PreparedStatement initializePreparedStatement(
			Connection con, 
			String sql, 
			boolean returnGeneratedKey, 
			Object...objects) throws SQLException {
		
		PreparedStatement ps = con.prepareStatement(sql, returnGeneratedKey ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; objects != null && i < objects.length ; i++) {
				ps.setObject(i+1, objects[i]);
		}
		return ps;
	}
	public static void close(ResultSet rs) {
		if(null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("ResultSet closing failed!");
			}
		}
	}
	public static void close(Statement statement) {
		if (null != statement) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Echec de la fermeture du Statement : " + e.getMessage());
			}
		}
	}

	/**
	 * Fermeture de la connexion
	 */
	public static void close(Connection connexion) {
		if (null != connexion) {
			try {
				connexion.close();
			} catch (SQLException e) {
				System.out.println("Echec de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}

	/**
	 * Fermeture du statement et de la connexion
	 */
	public static void close(Statement statement, Connection connexion) {
		close(statement);
		close(connexion);
	}

	/**
	 * Fermeture du ResultSet, du Statement et de la connexion
	 */
	public static void close(ResultSet resultSet, Statement statement, Connection connexion) {
		close(resultSet);
		close(statement);
		close(connexion);
	}

}
