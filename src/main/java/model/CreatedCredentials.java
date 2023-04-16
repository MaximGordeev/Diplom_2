package model;

public class CreatedCredentials {
    private String email = "jojomail@yandex.ru";
    private String password;
    private String name;

    public CreatedCredentials(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public CreatedCredentials() {

    }
    public static CreatedCredentials from(User user) {
        return new CreatedCredentials(user.getEmail(), user.getPassword(), user.getName());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
