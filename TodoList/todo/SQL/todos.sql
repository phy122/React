-- Active: 1732508384532@@127.0.0.1@3306@aloha
DROP TABLE IF EXISTS `todos`;

CREATE TABLE `todos` (
	`no`	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY	COMMENT 'PK',
	`id`	VARCHAR(64)	NOT NULL	COMMENT 'UK',
	`name`	TEXT	NOT NULL	COMMENT '할일',
	`status`	BOOLEAN	NOT NULL	DEFAULT false	COMMENT '상태',
	`seq`	INT	NOT NULL	DEFAULT 0	COMMENT '순서',
	`created_at`	TIMESTAMP	NOT NULL	DEFAULT current_timestamp	COMMENT '등록일자',
	`updated_at`	TIMESTAMP	NOT NULL	DEFAULT current_timestamp	COMMENT '수정일자'
);

TRUNCATE TABLE todos;

INSERT INTO todos(id, name, status, seq)
VALUES
	( UUID(), '할일1', TRUE,1),
	( UUID(), '할일2', FALSE,2),
	( UUID(), '할일3', TRUE,3),
	( UUID(), '할일4', FALSE,4),
	( UUID(), '할일5', TRUE,5),
	( UUID(), '할일6', FALSE,6),
	( UUID(), '할일7', TRUE,7),
	( UUID(), '할일8', FALSE,8),
	( UUID(), '할일9', TRUE,9),
	( UUID(), '할일10', FALSE,10);

	SELECT * FROM todos;