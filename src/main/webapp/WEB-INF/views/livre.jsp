<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des livres</title>
    <style>
        table { border-collapse: collapse; width: 90%; margin: 20px auto; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background: #f0f0f0; }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Liste des livres</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Titre</th>
            <th>ISBN</th>
            <th>Langue</th>
            <th>Année</th>
            <th>Synopsis</th>
            <th>Nb pages</th>
            <th>Éditeur</th>
            <th>Auteur</th>
        </tr>
        <c:forEach var="l" items="${livres}">
            <tr>
                <td>${l.idLivre}</td>
                <td>${l.titre}</td>
                <td>${l.isbn}</td>
                <td>${l.langue}</td>
                <td>${l.anneePublication}</td>
                <td>${l.synopsis}</td>
                <td>${l.nbPage}</td>
                <td>${l.editeur.nomEditeur}</td>
                <td>${l.auteur.nomAuteur} ${l.auteur.prenomAuteur}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
