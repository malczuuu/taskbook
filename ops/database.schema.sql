CREATE TABLE `boards` (
                          `board_id` bigint(20) NOT NULL,
                          `board_archived_time` datetime DEFAULT NULL,
                          `board_description` text COLLATE utf8_bin NOT NULL,
                          `board_name` varchar(255) COLLATE utf8_bin NOT NULL,
                          `board_uid` varchar(255) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `issues` (
                          `issue_id` bigint(20) NOT NULL,
                          `issue_archived_time` datetime DEFAULT NULL,
                          `issue_detail` varchar(255) COLLATE utf8_bin NOT NULL,
                          `issue_status` int(11) NOT NULL,
                          `issue_title` varchar(255) COLLATE utf8_bin NOT NULL,
                          `issue_uid` varchar(255) COLLATE utf8_bin NOT NULL,
                          `assignee_user_id` bigint(20) DEFAULT NULL,
                          `board_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `issue_comments` (
                                  `issue_comment_id` bigint(20) NOT NULL,
                                  `issue_comment_content` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                                  `issue_comment_created_time` datetime NOT NULL,
                                  `issue_comment_updated_time` datetime DEFAULT NULL,
                                  `author_user_id` bigint(20) NOT NULL,
                                  `issue_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `users` (
                         `user_id` bigint(20) NOT NULL,
                         `user_email` varchar(255) COLLATE utf8_bin NOT NULL,
                         `user_first_name` varchar(255) COLLATE utf8_bin NOT NULL,
                         `user_surname` varchar(255) COLLATE utf8_bin NOT NULL,
                         `user_password` varchar(255) COLLATE utf8_bin NOT NULL,
                         `user_role` int(11) NOT NULL,
                         `user_uid` varchar(255) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `boards`
    ADD PRIMARY KEY (`board_id`),
    ADD UNIQUE KEY `unique_board_uid` (`board_uid`);

ALTER TABLE `issues`
    ADD PRIMARY KEY (`issue_id`),
    ADD UNIQUE KEY `unique_uid_within_board` (`board_id`,`issue_uid`),
    ADD KEY `FKlg2ja57gnxxtlbset4ptnlfyi` (`assignee_user_id`);
ALTER TABLE `issue_comments`
    ADD PRIMARY KEY (`issue_comment_id`),
    ADD KEY `FKsotsp9w13kgvyxmfq8olqrh74` (`author_user_id`),
    ADD KEY `FKnvnj0204928o0w1th5jsx4f28` (`issue_id`);

ALTER TABLE `users`
    ADD PRIMARY KEY (`user_id`),
    ADD UNIQUE KEY `unique_user_uid` (`user_uid`),
    ADD UNIQUE KEY `unique_user_email` (`user_email`);

ALTER TABLE `boards`
    MODIFY `board_id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `issues`
    MODIFY `issue_id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `issue_comments`
    MODIFY `issue_comment_id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `users`
    MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `issues`
    ADD CONSTRAINT `FKlg2ja57gnxxtlbset4ptnlfyi` FOREIGN KEY (`assignee_user_id`) REFERENCES `users` (`user_id`),
    ADD CONSTRAINT `FKpwtkhqp48s1wm6i8ibr2lj6cc` FOREIGN KEY (`board_id`) REFERENCES `boards` (`board_id`);

ALTER TABLE `issue_comments`
    ADD CONSTRAINT `FKnvnj0204928o0w1th5jsx4f28` FOREIGN KEY (`issue_id`) REFERENCES `issues` (`issue_id`),
    ADD CONSTRAINT `FKsotsp9w13kgvyxmfq8olqrh74` FOREIGN KEY (`author_user_id`) REFERENCES `users` (`user_id`);
