package ma.zar.dreamshops.service.image;
import ma.zar.dreamshops.dtos.ImageDto;
import ma.zar.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long id);
    Image updateImage(MultipartFile image,Long id);
}
