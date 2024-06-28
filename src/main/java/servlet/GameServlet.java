package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Data;
import model.Question;
import service.QuestionService;

import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet(name = "GameServlet", value = "/game")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QuestionService questionService = new QuestionService();

        try {
            Data data = questionService.readFromFile();
            if (req.getAttribute("questionId") == null) {
                req.setAttribute("questionId", 1);
                req.setAttribute("question", data.questions.get(0).getQuestion());
                req.setAttribute("answer_1", data.answers.get(0).getName());
                req.setAttribute("answer_1_id", data.answers.get(0).getId());
                req.setAttribute("answer_2", data.answers.get(1).getName());
                req.setAttribute("answer_2_id", data.answers.get(1).getId());

                req.getRequestDispatcher("/game.jsp").forward(req, resp);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QuestionService questionService = new QuestionService();

        try {
            Data data = questionService.readFromFile();
            int questionId = Integer.parseInt(req.getParameter("questionId"));
            int answerId = Integer.parseInt(req.getParameter("action"));
            Question question = data.questions.stream().filter(q -> q.getId() == questionId).findFirst().orElse(null);
            int nextQuestionId = data.answers.stream().filter(a -> a.getId() == answerId).findFirst().orElse(null).getQuestion();

            if (nextQuestionId == 0 || question == null || question.isFailed()) {
                req.setAttribute("result", "Поражение");
                req.getRequestDispatcher("/result.jsp").forward(req, resp);
            } else {
                Question nextQuestion = data.questions.stream().filter(q -> q.getId() == nextQuestionId).findFirst().orElse(null);
                if (nextQuestion.isFailed()) {
                    req.setAttribute("result", "Поражение");
                    req.getRequestDispatcher("/result.jsp").forward(req, resp);
                } else if (nextQuestion.getSuccess()) {
                    req.setAttribute("result", "Победа");
                    req.getRequestDispatcher("/result.jsp").forward(req, resp);
                } else {
                    req.setAttribute("questionId", nextQuestion.getId());
                    req.setAttribute("question", nextQuestion.getQuestion());
                    req.setAttribute("answer_1", data.answers.get(nextQuestion.getAnswers().get(0) - 1).getName());
                    req.setAttribute("answer_1_id", data.answers.get(nextQuestion.getAnswers().get(0) - 1).getId());
                    req.setAttribute("answer_2", data.answers.get(nextQuestion.getAnswers().get(1) - 1).getName());
                    req.setAttribute("answer_2_id", data.answers.get(nextQuestion.getAnswers().get(1) - 1).getId());

                    req.getRequestDispatcher("/game.jsp").forward(req, resp);
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
