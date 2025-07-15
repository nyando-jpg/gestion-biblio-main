<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Adherents</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f7f7f7; color: #222; }
        form { max-width: 400px; margin: 0 auto 30px auto; padding: 20px; border: 1px solid #bbb; border-radius: 6px; background: #fff; }
        label { display: block; margin-bottom: 8px; color: #222; }
        input, select, button { width: 100%; margin-bottom: 16px; padding: 6px; box-sizing: border-box; border: 1px solid #bbb; border-radius: 3px; background: #fafafa; color: #222; }
        a, button { display: block; text-align: center; margin: 20px auto; text-decoration: none; background: #e0e0e0; color: #222; border-radius: 4px; padding: 10px 18px; border: 1px solid #bbb; transition: background 0.2s, color 0.2s; }
        a:hover, button:hover { background: #bbb; color: #000; }
        h1, h2 { text-align: center; color: #222; }
        table { border-collapse: collapse; width: 80%; margin: 20px auto; background: #fff; }
        th, td { border: 1px solid #bbb; padding: 8px; text-align: center; }
        th { background: #e0e0e0; color: #222; }
        span[style*="color: green"] { color: #222 !important; font-weight: bold; }
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
                    <a href="${pageContext.request.contextPath}/adherants/${a.idAdherant}" style="background: #007bff; color: white; padding: 5px 10px; text-decoration: none; border-radius: 3px;">Detail</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
</body>
</html>
