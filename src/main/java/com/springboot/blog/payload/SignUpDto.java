package com.springboot.blog.payload;

import lombok.Data;

/**
 * @author Barnaba Mutai
 * Created on Sunday, July , 17, 2022
 */
@Data
public class SignUpDto {
  private String name;
  private String username;
  private String email;
  private String password;
}
