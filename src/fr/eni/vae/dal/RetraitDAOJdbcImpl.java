package fr.eni.vae.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import fr.eni.vae.bo.Retrait;
import fr.eni.vae.bo.Utilisateur;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.ConnectionProvider;;

public class RetraitDAOJdbcImpl implements RetraitDAO {

	private final static String INSERERRETRAIT = "insert into RETRAITS(no_article, rue, code_postal, ville) values (?,?,?,?);";
	private final static String RECHERCHERETRAITVENTE = "select * from RETRAITS where no_article = ? ;";
	private final static String SUPPRESSION_RETRAIT = "delete FROM RETRAITS WHERE no_article = ?;";

	public void ajouterRetrait(Retrait retrait) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		// liste dans la requ�te : no_vente ,rue, code_postal, , ville.
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(INSERERRETRAIT);
			pstmt.setInt(1, retrait.getNoVente());
			pstmt.setString(2, retrait.getRue());
			pstmt.setString(3, retrait.getCodePostal());
			pstmt.setString(4, retrait.getVille());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DALException("Probleme - ajouterRetrait - " + e.getMessage());
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

	// Afficher un retrait suivant le n° de vente
	public Retrait AfficherRetrait(int numVente) throws DALException, SQLException {

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Retrait retrait = new Retrait();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(RECHERCHERETRAITVENTE);
			pstmt.setInt(1, numVente);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				retrait.setNoVente(numVente);
				retrait.setRue(rs.getString("rue"));
				retrait.setCodePostal(rs.getString("code_postal"));
				retrait.setVille(rs.getString("ville"));
			}
		} catch (SQLException e) {

			throw new DALException(
					"Probleme - Impossible d'affichage d' un article ( RetraitDAO ) - " + e.getMessage());
		} finally {

			if (pstmt != null)
				pstmt.close();
			if (cnx != null)
				cnx.close();

			return retrait;
		}
	}

	public void supprimerUnRetrait(int numVente) throws DALException {
		// Suppression d'un retrait d'une vente

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(SUPPRESSION_RETRAIT);
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
}
