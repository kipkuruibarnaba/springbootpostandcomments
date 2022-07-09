package com.springboot.blog.payload;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author Barnaba Mutai
 * Created on Sunday, June , 26, 2022
 */
@Data
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;
}
