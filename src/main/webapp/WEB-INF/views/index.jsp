<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f7f7f7; color: #222; }
        form { max-width: 400px; margin: 0 auto 30px auto; padding: 20px; border: 1px solid #bbb; border-radius: 6px; background: #fff; }
        label { display: block; margin-bottom: 8px; color: #222; }
        input, select, button { width: 100%; margin-bottom: 16px; padding: 6px; box-sizing: border-box; border: 1px solid #bbb; border-radius: 3px; background: #fafafa; color: #222; }
        a, button { display: block; text-align: center; margin: 20px auto; text-decoration: none; background: #e0e0e0; color: #222; border-radius: 4px; padding: 10px 18px; border: 1px solid #bbb; transition: background 0.2s, color 0.2s; }
        a:hover, button:hover { background: #bbb; color: #000; }
        h1, h2 { text-align: center; color: #222; }
    </style>
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