package ru.gb.bulgakov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.bulgakov.controller.NotFoundException;
import ru.gb.bulgakov.controller.dto.BrandDto;
import ru.gb.bulgakov.controller.dto.CategoryDto;
import ru.gb.bulgakov.controller.dto.ProductDto;
import ru.gb.bulgakov.persist.BrandRepository;
import ru.gb.bulgakov.persist.CategoryRepository;
import ru.gb.bulgakov.persist.ProductRepository;
import ru.gb.bulgakov.persist.ProductSpecification;
import ru.gb.bulgakov.persist.model.Brand;
import ru.gb.bulgakov.persist.model.Category;
import ru.gb.bulgakov.persist.model.Picture;
import ru.gb.bulgakov.persist.model.Product;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    private final PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              BrandRepository brandRepository, CategoryRepository categoryRepository, PictureService pictureService) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.pictureService = pictureService;
    }

    @Override
    public Page<ProductDto> findAll(Optional<Long> categoryId, Optional<Long> brandId, Optional<String> namePattern,
                                    Integer page, Integer size, String sortField) {
        Specification<Product> spec = Specification.where(null);
        if (categoryId.isPresent() && categoryId.get() != -1) {
            spec = spec.and(ProductSpecification.byCategory(categoryId.get()));
        }
        if (brandId.isPresent() && brandId.get() != -1) {
            spec = spec.and(ProductSpecification.byBrand(brandId.get()));
        }
        if (namePattern.isPresent()) {
            spec = spec.and(ProductSpecification.byName(namePattern.get()));
        }
        return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortField)))
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        new BrandDto(product.getBrand().getId(), product.getBrand().getName()),
                        new CategoryDto(product.getCategory().getId(), product.getCategory().getName()),
                        product.getPictures().stream().map(Picture::getId).collect(Collectors.toList())));
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        new BrandDto(product.getBrand().getId(), product.getBrand().getName()),
                        new CategoryDto(product.getCategory().getId(), product.getCategory().getName()),
                        product.getPictures().stream().map(Picture::getId).collect(Collectors.toList())));
    }

    @Override
    @Transactional
    public void save(ProductDto productDto) {
        Product product = (productDto.getId() != null) ? productRepository.findById(productDto.getId())
                .orElseThrow(() -> new NotFoundException("")) : new Product();
        Category category = categoryRepository.findById(productDto.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Brand brand = brandRepository.findById(productDto.getBrand().getId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        product.setName(productDto.getName());
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        if (productDto.getNewPicture() != null) {
            for (MultipartFile newPicture: productDto.getNewPicture()) {
                try {
                    product.getPictures().add(new Picture(null,
                            newPicture.getOriginalFilename(),
                            newPicture.getContentType(),
                            pictureService.createPicture(newPicture.getBytes()),
                            product));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
