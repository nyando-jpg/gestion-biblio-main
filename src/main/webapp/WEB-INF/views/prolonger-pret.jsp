<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Prolonger un prêt</title>
</head>
<body>
<h2>Prolonger un prêt</h2>

<c:if test="${not empty error}">
    <div style="color:red;">${error}</div>
</c:if>
<c:if test="${not empty success}">
    <div style="color:green;">${success}</div>
</c:if>
<c:if test="${not empty info}">
    <div style="color:blue;">${info}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/prets/prolonger">
    <input type="hidden" name="idAdherant" value="${idAdherant}" />
    <label for="idPret">Sélectionnez un prêt à prolonger :</label>
    <select name="idPret" id="idPret" required>
        <option value="">-- Choisir un prêt --</option>
        <c:forEach var="pret" items="${pretsActifs}">
            <option value="${pret.idPret}">
                Livre : ${pret.exemplaire.livre.titre} | Début : ${pret.dateDebut} | Exemplaire : ${pret.exemplaire.idExemplaire}
            </option>
        </c:forEach>
    </select>
    <br/><br/>
    <!-- Le bouton de prolongement reste toujours cliquable, l'erreur sera affichée si le prolongement n'est pas possible -->
    <button type="submit">Prolonger le prêt</button>
</form>

</body>
</html> 