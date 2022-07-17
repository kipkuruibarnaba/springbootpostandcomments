package com.springboot.blog.payload;

import lombok.Data;

/**
 * @author Barnaba Mutai
 * Created on Sunday, July , 17, 2022
 */
@Data
public class LoginDto {
  private String usernameOrEmail;
  private String password;

}
