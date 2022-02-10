package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
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
    private final MealDAO mealDAO = new MealDAOImpl();
    private final static String CREATE_OR_UPDATE = "create-update.jsp";
    private final static String LIST_MEAL = "meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String forward = "";
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equalsIgnoreCase("create")) {
                forward = CREATE_OR_UPDATE;
            } else if (action.equalsIgnoreCase("update")) {
                try {
                    Integer id = Integer.valueOf(request.getParameter("mealId"));
                    request.setAttribute("meal", mealDAO.getMealById(id));
                    forward = CREATE_OR_UPDATE;
                } catch (NullPointerException | NumberFormatException e) { /* NOP */ }
            } else if (action.equalsIgnoreCase("delete")) {
                try {
                    Integer id = Integer.valueOf(request.getParameter("mealId"));
                    mealDAO.deleteMealById(id);
                    response.sendRedirect("meals");
                    return;
                } catch (NullPointerException | NumberFormatException e) { /* NOP */ }
            }
        } else {
            int caloriesPerDay = 2000;
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(
                    mealDAO.getAllMeals(), LocalTime.of(0, 0), LocalTime.of(23, 59), caloriesPerDay);
            request.setAttribute("mealsTo", mealsTo);
            forward = LIST_MEAL;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equalsIgnoreCase("create")) {
                try {
                    String dateTimeParam = request.getParameter("dateTime");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeParam, formatter);
                    String description = request.getParameter("description");
                    int calories = Integer.parseInt(request.getParameter("calories"));

                    Meal meal = new Meal(dateTime, description, calories);
                    mealDAO.createMeal(meal);
                } catch (NullPointerException | NumberFormatException | DateTimeParseException e) { /* NOP */ }
            } else if (action.equalsIgnoreCase("update")) {
                try {
                    Integer id = Integer.valueOf(request.getParameter("mealId"));
                    String dateTimeParam = request.getParameter("dateTime");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeParam, formatter);
                    String description = request.getParameter("description");
                    int calories = Integer.parseInt(request.getParameter("calories"));
                    Meal meal = new Meal(id, dateTime, description, calories);
                    mealDAO.updateMeal(meal);
                } catch (NullPointerException | NumberFormatException | DateTimeParseException e) { /* NOP */ }
            }
        }

        response.sendRedirect("meals");
    }
}
