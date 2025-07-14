<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Faire un abonnement</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { max-width: 400px; margin: 0 auto 30px auto; padding: 20px; border: 1px solid #ccc; border-radius: 6px; }
        label { display: block; margin-bottom: 8px; }
        input, select, button { width: 100%; margin-bottom: 16px; padding: 6px; box-sizing: border-box; }
        a { display: block; text-align: center; margin: 20px auto; text-decoration: underline; }
        h1, h2 { text-align: center; }
    </style>
</head>
<body>
    <h2>Faire un abonnement</h2>
    
    <c:if test="${not empty error}">
        <div style="background-color: #f8d7da; color: #721c24; padding: 10px; margin: 10px 0; border: 1px solid #f5c6cb;">
            ${error}
        </div>
    </c:if>
    
    <div style="margin: 20px 0;">
        <strong>Adherent :</strong> ${adherant.nomAdherant} ${adherant.prenomAdherant} (ID: ${adherant.idAdherant})
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
    <a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
</body>
</html> 