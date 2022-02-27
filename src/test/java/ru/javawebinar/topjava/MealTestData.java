package ru.javawebinar.topjava;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 10;

    public static final Meal userMeal1 = new Meal(START_SEQ + 3, LocalDateTime.parse("2022-02-23T07:00"), "завтрак", 500);
    public static final Meal userMeal2 = new Meal(START_SEQ + 4, LocalDateTime.parse("2022-02-23T13:00"), "обед", 300);
    public static final Meal userMeal3 = new Meal(START_SEQ + 5, LocalDateTime.parse("2022-02-23T18:00"), "ужин", 400);
    public static final Meal userMeal4 = new Meal(START_SEQ + 6, LocalDateTime.parse("2022-02-22T08:00"), "завтрак", 1000);
    public static final Meal userMeal5 = new Meal(START_SEQ + 7, LocalDateTime.parse("2022-02-22T14:00"), "обед", 500);
    public static final Meal userMeal6 = new Meal(START_SEQ + 8, LocalDateTime.parse("2022-02-22T19:00"), "ужин", 499);
    public static final Meal adminMeal7 = new Meal(START_SEQ + 9, LocalDateTime.parse("2022-02-21T08:00"), "завтрак", 200);
    public static final Meal adminMeal8 = new Meal(START_SEQ + 10, LocalDateTime.parse("2022-02-21T15:00"), "обед", 1000);
    public static final Meal adminMeal9 = new Meal(START_SEQ + 11, LocalDateTime.parse("2022-02-21T20:00"), "ужин", 700);
}
