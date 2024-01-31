<%@ page import="java.util.Vector" %>
<%@ page import="judi.example.demo.Models.Objects.Test" %>
<%
    Vector<String> noms = (Vector<String>) request.getAttribute("nom");
    Vector<Test> prenoms = (Vector<Test>) request.getAttribute("prenom");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <table border="1">
        <tr>
            <th>Nom</th>
            <th>Prenom</th>
        </tr>
        <% out.println("Akory"); %>
        <% int i=0; 
            for(String nom : noms) { %>
        <tr>
            <td><%= nom %></td>
            <td><%= prenoms.elementAt(i).getPrenom() %></td>
        </tr>
        <% i++; } %>
    </table>
</body>
</html>