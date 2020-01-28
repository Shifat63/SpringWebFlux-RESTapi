package com.shifat63.webfluxrestapi.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

//Author: Shifat63

@Data
@Document
public class Category implements Serializable {

    @Id
    private String categoryId;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 1,max = 100, message = "Name must be between 1 to 100 characters")
    private String name;
}
