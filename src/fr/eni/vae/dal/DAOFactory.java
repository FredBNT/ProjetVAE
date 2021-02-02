package fr.eni.vae.dal;

public abstract class DAOFactory {


	public static CategorieDAO getCategorieDAO() {
		return new CategorieDAOJdbcImpl();
	}

	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOJdbcImpl();
	}

	public static RetraitDAO getRetraitDAO() {
		return new RetraitDAOJdbcImpl();
	}

	public static VenteDAO getVenteDAO() {
		return new VenteDAOJdbcImpl();
	}

}
