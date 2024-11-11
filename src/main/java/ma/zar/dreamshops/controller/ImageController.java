package ma.zar.dreamshops.controller;

import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.dtos.ImageDto;
import ma.zar.dreamshops.exceptions.ResourceNotFoundException;
import ma.zar.dreamshops.model.Image;
import ma.zar.dreamshops.model.Product;
import ma.zar.dreamshops.responce.ApiResponce;
import ma.zar.dreamshops.service.image.IImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponce> saveImage(@RequestParam List<MultipartFile> files,@RequestParam Long productId){
        try{
            List<ImageDto> imageDtos = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponce("upload seccessful",imageDtos));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponce("upload failed",e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image=imageService.getImageById(imageId);
        ByteArrayResource resource=new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attchement ;filename=\""+image.getFileName()+"\"")
                .body(resource);

    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponce>  updateImage(@PathVariable Long imageId,@RequestBody MultipartFile multipartFile){
        Image image=imageService.getImageById(imageId);
        try {
            if(image!=null){
                Image updatedImage=imageService.updateImage(multipartFile ,imageId);
                return ResponseEntity.ok(new ApiResponce("upsate success!", updatedImage));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponce("update failed", e.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponce("update failed", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponce>  updateImage(@PathVariable Long imageId){
        Image image=imageService.getImageById(imageId);
        try {
            if(image!=null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponce("upsate success!",null));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponce("update failed", e.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponce("update failed", INTERNAL_SERVER_ERROR));
    }

}
