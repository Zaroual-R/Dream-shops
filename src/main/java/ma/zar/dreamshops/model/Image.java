package ma.zar.dreamshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String fileName;
    private String fileType;
    @Lob
    private Blob image;
    private String  downloadUrl;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;
}
