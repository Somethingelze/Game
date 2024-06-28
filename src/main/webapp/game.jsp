<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Игра</title>
</head>
<body>
<h1>${question}</h1>
<form action="question" method="post">
    <button type="submit" name="action" value="action1">${answer_1}</button>
    <input type="hidden" name="next_question_id" value="${next_question_1}">
</form>
<form action="question" method="post">
    <button type="submit" name="action" value="action2">${answer_2}</button>
    <input type="hidden" name="next_question_id" value="${next_question_2}">
</form>
</body>
</html>
