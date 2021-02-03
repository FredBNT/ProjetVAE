package fr.eni.vae.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

import fr.eni.vae.bo.Enchere;
import fr.eni.vae.bo.Retrait;
import fr.eni.vae.bo.Utilisateur;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.ConnectionProvider;

import java.util.ArrayList;

public class EnchereDAOJdbcImpl implements EnchereDAO {

	private final String RECHERCHER_UN_NUMUSER = "select no_utilisateur from ENCHERES where no_article = ? ORDER BY montant_Enchere DESC;";
	private final String INSERERENCHERE = "insert into ENCHERES(date_enchere, no_utilisateur, no_article, montant_Enchere) values (?,?,?,?);";
	private final String VERIFICATION_USER_ENCHERIR = "select * FROM ENCHERES WHERE no_utilisateur = ? AND no_article = ? ;";
	private final String RECHERCHE_NOVENTE_PAR_NOUSER = "select no_article FROM ENCHERES WHERE no_utilisateur = ? ;";
	private final String RECHERCHELISTEENCHEREPARNOVENTE = "select * FROM ENCHERES WHERE no_article = ? ;";
	private final String MODIFIER = "update ENCHERES set date_enchere = ?, montant_Enchere = ? WHERE no_utilisateur= ? AND no_article = ?;";
	private final String SUPPRESSION_ENCHERE = "delete FROM ENCHERES WHERE no_utilisateur= ? AND no_article = ?;";
	private final String SUPPRESSION_ENCHERE_COMPLETE = "delete FROM ENCHERES WHERE no_article = ?;";
	private final String RECHERCHER_MEILLEURE_VENTE = "select * from ENCHERES where no_article = ? ORDER BY montant_Enchere DESC";

	public Enchere rechercheUnUserEncheri(int numVente) throws DALException {
		// Méthode recherchant un numUtil d'une personne qui encheri

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Enchere enchere = new Enchere();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(RECHERCHER_UN_NUMUSER);
			pstmt.setInt(1, numVente);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				enchere.setNoUtilisateur(rs.getInt("no_utilisateur"));
			}
		} catch (SQLException e) {
			throw new DALException(
					"Probleme - Impossible de prendre un n° utilisateur ( EnchereDAO ) - " + e.getMessage());
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return enchere;

	}

	// méthode pour créer une enchère

	public void ajouterEnchere(String date, Enchere enchere) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(INSERERENCHERE);
			pstmt.setString(1, date);
			pstmt.setInt(2, enchere.getNoUtilisateur());
			pstmt.setInt(3, enchere.getNoVente());
			pstmt.setInt(4, enchere.getMontantEnchere());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DALException("Probleme - ajouter une enchère - " + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - fermerConnexion - " + e.getMessage());
			}
		}
	}

	public Enchere verificationUserEncherir(int numVente, int numUser) throws DALException {
		// Permet de vérifier si l'utilisateur à enchéri au moins une fois

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Enchere verificationEnchere = new Enchere();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(VERIFICATION_USER_ENCHERIR);
			pstmt.setInt(1, numUser);
			pstmt.setInt(2, numVente);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				verificationEnchere.setNoUtilisateur(numUser);
				verificationEnchere.setNoVente(numVente);
				verificationEnchere.setPrixEnchere(rs.getInt("montant_Enchere"));
			}

		} catch (SQLException e) {
			throw new DALException("Probleme - VerificationUserEncherir - " + e.getMessage());
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - fermerConnexion - " + e.getMessage());
			}
		}

		return verificationEnchere;
	}

	public ArrayList<Integer> quelleVenteEncherie(int no_user) throws DALException {
		// chercher les ventes sur lesquelles l'utilisateur a ench�ri
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int no = 0;
		ArrayList<Integer> list = new ArrayList<>();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(RECHERCHE_NOVENTE_PAR_NOUSER);

			pstmt.setInt(1, no_user);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getInt("no_article"));
			}

		} catch (SQLException e) {
			throw new DALException("Probleme - VerificationUserEncherir - " + e.getMessage());
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - fermerConnexion - " + e.getMessage());
			}
		}
		return list;
	}

	// Méthode pour obtenir la liste d'enchère à partir du num vente
	public ArrayList<Enchere> listeEncheres(int numVente) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Enchere> listeEnchere = new ArrayList<>();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(RECHERCHELISTEENCHEREPARNOVENTE);
			pstmt.setInt(1, numVente);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Enchere enchereZ = new Enchere();

				enchereZ.setNoUtilisateur(rs.getInt("no_utilisateur"));
				enchereZ.setNoVente(numVente);
				enchereZ.setMontantEnchere(rs.getInt("montant_Enchere"));
				listeEnchere.add(enchereZ);

			}
		} catch (SQLException e) {
			throw new DALException("Probleme - listeEnchere - " + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return listeEnchere;
	}

	public void modificationEnchere(String dateTime, int numUtil, int numVente, int proposEnchere) throws DALException {
		// Permet de modifier une enchère

		Connection cnx = null;
		PreparedStatement pstmt = null;

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(MODIFIER);
			pstmt.setString(1, dateTime);
			pstmt.setInt(2, proposEnchere);
			pstmt.setInt(3, numUtil);
			pstmt.setInt(4, numVente);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Probleme - modifierEnchere - " + e.getMessage());
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - fermerConnexion - " + e.getMessage());
			}
		}

	}

	public void suppressionEnchere(int numUtil, int numVente) throws DALException {
		// Suppression d'une enchère pour ensuite l'ajouter de nouveau avec les bonnes
		// valeurs
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(SUPPRESSION_ENCHERE);
			pstmt.setInt(1, numUtil);
			pstmt.setInt(2, numVente);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Probleme - suppressionEnchere - " + e.getMessage());
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - fermerConnexion - " + e.getMessage());
			}
		}

	}

	public void ajouterEnchereSuiteSuppression(String date, int numUtil, int numVente, int proposEnchere)
			throws DALException {
		// Création d'une enchère suite à la suppression dans la base

		Connection cnx = null;
		PreparedStatement pstmt = null;

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(INSERERENCHERE);
			pstmt.setString(1, date);
			pstmt.setInt(2, numUtil);
			pstmt.setInt(3, numVente);
			pstmt.setInt(4, proposEnchere);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DALException("Probleme - ajouter une enchère suite suppression - " + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - fermerConnexion suite suppression - " + e.getMessage());
			}
		}

	}

	public void suppressionEnchereComplete(int numVente) throws DALException {
		// Suppression totale de l'enchere avec tous les users.

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(SUPPRESSION_ENCHERE_COMPLETE);
			pstmt.setInt(1, numVente);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Probleme - suppressionEnchere - " + e.getMessage());
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - fermerConnexion - " + e.getMessage());
			}
		}

	}

	public Enchere rechercheMeilleureMontant(int numVente) throws DALException {
		// Recherche de la meilleure vente afin de MAJ prixVente de la table VENTE

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Enchere enchere = new Enchere();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(RECHERCHER_MEILLEURE_VENTE);
			pstmt.setInt(1, numVente);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				enchere.setNoUtilisateur(rs.getInt("no_Utilisateur"));
				enchere.setPrixEnchere(rs.getInt("montant_Enchere"));
				enchere.setNoVente(numVente);

			}
		} catch (SQLException e) {

			throw new DALException(
					"Probleme - Impossible de prendre un n° utilisateur ( EnchereDAO ) - " + e.getMessage());
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

			return enchere;

		}
	}

}