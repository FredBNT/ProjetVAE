package fr.eni.vae.dal;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import fr.eni.vae.bo.Categorie;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.ConnectionProvider;
import fr.eni.vae.dal.DALException;;

public class VenteDAOJdbcImpl implements VenteDAO {

	private final String LISTER = "select * from ARTICLES_VENDUS;";
	private final String LISTACQUISITION = "select * from ARTICLES_VENDUS where  no_article = ? AND date_fin_encheres <= GETDATE();";
	private final String UNEVENTE = "select * from ARTICLES_VENDUS where no_article = ? ;";
	private final String UNEENCHERE = "select * from ARTICLES_VENDUS where no_article = ? and date_fin_encheres > GETDATE();";
	private final String DATEVENTE = "select date_fin_encheres from ARTICLES_VENDUS where no_article = ? ;";
	private final String NUMVENDEUR = "select no_utilisateur from ARTICLES_VENDUS where no_article = ? ;";
	private final String LISTVENTESUTILISATEUR = "select * from ARTICLES_VENDUS where no_utilisateur = ? ;";
	private final String LISTVENTESCATEGORIE = "select * from ARTICLES_VENDUS where no_categorie = ? ;";
	private final String LISTVENTESARTICLE = "select * from ARTICLES_VENDUS where nom_article LIKE ? ;";
	private final String INSERER = "insert into ARTICLES_VENDUS(nom_article, description, date_fin_encheres, prix_initial, prix_vente ,no_utilisateur, no_categorie, visibilite_publication, photo) values (?,?,?,?,?,?,?,?,?);";
	private final String SUPPRIMER = "delete from ARTICLES_VENDUS where no_utilisateur = ? and no_article = ? ;";
	private final String RECHPARUTETNOM = "select * from ARTICLES_VENDUS where no_utilisateur = ? and nom_article LIKE ?";
	private final String RECHPARUTETCATE = "select * from ARTICLES_VENDUS where no_utilisateur = ? and no_categorie =?";
	private final String RECHPARUTILCATETNOM = "select * from ARTICLES_VENDUS where no_utilisateur = ? and no_categorie = ? and nom_article LIKE ?";
	private final String RECHPARNOVTECATNOM = "select * from ARTICLES_VENDUS where no_article = ? and no_categorie = ? and nom_article LIKE ? and date_fin_encheres > GETDATE()";
	private final String MODIFIERPRIX = "update ARTICLES_VENDUS set prix_vente = ? WHERE no_article= ?;";
	private final String FINIRVENTE = "update ARTICLES_VENDUS set fin_enchere = ? WHERE no_article= ?;";
	private final String PUBLIER = "update ARTICLES_VENDUS set visibilite_publication = 1 WHERE no_article=?;";

	// M�thode listeToutesVENTES
	public ArrayList<Vente> listeVentes() throws DALException {
		Connection cnx = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Vente> listeV = new ArrayList<>();

		try {
			cnx = ConnectionProvider.getConnection();
			stmt = cnx.createStatement();
			rs = stmt.executeQuery(LISTER);

			while (rs.next()) {
				Vente venteU = new Vente();
				venteU.setNumVente(rs.getInt("no_article"));
				venteU.setNomArticle(rs.getString("nom_article"));
				venteU.setDescription(rs.getString("description"));
				venteU.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteU.setPrixInitial(rs.getInt("prix_initial"));
				venteU.setPrixvente(rs.getInt("prix_vente"));
				venteU.setNumUtil(rs.getInt("no_utilisateur"));
				venteU.setNumCate(rs.getInt("no_categorie"));
				venteU.setPhoto(rs.getString("photo"));
				listeV.add(venteU);
			}
		} catch (SQLException e) {
			throw new DALException("Probleme - liste VENTES - " + e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return listeV;
	}

	public ArrayList<Vente> ventesUtilisateur(int no_utilisateur) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Vente> listeVentes = new ArrayList<>();
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(LISTVENTESUTILISATEUR);
			pstmt.setInt(1, no_utilisateur);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Vente venteU = new Vente();
				venteU.setNumVente(rs.getInt("no_article"));
				venteU.setNomArticle(rs.getString("nom_article"));
				venteU.setDescription(rs.getString("description"));
				venteU.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteU.setPrixInitial(rs.getInt("prix_initial"));
				venteU.setPrixvente(rs.getInt("prix_vente"));
				venteU.setNumCate(rs.getInt("no_categorie"));
				venteU.setNumUtil(rs.getInt("no_utilisateur"));
				venteU.setPhoto(rs.getString("photo"));
				listeVentes.add(venteU);
			}
		} catch (SQLException e) {
			throw new DALException("Probleme - VentesUtilisateur - " + e.getMessage());
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
		return listeVentes;
	}

