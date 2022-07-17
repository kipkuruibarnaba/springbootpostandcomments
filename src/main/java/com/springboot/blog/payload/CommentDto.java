package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Barnaba Mutai
 * Created on Friday, July , 08, 2022
 */
@Data
public class CommentDto {
  private long id;
  @NotEmpty
  @Size(min = 3, message = "Comment name should have at least 3 characters")
  private String name;
  @NotEmpty(message = "Email is required")
  @Email
  private  String email;
  @NotEmpty
  @Size(min = 10, message = "Comment body should have at least 10 characters")
  private  String body;
}
