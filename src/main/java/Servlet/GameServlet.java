package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Data;
import service.QuestionService;

import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet(name = "GameServlet", value = "/question")
public class GameServlet extends HttpServlet {

    private Data data;

    @Override
    public void init() throws ServletException {
        super.init();
        QuestionService questionService = new QuestionService();
        try {
            data = questionService.readFromFile();
        } catch (IOException | URISyntaxException e) {
            throw new ServletException("Failed to load questions", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getAttribute("question") == null) {
            req.setAttribute("question", data.getQuestions().get(0).getQuestion());
            req.setAttribute("answer_1", data.getAnswers().get(0).getName());
            req.setAttribute("answer_2", data.getAnswers().get(1).getName());

            req.getRequestDispatcher("/game.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            int nextQuestionId = 0;
            if (action.equals("action1")) {
                nextQuestionId = data.getAnswers().get(0).getQuestion();
            } else if (action.equals("action2")) {
                nextQuestionId = data.getAnswers().get(1).getQuestion();
            }

            for (model.Question question : data.getQuestions()) {
                if (question.getId() == nextQuestionId) {
                    req.setAttribute("question", question.getQuestion());
                    if (question.isFailed()) {
                        req.setAttribute("result", "Поражение");
                        req.getRequestDispatcher("/result.jsp").forward(req, resp);
                    } else {
                        req.setAttribute("answer_1", data.getAnswers().get(0).getName());
                        req.setAttribute("answer_2", data.getAnswers().get(1).getName());
                        req.getRequestDispatcher("/game.jsp").forward(req, resp);
                    }
                    return;
                }
            }
        }
        resp.sendRedirect("/question");
    }
}
