package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MockDatabase {
    private static final List<Meal> meals = new CopyOnWriteArrayList<>();
    private static AtomicInteger idCount = new AtomicInteger(0);

    static {
        meals.add(new Meal(idCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(idCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(idCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(idCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(idCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(idCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(idCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public static List<Meal> selectAll() {
        return new ArrayList<>(meals);
    }

    public static Meal getById(int id) {
        for (Meal meal : meals) {
            if (meal.getId().equals(id)) {
                return meal;
            }
        }
        return new Meal();
    }

    public static void create(Meal meal) {
        meals.add(new Meal(idCount.incrementAndGet(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    public static void update(Meal meal) {
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId().equals(meal.getId())) {
                meals.set(i, meal);
            }
        }
    }

    public static void delete(int id) {
        meals.removeIf(meal -> meal.getId().equals(id));
    }
}
