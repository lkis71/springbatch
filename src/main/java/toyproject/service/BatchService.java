package toyproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.entity.User;
import toyproject.repository.batch.BatchRepository;
import toyproject.springbatch.dto.TourGallery;

@Service
@Transactional
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository commonRepository;

    public void save(TourGallery tourGallery) {
        commonRepository.save(tourGallery);
    }
}
