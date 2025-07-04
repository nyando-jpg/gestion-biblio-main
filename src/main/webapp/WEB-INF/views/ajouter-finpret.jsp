<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Retour de prêt</title>
    <style>
        form { width: 350px; margin: 40px auto; padding: 20px; border: 1px solid #ccc; border-radius: 6px; background: #f9f9f9; }
        label { display: block; margin-bottom: 8px; font-weight: bold; }
        input[type="number"], input[type="datetime-local"] { width: 100%; padding: 8px; margin-bottom: 16px; border: 1px solid #ccc; border-radius: 4px; }
        button { padding: 10px 20px; background: #6c757d; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #343a40; }
        a { display: inline-block; margin-top: 20px; }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Retour de prêt</h2>
    <c:if test="${not empty error}">
        <div style="color: red; text-align: center; margin-bottom: 10px;">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div style="color: green; text-align: center; margin-bottom: 10px;">${success}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/finprets/ajouter">
        <label for="idAdherant">ID Adhérent :</label>
        <input type="number" id="idAdherant" name="idAdherant" required />

        <label for="idExemplaire">ID Exemplaire :</label>
        <input type="number" id="idExemplaire" name="idExemplaire" required />

        <label for="dateFin">Date de retour :</label>
        <input type="datetime-local" id="dateFin" name="dateFin" required />

        <button type="submit">Valider le retour</button>
    </form>
    <div style="text-align:center;">
        <a href="${pageContext.request.contextPath}/admin-home">Retour accueil admin</a>
    </div>
</body>
</html>
