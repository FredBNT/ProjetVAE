package fr.eni.vae.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.vae.bo.Enchere;
import fr.eni.vae.dal.DALException;
import fr.eni.vae.dal.DAOFactory;

public class EnchereManager {

	public static ArrayList<Integer> quelleVenteEncherie(int numUtil) throws DALException {
		return DAOFactory.getEnchereDAO().quelleVenteEncherie(numUtil);
	}

	public static Enchere verificationUserEncherir(int numVente, int numUtil) throws DALException {
		return DAOFactory.getEnchereDAO().verificationUserEncherir(numVente, numUtil);
	}

	public static void suppressionEnchere(int numUtil, int numVente) throws DALException {
		DAOFactory.getEnchereDAO().suppressionEnchere(numUtil, numVente);
	}

	public static Enchere rechercheMeilleureMontant(int numVente) throws DALException {
		return DAOFactory.getEnchereDAO().rechercheMeilleureMontant(numVente);
	}

	public static void suppressionEnchereComplete(int numVente) throws DALException {
		DAOFactory.getEnchereDAO().suppressionEnchereComplete(numVente);
	}

	public static Enchere rechercheUnUserEncheri(int numVente) throws DALException {
		return DAOFactory.getEnchereDAO().rechercheUnUserEncheri(numVente);
	}

	public static void ajouterEnchere(String date, Enchere enchere) throws DALException {
		DAOFactory.getEnchereDAO().ajouterEnchere(date, enchere);
	}

	public static List<Enchere> listeEncheres(int numVente) throws DALException {
		return DAOFactory.getEnchereDAO().listeEncheres(numVente);
	}




}
