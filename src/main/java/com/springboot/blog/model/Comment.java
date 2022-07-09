package com.springboot.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Barnaba Mutai
 * Created on Thursday, July , 07, 2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
  name = "comments"
)
public class Comment {
  @Id
  @GeneratedValue(
    strategy = GenerationType.IDENTITY
  )
  private long id;
  private String name;
  private  String email;
  private  String body;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id",nullable = false)
  private Post post;
}
