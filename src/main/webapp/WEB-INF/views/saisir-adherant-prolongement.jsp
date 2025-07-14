<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Choisir un adhérent pour prolonger un prêt</title>
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
</body>
</html> 