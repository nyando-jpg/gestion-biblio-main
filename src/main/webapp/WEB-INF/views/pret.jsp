<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { max-width: 400px; margin: 0 auto 30px auto; padding: 20px; border: 1px solid #ccc; border-radius: 6px; }
        label { display: block; margin-bottom: 8px; }
        input, select, button { width: 100%; margin-bottom: 16px; padding: 6px; box-sizing: border-box; }
        a { display: block; text-align: center; margin: 20px auto; text-decoration: underline; }
        h1 { text-align: center; }
    </style>
</head>
<body>
    <h1>Preter un Livre</h1>
    <form action="/preter" method="post">
        <label for="adherantId">ID de l'adherant :</label>
        <input list="adherant-list" id="adherantId" name="adherantId" required autocomplete="off">
        <datalist id="adherant-list">
            <c:forEach var="ad" items="${adherants}">
                <option value="${ad.idAdherant}">${ad.nomAdherant} ${ad.prenomAdherant}</option>
            </c:forEach>
        </datalist>
        <br><br>
        <label for="exemplaires">Selectionnez les exemplaires a preter :</label>
        <select id="exemplaires" name="exemplaires" multiple size="5" required>
            <c:forEach var="ex" items="${exemplaires}">
                <option value="${ex.idExemplaire}">
                    Exemplaire ${ex.idExemplaire}
                    <c:if test="${not empty ex.livre}">
                        - Livre: ${ex.livre.titre}
                    </c:if>
                </option>
            </c:forEach>
        </select>
        <br><br>
        <button type="submit">Valider le pret</button>
    </form>
    <a href="${pageContext.request.contextPath}/prets/prolongement-form">Prolonger un pret</a>
    <a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
</body>
</html>