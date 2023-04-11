package utils;

import model.NewUserData;
import org.apache.commons.lang3.RandomStringUtils;

public class UserNewDataGenerator {
    public NewUserData random() {
        return new NewUserData(RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru", RandomStringUtils.randomAlphabetic(8));
    }
}
