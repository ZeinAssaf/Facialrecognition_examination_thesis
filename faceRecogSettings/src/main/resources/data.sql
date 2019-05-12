INSERT INTO role (id,name) VALUES (1,"ADMIN") ON DUPLICATE KEY UPDATE id=id;
INSERT INTO role (id,name) VALUES (2,"MODERATOR") ON DUPLICATE KEY UPDATE id=id;
INSERT INTO role (id,name) VALUES (3,"USER") ON DUPLICATE KEY UPDATE id=id;

INSERT INTO user (id,username,password) VALUES (1,"ADMIN","$2a$10$wgid6sn.Di7/4dMUWCMrpuM1Af9QyEW3HckXclmLK.6ZPDe/jSFsG") ON DUPLICATE KEY UPDATE id=id;

INSERT INTO user_role (user_id,role_id) VALUES (1,1) ON DUPLICATE KEY UPDATE user_id=user_id;
INSERT INTO user_role (user_id,role_id) VALUES (1,2) ON DUPLICATE KEY UPDATE user_id=user_id;
INSERT INTO user_role (user_id,role_id) VALUES (1,3) ON DUPLICATE KEY UPDATE user_id=user_id;