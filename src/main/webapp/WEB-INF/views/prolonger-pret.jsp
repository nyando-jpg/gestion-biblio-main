<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Prolonger un pret</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f7f7f7; color: #222; }
        form { max-width: 400px; margin: 0 auto 30px auto; padding: 20px; border: 1px solid #bbb; border-radius: 6px; background: #fff; }
        label { display: block; margin-bottom: 8px; color: #222; }
        input, select, button { width: 100%; margin-bottom: 16px; padding: 6px; box-sizing: border-box; border: 1px solid #bbb; border-radius: 3px; background: #fafafa; color: #222; }
        a, button { display: block; text-align: center; margin: 20px auto; text-decoration: none; background: #e0e0e0; color: #222; border-radius: 4px; padding: 10px 18px; border: 1px solid #bbb; transition: background 0.2s, color 0.2s; }
        a:hover, button:hover { background: #bbb; color: #000; }
        h1, h2 { text-align: center; color: #222; }
        div[style*="color:green"], div[style*="color:red"], div[style*="color:blue"] {
            color: #222 !important; font-weight: bold; background: #ededed !important; border: 1px solid #bbb !important;
        }
    </style>
</head>
<body>
<h2>Prolonger un pret</h2>

<c:if test="${not empty error}">
    <div style="color:red;">${error}</div>
</c:if>
<c:if test="${not empty success}">
    <div style="color:green;">${success}</div>
</c:if>
<c:if test="${not empty info}">
    <div style="color:blue;">${info}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/prets/prolonger">
    <input type="hidden" name="idAdherant" value="${idAdherant}" />
    <label for="idPret">Selectionnez un pret a prolonger :</label>
    <select name="idPret" id="idPret" required>
        <option value="">-- Choisir un pret --</option>
        <c:forEach var="pret" items="${pretsActifs}">
            <option value="${pret.idPret}">
                Livre : ${pret.exemplaire.livre.titre} | Debut : ${pret.dateDebut} | Exemplaire : ${pret.exemplaire.idExemplaire}
            </option>
        </c:forEach>
    </select>
    <br/><br/>
    <button type="submit">Prolonger le pret</button>
</form>
<a href="${pageContext.request.contextPath}/admin/home"><button type="button">Retour Admin</button></a>
</body>
</html> 