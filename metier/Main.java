
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) throws Exception{
        String mot = "telephone meilleur 500 Ar , moin chere et pire qualite ";
        Connection connection = null;
		try {
            connection = ConnectionPostgres.connectDefault();
			connection.setAutoCommit(false);
			String query = WordFunction.getQuery(mot, connection);

			System.out.println("\n"+query);

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
            connection.commit();
            connection.close();
        }
    }
}