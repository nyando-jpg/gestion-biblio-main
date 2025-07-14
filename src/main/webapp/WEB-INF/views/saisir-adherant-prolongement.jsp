<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Choisir un adhérent pour prolonger un prêt</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { max-width: 400px; margin: 0 auto 30px auto; padding: 20px; border: 1px solid #ccc; border-radius: 6px; }
        label { display: block; margin-bottom: 8px; }
        input, select, button { width: 100%; margin-bottom: 16px; padding: 6px; box-sizing: border-box; }
        a { display: block; text-align: center; margin: 20px auto; text-decoration: underline; }
        h2 { text-align: center; }
    </style>
</head>
<body>
<h2>Choisir un adhérent</h2>
<form method="get" action="${pageContext.request.contextPath}/prets/prolonger">
    <label for="idAdherant">Sélectionnez un adhérent :</label>
    <select name="idAdherant" id="idAdherant" required>
        <option value="">-- Choisir un adhérent --</option>
        <c:forEach var="ad" items="${adherants}">
            <option value="${ad.idAdherant}">${ad.nomAdherant} ${ad.prenomAdherant} (ID: ${ad.idAdherant})</option>
        </c:forEach>
    </select>
    <br/><br/>
    <button type="submit">Voir les prêts à prolonger</button>
</form>
<a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
</body>
</html> 