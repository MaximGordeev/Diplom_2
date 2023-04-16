package setup;

import model.User;
import org.apache.commons.lang3.RandomStringUtils;

public class UserGen {
    public User random() {
        return new User(RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru", "1234567890", "name");
    }
    public User generic() {
        return new User("username@yandex.ru", "1234567890", "name");
    }
}
