package toyproject.repository.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.entity.User;
import toyproject.springbatch.dto.TourGallery;

public interface BatchRepository extends JpaRepository<TourGallery, Long> {
}
