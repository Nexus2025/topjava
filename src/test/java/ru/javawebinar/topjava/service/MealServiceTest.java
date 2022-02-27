package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.*;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(userMeal1.getId(), USER_ID);
        Assertions.assertThat(meal).usingRecursiveComparison().isEqualTo(userMeal1);
    }

    @Test
    public void getNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getAnotherUserMeal(){
        Assert.assertThrows(NotFoundException.class, () -> service.get(adminMeal7.getId(), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(userMeal1.getId(), USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(userMeal1.getId(), USER_ID));
    }

    @Test
    public void deleteMealNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, UserTestData.NOT_FOUND));
    }

    @Test
    public void deleteMealAnotherUser() {
        Meal anotherUserMeal = service.get(adminMeal7.getId(), ADMIN_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.delete(anotherUserMeal.getId(), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> expected = Arrays.asList(userMeal4, userMeal5, userMeal6)
                .stream()
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
        List<Meal> result = service.getBetweenInclusive(LocalDate.parse("2022-02-22"), LocalDate.parse("2022-02-22"), USER_ID);
        Assertions.assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    @Test
    public void getAll() {
        List<Meal> expected = Arrays.asList(userMeal1, userMeal2, userMeal3, userMeal4, userMeal5, userMeal6)
                .stream()
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
        List<Meal> result = service.getAll(USER_ID);
        Assertions.assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    @Test
    public void update() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(LocalDateTime.parse("2021-02-23T13:21"));
        updated.setDescription("newDescription");
        updated.setCalories(1999);
        service.update(updated, USER_ID);
        Assertions.assertThat(service.get(updated.getId(), USER_ID)).usingRecursiveComparison().isEqualTo(updated);
    }

    @Test
    public void updateAnotherUserMeal() {
        Meal updated = service.get(adminMeal7.getId(), ADMIN_ID);
        updated.setDateTime(LocalDateTime.parse("2022-11-11T11:11"));
        updated.setDescription("updateAnotherUserMeal");
        updated.setCalories(999);
        Assert.assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void create() {
        Meal actual = service.create(new Meal(LocalDateTime.parse("2021-02-23T13:21"), "newDescription", 1), USER_ID);
        Integer newId = actual.getId();
        Meal newMeal = new Meal(LocalDateTime.parse("2021-02-23T13:21"), "newDescription", 1);
        newMeal.setId(newId);
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(newMeal);
        Assertions.assertThat(service.get(newId, USER_ID)).usingRecursiveComparison().isEqualTo(newMeal);
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateDateTimeCreate() {
        Meal dateTimeDuplicate = service.create(new Meal(LocalDateTime.parse("2022-02-23T07:00"), "newDescription", 1), USER_ID);
        service.create(dateTimeDuplicate, USER_ID);
    }
}