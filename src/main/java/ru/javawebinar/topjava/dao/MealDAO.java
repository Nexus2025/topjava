package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    List<Meal> getAllMeals();
    Meal getMealById(int id);
    void createMeal(Meal meal);
    void deleteMealById(int id);
    void updateMeal(Meal meal);
}
