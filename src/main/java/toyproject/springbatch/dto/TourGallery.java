package toyproject.springbatch.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TourGallery {

    @Id
    @Column(name = "content_id")
    private String galContentId;

    @Column(name = "content_type_id")
    private String galContentTypeId;

    @Column(name = "title")
    private String galTitle;

    @Column(name = "web_image_url")
    private String galWebImageUrl;

    @Column(name = "photography_month")
    private String galPhotographyMonth;

    @Column(name = "photography_location")
    private String galPhotographyLocation;

    @Column(name = "photographer")
    private String galPhotographer;

    @Column(name = "searchKeyword")
    private String galSearchKeyword;

    @Column(name = "create_time")
    private String galCreatedtime;

    @Column(name = "modified_time")
    private String galModifiedtime;

    @Builder
    public TourGallery(String galContentId, String galContentTypeId, String galTitle, String galWebImageUrl, String galPhotographyMonth, String galPhotographyLocation, String galPhotographer, String galSearchKeyword, String galCreatedtime, String galModifiedtime) {
        this.galContentId = galContentId;
        this.galContentTypeId = galContentTypeId;
        this.galTitle = galTitle;
        this.galWebImageUrl = galWebImageUrl;
        this.galPhotographyMonth = galPhotographyMonth;
        this.galPhotographyLocation = galPhotographyLocation;
        this.galPhotographer = galPhotographer;
        this.galSearchKeyword = galSearchKeyword;
        this.galCreatedtime = galCreatedtime;
        this.galModifiedtime = galModifiedtime;
    }
}
