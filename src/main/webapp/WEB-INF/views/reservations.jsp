<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Réservations</title>
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
    <h2>Liste des réservations</h2>
    <div style="text-align:center;">
        <a href="${pageContext.request.contextPath}/reservations/ajouter" style="display:inline-block;margin:20px 0 20px 0;padding:10px 18px;background:#20c997;color:#fff;text-decoration:none;border-radius:4px;">Faire une réservation</a>
    </div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Date de réservation</th>
                <th>Admin</th>
                <th>Statut</th>
                <th>Exemplaire</th>
                <th>Adhérent</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="reservation" items="${reservations}">
                <tr>
                    <td>${reservation.idReservation}</td>
                    <td>${reservation.dateDeReservation}</td>
                    <td>${reservation.admin.nomAdmin}</td>
                    <td>${reservation.statut.nomStatut}</td>
                    <td>${reservation.exemplaire.idExemplaire}</td>
                    <td>${reservation.adherant.nomAdherant}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div style="text-align:center;">
        <a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
    </div>
</body>
</html>
