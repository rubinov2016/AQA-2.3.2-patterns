package ru.netology.testmode.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    //наличие пользователя;
    @Test
    @DisplayName("1 Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(15));

    }

    //наличие пользователя;
    @Test
    @DisplayName("2 Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Неверно указан логин")).shouldBe(visible, Duration.ofSeconds(15));
    }

    //статус пользователя;
    @Test
    @DisplayName("3 Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Пользователь заблокирован")).shouldBe(visible, Duration.ofSeconds(15));
    }

    //невалидный логин;
    @Test
    @DisplayName("4 Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        $("[data-test-id=login] input").setValue(wrongLogin);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Неверно указан логин")).shouldBe(visible, Duration.ofSeconds(15));
    }

    //невалидный пароль.
    @Test
    @DisplayName("5 Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongPassword);
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Неверно указан логин")).shouldBe(visible, Duration.ofSeconds(15));
    }
}
