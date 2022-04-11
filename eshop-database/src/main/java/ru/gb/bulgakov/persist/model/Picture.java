package ru.gb.bulgakov.persist.model;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024, nullable = false)
    private String name;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "storage_file_name", nullable = false, unique = true)
    private String storageFileName;

    @ManyToOne(optional = false)
    private Product product;

    public Picture() {
    }

    public Picture(Long id, String name, String contentType, String storageFileName, Product product) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.storageFileName = storageFileName;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getStorageFileName() {
        return storageFileName;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
