package com.shifat63.webfluxrestapi.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//Author: Shifat63

@Data
@Document
public class Recipe implements Serializable {

    @Id
    private String recipeId;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 1,max = 200, message = "Name must be between 1 to 200 characters")
    private String name;

    @NotEmpty(message = "Recipe must belong to one or more than one category")
    @DBRef
    private Set<Category> categories = new HashSet<>();
}
