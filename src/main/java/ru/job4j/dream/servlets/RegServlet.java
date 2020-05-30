package ru.job4j.dream.servlets;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("get").equals("cities")) {
            sendCities(req, resp);
        }
        if (req.getParameter("get").equals("registration")) {
            registration(req, resp);
        }
    }

    private void registration(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        String city = req.getParameter("city");
        if (PsqlStore.instOf().isExist(email)) {
            req.setAttribute("error", "Пользователь уже зарегистрирован");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        HttpSession sc = req.getSession();
        int cityId = PsqlStore.instOf().findCityByName(city);
        User newUser = new User(0, name, email, pass, cityId);
        PsqlStore.instOf().save(newUser);
        sc.setAttribute("user", newUser);
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }

    private void sendCities(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        List<String> cities = PsqlStore.instOf().getAllCities();
        try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream()))) {
            for (String c : cities) {
                wr.write(c);
                wr.write("#");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
