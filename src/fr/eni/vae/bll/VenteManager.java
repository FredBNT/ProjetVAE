package fr.eni.vae.bll;

import java.util.List;

import fr.eni.vae.bo.Categorie;
import fr.eni.vae.bo.Enchere;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.DALException;
import fr.eni.vae.dal.DAOFactory;

public class VenteManager {

	public static List<Vente> listeVentes() throws DALException {
		return DAOFactory.getVenteDAO().listeVentes();
	}

	public static List<Vente> ventesCategorie(int num) throws DALException {
		return DAOFactory.getVenteDAO().ventesCategorie(num);
	}

	public static List<Vente> listeVenteArticle(String nom) throws DALException {
		return DAOFactory.getVenteDAO().listeVenteArticle(nom);
	}

	public static List<Vente> ventesUtilisateur(int numUtil) throws DALException {
		return DAOFactory.getVenteDAO().ventesUtilisateur(numUtil);
	}

	public static List<Vente> listeUtCat(int num, int numUtil) throws DALException {
		return DAOFactory.getVenteDAO().listeUtCat(num, numUtil);
	}

	public static List<Vente> listeUtNomCat(String nom, int numUtil, int num) throws DALException {
		return DAOFactory.getVenteDAO().listeUtNomCat(nom, numUtil, num);
	}

	public static List<Vente> listeUtNom(String parameter, int numUtil) throws DALException {
		return DAOFactory.getVenteDAO().listeUtNom(parameter, numUtil);
	}

	public static Object AfficherEnchereEnCours(Integer no) throws DALException {
		return DAOFactory.getVenteDAO().AfficherEnchereEnCours(no);
	}

	public static Vente mesEncheresParNomCat(Integer no, int num, String nom) throws DALException {
		return DAOFactory.getVenteDAO().mesEncheresParNomCat(no, num, nom);
	}

	public static Vente mesAcquisitions(Integer no) throws DALException {
		return DAOFactory.getVenteDAO().mesAcquisitions(no);
	}

	public static Vente AfficherArticle(int numVente) throws DALException {
		return DAOFactory.getVenteDAO().AfficherArticle(numVente);
	}

	public static void initPrixVente(int prixInitial, int numVente) throws DALException {
		DAOFactory.getVenteDAO().initPrixVente(prixInitial, numVente);
	}

	public static List<Enchere> listeEncheres(int numVente) throws DALException {
		return DAOFactory.getEnchereDAO().listeEncheres(numVente);
	}

	public static void supprimerVente(int numVente, int numUtil) throws DALException {
		DAOFactory.getVenteDAO().supprimerVente(numVente, numUtil);
	}

	public static Vente obtenirDateFinEnchere(int numVente) throws DALException {
		return DAOFactory.getVenteDAO().obtenirDateFinEnchere(numVente);
	}

	public static int obtenirLeNumVendeur(int numVente) throws DALException {
		return DAOFactory.getVenteDAO().obtenirLeNumVendeur(numVente);
	}

	public static void finirVente(int numVente) throws DALException {
		DAOFactory.getVenteDAO().finirVente(numVente);
	}

	public static void ajouter(Vente nVente) throws DALException {
		DAOFactory.getVenteDAO().ajouter(nVente);
	}

	public static void publier(int numVente) throws DALException {
		DAOFactory.getVenteDAO().publier(numVente);
	}

}
