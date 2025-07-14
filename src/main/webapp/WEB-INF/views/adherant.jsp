<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Adherents</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { max-width: 400px; margin: 0 auto 30px auto; padding: 20px; border: 1px solid #ccc; border-radius: 6px; }
        label { display: block; margin-bottom: 8px; }
        input, select, button { width: 100%; margin-bottom: 16px; padding: 6px; box-sizing: border-box; }
        a { display: block; text-align: center; margin: 20px auto; text-decoration: underline; }
        h1, h2 { text-align: center; }
        table { border-collapse: collapse; width: 80%; margin: 20px auto; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background: #f0f0f0; }
    </style>
</head>
<body>
    <div style="width:80%;margin:20px auto 0 auto;display:flex;justify-content:space-between;align-items:center;">
        <h2>Liste des adherents</h2>
<a href="${pageContext.request.contextPath}/ajouter-adherant">Ajouter</a>    </div>
    <table>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        <c:forEach var="a" items="${adherants}">
            <tr>
                <td>${a.idAdherant}</td>
                <td>${a.nomAdherant}</td>
                <td>${a.prenomAdherant}</td>
                <td>
                    <c:choose>
                        <c:when test="${a.statusAdherant eq 'Actif'}">Actif</c:when>
                        <c:otherwise>Non actif</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${a.statusAdherant eq 'Actif'}">
                            <span style="color: green;">Actif</span>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/adherants/abonnement?id=${a.idAdherant}" 
                               style="background: #007bff; color: white; padding: 5px 10px; text-decoration: none; border-radius: 3px;">
                                Faire abonnement
                            </a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
</body>
</html>
