<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des réservations</title>
    <style>
        table { width: 90%; margin: 30px auto; border-collapse: collapse; }
        th, td { border: 1px solid #ccc; padding: 8px 12px; text-align: center; }
        th { background: #17a2b8; color: #fff; }
        tr:nth-child(even) { background: #f9f9f9; }
        h2 { text-align: center; }
        a { display: inline-block; margin: 20px; }
    </style>
</head>
<body>
    <h2>Liste des réservations</h2>
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
        <a href="${pageContext.request.contextPath}/admin-home">Retour accueil admin</a>
    </div>
</body>
</html>
