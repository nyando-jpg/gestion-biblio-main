<!DOCTYPE html>
<html>
<head>
    <title>Accueil Admin</title>
</head>
<body>
    <h2>Bienvenue, admin !</h2>
    <a href="${pageContext.request.contextPath}/adherants" style="display:inline-block;margin:20px 0 10px 0;padding:10px 18px;background:#007bff;color:#fff;text-decoration:none;border-radius:4px;">Liste des adhérents</a>
    <br/>
    <a href="${pageContext.request.contextPath}/livres" style="display:inline-block;margin:10px 0 20px 0;padding:10px 18px;background:#28a745;color:#fff;text-decoration:none;border-radius:4px;">Liste des livres</a>
    <br/>
    <a href="${pageContext.request.contextPath}/prets/faire" style="display:inline-block;margin:10px 0 20px 0;padding:10px 18px;background:#ffc107;color:#333;text-decoration:none;border-radius:4px;">Faire un prêt</a>
    <br/>
    <a href="${pageContext.request.contextPath}/penalites" style="display:inline-block;margin:10px 0 20px 0;padding:10px 18px;background:#dc3545;color:#fff;text-decoration:none;border-radius:4px;">Lister les pénalités</a>
</body>
</html>
