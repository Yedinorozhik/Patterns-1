package ru.netology.delivery.data;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.Character.getName;
import static ru.netology.delivery.data.DataGenerator.*;
import static ru.netology.delivery.data.DataGenerator.Registration.faker;

public class GeneratorTest {

    @BeforeAll
    static void setUpAlll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999/");
    }

    @Test
    void positiveEntryForm() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        String firstDate = generateDate(3);
        String newDate = generateDate(6);
        Allure.step("Заполнение данных для доставки карты", () -> {
            $("[data-test-id='city'] input").setValue(generateCity());
            $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE)).setValue(firstDate);
            $("[data-test-id='name'] input").setValue(validUser.getName());
            $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        } );
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstDate))
                .shouldBe(Condition.visible);
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE)).setValue(newDate);
        $("button.button").click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible);
        $("button.button").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + newDate))
                .shouldBe(Condition.visible);
    }
}
