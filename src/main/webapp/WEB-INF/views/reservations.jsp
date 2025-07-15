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
        .btn-validate { background: #28a745; color: white; border: none; padding: 5px 10px; border-radius: 3px; cursor: pointer; }
        .btn-validate:hover { background: #218838; }
        .status-validated { color: #28a745; font-weight: bold; }
    </style>
</head>
<body>
    <h2>Liste des reservations</h2>
    <div style="text-align:center;">
        <a href="${pageContext.request.contextPath}/reservations/ajouter" style="display:inline-block;margin:20px 0 20px 0;padding:10px 18px;background:#20c997;color:#fff;text-decoration:none;border-radius:4px;">Faire une reservation</a>
    </div>
    
    <c:if test="${not empty success}">
        <div style="background-color: #d4edda; color: #155724; padding: 10px; margin: 10px 0; border: 1px solid #c3e6cb;">
            ${success}
        </div>
    </c:if>
    
    <c:if test="${not empty error}">
        <div style="background-color: #f8d7da; color: #721c24; padding: 10px; margin: 10px 0; border: 1px solid #f5c6cb;">
            ${error}
        </div>
    </c:if>
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Date de reservation</th>
                <th>Admin</th>
                <th>Statut</th>
                <th>Exemplaire</th>
                <th>Adherent</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="reservation" items="${reservations}">
                <tr>
                    <td>${reservation.idReservation}</td>
                    <td>${reservation.dateDeReservation}</td>
                    <td>${reservation.admin.nomAdmin}</td>
                    <td class="${reservation.statut.idStatut == 2 ? 'status-validated' : ''}">${reservation.statut.nomStatut}</td>
                    <td>${reservation.exemplaire.idExemplaire}</td>
                    <td>${reservation.adherant.nomAdherant}</td>
                    <td>
                        <c:if test="${reservation.statut.idStatut == 1}">
                            <form action="${pageContext.request.contextPath}/reservations/valider" method="post" style="display: inline;">
                                <input type="hidden" name="idReservation" value="${reservation.idReservation}">
                                <button type="submit" class="btn-validate">Valider</button>
                            </form>
                        </c:if>
                        <c:if test="${reservation.statut.idStatut == 2}">
                            <span style="color: #28a745;">Validée</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div style="text-align:center;">
        <a href="${pageContext.request.contextPath}/" style="display:inline-block;margin:20px 0 20px 0;padding:10px 18px;background:#6c757d;color:#fff;text-decoration:none;border-radius:4px;">Retour à l'accueil</a>
    </div>
</body>
</html>
