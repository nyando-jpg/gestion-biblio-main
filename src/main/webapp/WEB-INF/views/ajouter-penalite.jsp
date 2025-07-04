<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter une pénalité</title>
    <style>
        form { width: 350px; margin: 40px auto; padding: 20px; border: 1px solid #ccc; border-radius: 6px; background: #f9f9f9; }
        label { display: block; margin-bottom: 8px; font-weight: bold; }
        input[type="text"], input[type="number"], input[type="datetime-local"] { width: 100%; padding: 8px; margin-bottom: 16px; border: 1px solid #ccc; border-radius: 4px; }
        button { padding: 10px 20px; background: #007bff; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
        a { display: inline-block; margin-top: 20px; }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Ajouter une pénalité</h2>
    <c:if test="${not empty success}">
        <div style="color:green; font-weight:bold; margin-bottom:10px; text-align:center;">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div style="color:red; font-weight:bold; margin-bottom:10px; text-align:center;">${error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/penalites/ajouter">
        <label for="idAdherant">ID Adhérent :</label>
        <input type="number" id="idAdherant" name="idAdherant" required />

        <label for="duree">Durée (jours) :</label>
        <input type="number" id="duree" name="duree" required />

        <label for="datePenalite">Date pénalité :</label>
        <input type="datetime-local" id="datePenalite" name="datePenalite" required />

        <button type="submit">Ajouter</button>
    </form>
    <div style="text-align:center;">
        <a href="${pageContext.request.contextPath}/penalites">Retour à la liste des pénalités</a>
    </div>
</body>
</html>
