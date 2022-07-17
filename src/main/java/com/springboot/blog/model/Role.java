package com.springboot.blog.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Barnaba Mutai
 * Created on Saturday, July , 16, 2022
 */
@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(
    strategy = GenerationType.IDENTITY
  )
  private Long id;
  @Column(length = 60)
  private String name;
}
