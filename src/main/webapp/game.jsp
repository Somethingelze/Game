<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Игра</title>
    <style>
        p {padding-top: 40px;}
    </style>
</head>
<body>
<h1>${question}</h1>
<form action="game" method="post">
    <button type="submit" name="action" value="${answer1id}">${answer1}</button>
    <button type="submit" name="action" value="${answer2id}">${answer2}</button>
</form>
<p>
    <form>
    Количество игр: ${sessionScope.numberOfGames},<br>
    Количество побед: ${sessionScope.numberOfWins},<br>
    Количество поражений: ${sessionScope.numberOfLose}.
</form>
</body>
</html>
