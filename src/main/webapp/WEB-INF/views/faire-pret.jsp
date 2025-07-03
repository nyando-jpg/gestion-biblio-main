<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Faire un prêt</title>
    <style>
        form { width: 350px; margin: 40px auto; padding: 20px; border: 1px solid #ccc; border-radius: 6px; background: #f9f9f9; }
        label { display: block; margin-bottom: 8px; font-weight: bold; }
        input[type="text"] { width: 100%; padding: 8px; margin-bottom: 16px; border: 1px solid #ccc; border-radius: 4px; }
        button { padding: 10px 20px; background: #007bff; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Faire un prêt</h2>
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

        <button type="submit">Valider le prêt</button>
    </form>
</body>
</html>
