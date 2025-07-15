<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Detail du livre</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f7f7f7; color: #222; }
        .container { max-width: 700px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 8px #ddd; }
        h2 { text-align: center; margin-bottom: 30px; }
        .infos { margin-bottom: 30px; }
        .infos label { font-weight: bold; display: inline-block; width: 140px; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; background: #fafafa; }
        th, td { border: 1px solid #bbb; padding: 8px; text-align: center; }
        th { background: #e0e0e0; }
        .dispo-oui { color: #28a745; font-weight: bold; }
        .dispo-non { color: #dc3545; font-weight: bold; }
        a, button { display: block; text-align: center; margin: 30px auto 0 auto; text-decoration: none; background: #e0e0e0; color: #222; border-radius: 4px; padding: 10px 18px; border: 1px solid #bbb; transition: background 0.2s, color 0.2s; }
        a:hover, button:hover { background: #bbb; color: #000; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Détail du livre</h2>
        <table>
            <c:forEach var="entry" items="${jsonLivreSimple.entrySet()}">
                <tr>
                    <th>${entry.key}</th>
                    <td>${entry.value}</td>
                </tr>
            </c:forEach>
        </table>
        <h3>Exemplaires</h3>
        <table>
            <tr>
                <th>#</th>
                <c:forEach var="col" items="${jsonExemplairesSimples[0].entrySet()}">
                    <th>${col.key}</th>
                </c:forEach>
            </tr>
            <c:forEach var="ex" items="${jsonExemplairesSimples}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <c:forEach var="col" items="${ex.entrySet()}">
                        <td>${col.value}</td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
        <a href="${pageContext.request.contextPath}/livres">Retour à la liste des livres</a>
    </div>
</body>
</html> 