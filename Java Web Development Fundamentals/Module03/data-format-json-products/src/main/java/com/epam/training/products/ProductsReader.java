package com.epam.training.products;

import com.epam.training.products.domain.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProductsReader {

    private final File file;

    public ProductsReader(File file) { this.file = file; }

    public List<Product> read() {

        if (file == null || !file.exists())
            return Collections.emptyList();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, new TypeReference<List<Product>>() {});
        }
        catch (IOException e) {
            throw (new RuntimeException("read error: " + file.getPath(), e));
        }
    }
}
