<!DOCTYPE html>
<html>
<head>
    <title>Accueil Admin</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        a { display: block; text-align: center; margin: 20px auto; text-decoration: underline; }
        h2 { text-align: center; }
    </style>
</head>
<body>
    <h2>Bienvenue, admin !</h2>
    <a href="${pageContext.request.contextPath}/adherants" style="display:inline-block;margin:20px 0 10px 0;padding:10px 18px;background:#007bff;color:#fff;text-decoration:none;border-radius:4px;">adherents</a>
    <br/>
    <a href="${pageContext.request.contextPath}/livres" style="display:inline-block;margin:10px 0 20px 0;padding:10px 18px;background:#28a745;color:#fff;text-decoration:none;border-radius:4px;">livres</a>
    <br/>
    <a href="${pageContext.request.contextPath}/prets/faire" style="display:inline-block;margin:10px 0 20px 0;padding:10px 18px;background:#ffc107;color:#333;text-decoration:none;border-radius:4px;">Faire un pret</a>
    <br/>
    <a href="${pageContext.request.contextPath}/penalites" style="display:inline-block;margin:10px 0 20px 0;padding:10px 18px;background:#dc3545;color:#fff;text-decoration:none;border-radius:4px;">penalites</a>
    <br/>
    <a href="${pageContext.request.contextPath}/finprets/ajouter" style="display:inline-block;margin:10px 0 20px 0;padding:10px 18px;background:#6c757d;color:#fff;text-decoration:none;border-radius:4px;">Retour de pret</a>
    <br/>
    <a href="${pageContext.request.contextPath}/reservations" style="display:inline-block;margin:10px 0 20px 0;padding:10px 18px;background:#17a2b8;color:#fff;text-decoration:none;border-radius:4px;">reservations</a>
    <br/>
    <a href="${pageContext.request.contextPath}/prets/prolongement-form" style="display:inline-block;margin:10px 0 20px 0;padding:10px 18px;background:#6610f2;color:#fff;text-decoration:none;border-radius:4px;">Prolonger un pret</a>
</body>
</html>
