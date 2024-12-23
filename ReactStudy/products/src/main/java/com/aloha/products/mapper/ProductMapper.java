package com.aloha.products.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.aloha.products.domain.Product;


@Mapper
public interface ProductMapper extends BaseMapper<Product>{
    
}
