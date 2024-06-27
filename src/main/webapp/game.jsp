<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Игра</title>
</head>
<body>
<h1>${question}</h1>
<form action="game" method="post">
    <button type="submit" name="action" value="action1">${answer_1}</button>
    <button type="submit" name="action" value="action2">${answer_2}</button>
</form>
</body>
</html>
