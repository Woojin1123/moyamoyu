-- Table `moim`
CREATE TABLE moim (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      capacity BIGINT,
                      description VARCHAR(255),
                      join_policy ENUM('OPEN', 'PRIVATE'),
                      member_count BIGINT,
                      name VARCHAR(255),
                      PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Table `user`
CREATE TABLE user (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      created_at DATETIME(6),
                      email VARCHAR(255),
                      is_deleted TINYINT(1) DEFAULT FALSE,
                      nickname VARCHAR(255),
                      password VARCHAR(255),
                      service_role ENUM ('ROLE_USER','ROLE_ADMIN'),
                      PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Table `moim_member`
CREATE TABLE moim_member (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             user_id BIGINT NOT NULL,
                             moim_id BIGINT NOT NULL,
                             joined_at DATETIME(6),
                             reason VARCHAR(255),
                             role ENUM('LEADER','MEMBER'),
                             status ENUM('ACTIVE', 'QUIT'),
                             PRIMARY KEY (id),
                             FOREIGN KEY (user_id) REFERENCES user(id),
                             FOREIGN KEY (moim_id) REFERENCES moim(id)
) ENGINE=InnoDB;

-- Table `join_request`
CREATE TABLE join_request (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              leader_id BIGINT NOT NULL,
                              moim_id BIGINT NOT NULL,
                              participant_id BIGINT NOT NULL,
                              created_at DATETIME(6),
                              decided_at DATETIME(6),
                              message VARCHAR(255),
                              reject_reason VARCHAR(255),
                              status ENUM('APPROVED', 'CANCELED', 'PENDING', 'REJECTED'),
                              PRIMARY KEY (id),
                              FOREIGN KEY (leader_id) REFERENCES user(id),
                              FOREIGN KEY (participant_id) REFERENCES user(id),
                              FOREIGN KEY (moim_id) REFERENCES moim(id)
) ENGINE=InnoDB;