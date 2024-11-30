package com.dev.inventoryservice.dto;



public record ProductRequestDTO (
        String id,
        String name,
        double price,
        int quantity,
        Long categoryId){

}


