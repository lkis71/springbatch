package toyproject.repository.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.entity.User;

public interface BatchRepository extends JpaRepository<User, Long> {
}
