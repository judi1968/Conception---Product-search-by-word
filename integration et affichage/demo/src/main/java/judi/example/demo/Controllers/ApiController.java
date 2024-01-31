package judi.example.demo.Controllers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import judi.example.demo.Models.Objects.Main;
import judi.example.demo.Models.Objects.Produit;
import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {
    @GetMapping("produit/{phrase}")
    public Map<String, Object> produitByPhrase(@PathVariable String phrase) throws Exception{
        Map<String, Object> resultat = new HashMap<>();
        int status = 0;
        String titre = null;
        String message = null;
        Produit[] produits = null;
        Connection connection = null;
        try {
            connection = ConnectionPostgres.connectDefault();
            connection.setAutoCommit(false);
            produits = Main.getAllProduitByPhrase(phrase,connection);
            status = 200;
            titre = "Prendre tout les produit est fait avec succes";
            message = "Excellent , voici tout les produits";
        } catch (Exception e) {
            status = 500;
            titre = "Prendre les produits valides a echoue";
            message = e.getMessage();
            e.printStackTrace();
        } finally {
            resultat.put("produits", produits);
            resultat.put("status", status);
            resultat.put("titre", titre);
            resultat.put("message", message);
            if (!(connection==null)) {
                connection.commit();
                connection.close();
            }
        }
    
        return resultat;
    }
}
