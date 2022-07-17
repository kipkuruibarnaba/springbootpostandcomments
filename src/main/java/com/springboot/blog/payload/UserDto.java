package com.springboot.blog.payload;

import lombok.Data;

/**
 * @author Barnaba Mutai
 * Created on Saturday, July , 16, 2022
 */
@Data
public class UserDto {
  private Long id;
  private String name;
  private String username;
  private String email;
  private String password;
}
