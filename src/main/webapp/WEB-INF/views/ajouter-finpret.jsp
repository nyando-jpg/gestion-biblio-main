<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un retour de pret</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f7f7f7; color: #222; }
        form { max-width: 400px; margin: 0 auto 30px auto; padding: 20px; border: 1px solid #bbb; border-radius: 6px; background: #fff; }
        label { display: block; margin-bottom: 8px; color: #222; }
        input, select, button { width: 100%; margin-bottom: 16px; padding: 6px; box-sizing: border-box; border: 1px solid #bbb; border-radius: 3px; background: #fafafa; color: #222; }
        a, button { display: block; text-align: center; margin: 20px auto; text-decoration: none; background: #e0e0e0; color: #222; border-radius: 4px; padding: 10px 18px; border: 1px solid #bbb; transition: background 0.2s, color 0.2s; }
        a:hover, button:hover { background: #bbb; color: #000; }
        h1, h2 { text-align: center; color: #222; }
        div[style*="color:green"], div[style*="color:red"] {
            color: #222 !important; font-weight: bold; background: #ededed !important; border: 1px solid #bbb !important;
        }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Retour de pret</h2>
    <c:if test="${not empty error}">
        <div style="color: red; text-align: center; margin-bottom: 10px;">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div style="color: green; text-align: center; margin-bottom: 10px;">${success}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/finprets/ajouter">
        <label for="idAdherant">ID Adherent :</label>
        <input type="number" id="idAdherant" name="idAdherant" required />

        <label for="idExemplaire">ID Exemplaire :</label>
        <input type="number" id="idExemplaire" name="idExemplaire" required />

        <label for="dateFin">Date de retour :</label>
        <input type="datetime-local" id="dateFin" name="dateFin" required />

        <button type="submit">Valider le retour</button>
    </form>
    <div style="text-align:center;">
        <a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
    </div>
</body>
</html>
