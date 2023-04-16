package toyproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.entity.User;
import toyproject.repository.batch.BatchRepository;
import toyproject.service.BatchService;
import toyproject.springbatch.api.TourListApiConnection;
import toyproject.springbatch.dto.TourGallery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BatchController {

    private final BatchService commonService;
    private final BatchRepository batchRepository;

    @PostMapping("/user")
    public List<TourGallery> saveUser() throws JsonProcessingException {

        JSONObject tourList = TourListApiConnection.getTourList();
        JSONObject items = tourList.getJSONObject("items");
        JSONArray itemArr = items.getJSONArray("item");

        List<TourGallery> tourGalleries = new ArrayList<>();

        for (Object item : itemArr) {
            TourGallery tourGallery = new ObjectMapper().readValue(item.toString(), TourGallery.class);
            tourGalleries.add(tourGallery);
        }

        batchRepository.saveAll(tourGalleries);

        return tourGalleries;
    }
}
