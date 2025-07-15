<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Réservations</title>
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
    <h2>Liste des reservations</h2>
    <div style="text-align:center;">
        <a href="${pageContext.request.contextPath}/reservations/ajouter" style="display:inline-block;margin:20px 0 20px 0;padding:10px 18px;background:#20c997;color:#fff;text-decoration:none;border-radius:4px;">Faire une reservation</a>
    </div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Date de reservation</th>
                <th>Admin</th>
                <th>Statut</th>
                <th>Exemplaire</th>
                <th>Adherent</th>
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
