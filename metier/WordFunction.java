
import java.sql.Connection;
import java.util.Vector;

public class WordFunction {
    public static String getQuery(String phrase,Connection connection) throws Exception{
        // separer le phrase par le espace et le mettre dans un vector
        String[] phraseSpliter = phrase.split(" ");
        Vector<String> motList = new Vector<>();
        for (String string : phraseSpliter) {
            motList.add(string);
        }

        // verifier le nom du produit concerner
        // ... en attente
        // enlever un element : maVector.removeElementAt(0);
        
        System.out.println(motList);
    
        return "query";
    }
}
