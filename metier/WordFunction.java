
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class WordFunction {
    public static Categorie categorieConcerner;
    public static Vector<String> motList;

    public static Vector<String> conditionQuery = new Vector<>();
    public static Vector<String> orderQuery = new Vector<>();

    public static String queryBuild = "";
    public static String number = "";


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
    public static void buildQuery(int index,int indexMot) throws Exception{
        if (indexMot<WordFunction.motList.size()) {
            String mot = WordFunction.motList.elementAt(indexMot);
            
        
        // System.out.println(mot);
        if(index==0){
            if (
                mot.toLowerCase().compareToIgnoreCase("plus")==0 ||
                mot.toLowerCase().compareToIgnoreCase("moin")==0 
                )
                {
                    WordFunction.queryBuild = WordFunction.queryBuild.concat(mot);
                    buildQuery(index+1,indexMot+1);
                }
            else if
                (
                mot.toLowerCase().compareToIgnoreCase("meilleur")==0 ||
                mot.toLowerCase().compareToIgnoreCase("pire")==0 
                )  
                {
                    WordFunction.queryBuild = WordFunction.queryBuild.concat(mot);
                    buildQuery(index+1,indexMot+1);

                } else {
                    return;
                }
    }else if(index==1){
        if(
            (mot.toLowerCase().compareToIgnoreCase("chere")==0 && 
            ( WordFunction.queryBuild.compareToIgnoreCase("plus")==0 || WordFunction.queryBuild.compareToIgnoreCase("moin")==0 ))
        ){
            if ( WordFunction.queryBuild.compareToIgnoreCase("plus")==0 ) {
                WordFunction.queryBuild = "";
                WordFunction.orderQuery.add("prix desc");
            }else{
                WordFunction.queryBuild = "";
                WordFunction.orderQuery.add("prix asc");

            }
            buildQuery(0,indexMot+1);

        }
        else if(
            (mot.toLowerCase().compareToIgnoreCase("prix")==0 && 
            ( WordFunction.queryBuild.compareToIgnoreCase("meilleur")==0 || WordFunction.queryBuild.compareToIgnoreCase("pire")==0 ))
        ){
            if ( WordFunction.queryBuild.compareToIgnoreCase("meilleur")==0 ) {
                WordFunction.queryBuild = "";
                WordFunction.orderQuery.add("prix asc");
            }else{
                WordFunction.queryBuild = "";
                WordFunction.orderQuery.add("prix desc");

            }
            buildQuery(0,indexMot+1);

        }
        else if(
            (mot.toLowerCase().compareToIgnoreCase("qualite")==0 && 
            ( WordFunction.queryBuild.compareToIgnoreCase("meilleur")==0 || WordFunction.queryBuild.compareToIgnoreCase("pire")==0 ))
        ){
            if ( WordFunction.queryBuild.compareToIgnoreCase("meilleur")==0 ) {
                WordFunction.queryBuild = "";
                WordFunction.orderQuery.add("qualite desc");
            }else{
                WordFunction.queryBuild = "";
                WordFunction.orderQuery.add("qualite asc");

            }
            buildQuery(0,indexMot+1);

        }
        else if(
            (mot.toLowerCase().compareToIgnoreCase("rapport")==0 && 
            ( WordFunction.queryBuild.compareToIgnoreCase("meilleur")==0 || WordFunction.queryBuild.compareToIgnoreCase("pire")==0 ))
        ){
            WordFunction.queryBuild = WordFunction.queryBuild.concat(" "+mot);
            buildQuery(index+1,indexMot+1);

        }
        else if (estParsableEnDouble(mot)) {
            WordFunction.number = mot;
           buildQuery(index+1, indexMot+1);
        }
    }else if(index==2) {
        if(mot.toLowerCase().compareToIgnoreCase("ar")==0
            &&( WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("plus")==0 || WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("pire")==0)
        ){
            WordFunction.conditionQuery.add("prix>"+WordFunction.number);
            WordFunction.number = "";
            WordFunction.queryBuild = "";
            buildQuery(0,indexMot+1);
            
        }else if (mot.toLowerCase().compareToIgnoreCase("ar")==0
        &&( WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("moin")==0 || WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("meilleur")==0 )
        ) {
            WordFunction.conditionQuery.add("prix<"+WordFunction.number);
            WordFunction.number = "";
            WordFunction.queryBuild = "";
            buildQuery(0,indexMot+1);

        }else
        if(mot.toLowerCase().compareToIgnoreCase("q")==0
            &&( WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("plus")==0 || WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("pire")==0 )
        ){
            WordFunction.conditionQuery.add("qualite<"+WordFunction.number);
            WordFunction.number = "";
            WordFunction.queryBuild = "";
            buildQuery(0,indexMot+1);

        }else if (mot.toLowerCase().compareToIgnoreCase("q")==0
        &&( WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("moin")==0 || WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("meilleur")==0)
        ) {
            WordFunction.conditionQuery.add("qualite>"+WordFunction.number);
            WordFunction.number = "";
            WordFunction.queryBuild = "";
            buildQuery(0,indexMot+1);
        }else if (mot.toLowerCase().compareToIgnoreCase("qualite")==0
        &&( WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("meilleur rapport")==0 || WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("pire rapport")==0)
        ) {
            WordFunction.queryBuild = WordFunction.queryBuild.concat(" "+mot);
            buildQuery(index+1,indexMot+1);
        }
    }else if (index==3) {
        if (mot.toLowerCase().compareToIgnoreCase("prix")==0
        &&( WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("meilleur rapport qualite")==0 || WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("pire rapport qualite")==0)
        ) {
            if ( WordFunction.queryBuild.toLowerCase().compareToIgnoreCase("meilleur rapport qualite")==0) {
                WordFunction.orderQuery.add("(qualite/prix) asc");
            }else{
                WordFunction.orderQuery.add("(qualite/prix) desc");
            }
            WordFunction.queryBuild = "";
            buildQuery(0,indexMot+1);
        }
    }
    }
}

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
        WordFunction.buildQuery(0,0);
        
        System.out.println(WordFunction.orderQuery);
        System.out.println(WordFunction.conditionQuery);
        System.out.println(WordFunction.motList);
        // former le requette
        String query = "select * from produit where id_categorie_fk="+WordFunction.categorieConcerner.getIdCategorie();
        for (int i = 0 ; i < WordFunction.conditionQuery.size() ; i++) {
                query = query.concat(" and "+WordFunction.conditionQuery.elementAt(i));
        }

        for (int i = 0 ; i < WordFunction.orderQuery.size() ; i++) {
            if (i==0) {
                query = query.concat(" order by "+WordFunction.orderQuery.elementAt(i));
            }else{
                query = query.concat(" , "+WordFunction.orderQuery.elementAt(i));
            }
        }
    
        WordFunction.conditionQuery = null;
        WordFunction.orderQuery = null;
        WordFunction.queryBuild = "";
        WordFunction.number = "";

        WordFunction.conditionQuery = new Vector<>();
        WordFunction.orderQuery = new Vector<>();
        return query;
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