	// methode listeVenteCategorie prealable affichage Ventes par categorie
	public ArrayList<Vente> ventesCategorie(int no_categorie) throws DALException {

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Vente> listeVentes = new ArrayList<>();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(LISTVENTESCATEGORIE);
			pstmt.setInt(1, no_categorie);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Vente venteU = new Vente();
				venteU.setNumVente(rs.getInt("no_article"));
				venteU.setNomArticle(rs.getString("nom_article"));
				venteU.setDescription(rs.getString("description"));
				venteU.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteU.setPrixInitial(rs.getInt("prix_initial"));
				venteU.setPrixvente(rs.getInt("prix_vente"));
				venteU.setNumCate(rs.getInt("no_categorie"));
				venteU.setNumUtil(rs.getInt("no_utilisateur"));
				venteU.setPhoto(rs.getString("photo"));
				listeVentes.add(venteU);
			}
		} catch (SQLException e) {
			throw new DALException("Probleme - VentesCategorie - " + e.getMessage());
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
		return listeVentes;
	}

	// m�thode listeVenteArticle pr�alable � l'affichage des Ventes en fonction du
	// nom de l'article
	public ArrayList<Vente> listeVenteArticle(String nom_article) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Vente> listeVentes = new ArrayList<>();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(LISTVENTESARTICLE);
			pstmt.setString(1, "%" + nom_article + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Vente venteA = new Vente();
				venteA.setNumVente(rs.getInt("no_article"));
				venteA.setNumUtil(rs.getInt("no_utilisateur"));
				venteA.setNomArticle(rs.getString("nom_article"));
				venteA.setDescription(rs.getString("description"));
				venteA.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteA.setPrixInitial(rs.getInt("prix_initial"));
				venteA.setPrixvente(rs.getInt("prix_vente"));
				venteA.setNumCate(rs.getInt("no_categorie"));
				venteA.setPhoto(rs.getString("photo"));
				listeVentes.add(venteA);

			}
		} catch (SQLException e) {
			throw new DALException("Probleme - listeVentesArticle - " + e.getMessage());
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
		return listeVentes;
	}

	// methode listeVenteUtNom pr�alable affichage Ventes par utilisateur et nom
	// article
	public ArrayList<Vente> listeUtNom(String nom_article, int no_utilisateur) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Vente> listeVentes = new ArrayList<>();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(RECHPARUTETNOM);
			pstmt.setInt(1, no_utilisateur);
			pstmt.setString(2, "%" + nom_article + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Vente venteU = new Vente();
				venteU.setNumVente(rs.getInt("no_article"));
				venteU.setNomArticle(nom_article);
				venteU.setDescription(rs.getString("description"));
				venteU.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteU.setPrixInitial(rs.getInt("prix_initial"));
				venteU.setPrixvente(rs.getInt("prix_vente"));
				venteU.setNumCate(rs.getInt("no_categorie"));
				venteU.setNumUtil(no_utilisateur);
				venteU.setPhoto(rs.getString("photo"));
				listeVentes.add(venteU);
			}
		} catch (SQLException e) {
			throw new DALException("Probleme - listeUtNom - " + e.getMessage());
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
		return listeVentes;
	}

	// methode listeVenteUtCat pr�alable affichage Ventes par utilisateur et
	// categorie article
	public ArrayList<Vente> listeUtCat(int no_cate, int no_utilisateur) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Vente> listeVentes = new ArrayList<>();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(RECHPARUTETCATE);
			pstmt.setInt(1, no_utilisateur);
			pstmt.setInt(2, no_cate);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Vente venteU = new Vente();
				venteU.setNumVente(rs.getInt("no_article"));
				venteU.setNomArticle(rs.getString("nom_article"));
				venteU.setDescription(rs.getString("description"));
				venteU.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteU.setPrixInitial(rs.getInt("prix_initial"));
				venteU.setPrixvente(rs.getInt("prix_vente"));
				venteU.setNumCate(rs.getInt("no_categorie"));
				venteU.setPhoto(rs.getString("photo"));
				venteU.setNumUtil(no_utilisateur);
				listeVentes.add(venteU);
			}
		} catch (SQLException e) {
			throw new DALException("Probleme - listeUtNom - " + e.getMessage());
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
		return listeVentes;
	}

	// methode listeVenteUtNom pr�alable affichage Ventes par utilisateur cat�gorie
	// et nom article
	public ArrayList<Vente> listeUtNomCat(String nom_article, int no_utilisateur, int no_cate) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Vente> listeVentes = new ArrayList<>();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(RECHPARUTILCATETNOM);
			pstmt.setInt(1, no_utilisateur);
			pstmt.setInt(2, no_cate);
			pstmt.setString(3, "%" + nom_article + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Vente venteU = new Vente();
				venteU.setNumVente(rs.getInt("no_article"));
				venteU.setNomArticle(nom_article);
				venteU.setDescription(rs.getString("description"));
				venteU.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteU.setPrixInitial(rs.getInt("prix_initial"));
				venteU.setPrixvente(rs.getInt("prix_vente"));
				venteU.setNumCate(no_cate);
				venteU.setNumUtil(rs.getInt("no_utilisateur"));
				venteU.setPhoto(rs.getString("photo"));
				listeVentes.add(venteU);

			}
		} catch (SQLException e) {
			throw new DALException("Probleme - listeUtNomCat - " + e.getMessage());
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
		return listeVentes;
	}

	// Méthode insérer, créer une nouvelle vente
	public void ajouter(Vente nVente) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		// liste dans la requête : nom_article, description, date_fin_encheres,
		// prix_initial, prix_vente ,no_utilisateur, no_categorie
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(INSERER, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, nVente.getNomArticle());
			pstmt.setString(2, nVente.getDescription());
			pstmt.setDate(3, Date.valueOf(nVente.getDateFinEnchere()));
			pstmt.setInt(4, nVente.getPrixInitial());
			pstmt.setInt(5, nVente.getPrixvente());
			pstmt.setInt(6, nVente.getNumUtil());
			pstmt.setInt(7, nVente.getNumCate());
			if (nVente.isPublier()) {
				pstmt.setInt(8, 1);
			} else {
				pstmt.setInt(8, 0);
			}
			pstmt.setString(9, nVente.getPhoto());
			pstmt.executeUpdate();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					nVente.setNumVente(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Impossible de creer la vente, aucun ID obtenu.");
				}
			}
		} catch (SQLException e) {
			throw new DALException("Probleme - ajouterVente - " + e.getMessage());
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

	//
	//
	// m�thode supprimer une vente

	public int supprimerVente(int noVente, int noUtil) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		int nblig = 0;
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(SUPPRIMER);
			pstmt.setInt(1, noUtil);
			pstmt.setInt(2, noVente);

			nblig = pstmt.executeUpdate();
			return nblig;
		} catch (SQLException e) {
			throw new DALException("Probleme - supprimer Vente - " + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("DAL - fermerConnexion - " + e.getMessage());
			}
		}
	}

	public Vente AfficherArticle(int numVente) throws DALException {
		// Recherche d'un article avec le numVente

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vente venteA = new Vente();
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(UNEVENTE);
			pstmt.setInt(1, numVente);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				venteA.setNumVente(numVente);
				venteA.setNomArticle(rs.getString("nom_article"));
				venteA.setDescription(rs.getString("description"));
				venteA.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteA.setPrixInitial(rs.getInt("prix_initial"));
				venteA.setPrixvente(rs.getInt("prix_vente"));
				venteA.setNumCate(rs.getInt("no_categorie"));
				venteA.setNumUtil(Integer.parseInt(rs.getString("no_utilisateur")));
				venteA.setPhoto(rs.getString("photo"));
			}

		} catch (SQLException e) {
			throw new DALException("Probleme - Impossible d'affichage d' un article ( VenteDAO ) - " + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("DAL - fermerConnexion - " + e.getMessage());
			}

			return venteA;

		}
	}

	public Vente AfficherEnchereEnCours(int numVente) throws DALException {
		// Recherche d'un article avec le numVente

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vente venteA = new Vente();
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(UNEENCHERE);
			pstmt.setInt(1, numVente);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				venteA.setNumVente(numVente);
				venteA.setNomArticle(rs.getString("nom_article"));
				venteA.setDescription(rs.getString("description"));
				venteA.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteA.setPrixInitial(rs.getInt("prix_initial"));
				venteA.setPrixvente(rs.getInt("prix_vente"));
				venteA.setNumCate(rs.getInt("no_categorie"));
				venteA.setNumUtil(Integer.parseInt(rs.getString("no_utilisateur")));
				venteA.setPhoto(rs.getString("photo"));
			}

		} catch (SQLException e) {
			throw new DALException("Probleme - Impossible d'affichage d' une enchere ( VenteDAO ) - " + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("DAL - fermerConnexion - " + e.getMessage());
			}

			return venteA;

		}
	}

	public void initPrixVente(int proposEnchere, int numVente) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(MODIFIERPRIX);
			pstmt.setInt(1, proposEnchere);
			pstmt.setInt(2, numVente);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DALException("Probleme - modifierPrix - " + e.getMessage());
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

	public Vente obtenirDateFinEnchere(int numVente) throws DALException {
		Vente laVente = new Vente();
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(DATEVENTE);
			pstmt.setInt(1, numVente);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				laVente.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));

			}
		} catch (SQLException e) {
			throw new DALException("Probleme - obtenirLa DateFinEnchere - " + e.getMessage());
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

		return laVente;

	}

	public int obtenirLeNumVendeur(int numVente) throws DALException {
		int numVendeur = -1;
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(NUMVENDEUR);
			pstmt.setInt(1, numVente);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				numVendeur = rs.getInt("no_utilisateur");

			}
		} catch (SQLException e) {
			throw new DALException("Probleme - obtenir leNumUtilDuVendeur - " + e.getMessage());
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

		return numVendeur;

	}

	public Vente mesEncheresParNomCat(int noVente, int noCat, String nom) throws DALException {

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vente venteA = new Vente();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(RECHPARNOVTECATNOM);
			pstmt.setInt(1, noVente);
			pstmt.setInt(2, noCat);
			pstmt.setString(3, "%" + nom + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {

				venteA.setNumVente(noVente);
				venteA.setNumUtil(rs.getInt("no_utilisateur"));
				venteA.setNomArticle(rs.getString("nom_article"));
				venteA.setDescription(rs.getString("description"));
				venteA.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteA.setPrixInitial(rs.getInt("prix_initial"));
				venteA.setPrixvente(rs.getInt("prix_vente"));
				venteA.setNumCate(rs.getInt("no_categorie"));
				venteA.setPhoto(rs.getString("photo"));
			}
		} catch (SQLException e) {
			throw new DALException("Probleme - recherche mes enchères par nom et cat - " + e.getMessage());
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
		return venteA;
	}

	public void finirVente(int numVente) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(FINIRVENTE);
			pstmt.setInt(1, -1);
			pstmt.setInt(2, numVente);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DALException("Probleme - finirVente - " + e.getMessage());
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

	public Vente mesAcquisitions(int noVente) throws DALException {

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vente venteA = new Vente();

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(LISTACQUISITION);
			pstmt.setInt(1, noVente);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				venteA.setNumVente(noVente);
				venteA.setNumUtil(Integer.parseInt(rs.getString("no_utilisateur")));
				venteA.setNomArticle(rs.getString("nom_article"));
				venteA.setDescription(rs.getString("description"));
				venteA.setDateFinEnchere(LocalDate.parse(rs.getString("date_fin_encheres")));
				venteA.setPrixInitial(rs.getInt("prix_initial"));
				venteA.setPrixvente(rs.getInt("prix_vente"));
				venteA.setNumCate(rs.getInt("no_categorie"));
				venteA.setPhoto(rs.getString("photo"));

			}
		} catch (SQLException e) {
			throw new DALException("Probleme - recherche mes acquisitions - " + e.getMessage());
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
		return venteA;
	}

	// m�thode publier versus enregistrer
	public void publier(int noVente) throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		int nblig = 0;
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(PUBLIER);
			pstmt.setInt(1, noVente);
			nblig = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DALException("Probleme - publier Vente - " + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("DAL - fermerConnexion - " + e.getMessage());
			}
		}
	}

}
