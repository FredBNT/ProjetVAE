package fr.eni.vae.bll;

import java.sql.SQLException;

import fr.eni.vae.bo.Retrait;
import fr.eni.vae.dal.DALException;
import fr.eni.vae.dal.DAOFactory;

public class RetraitManager {

	public static Retrait AfficherRetrait(Integer test) throws DALException, SQLException {
		return DAOFactory.getRetraitDAO().AfficherRetrait(test);
	}

	public static void supprimerUnRetrait(int numVente) throws DALException {
		DAOFactory.getRetraitDAO().supprimerUnRetrait(numVente);
	}

	public static void ajouterRetrait(Retrait retrait) throws DALException {
		DAOFactory.getRetraitDAO().ajouterRetrait(retrait);;
	}

}
