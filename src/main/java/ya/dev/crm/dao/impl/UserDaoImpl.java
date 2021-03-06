package ya.dev.crm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ya.dev.crm.dao.UserDao;
import ya.dev.crm.exception.DaoException;
import ya.dev.crm.model.User;

public class UserDaoImpl implements UserDao {

	private static final String REQ_SEL_USERS = "SELECT id, username, password, mail FROM users;";
	private static final String REQ_SEL_USER_BY_ID = "SELECT id, username, password, mail FROM users WHERE id = ?;";
	private static final String REQ_SEL_USER_BY_USERNAME = "SELECT id, username, password, mail FROM users WHERE username LIKE ?;";
	private static final String REQ_CREATE_USER = "INSERT INTO users(username, password, mail)	VALUES (?, ?, ?);";
	private static final String REQ_UPDATE_USER = "UPDATE public.users SET username=?, password=?, mail=? WHERE ?;";
	private static final String REQ_DEL_USER = "DELETE FROM users WHERE id = ?;";

	private DaoFactory daoFactory;

	UserDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public List<User> getAll() throws DaoException {

		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<User> users = new ArrayList<>();

		try {
			connexion = daoFactory.getConnexion();
			ps = DaoUtilis.initializePreparedStatement(connexion, REQ_SEL_USERS, false, null);
			rs = ps.executeQuery();

			while (rs.next()) {
				users.add(map(rs));
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtilis.close(rs, ps, connexion);
		}
		return users;
	}

	@Override
	public User getById(Integer id) throws DaoException {
		return find(REQ_SEL_USER_BY_ID, id);
	}

	@Override
	public User getByUsername(String username) throws DaoException {
		return find(REQ_SEL_USER_BY_USERNAME, username);
	}

	@Override
	public void createUser(User user) throws DaoException {
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connexion = daoFactory.getConnexion();
			ps = DaoUtilis.initializePreparedStatement(connexion, REQ_CREATE_USER, true, user.getUsername(),
					user.getPassword(), user.getEmail());
			int statut = ps.executeUpdate();

			if (statut == 0) {
				throw new DaoException("Echec de la cr??ation de l'utiilisateur, aucune ligne ajout??e dans la table");
			}

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				user.setId(rs.getInt(1));
			} else {
				throw new DaoException(
						"Echec de la cr??ation de l'utiilisateur en base, aucun ID auto-g??n??r?? retourn??.");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtilis.close(ps, connexion);
		}
	}

	@Override
	public void udpateUser(User user) throws DaoException {
		Connection connexion = null;
		PreparedStatement ps = null;

		try {
			connexion = daoFactory.getConnexion();
			ps = DaoUtilis.initializePreparedStatement(connexion, REQ_UPDATE_USER, false, user.getUsername(),
					user.getPassword(), user.getEmail(), user.getId());
			int statut = ps.executeUpdate();

			if (statut == 0) {
				throw new DaoException(
						"Echec de la modification de l'utilisateur, aucune ligne modifi??e dans la table");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtilis.close(ps, connexion);
		}
	}

	@Override
	public void deleteUser(User user) throws DaoException {
		Connection connexion = null;
		PreparedStatement ps = null;

		try {
			connexion = daoFactory.getConnexion();
			ps = DaoUtilis.initializePreparedStatement(connexion, REQ_DEL_USER, false, user.getId());
			int statut = ps.executeUpdate();

			if (statut == 0) {
				throw new DaoException("Echec de la suppression de l'utilsateur, aucune ligne supprim??e dans la table");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtilis.close(ps, connexion);
		}
	}

	private static User map(ResultSet rs) throws SQLException {
		User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("mail"));
		return user;
	}

	private User find(String requete, Object... objects) {
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		User user = new User();

		try {
			connexion = daoFactory.getConnexion();
			ps = DaoUtilis.initializePreparedStatement(connexion, requete, false, objects);
			rs = ps.executeQuery();

			while (rs.next()) {
				user = map(rs);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtilis.close(rs, ps, connexion);
		}
		return user;
	}
}
