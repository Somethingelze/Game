package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        HttpSession session = req.getSession();

        Integer numberOfGames = (Integer) session.getAttribute("numberOfGames");
        Integer numberOfWins = (Integer) session.getAttribute("numberOfWins");
        Integer numberOfLose = (Integer) session.getAttribute("numberOfLose");

        if (numberOfGames == null) session.setAttribute("numberOfGames", 0);
        if (numberOfWins == null) session.setAttribute("numberOfWins", 0);
        if (numberOfLose == null) session.setAttribute("numberOfLose", 0);

        try {
            Data data = questionService.readFromFile();
            if (req.getAttribute("questionId") == null) {
                req.setAttribute("questionId", 1);
                req.setAttribute("question", data.questions.get(0).getQuestion());
                req.setAttribute("answer1", data.answers.get(0).getName());
                req.setAttribute("answer1id", data.answers.get(0).getId());
                req.setAttribute("answer2", data.answers.get(1).getName());
                req.setAttribute("answer2id", data.answers.get(1).getId());

                req.getRequestDispatcher("/game.jsp").forward(req, resp);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        QuestionService questionService = new QuestionService();
        HttpSession session = req.getSession();

        Integer numberOfGames = (Integer) session.getAttribute("numberOfGames");
        Integer numberOfWins = (Integer) session.getAttribute("numberOfWins");
        Integer numberOfLose = (Integer) session.getAttribute("numberOfLose");

        try {
            Data data = questionService.readFromFile();
            int answerId = Integer.parseInt(req.getParameter("action"));
            int nextQuestionId = data.answers.stream()
                    .filter(a -> a.getId() == answerId)
                    .findFirst()
                    .orElse(null)
                    .getQuestion();
            Question nextQuestion = data.questions.stream()
                    .filter(q -> q.getId() == nextQuestionId)
                    .findFirst()
                    .orElse(null);

            if (nextQuestion.isFailed()) {
                req.setAttribute("result", nextQuestion.getQuestion());
                session.setAttribute("numberOfGames", ++numberOfGames);
                session.setAttribute("numberOfLose", ++numberOfLose);
            } else if (nextQuestion.isSuccess()) {
                req.setAttribute("result", nextQuestion.getQuestion());
                session.setAttribute("numberOfGames", ++numberOfGames);
                session.setAttribute("numberOfWins", ++numberOfWins);
            } else {
                req.setAttribute("questionId", nextQuestion.getId());
                req.setAttribute("question", nextQuestion.getQuestion());
                req.setAttribute("answer1", data.answers.get(nextQuestion.getAnswers().get(0) - 1).getName());
                req.setAttribute("answer1id", data.answers.get(nextQuestion.getAnswers().get(0) - 1).getId());
                req.setAttribute("answer2", data.answers.get(nextQuestion.getAnswers().get(1) - 1).getName());
                req.setAttribute("answer2id", data.answers.get(nextQuestion.getAnswers().get(1) - 1).getId());

                req.getRequestDispatcher("/game.jsp").forward(req, resp);
                return;
            }
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}