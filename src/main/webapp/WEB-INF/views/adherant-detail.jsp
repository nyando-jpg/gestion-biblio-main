<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Detail de l'adherent</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f7f7f7; color: #222; }
        .container { max-width: 700px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 8px #ddd; }
        h2 { text-align: center; margin-bottom: 30px; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; background: #fafafa; }
        th, td { border: 1px solid #bbb; padding: 8px; text-align: left; }
        th { background: #e0e0e0; }
        a, button { display: block; text-align: center; margin: 30px auto 0 auto; text-decoration: none; background: #e0e0e0; color: #222; border-radius: 4px; padding: 10px 18px; border: 1px solid #bbb; transition: background 0.2s, color 0.2s; }
        a:hover, button:hover { background: #bbb; color: #000; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Detail de l'adherent</h2>
        <table>
            <c:forEach var="entry" items="${adherantSimple.entrySet()}">
                <tr>
                    <th>${entry.key}</th>
                    <td>${entry.value}</td>
                </tr>
            </c:forEach>
            <tr>
                <th>Date debut abonnement</th>
                <td>${adherantSimple.dateDebutAbonnement != null ? adherantSimple.dateDebutAbonnement : ''}</td>
            </tr>
            <tr>
                <th>Duree abonnement (jours)</th>
                <td>${adherantSimple.dureeAbonnementJours != null ? adherantSimple.dureeAbonnementJours : ''}</td>
            </tr>
        </table>
        <c:if test="${not empty penalitesEnCours}">
            <h3>Penalites en cours</h3>
            <table>
                <tr>
                    <th>Date debut</th>
                    <th>Duree (jours)</th>
                    <th>Fin de penalite</th>
                </tr>
                <c:forEach var="p" items="${penalitesEnCours}">
                    <tr>
                        <td>${p.dateDebut}</td>
                        <td>${p.dureeJours}</td>
                        <td>${p.finPenalite}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <h3>Quotas de pret</h3>
        <table>
            <tr>
                <th>Type de pret</th>
                <th>Quota</th>
                <th>Max</th>
            </tr>
            <c:forEach var="q" items="${quotas}">
                <tr>
                    <td>${q.typePret}</td>
                    <td>${q.quota}</td>
                    <td>${q.max}</td>
                </tr>
            </c:forEach>
        </table>
        <a href="${pageContext.request.contextPath}/adherants">Retour à la liste des adherents</a>
    </div>
</body>
</html> 