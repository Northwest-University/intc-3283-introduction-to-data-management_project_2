CREATE TABLE `tasks`
(
    `id`          BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `title`       VARCHAR(255)                   NOT NULL,
    `description` TEXT                           NOT NULL,
    `status`      VARCHAR(255)                   NOT NULL DEFAULT 'PENDING',
    `created_at`  TIMESTAMP                      NOT NULL DEFAULT CURRENT_TIMESTAMP
);
