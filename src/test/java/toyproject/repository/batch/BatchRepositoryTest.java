package toyproject.repository.batch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import toyproject.entity.User;
import toyproject.service.BatchService;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchRepositoryTest {

    @Autowired
    BatchService batchService;

    @Autowired
    BatchRepository batchRepository;

    @Test
    public void svaeUser() throws Exception {
        //given
        User user = User.createUser(1L, "사용자1");

        //when
        batchService.save(user);

        //then
        Optional<User> findUser = batchRepository.findById(user.getId());

        Assertions.assertThat("사용자1").isEqualTo(findUser.get().getName());
    }
}