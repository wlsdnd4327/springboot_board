package org.koreait.services.post.comment;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.post.Comment;
import org.koreait.repositories.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentInfoService {
    private final CommentRepository repository;

    public Comment get(Long id){
      return repository.findById(id).orElse(null);
    }
}
