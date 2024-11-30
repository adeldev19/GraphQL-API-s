package com.dev.inventoryservice.web;

import com.dev.inventoryservice.dto.ProductRequestDTO;
import com.dev.inventoryservice.entities.Category;
import com.dev.inventoryservice.entities.Product;
import com.dev.inventoryservice.repository.CategoryRepository;
import com.dev.inventoryservice.repository.ProductRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductGraphQLController {

    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    public ProductGraphQLController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @QueryMapping
    public List<Product> productList() {
        return productRepository.findAll();
    }

    @QueryMapping
    public Product productById(@Argument String id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Product %s not found", id))
        );
    }

    @QueryMapping
    public List<Category> categories(){
        return categoryRepository.findAll();
    }

    @QueryMapping
    public Category categoriesById(@Argument Long id){
        return categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Category %s not found", id))
        );
    }
    @MutationMapping
    public Product saveProduct(@Argument ProductRequestDTO product){
        Category category = categoryRepository.findById(product.categoryId()).orElse(null);
        Product productToSave = new Product();
        productToSave.setId(UUID.randomUUID().toString());
        productToSave.setName(product.name());
        productToSave.setPrice(product.price());
        productToSave.setQuantity(product.quantity());
        if(category!=null) productToSave.setCategory(category);
        return productRepository.save(productToSave);
    }

    @MutationMapping
    public Product updateProduct(@Argument String id,@Argument ProductRequestDTO product){
        Category category = categoryRepository.findById(product.categoryId()).orElse(null);
        Product productToSave = new Product();
        productToSave.setId(id);
        productToSave.setName(product.name());
        productToSave.setPrice(product.price());
        productToSave.setQuantity(product.quantity());
        if(category!=null) productToSave.setCategory(category);
        return productRepository.save(productToSave);
    }
    @MutationMapping
    public void  deleteProduct(@Argument String id) {
        productRepository.deleteById(id);
    }

}

/*
query{
    productList {
        id,
                name
    }
}*/

/*
mutation{
    updateProduct(
            id: "ab5c135-3b10-4947-9d0d-2eebd7e56bb8",
            product : {
        name:"AAAA",
                price:333,
                quantity:455,
                categoryId:1
    })
    {
        name, price

    }
}*/
/*
mutation{
    deleteProduct(id:"502c8d52-ecbe-4b3d-be19-4717211029c4")
}*/
/*
mutation($n:String,$p:Float,$q:Int,$catId:Float){
    saveProduct(product : {
        name: $n, price:$p, quantity: $q, categoryId:$catId
    }
)
    {
        id,name,price,quantity,category{name}
    }
}
{"n": "P43","p": 5542,"q": 23,"catId": 1}
*/

/*

query($id :String){
  productById(id :$id ){
    name, price, category{id, name, products {
      name
    }},
  }
}
* */