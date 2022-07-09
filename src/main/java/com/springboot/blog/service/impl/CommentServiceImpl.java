package com.springboot.blog.service.impl;

import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Barnaba Mutai
 * Created on Friday, July , 08, 2022
 */
@Service
public class CommentServiceImpl implements CommentService {
  private CommentRepository commentRepository;
  private PostRepository postRepository;


  public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
  }

  @Override
  public CommentDto createComment(long postId, CommentDto commentDto) {
    Comment comment = mapToEntity(commentDto);
    //Retrieve post entity by id
    Post post = postRepository.findById(postId).orElseThrow(
      ()->new ResourceNotFoundException("Post", "id", postId));
    //set post to comment
    comment.setPost(post);
    //Save comment entity to the database
    Comment newComment= commentRepository.save(comment);
    return mapToDTO(newComment);
  }

  @Override
  public List<CommentDto> getCommentsByPostId(long postId) {
    //Retrieve comments by postId
    List<Comment> comments = commentRepository.findByPostId(postId);
   //Convert list of comment entities to list of comment dto's
    return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
  }

  @Override
  public CommentDto getCommentById(Long postId, Long commentId) {
    //Retrieve post entity by id
    Post post = postRepository.findById(postId).orElseThrow(
      ()->new ResourceNotFoundException("Post", "id", postId));
    //Retrieve comment by id
    Comment comment = commentRepository.findById(commentId).orElseThrow(
      ()->new ResourceNotFoundException("Comment", "id", commentId));
    if(!comment.getPost().getId().equals(post.getId())){
      throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
    }
    return mapToDTO(comment);
  }

  @Override
  public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
    //Retrieve post entity by id
    Post post = postRepository.findById(postId).orElseThrow(
      ()->new ResourceNotFoundException("Post", "id", postId));
    //Retrieve comment by id
    Comment comment = commentRepository.findById(commentId).orElseThrow(
      ()->new ResourceNotFoundException("Comment", "id", commentId));
    if(!comment.getPost().getId().equals(post.getId())){
      throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
    }
    comment.setName(commentRequest.getName());
    comment.setBody(commentRequest.getBody());
    comment.setEmail(commentRequest.getEmail());
     Comment updatedComment = commentRepository.save(comment);
     return mapToDTO(updatedComment);
  }

  @Override
  public void deleteComment(Long postId, Long commentId) {
    //Retrieve post entity by id
    Post post = postRepository.findById(postId).orElseThrow(
      ()->new ResourceNotFoundException("Post", "id", postId));
    //Retrieve comment by id
    Comment comment = commentRepository.findById(commentId).orElseThrow(
      ()->new ResourceNotFoundException("Comment", "id", commentId));
    if(!comment.getPost().getId().equals(post.getId())){
      throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
    }
      commentRepository.delete(comment);
  }

  //Convert Entity into DTO
  private CommentDto mapToDTO(Comment comment){
      CommentDto commentDto = new CommentDto();
      commentDto.setId(comment.getId());
      commentDto.setName(comment.getName());
      commentDto.setEmail(comment.getEmail());
      commentDto.setBody(comment.getBody());
      return  commentDto;
  }

  //Convert DTO to  Entity
  private Comment mapToEntity(CommentDto commentDto){
    Comment comment = new Comment();
    comment.setId(commentDto.getId());
    comment.setName(commentDto.getName());
    comment.setEmail(commentDto.getEmail());
    comment.setBody(commentDto.getBody());
    return comment;
  }
}
