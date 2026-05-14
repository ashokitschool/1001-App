package in.ashokit.service.impl;

import in.ashokit.constants.AppConstants;
import in.ashokit.dto.ProductDto;
import in.ashokit.entity.ProductEntity;
import in.ashokit.mapper.ProductMapper;
import in.ashokit.repo.ProductCategoryRepository;
import in.ashokit.repo.ProductRepository;
import in.ashokit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${app.images.upload-dir}")
    private String imagesUploadDir;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductDto createProduct(Integer categoryId, ProductDto productDto, MultipartFile productImage) throws Exception {

        UUID uuid = UUID.randomUUID();

        String originalFilename = uuid.toString() + "_" + productImage.getOriginalFilename();

        Path filePath = Paths.get(imagesUploadDir + originalFilename);

        // create uploading dir if it is not available
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath.getParent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // save the file to specified location
        Files.copy(productImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        productDto.setImageUrl(filePath.toString());

        ProductEntity productEntity = ProductMapper.toEntity(productDto);

        productCategoryRepository.findById(categoryId).ifPresent(category -> {
            productEntity.setCategory(category);
        });

        ProductEntity savedEntity = productRepository.save(productEntity); // UPSERT

        return ProductMapper.toDto(savedEntity);

    }

    @Override
    public ProductDto updateProduct(Integer productId, ProductDto productDto, MultipartFile productImage) throws Exception {

        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        UUID uuid = UUID.randomUUID();

        String originalFilename = uuid.toString() + "_" + productImage.getOriginalFilename();

        Path filePath = Paths.get(imagesUploadDir + originalFilename);

        // Create the directory if it doesn't exist
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath.getParent());
            } catch (IOException e) {
                throw new RuntimeException("Failed to save product image", e);
            }
        }

        // Save the file to the specified location
        Files.copy(productImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        productEntity.setImageUrl(filePath.toString());
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setUnitsStock(productDto.getUnitsStock());
        // productEntity.setActive(productDto.isActive());
        productEntity.setUnitPrice(productDto.getUnitPrice());

        ProductEntity updatedEntity = productRepository.save(productEntity); // UPSERT
        return ProductMapper.toDto(updatedEntity);
    }

    @Override
    public List<ProductDto> getAllProductsByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId).stream().map(ProductMapper::toDto).toList();
    }

    @Override
    public ProductDto getProductById(Integer productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return ProductMapper.toDto(productEntity);
    }

    @Override
    public List<ProductDto> getProductsByName(String productName) {
        return productRepository.findByNameContainingIgnoreCase(productName).stream().map(ProductMapper::toDto).toList();
    }

    @Override
    public ProductDto deleteProduct(Integer productId) {

        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // soft delete - update the active status to false instead of deleting the record from the database
        productEntity.setActive(AppConstants.NO);
        ProductEntity save = productRepository.save(productEntity);

        return ProductMapper.toDto(save);
    }
}
