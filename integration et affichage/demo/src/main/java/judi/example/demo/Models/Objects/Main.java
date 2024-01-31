package judi.example.demo.Models.Objects;
import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Main
 */
public class Main {

    public static Produit[] getAllProduitByPhrase(String mot,Connection connection) throws Exception{
        Produit[] produits = null;
		try {
			String query = WordFunction.getQuery(mot, connection);

            produits = Produit.getAllProduitByQuery(query, connection);
            
		}catch (Exception e) {
            e.printStackTrace();
		}
        return produits;
    }
}