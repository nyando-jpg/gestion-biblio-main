<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un adhérent</title>
    <style>
        form { width: 400px; margin: 40px auto; padding: 20px; border: 1px solid #ccc; border-radius: 8px; background: #fafafa; }
        label { display: block; margin-top: 12px; }
        input, select { width: 100%; padding: 6px; margin-top: 4px; }
        button { margin-top: 16px; padding: 8px 16px; background: #007bff; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Ajouter un nouvel adhérent</h2>
    <form method="post" action="${pageContext.request.contextPath}/ajouter-adherant">
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nomAdherant" required />

        <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" name="prenomAdherant" required />

        <label for="password">Mot de passe :</label>
        <input type="password" id="password" name="password" required />

        <label for="profil">Profil :</label>
        <select id="profil" name="idProfil" required>
            <c:forEach var="p" items="${profils}">
                <option value="${p.idProfil}">${p.nomProfil}</option>
            </c:forEach>
        </select>

        <button type="submit">Enregistrer</button>
    </form>
</body>
</html>
