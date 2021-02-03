package fr.eni.vae.dal;

import java.util.ArrayList;

import fr.eni.vae.bo.Enchere;

public interface EnchereDAO {

	public Enchere rechercheUnUserEncheri(int numVente) throws DALException;

	public void ajouterEnchere(String date, Enchere enchere) throws DALException;

	public Enchere verificationUserEncherir(int numVente, int numUser) throws DALException;

	public ArrayList<Integer> quelleVenteEncherie(int no_user) throws DALException;

	public ArrayList<Enchere> listeEncheres(int numVente) throws DALException;

	public void modificationEnchere(String dateTime, int numUtil, int numVente, int proposEnchere) throws DALException;

	public void suppressionEnchere(int numUtil, int numVente) throws DALException;

	public void ajouterEnchereSuiteSuppression(String date, int numUtil, int numVente, int proposEnchere)
			throws DALException;

	public void suppressionEnchereComplete(int numVente) throws DALException;

	public Enchere rechercheMeilleureMontant(int numVente) throws DALException;

}
