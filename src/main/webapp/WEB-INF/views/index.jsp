<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <hr style="margin:30px 0;">
    <h2>Connexion admin</h2>
    <form method="post" action="${pageContext.request.contextPath}/admin-login" style="max-width:350px;margin:0 auto;">
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nomAdmin" required />
        <label for="prenom">Prenom :</label>
        <input type="text" id="prenom" name="prenomAdmin" required />
        <label for="password">Mot de passe :</label>
        <input type="password" id="password" name="password" required />
        <button type="submit" style="margin-top:12px;">Se connecter</button>
    </form>
</body>
</html>