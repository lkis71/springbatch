package toyproject.springbatch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Getter
@NoArgsConstructor
public class TourGallery {

    private String galContentId;
    private String galContentTypeId;
    private String galTitle;
    private String galWebImageUrl;
    private String galCreatedtime;
    private String galModifiedtime;
    private String galPhotographyMonth;
    private String galPhotographyLocation;

    public static TourGallery setTourGalleries(JSONObject object) {

        TourGallery tourGallery = new TourGallery();
        tourGallery.galContentId = String.valueOf(object.get("galContentId"));
        tourGallery.galContentTypeId = String.valueOf(object.get("galContentTypeId"));
        tourGallery.galTitle = String.valueOf(object.get("galTitle"));
        tourGallery.galWebImageUrl = String.valueOf(object.get("galWebImageUrl"));
        tourGallery.galCreatedtime = String.valueOf(object.get("galCreatedtime"));
        tourGallery.galModifiedtime = String.valueOf(object.get("galModifiedtime"));
        tourGallery.galPhotographyMonth = String.valueOf(object.get("galPhotographyMonth"));
        tourGallery.galPhotographyLocation = String.valueOf(object.get("galPhotographyLocation"));

        return tourGallery;
    }
}
