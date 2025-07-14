<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pénalités</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { max-width: 400px; margin: 0 auto 30px auto; padding: 20px; border: 1px solid #ccc; border-radius: 6px; }
        label { display: block; margin-bottom: 8px; }
        input, select, button { width: 100%; margin-bottom: 16px; padding: 6px; box-sizing: border-box; }
        a { display: block; text-align: center; margin: 20px auto; text-decoration: underline; }
        h1, h2 { text-align: center; }
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
    <a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
</body>
</html>
