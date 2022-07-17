package com.springboot.blog.payload;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author Barnaba Mutai
 * Created on Saturday, July , 16, 2022
 */
@Data
public class RoleDto {
  private Long id;
  private String name;
}
