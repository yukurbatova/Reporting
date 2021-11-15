package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationInfo;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.generateCity;
import static ru.netology.data.DataGenerator.Registration.generateDate;

public class DeliveryCardTest {
    RegistrationInfo info = DataGenerator.Registration.generate("ru");

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldGenerateTestDataSuccessTest() {
        $("[data-test-id=city] .input__control").setValue(generateCity());
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(generateDate(3));
        $("[data-test-id=name] .input__control").setValue(info.getName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Запланировать")).click();
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + generateDate(3)));
        $("[data-test-id=success-notification] .icon-button__content").click();
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(generateDate(5));
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__title").shouldHave(exactText("Необходимо подтверждение"));
        $(byText("Перепланировать")).click();
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + generateDate(5)));
    }

    @Test
    void shouldGenerateTestDataFailedTest() {
        $("[data-test-id=city] .input__control").setValue(generateCity());
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(generateDate(2));
        $("[data-test-id=name] .input__control").setValue(info.getName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Запланировать")).click();
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + generateDate(3)));
        $("[data-test-id=success-notification] .icon-button__content").click();
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(generateDate(5));
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__title").shouldHave(exactText("Необходимо подтверждение"));
        $(byText("Перепланировать")).click();
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + generateDate(5)));
    }
}