
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) throws Exception{
        String mot = "telephone plus de 500 Ar , meilleur rapport qualite prix ";
        Connection connection = null;
		try {
            connection = ConnectionPostgres.connectDefault();
			connection.setAutoCommit(false);
			String query = WordFunction.getQuery(mot, connection);

            System.out.println(query);
            Produit[] produits = Produit.getAllProduitByQuery(query, connection);

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
            connection.commit();
            connection.close();
        }
    }
}