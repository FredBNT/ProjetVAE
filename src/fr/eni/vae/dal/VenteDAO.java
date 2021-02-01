package fr.eni.vae.dal;

import java.util.ArrayList;

import fr.eni.vae.bo.Categorie;
import fr.eni.vae.bo.Vente;

public interface VenteDAO {

	public ArrayList<Vente> listeVentes() throws DALException;

	public ArrayList<Vente> ventesUtilisateur(int no_utilisateur) throws DALException;

	public ArrayList<Vente> ventesCategorie(int no_categorie) throws DALException;

	public ArrayList<Vente> listeVenteArticle(String nomArticle) throws DALException;

	public ArrayList<Vente> listeUtNom(String nomarticle, int no_utilisateur) throws DALException;

	public ArrayList<Vente> listeUtCat(int no_cate, int no_utilisateur) throws DALException;

	public ArrayList<Vente> listeUtNomCat(String nomarticle, int no_utilisateur, int no_cate) throws DALException;

	public void ajouter(Vente nVente) throws DALException;

	public int supprimerVente(int noVente, int noUtil) throws DALException;

	public Vente AfficherArticle(int numVente) throws DALException;

	public Vente AfficherEnchereEnCours(int numVente) throws DALException;

	public void initPrixVente(int proposEnchere, int numVente) throws DALException;

	public Vente obtenirDateFinEnchere(int numVente) throws DALException;

	public int obtenirLeNumVendeur(int numVente) throws DALException;

	public Vente mesEncheresParNomCat(int noVente, int noCat, String nom) throws DALException;

	public void finirVente(int numVente) throws DALException;

	public Vente mesAcquisitions(int noVente) throws DALException;

	public void publier(int noVente) throws DALException;

}
