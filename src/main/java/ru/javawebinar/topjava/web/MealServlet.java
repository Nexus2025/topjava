package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.dao.InMemoryMealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MealServlet extends HttpServlet {
    private MealRepository repository;

    @Override
    public void init() {
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("meals", MealsUtil.filteredByStreams(
                    repository.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("create")) {
            request.getRequestDispatcher("mealForm.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("update")) {
            request.setAttribute("meal", repository.get(Integer.parseInt(request.getParameter("mealId"))));
            request.getRequestDispatcher("mealForm.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            repository.delete(Integer.parseInt(request.getParameter("mealId")));
            response.sendRedirect("meals");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        repository.save(meal);
        response.sendRedirect("meals");
    }
}
