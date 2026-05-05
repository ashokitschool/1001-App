package in.ashokit.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductDto {

    private Integer productId;
    private String name;
    private String description;
    private String title;
    private Double unitPrice;
    private String imageUrl;
    private String active;
    private Integer unitsStock;
    private Integer createdBy;
    private Integer updatedBy;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
