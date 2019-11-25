package io.github.malczuuu.taskbook.core.service;

import io.github.malczuuu.taskbook.model.IssueModel;
import io.github.malczuuu.taskbook.model.IssueUpdateModel;
import io.github.malczuuu.taskbook.model.NewIssueModel;
import org.springframework.data.domain.Page;

public interface IssueService {

  Page<IssueModel> findAll(String board, int page, int size);

  Page<IssueModel> findAllFilterByTitle(String board, String title, int page, int size);

  IssueModel findByUid(String board, String uid);

  IssueModel create(String board, NewIssueModel issue);

  IssueModel updateByUid(String board, String uid, IssueUpdateModel issue);

  void deleteByUid(String board, String uid);
}
