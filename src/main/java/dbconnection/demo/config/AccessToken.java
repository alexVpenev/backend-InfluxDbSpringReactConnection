package dbconnection.demo.config;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class AccessToken {
    private Long user_id;
    private List<String> role;
    private String subject;

    public AccessToken (Long user_id, List<String> role, String subject) {
        this.user_id = user_id;
        this.role = role;
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
    public List<String> getRole() {
        return role;
    }
}
