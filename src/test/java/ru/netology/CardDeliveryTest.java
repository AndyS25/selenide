package ru.netology;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {
    //генерация строки с датой
    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    //Позитивный тест отправки валидных данных
    @Test
    void shouldSendFormCardDelivery() {

        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Макаров Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='notification']")
                .should(visible, Duration.ofSeconds(15))
                .should(text("Встреча успешно забронирована на " + planningDate));
    }

    //Негативные тесты

    //тесты поля City
    @Test
    void shouldEmptyCitySendFormCardDelivery() {

        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(3, "dd.MM.yyyy");

        //$("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Макаров Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldUnknownCitySendFormCardDelivery() {

        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Химки");
        $("[data-test-id='name'] input").setValue("Макаров Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldBe(visible).shouldHave(text("Доставка в выбранный город недоступна"));
    }

    //тесты поля Name
    @Test
    void shouldEmptyNameSendFormCardDelivery() {

        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        //$("[data-test-id='name'] input").setValue("Макаров Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldLatinNameSendFormCardDelivery() {

        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Makarov Ivan");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldBe(visible).shouldHave(text("Имя и Фамилия указаные неверно. " +
                        "Допустимы только русские буквы, пробелы и дефисы."));
    }

    //тесты поля Phone
    @Test
    void shouldEmptyPhoneSendFormCardDelivery() {

        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Макаров Иван");
        //$("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldInvalidFormatPhoneSendFormCardDelivery() {

        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Макаров Иван");
        $("[data-test-id='phone'] input").setValue("+799911122");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void shouldNotFirstPlusInPhoneSendFormCardDelivery() {

        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Макаров Иван");
        $("[data-test-id='phone'] input").setValue("7999+1112233");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    //тест отметки чек-бокса
    @Test
    void shouldCheckBoxNotCheckedSendFormCardDelivery() {

        Selenide.open("http://localhost:9999");
        String planningDate = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Макаров Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        // $("[data-test-id='agreement']").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $(".button span.button__content").click();
        $("[data-test-id='agreement'].input_invalid")
                .shouldBe(visible)
                .shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
