package ma.zar.dreamshops.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private int imageId;
    private String FileName;
    private String downloadUrl;
}
