<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Faire un abonnement</title>
</head>
<body>
    <h2>Faire un abonnement</h2>
    
    <c:if test="${not empty error}">
        <div style="background-color: #f8d7da; color: #721c24; padding: 10px; margin: 10px 0; border: 1px solid #f5c6cb;">
            ${error}
        </div>
    </c:if>
    
    <div style="margin: 20px 0;">
        <strong>Adhérent :</strong> ${adherant.nomAdherant} ${adherant.prenomAdherant} (ID: ${adherant.idAdherant})
    </div>
    
    <form action="${pageContext.request.contextPath}/adherants/abonnement" method="post">
        <input type="hidden" name="idAdherant" value="${adherant.idAdherant}">
        
        <div>
            <label for="dateInscription">Date d'inscription :</label>
            <input type="datetime-local" name="dateInscription" id="dateInscription" required>
        </div>
        
        <button type="submit">Activer l'abonnement</button>
    </form>
    
    <a href="${pageContext.request.contextPath}/adherants">Retour à la liste des adhérents</a>
</body>
</html> 