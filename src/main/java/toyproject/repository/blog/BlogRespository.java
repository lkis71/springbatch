package toyproject.repository.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BlogRespository {

    private final EntityManager em;
}
