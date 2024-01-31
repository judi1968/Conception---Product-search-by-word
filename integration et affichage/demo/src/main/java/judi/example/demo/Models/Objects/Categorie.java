package judi.example.demo.Models.Objects;
import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Categorie {
    int idCategorie;
    String designationCategorie;

    public String getDesignationCategorie() {
        return designationCategorie;
    }
    public int getIdCategorie() {
        return idCategorie;
    }
    
    public void setDesignationCategorie(String designationCategorie) {
        this.designationCategorie = designationCategorie;
    }
    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public static Categorie[] getAllCategories(Connection connection) throws Exception{
        String query = "SELECT * FROM categorie e;";
        Categorie[] categories;
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
                categories = new Categorie[0];
            }else{
                categories = new Categorie[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    categories[i] = new Categorie();
                    categories[i].setIdCategorie(resultset.getInt("id_categorie"));
                    categories[i].setDesignationCategorie(resultset.getString("designation"));
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
        return categories;
    } 
}