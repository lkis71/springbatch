package toyproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.entity.User;
import toyproject.repository.batch.BatchRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository commonRepository;

    public void save(User user) {
        commonRepository.save(user);
    }
}
