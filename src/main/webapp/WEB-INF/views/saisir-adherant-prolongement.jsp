<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Choisir un adherant pour prolonger un pret</title>
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
<h2>Choisir un adherant</h2>
<form method="get" action="${pageContext.request.contextPath}/prets/prolonger">
    <label for="idAdherant">Selectionnez un adherant :</label>
    <select name="idAdherant" id="idAdherant" required>
        <option value="">-- Choisir un adherant --</option>
        <c:forEach var="ad" items="${adherants}">
            <option value="${ad.idAdherant}">${ad.nomAdherant} ${ad.prenomAdherant} (ID: ${ad.idAdherant})</option>
        </c:forEach>
    </select>
    <br/><br/>
    <button type="submit">Voir les prets a prolonger</button>
</form>
<a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
</body>
</html> 