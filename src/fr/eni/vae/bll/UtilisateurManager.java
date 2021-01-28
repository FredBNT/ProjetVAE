package fr.eni.vae.bll;

import fr.eni.vae.bo.Utilisateur;
import fr.eni.vae.dal.DALException;
import fr.eni.vae.dal.DAOFactory;
import fr.eni.vae.dal.UtilisateurDAO;

/**
 * Class metier utilisateur
 */
public class UtilisateurManager {
	private UtilisateurDAO utilisateurDAO;

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	public static boolean verifPseudo(String pseudo) {
		return DAOFactory.getUtilisateurDAO().verifPseudo(pseudo);
	}

	public static boolean verifMail(String mail) {
		return DAOFactory.getUtilisateurDAO().verifMail(mail);
	}

	public static boolean verifTel(String tel) {
		return DAOFactory.getUtilisateurDAO().verifTel(tel);
	}

	public static void ajouter(Utilisateur unUtilisateur) throws DALException {
		DAOFactory.getUtilisateurDAO().ajouter(unUtilisateur);		
	}

	public static Utilisateur autreUtilisateur(String pseudo) throws DALException {
		return DAOFactory.getUtilisateurDAO().autreUtilisateur(pseudo);
	}

	public static void modifier(String vLogin, String vNom, String vPrenom, String vMail, String vTelephone,
			String vRue, String vCP, String vVille, String vMdp, int attribute) throws DALException {
		DAOFactory.getUtilisateurDAO().modifier(vLogin, vNom, vPrenom, vMail, vTelephone, vRue, vCP, vVille, vMdp, attribute);
	}

	public static void modifierCredit(int ajoutCredit, int numUtil) throws DALException {
		DAOFactory.getUtilisateurDAO().modifierCredit(ajoutCredit,numUtil);
	}

	public static Utilisateur obtenirUnUtil(int utilEnchere) throws DALException {
		return DAOFactory.getUtilisateurDAO().obtenirUnUtil(utilEnchere);
	}

	public static String rechercheUnPseudo(int numVend) throws DALException {
		return DAOFactory.getUtilisateurDAO().rechercheUnPseudo(numVend);
	}

	public static Utilisateur obtenirUnUtilpseudo(String pseudoGagnant) throws DALException {
		return DAOFactory.getUtilisateurDAO().obtenirUnUtilpseudo(pseudoGagnant);
	}

	public static Utilisateur verification(String vLogin, String vMdp, String vMail) throws DALException {
		return DAOFactory.getUtilisateurDAO().verification(vLogin, vMdp, vMail);
	}

	public static Utilisateur verificationMdp(int numUtil) throws DALException {
		return DAOFactory.getUtilisateurDAO().verificationMdp(numUtil);
	}

}
