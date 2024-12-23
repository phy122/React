package com.aloha.products.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.products.domain.Product;
import com.aloha.products.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> list() {
        return productMapper.list();
    }

    @Override
    public Product select(Long no) {
        return productMapper.select(no);
    }

    @Override
    public Product selectById(String id) {
        return productMapper.selectById(id);
    }

    @Override
    public boolean insert(Product entity) {
        return productMapper.insert(entity) > 0;
    }

    @Override
    public boolean update(Product entity) {
        return productMapper.update(entity) > 0;
    }

    @Override
    public boolean updateById(Product entity) {
        return productMapper.updateById(entity) > 0;
    }

    @Override
    public boolean delete(Long no) {
        return productMapper.delete(no) > 0;
    }

    @Override
    public boolean deleteById(String id) {
        return productMapper.deleteById(id) > 0;
    }
    
    
}
