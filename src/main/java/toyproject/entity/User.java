package toyproject.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @Column(name = "user_id")
    private Long id;

    private String name;

    public static User createUser(Long id, String name) {

        User user = new User();
        user.id = id;
        user.name = name;

        return user;
    }

    public static List<User> addAllUser(int cnt) {

        if (cnt < 1) {
            return new ArrayList<>();
        }

        List<User> users = new ArrayList<>();

        for (int i=1; i<=cnt; i++) {
            User user = User.createUser(Long.parseLong(String.valueOf(i)), "user" + i);
            users.add(user);
        }

        return users;
    }
}
