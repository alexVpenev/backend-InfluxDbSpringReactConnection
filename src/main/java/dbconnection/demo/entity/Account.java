package dbconnection.demo.entity;

public class Account {

    private String id;
    private String email;
    private String password;
    private Role role;

    public Account() {

    }

    public Account(String id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }


}
