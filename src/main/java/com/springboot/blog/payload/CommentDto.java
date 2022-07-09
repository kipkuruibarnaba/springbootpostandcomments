package com.springboot.blog.payload;

import lombok.Data;

/**
 * @author Barnaba Mutai
 * Created on Friday, July , 08, 2022
 */
@Data
public class CommentDto {
  private long id;
  private String name;
  private  String email;
  private  String body;
}
