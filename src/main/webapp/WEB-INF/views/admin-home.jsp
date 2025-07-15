<!DOCTYPE html>
<html>
<head>
    <title>Accueil Admin</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f7f7f7; color: #222; }
        a, button {
            display: block;
            text-align: center;
            margin: 20px auto;
            text-decoration: none;
            background: #e0e0e0;
            color: #222;
            border-radius: 4px;
            padding: 10px 18px;
            border: 1px solid #bbb;
            transition: background 0.2s, color 0.2s;
        }
        a:hover, button:hover {
            background: #bbb;
            color: #000;
        }
        h2 { text-align: center; color: #222; }
    </style>
</head>
<body>
    <h2>Bienvenue, admin !</h2>
    <a href="${pageContext.request.contextPath}/adherants">adherents</a>
    <br/>
    <a href="${pageContext.request.contextPath}/livres">livres</a>
    <br/>
    <a href="${pageContext.request.contextPath}/prets/faire">Faire un pret</a>
    <br/>
    <a href="${pageContext.request.contextPath}/penalites">penalites</a>
    <br/>
    <a href="${pageContext.request.contextPath}/finprets/ajouter">Retour de pret</a>
    <br/>
    <a href="${pageContext.request.contextPath}/reservations">reservations</a>
    <br/>
    <a href="${pageContext.request.contextPath}/prets/prolongement-form">Prolonger un pret</a>
</body>
</html>
