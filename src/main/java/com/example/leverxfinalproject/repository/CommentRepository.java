package com.example.leverxfinalproject.repository;

import com.example.leverxfinalproject.model.Comment;
import com.example.leverxfinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByUserAndApproved(User user, boolean approved);
    Optional<Comment> findCommentById(Integer id);
    @Query("SELECT c FROM Comment c WHERE c.approved = false")
    List<Comment> findCommentsToApprove();
}
