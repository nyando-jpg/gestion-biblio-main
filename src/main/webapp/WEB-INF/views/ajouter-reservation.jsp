<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Faire une réservation</title>
</head>
<body>
    <h2>Faire une réservation</h2>
    
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
    
    <form action="${pageContext.request.contextPath}/reservations/creer" method="post">
        <div>
            <label for="idAdherant">Adhérent :</label>
            <select name="idAdherant" id="idAdherant" required>
                <option value="">-- Sélectionner un adhérent --</option>
                <c:forEach var="adherant" items="${adherants}">
                    <option value="${adherant.idAdherant}">
                        ${adherant.nomAdherant} ${adherant.prenomAdherant} 
                        (${adherant.statusAdherant})
                    </option>
                </c:forEach>
            </select>
        </div>
        
        <div>
            <label for="idExemplaire">Exemplaire :</label>
            <select name="idExemplaire" id="idExemplaire" required>
                <option value="">-- Sélectionner un exemplaire --</option>
                <c:forEach var="exemplaire" items="${exemplaires}">
                    <option value="${exemplaire.idExemplaire}">
                        Exemplaire ${exemplaire.idExemplaire} 
                        (${exemplaire.dispo ? 'Disponible' : 'Non disponible'})
                    </option>
                </c:forEach>
            </select>
        </div>
        
        <div>
            <label for="dateReservation">Date de réservation :</label>
            <input type="datetime-local" name="dateReservation" id="dateReservation" required>
        </div>
        
        <button type="submit">Créer la réservation</button>
    </form>
    
    <a href="${pageContext.request.contextPath}/reservations">Retour à la liste des réservations</a>
</body>
</html>
