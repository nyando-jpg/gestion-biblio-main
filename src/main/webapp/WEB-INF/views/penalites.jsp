<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des pénalités</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin: 30px auto; }
        th, td { border: 1px solid #ccc; padding: 8px 12px; text-align: center; }
        th { background: #f2f2f2; }
        a { display: inline-block; margin: 20px; padding: 10px 18px; background: #007bff; color: #fff; text-decoration: none; border-radius: 4px; }
        a:hover { background: #0056b3; }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Liste des pénalités</h2>
    <div style="text-align:center; margin-bottom:20px;">
        <a href="${pageContext.request.contextPath}/penalites/ajouter" style="background:#28a745;">Ajouter une pénalité</a>
    </div>
    <table>
        <tr>
            <th>ID</th>
            <th>ID Adhérent</th>
            <th>Date pénalité</th>
            <th>Durée (jours)</th>
        </tr>
        <c:forEach var="p" items="${penalites}">
            <tr>
                <td>${p.idPenalite}</td>
                <td>${p.adherant.idAdherant}</td>
                <td>${p.datePenalite}</td>
                <td>${p.duree}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
