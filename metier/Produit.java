import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Produit {
    int id_produit;
    String designationProduit;
    double prix;
    double qualite;
    Categorie categorie;

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    public void setDesignationProduit(String designationProduit) {
        this.designationProduit = designationProduit;
    }
    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public void setQualite(double qualite) {
        this.qualite = qualite;
    }

    public Categorie getCategorie() {
        return categorie;
    }
    public String getDesignationProduit() {
        return designationProduit;
    }
    public int getId_produit() {
        return id_produit;
    }
    public double getPrix() {
        return prix;
    }
    public double getQualite() {
        return qualite;
    }

    
    public static Produit[] getAllProduitByQuery(String query,Connection connection) throws Exception{
        Produit[] produits;
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
                produits = new Produit[0];
            }else{
                produits = new Produit[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    produits[i] = new Produit();
                    produits[i].setId_produit(resultset.getInt("id_activite"));
                    produits[i].setDesignationProduit(resultset.getString("nom_activite"));
                    produits[i].setPrix(resultset.getDouble("nom_activite"));
                    produits[i].setQualite(resultset.getDouble("nom_activite"));
                    // produits[i].setCategorie(Categorie.resultset.getDouble("nom_activite"));


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
        return produits;
    }
   
}
