package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {
    //генерация строки с датой
    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

        @Test
        void shouldSendFormCardDelivery() {

            Selenide.open("http://localhost:9999");
            String planningDate = generateDate(4, "dd.MM.yyyy");

            $("[data-test-id='city'] input").setValue("Москва");
            $("[data-test-id='name'] input").setValue("Макаров Иван");
            $("[data-test-id='phone'] input").setValue("+79991112233");
            $("[data-test-id='agreement']").click();
            $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
            $("[data-test-id='date'] input").setValue(planningDate);
            $(".button span.button__content").click();
            $("[data-test-id='notification']")
                    .should(Condition.visible, Duration.ofSeconds(15))
                    .should(Condition.text("Встреча успешно забронирована на " + planningDate));

        }
}
