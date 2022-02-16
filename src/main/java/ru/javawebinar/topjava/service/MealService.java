package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService (MealRepository repository) {
        this.repository = repository;
    }

    public MealTo create(Meal meal, int userId) {
        repository.save(meal, userId);
        List<MealTo> userMealsTo = MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        return userMealsTo.stream().filter(mealTo -> mealTo.getId().equals(meal.getId())).findFirst().get();
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public MealTo get(int id, int userId) {
        Meal meal = checkNotFoundWithId(repository.get(id, userId), id);
        List<MealTo> userMealsTo = MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        return userMealsTo.stream().filter(mealTo -> mealTo.getId().equals(meal.getId())).findFirst().get();
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}