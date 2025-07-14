<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un retour de prêt</title>
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
        <a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
        <a href="${pageContext.request.contextPath}/admin-home">Retour accueil admin</a>
    </div>
</body>
</html>
