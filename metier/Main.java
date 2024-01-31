
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) throws Exception{
        String mot = "telephone le plus chere et moin de 1500 Ar";
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