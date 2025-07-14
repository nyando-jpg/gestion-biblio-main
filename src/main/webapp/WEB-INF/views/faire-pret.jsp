<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Faire un prêt</title>
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
    <h2 style="text-align:center;">Faire un prêt</h2>
    <c:if test="${not empty error}">
        <div style="color:red; font-weight:bold; margin-bottom:10px; text-align:center;">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div style="color:green; font-weight:bold; margin-bottom:10px; text-align:center;">${success}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/prets/creer">
        <label for="idAdherant">ID Adhérant :</label>
        <input type="text" id="idAdherant" name="idAdherant" required />

        <label for="idExemplaire">ID Exemplaire :</label>
        <input type="text" id="idExemplaire" name="idExemplaire" required />

        <label for="idTypePret">Type de prêt :</label>
        <select id="idTypePret" name="idTypePret" required>
            <c:forEach var="tp" items="${typesPret}">
                <option value="${tp.idTypePret}">${tp.type}</option>
            </c:forEach>
        </select>

        <label for="datePret">Date de prêt :</label>
        <input type="datetime-local" id="datePret" name="datePret" required />

        <button type="submit">Valider le prêt</button>
    </form>
    <a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
</body>
</html>
