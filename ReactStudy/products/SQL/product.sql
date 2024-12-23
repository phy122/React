-- Active: 1732508384532@@127.0.0.1@3306@aloha
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
    `no`    BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    `id`    VARCHAR(64) NOT NULL    COMMENT 'UK',
    `title` VARCHAR(100)    NOT NULL    COMMENT '상품명',
    `content`   TEXT    NULL    COMMENT '설명',
    `likes` BIGINT  NULL    DEFAULT 0   COMMENT '좋아요',
    `img`   TEXT    NULL    COMMENT '이미지경로',
    `created_at`    TIMESTAMP   NOT NULL    DEFAULT CURRENT_TIMESTAMP   COMMENT '등록일자',
    `updated_at`    TIMESTAMP   NOT NULL    DEFAULT CURRENT_TIMESTAMP   COMMENT '수정일자'
);

