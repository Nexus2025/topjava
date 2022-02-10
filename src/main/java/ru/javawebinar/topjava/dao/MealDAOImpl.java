package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealDAOImpl implements MealDAO {

    @Override
    public List<Meal> getAllMeals() {
        return MockDatabase.selectAll();
    }

    @Override
    public Meal getMealById(int id) {
        return MockDatabase.getById(id);
    }

    @Override
    public void createMeal(Meal meal) {
        MockDatabase.create(meal);
    }

    @Override
    public void deleteMealById(int id) {
        MockDatabase.delete(id);
    }

    @Override
    public void updateMeal(Meal meal) {
        MockDatabase.update(meal);
    }
}
