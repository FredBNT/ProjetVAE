package fr.eni.vae.bll;

import java.util.List;

import fr.eni.vae.bo.Categorie;
import fr.eni.vae.dal.DALException;
import fr.eni.vae.dal.DAOFactory;

public class CategorieManager {

	public static List<Categorie> recupCategorie() throws DALException {
		return DAOFactory.getCategorieDAO().recupCategorie();
	}

}
