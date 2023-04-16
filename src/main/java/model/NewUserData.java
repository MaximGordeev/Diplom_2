package model;

public class NewUserData {
    private String email;
    private String name;

    public NewUserData(String email, String name) {
        this.email = email;
        this.name = name;
    }
    public NewUserData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
