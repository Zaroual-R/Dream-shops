package ma.zar.dreamshops.service.image;

import lombok.RequiredArgsConstructor;
import ma.zar.dreamshops.dtos.ImageDto;
import ma.zar.dreamshops.exceptions.ImageNotFoundException;
import ma.zar.dreamshops.model.Image;
import ma.zar.dreamshops.model.Product;
import ma.zar.dreamshops.repository.ImageRepository;
import ma.zar.dreamshops.service.product.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImageService implements  IImageService {
    private final ImageRepository imageRepository;
    private final IProductService iProductService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(Math.toIntExact(id)).orElseThrow(
                ()->new ImageNotFoundException("the image with id:"+id+" is not found")
        );
    }


    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(Math.toIntExact(id)).ifPresentOrElse(imageRepository::delete, ()->{
            throw new ImageNotFoundException("the image with id:"+id+" is not found");
        });
    }


    @Override
    public List<ImageDto>  saveImage(List<MultipartFile> files, Long ProductId) {
        Product product=iProductService.getProductById(ProductId);
        List<ImageDto> savedimageDtos=new ArrayList<>();
        for(MultipartFile file:files){
            try{
                 Image image=new Image();
                 image.setFileName(file.getOriginalFilename());
                 image.setFileType(file.getContentType());
                 image.setImage(new SerialBlob(file.getBytes()));
                 image.setProduct(product);
                 String downloadUrl="/api/v1/images/image/download/"+image.getImageId();
                 image.setDownloadUrl(downloadUrl);
                 Image savedImage=imageRepository.save(image);
                 savedImage.setDownloadUrl("/api/v1/images/image/download/"+savedImage.getImageId());
                 imageRepository.save(savedImage);

                 ImageDto imageDto=new ImageDto();
                 imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                 imageDto.setImageId(Math.toIntExact(savedImage.getImageId()));
                 imageDto.setFileName(savedImage.getFileName());

                savedimageDtos.add(imageDto);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return savedimageDtos;
    }

    //update Image
    @Override
    public Image updateImage(MultipartFile file, Long id) {
        Image image=getImageById(id);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }catch(Exception e){
            e.printStackTrace();
        }
        return image;
    }

    public List<Image> getImageByProductId(Long productId){
        try {
            List<Image> images=imageRepository.findByProductProductId(productId);
            return images;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
