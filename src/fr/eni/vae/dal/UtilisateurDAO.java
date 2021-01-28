package fr.eni.vae.dal;

import fr.eni.vae.bo.Utilisateur;

public interface UtilisateurDAO {

	public Utilisateur rechercher(String pseudo, String mdp) throws DALException;

	public void ajouter(Utilisateur user) throws DALException;

	public boolean verifPseudo(String pseudo);

	public boolean verifMail(String mail);

	public boolean verifTel(String numTel);

	public Utilisateur verification(String pseudo, String mdp, String mail) throws DALException;

	public int supprimer(String pseudo, String mdp) throws DALException;

	public int modifier(String pseudo, String nom, String prenom, String email, String telephone, String rue, String cp,
			String ville, String mdp, int numUtil) throws DALException;

	public Utilisateur autreUtilisateur(String pseudo) throws DALException;

	public String rechercheUnPseudo(int numUtil) throws DALException;

	public Utilisateur verificationMdp(int numUtil) throws DALException;

	public void modifierCredit(int credit, int numUtil) throws DALException;

	public Utilisateur obtenirUnUtil(int noUtil) throws DALException;

	public Utilisateur obtenirUnUtilpseudo(String pseudo) throws DALException;

}
