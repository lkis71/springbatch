package toyproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.entity.User;
import toyproject.repository.batch.BatchRepository;
import toyproject.service.BatchService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BatchController {

    private final BatchService commonService;
    private final BatchRepository batchRepository;

    @PostMapping("/user")
    public void saveUser() {

        List<User> users = new ArrayList<>();

        for (int i=1; i<=10000; i++) {
            User user = User.createUser(Long.parseLong(String.valueOf(i)), "user" + i);
            users.add(user);
        }

        batchRepository.saveAll(users);
    }
}
