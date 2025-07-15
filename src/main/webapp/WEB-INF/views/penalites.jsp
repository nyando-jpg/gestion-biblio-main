<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pénalités</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f7f7f7; color: #222; }
        form { max-width: 400px; margin: 0 auto 30px auto; padding: 20px; border: 1px solid #bbb; border-radius: 6px; background: #fff; }
        label { display: block; margin-bottom: 8px; color: #222; }
        input, select, button { width: 100%; margin-bottom: 16px; padding: 6px; box-sizing: border-box; border: 1px solid #bbb; border-radius: 3px; background: #fafafa; color: #222; }
        a, button { display: block; text-align: center; margin: 20px auto; text-decoration: none; background: #e0e0e0; color: #222; border-radius: 4px; padding: 10px 18px; border: 1px solid #bbb; transition: background 0.2s, color 0.2s; }
        a:hover, button:hover { background: #bbb; color: #000; }
        h1, h2 { text-align: center; color: #222; }
        table { border-collapse: collapse; width: 90%; margin: 20px auto; background: #fff; }
        th, td { border: 1px solid #bbb; padding: 8px; text-align: center; }
        th { background: #e0e0e0; color: #222; }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Liste des pénalites</h2>
    <div style="text-align:center; margin-bottom:20px;">
        <a href="${pageContext.request.contextPath}/penalites/ajouter" style="background:#28a745;">Ajouter une pénalité</a>
    </div>
    <table>
        <tr>
            <th>ID</th>
            <th>ID Adherent</th>
            <th>Date penalite</th>
            <th>Duree (jours)</th>
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
