
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class WordFunction {
    public static Categorie categorieConcerner;
    public static Vector<String> motList;

    // 1 - mettre le phrase en liste de mot
    public static void setMotList(String phrase) {
        String[] phraseSpliter = phrase.split(" ");
        WordFunction.motList = new Vector<>();
        for (String string : phraseSpliter) {
            motList.add(string);
        }
    }

    // 2 - verifier le nom du produit concerner
    public static void setCategorieConcerner(Connection connection) throws Exception {
        Categorie[] categories = Categorie.getAllCategories(connection);
        for (Categorie categorie : categories) {
            int i = 0;
            for (String mot : motList) {
                if ((mot.toLowerCase()).compareToIgnoreCase(categorie.getDesignationCategorie().toLowerCase())==0) {
                    WordFunction.categorieConcerner = categorie; 
                    WordFunction.motList.removeElementAt(i);
                    break;
                }
                i++;
            }
        }
    }

    // 3 . enlever tout les mots pas concerne
    public static void removeWordNotConcerned(Connection connection) throws Exception{
        String[] motConcerne = WordFunction.getAllMotConcerne(connection);
        Vector<String> motRestant = new Vector<>();
        int i = 0;
        for (String motInput : motList) {
            if (WordFunction.estParsableEnDouble(motInput)) {
                motRestant.add(motInput);
            }else{
                boolean isExist = false;
                for (String motBase : motConcerne) {
                    if ((motInput.toLowerCase()).compareToIgnoreCase(motBase.toLowerCase())==0) {
                        isExist = true;
                        break;
                    }
                }
                if (isExist) {
                    motRestant.add(motInput);
                }
            }
            i++;
        }
        WordFunction.motList = null;
        WordFunction.motList = motRestant;

    }

    // 4 . former le requette

  /****************************************************************************************************** */  

    public static String getQuery(String phrase,Connection connection) throws Exception{
        // separer le phrase par le espace et le mettre dans un vector
        WordFunction.setMotList(phrase);

        // verifier le nom du produit concerner
        WordFunction.setCategorieConcerner(connection);
        if (WordFunction.categorieConcerner==null) {
            throw new Exception("Pas de categorie concerne");
        }
        // prendre tout les mots important puis enlever tout les mots pas important
        WordFunction.removeWordNotConcerned(connection);
        System.out.println(WordFunction.motList);

        // former le requette

        // System.out.println(motList.elementAt(0));
    
        return "query";
    }
  /****************************************************************************************************** */  

    // prendre tout les mots importants 
    public static String[] getAllMotConcerne(Connection connection) throws Exception{
        String query = "SELECT * FROM mot_concerner";
        String[] mots;
        int size = 0;
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
		try {
            if(connection==null) {
                connection = ConnectionPostgres.connectDefault();
                connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);

			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
			while(resultset.next()) {
                size++;
			}
            if(size==0){
                mots = new String[0];
            }else{
                mots = new String[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    mots[i] = resultset.getString("mot");
                    i++;
                }
            }
			statement.close();
			
		}catch (Exception e) {
			throw e;
		}finally {
			if(statementOpen) {
				statement.close();
			}
			if(resultsetOpen) {
				resultset.close();
			}
			if(closeable) {
				connection.commit();
				connection.close();
			}
		}
        return mots;
    }   
    
    
    // verification si on peut parser String en double
    public static boolean estParsableEnDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
