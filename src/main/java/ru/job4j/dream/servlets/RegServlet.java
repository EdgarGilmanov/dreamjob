package ru.job4j.dream.servlets;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        if (PsqlStore.instOf().isExist(email)) {
            req.setAttribute("error", "Пользователь уже зарегистрирован");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        HttpSession sc = req.getSession();
        User newUser = new User(0, name, email, pass);
        PsqlStore.instOf().save(newUser);
        sc.setAttribute("user", newUser);
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
