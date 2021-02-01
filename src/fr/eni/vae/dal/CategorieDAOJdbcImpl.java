package fr.eni.vae.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.vae.bo.Categorie;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.ConnectionProvider;

public class CategorieDAOJdbcImpl implements CategorieDAO {
	/**
	 * @author
	 */
	private final static String NUMCATEGORIE = "select * from CATEGORIES where no_categorie = ? ;";
	private final static String LIBCATEGORIE = "select * from CATEGORIES where libelle = ? ;";
	private static final String LISTCATEGORIE = "select * from CATEGORIES;";

	// m�thode chercher libell� cat�gorie � partir du num�ro
	public Categorie libelleCategorie(int noCate) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Categorie cate = new Categorie();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(NUMCATEGORIE);
			pstmt.setInt(1, noCate);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				cate.setLibelle(rs.getString("libelle"));
			}
		} catch (SQLException e) {
			throw new DALException("Probleme - m�thode libelleCat�gorie - " + e.getMessage());
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
		return cate;
	}

	// m�thode chercher num�ro cat�gorie � partir du libell�
	public Categorie numCategorie(String libel) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Categorie cate = new Categorie();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(LIBCATEGORIE);
			pstmt.setString(1, libel);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cate.setNumCate(rs.getInt("no_categorie"));
				cate.setLibelle(libel);
			}
		} catch (SQLException e) {
			throw new DALException("Probleme - m�thode libelleCat�gorie - " + e.getMessage());
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
		return cate;
	}

	@Override
	public List<Categorie> recupCategorie() throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Categorie> listeCategorie = new ArrayList<>();
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(LISTCATEGORIE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Categorie categorieU = new Categorie();
				categorieU.setNumCate(rs.getInt("no_categorie"));
				categorieU.setLibelle(rs.getString("libelle"));
				listeCategorie.add(categorieU);
			}
		} catch (SQLException e) {
			throw new DALException("Probleme - ventesUtilisateur - " + e.getMessage());
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
		return listeCategorie;
	}
}
