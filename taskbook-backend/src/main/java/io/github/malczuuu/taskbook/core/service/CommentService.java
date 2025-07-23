package io.github.malczuuu.taskbook.core.service;

import io.github.malczuuu.taskbook.model.CommentModel;
import io.github.malczuuu.taskbook.model.NewCommentModel;
import org.springframework.data.domain.Page;

public interface CommentService {

  Page<CommentModel> findAll(String board, String issue, int page, int size);

  CommentModel findByUid(String board, String issue, String uid);

  CommentModel create(String board, String issue, NewCommentModel comment, String principal);
}
