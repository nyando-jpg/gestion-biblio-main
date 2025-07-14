<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un adhérent</title>
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
    <h2 style="text-align:center;">Ajouter un nouvel adherent</h2>
    <form method="post" action="${pageContext.request.contextPath}/ajouter-adherant">
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nomAdherant" required />

        <label for="prenom">Prenom :</label>
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
    <a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
</body>
</html>
