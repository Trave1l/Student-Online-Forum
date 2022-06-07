/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : dbforcommunity

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 12/12/2021 11:33:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bbs
-- ----------------------------
DROP TABLE IF EXISTS `bbs`;
CREATE TABLE `bbs`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `follow` int NULL DEFAULT NULL COMMENT '浏览数',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发帖者',
  `aid` int NULL DEFAULT NULL COMMENT '发帖者ID',
  `good` int NULL DEFAULT NULL COMMENT '点赞数',
  `bad` int NULL DEFAULT NULL COMMENT '点灭数',
  `section` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '板块',
  `star` int NULL DEFAULT NULL COMMENT '精华贴',
  `report` int NULL DEFAULT NULL COMMENT '举报状况 0--正常 1--有违规 2--冻结',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bbs
-- ----------------------------
INSERT INTO `bbs` VALUES (59, 'Welcome!!!!', '<p>Welcome to the student form in the university of science and technology!!</p>', '2021-12-09 12:16:53', 50, 'admin', 1, 40, 1, 'NULL', 1, 0);
INSERT INTO `bbs` VALUES (60, 'How to study well in software engineer?', '<p>I am the student in the class of SE, I am confused about how to study well in this class. Could you give some advice???<span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">🤡</span></p>', '2021-12-09 12:21:30', 20, 'YUBO', 25, 15, 1, 'Study', 1, 0);
INSERT INTO `bbs` VALUES (61, 'Where to eat delicious food?', '<p>I am the freshman in MUST, I want to find some delicious food. Could you recommand some resturants to me.<span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">👣</span></p>', '2021-12-09 12:25:30', 5, 'YUBO', 25, 0, 2, 'Food', 0, 0);
INSERT INTO `bbs` VALUES (62, 'Shopping vlog 1', '<p>Shopping in Galaxy Macau.</p><p><img src=\"https://img.galaxymacau.com/media_library/gh_hotel_main_hero.jpg?x-oss-process=image/resize%2Cm_lfit%2Cw_1920%2Climit_0/format%2Cwebp/quality%2Cq_75\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', '2021-12-09 12:28:59', 12, 'YUBO', 25, 0, 1, 'Shopping', 0, 0);
INSERT INTO `bbs` VALUES (63, 'Macau tower vlog', '<p>2021/10/20</p><p>Recommandation and experience of the macau tower.</p><p><br/><img src=\"data:image/jpeg;base64,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\" contenteditable=\"false\" style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size); max-width: 100%;\"/></p>', '2021-12-09 12:31:26', 44, 'YUBO', 25, 34, 1, 'Travel', 1, 0);
INSERT INTO `bbs` VALUES (64, 'I am spider man, I expect the response from Captain American', '<p>The Avengers assemble!!!<span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">😜</span></p><p><span style=\"font-size: 14px;\"><br/></span><img src=\"https://logolook.net/wp-content/uploads/2021/07/Avengers-Log%D0%BE.png\" contenteditable=\"false\" style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size); max-width: 100%;\"/></p>', '2021-12-09 12:38:39', 6, 'GRAY', 61, 1, 5, 'Study', 0, 0);
INSERT INTO `bbs` VALUES (65, 'Black Friday', '<p>I want to buy some gifts on the Black Friday. Do you know some shops which have the biggest discount.</p><p><br/><img src=\"https://images.squarespace-cdn.com/content/v1/5c5867627a1fbd515af1bd68/1631914145181-KW1T90WGEYVO61TAAAIM/UN+-+BLACKPINK+Photograph.jpg?format=1500w\" contenteditable=\"false\" style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size); max-width: 100%;\"/></p>', '2021-12-09 12:41:40', 20, 'GRAY', 61, 14, 0, 'Shopping', 0, 0);
INSERT INTO `bbs` VALUES (67, 'Hello! I\'m Boyu', '<p>Hi !!!<span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">🤣</span></p>', '2021-12-11 00:40:59', 5, 'ChenBoyu', 107, 4, 0, 'Study', 0, 0);
INSERT INTO `bbs` VALUES (70, 'Hello! I\'m Boyu', '<p>Hi !!!</p>', '2021-12-11 00:49:07', 1, 'ChenBoyu', 107, 0, 0, 'Study', 0, 0);

-- ----------------------------
-- Table structure for complain
-- ----------------------------
DROP TABLE IF EXISTS `complain`;
CREATE TABLE `complain`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `com_uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申诉账户名',
  `com_ucontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '申诉内容',
  `com_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申诉帖子名',
  `com_tcontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '申诉帖子内容',
  `time` datetime NULL DEFAULT NULL COMMENT '申诉时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of complain
-- ----------------------------
INSERT INTO `complain` VALUES (3, 'User1', '误封', NULL, NULL, '2021-12-01 10:16:16');
INSERT INTO `complain` VALUES (4, 'User2', '误封！！！', NULL, NULL, '2021-12-02 09:21:16');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `message_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '评论内容',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论人',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论时间',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父ID',
  `foreign_id` bigint NULL DEFAULT 0 COMMENT '关联id',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `parent_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '父内容',
  `aid` int NULL DEFAULT NULL COMMENT '评论者ID',
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '帖子名称',
  `parent_author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '父评论者名字',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '帖子内容',
  `follow` int NULL DEFAULT NULL COMMENT '浏览数',
  `bbs_author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发帖人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (111, 'Hello, everyone!!!!', 'YUBO', '2021-12-09 12:18:00', NULL, 59, 'http://localhost:9092/files/4a35870a585649ad8bafa8beebdb4c15', NULL, 25, 'Welcome!!!!', NULL, '<p>Welcome to the student form in the university of science and technology!!</p>', 2, 'admin');
INSERT INTO `message` VALUES (112, 'Hi, I am Spider man.', 'GRAY', '2021-12-09 12:34:54', 111, 59, 'http://localhost:9092/files/fe22796e83b2487d8999507061f20745', 'Hello, everyone!!!!', 61, 'Welcome!!!!', 'YUBO', '<p>Welcome to the student form in the university of science and technology!!</p>', NULL, 'admin');
INSERT INTO `message` VALUES (113, 'Hello, I am iron man!!', 'GRAY', '2021-12-09 12:35:20', NULL, 59, 'http://localhost:9092/files/fe22796e83b2487d8999507061f20745', NULL, 61, 'Welcome!!!!', NULL, '<p>Welcome to the student form in the university of science and technology!!</p>', 4, 'admin');
INSERT INTO `message` VALUES (124, 'HELLO!\n', 'ChenBoyu', '2021-12-11 00:37:14', NULL, 59, 'http://localhost:9092/files/43de1848b6dd44b5bd4329ee710790fd', NULL, 107, 'Welcome!!!!', NULL, '<p>Welcome to the student form in the university of science and technology!!</p>', 15, 'admin');
INSERT INTO `message` VALUES (125, 'Hi  !!', 'ChenBoyu', '2021-12-11 00:37:24', 124, 59, 'http://localhost:9092/files/43de1848b6dd44b5bd4329ee710790fd', 'HELLO!\n', 107, 'Welcome!!!!', 'ChenBoyu', '<p>Welcome to the student form in the university of science and technology!!</p>', NULL, 'admin');
INSERT INTO `message` VALUES (126, 'HELLO!\n', 'ChenBoyu', '2021-12-11 00:37:42', NULL, 59, 'http://localhost:9092/files/43de1848b6dd44b5bd4329ee710790fd', NULL, 107, 'Welcome!!!!', NULL, '<p>Welcome to the student form in the university of science and technology!!</p>', 16, 'admin');
INSERT INTO `message` VALUES (127, 'Hi  !!', 'ChenBoyu', '2021-12-11 00:37:43', 126, 59, 'http://localhost:9092/files/43de1848b6dd44b5bd4329ee710790fd', 'HELLO!\n', 107, 'Welcome!!!!', 'ChenBoyu', '<p>Welcome to the student form in the university of science and technology!!</p>', NULL, 'admin');
INSERT INTO `message` VALUES (128, 'HELLO!\n', 'ChenBoyu', '2021-12-11 00:38:16', NULL, 59, 'http://localhost:9092/files/43de1848b6dd44b5bd4329ee710790fd', NULL, 107, 'Welcome!!!!', NULL, '<p>Welcome to the student form in the university of science and technology!!</p>', 17, 'admin');
INSERT INTO `message` VALUES (129, 'Hi  !!', 'ChenBoyu', '2021-12-11 00:38:18', 128, 59, 'http://localhost:9092/files/43de1848b6dd44b5bd4329ee710790fd', 'HELLO!\n', 107, 'Welcome!!!!', 'ChenBoyu', '<p>Welcome to the student form in the university of science and technology!!</p>', NULL, 'admin');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (10, 'Test', '<p><b><font size=\"6\"><u>Test</u></font></b><br/></p><hr/><p><img src=\"http://localhost:9092/files/75548d9b1a32471ba950868b5bcdfa06\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 'admin', '2021-10-25 12:11:01');
INSERT INTO `news` VALUES (15, 'System Announcement', '<p>Implementing the function of system announcement</p>', 'admin', '2021-10-25 15:55:46');
INSERT INTO `news` VALUES (18, 'Achieve Student Forum', '<p>The frist page of student forum-- unconfirm</p>', 'admin', '2021-11-04 11:16:21');
INSERT INTO `news` VALUES (19, 'View ciunt', '<p>Implementing the function of view count</p>', 'admin', '2021-11-14 15:07:33');
INSERT INTO `news` VALUES (20, 'Comment', '<p>You can view the comment in the post</p>', 'admin', '2021-11-14 21:47:53');
INSERT INTO `news` VALUES (21, 'Perfect the review', '<p>The user can comment under other comments.</p>', 'admin', '2021-11-15 11:54:59');
INSERT INTO `news` VALUES (23, 'Update the posts', '<p>Open indepedent page to posts.</p>', 'admin', '2021-11-18 12:24:48');
INSERT INTO `news` VALUES (25, 'UI', '<p>Perfect the UI</p>', 'admin', '2021-12-01 00:40:34');

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `port_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '举报人',
  `re_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '被举报评论',
  `re_mid` int NULL DEFAULT NULL COMMENT '被举报评论ID',
  `port_id` int NULL DEFAULT NULL COMMENT '举报人ID',
  `re_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '被举报帖子',
  `re_tid` int NULL DEFAULT NULL COMMENT '被举报帖子ID',
  `re_uid` int NULL DEFAULT NULL COMMENT '被举报账户ID',
  `re_uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '被举报账号名',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '举报原因',
  `time` datetime NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO `report` VALUES (18, 'admin', NULL, NULL, 1, 'Taro No Cox', 2, NULL, NULL, 'qqq', '2021-11-29 16:24:47');
INSERT INTO `report` VALUES (21, 'admin', 'messageContent', 62, 1, NULL, NULL, NULL, NULL, '123', '2021-11-30 12:04:22');
INSERT INTO `report` VALUES (49, 'admin', NULL, NULL, 1, NULL, NULL, 25, '学生', NULL, '2021-12-02 10:44:51');
INSERT INTO `report` VALUES (50, 'ChenBoyu', NULL, NULL, 107, '学习', 18, NULL, NULL, '虚假标题！', '2021-12-07 09:41:58');
INSERT INTO `report` VALUES (51, 'ChenBoyu', NULL, NULL, 107, '学习', 18, NULL, NULL, '虚假标题！', '2021-12-07 09:42:45');
INSERT INTO `report` VALUES (52, 'ChenBoyu', NULL, NULL, 107, '学习', 18, NULL, NULL, '虚假标题！', '2021-12-07 09:43:23');
INSERT INTO `report` VALUES (53, 'ChenBoyu', NULL, NULL, 107, '学习', 18, NULL, NULL, '虚假标题！', '2021-12-07 09:44:32');
INSERT INTO `report` VALUES (54, 'ChenBoyu', NULL, NULL, 107, '学习', 18, NULL, NULL, '虚假标题！', '2021-12-07 09:46:26');
INSERT INTO `report` VALUES (55, 'ChenBoyu', NULL, NULL, 107, '学习', 18, NULL, NULL, '虚假标题！', '2021-12-09 10:17:12');

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '帖子标题',
  `bbs_id` int NULL DEFAULT NULL COMMENT '帖子ID',
  `time` datetime NULL DEFAULT NULL COMMENT '收藏时间',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '帖子内容',
  `follow` int NULL DEFAULT NULL COMMENT '浏览数',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES (34, 'MUST-GRAY', 'How to study well in software engineer?', 60, '2021-12-09 12:33:21', '<p>I am the student in the class of SE, I am confused about how to study well in this class. Could you give some advice???<span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">🤡</span></p>', 1, 61);
INSERT INTO `store` VALUES (35, 'MUST-GRAY', 'Where to eat delicious food?', 61, '2021-12-09 12:33:28', '<p>I am the freshman in MUST, I want to find some delicious food. Could you recommand some resturants to me.<span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">👣</span></p>', 1, 61);
INSERT INTO `store` VALUES (36, 'MUST-GRAY', 'Shopping vlog 1', 62, '2021-12-09 12:33:45', '<p>Shopping in Galaxy Macau.</p><p><img src=\"https://img.galaxymacau.com/media_library/gh_hotel_main_hero.jpg?x-oss-process=image/resize%2Cm_lfit%2Cw_1920%2Climit_0/format%2Cwebp/quality%2Cq_75\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 2, 61);
INSERT INTO `store` VALUES (37, 'MUST-GRAY', 'Macau tower vlog', 63, '2021-12-09 12:33:51', '<p>2021/10/20</p><p>Recommandation and experience of the macau tower.</p><p><br/><img src=\"data:image/jpeg;base64,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\" contenteditable=\"false\" style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size); max-width: 100%;\"/></p>', 5, 61);
INSERT INTO `store` VALUES (40, 'MUST-BOYU', 'Hello! I\'m Boyu', 67, '2021-12-11 00:49:08', '<p>Hi !!!<span style=\"color: var(--el-text-color-regular); font-size: var(--el-dialog-content-font-size);\">🤣</span></p>', 4, 107);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `role` int NULL DEFAULT NULL COMMENT '角色',
  `state` int NULL DEFAULT NULL COMMENT '状态 -- 0（离线） 1（在线）',
  `emergency` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '紧急代码',
  `report` int NULL DEFAULT NULL COMMENT '举报状况 0--正常 1--有违规 2--冻结',
  `cnt` int NULL DEFAULT NULL COMMENT '举报人数 > 20 冻结',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学生用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin123', 'admin', 22, 'Male', 'http://localhost:9092/files/4305093c0ae742e8af9fb4b1c122266b', 1, 0, 'X5G7V', 0, 0);
INSERT INTO `user` VALUES (25, 'MUST-YUBO', 'stu123456', 'YUBO', 21, 'Male', 'http://localhost:9092/files/4a35870a585649ad8bafa8beebdb4c15', 2, 0, 'XYP9X', 0, 0);
INSERT INTO `user` VALUES (26, 'MUST-TARO', 'stu123456', 'TARO', 13, 'Female', 'http://localhost:9092/files/5de7c593fe9e480c92285c5112dc3230', 2, 0, 'CTX1W', 2, 500);
INSERT INTO `user` VALUES (61, 'MUST-GRAY', 'stu123456', 'GRAY', 11, 'Unknown', 'http://localhost:9092/files/fe22796e83b2487d8999507061f20745', 2, 0, '75M84', 1, 20);
INSERT INTO `user` VALUES (107, 'MUST-BOYU', 'stu123456', 'ChenBoyu', 21, 'Male', 'http://localhost:9092/files/43de1848b6dd44b5bd4329ee710790fd', 2, 0, 'LLF5I', 0, 0);
INSERT INTO `user` VALUES (108, 'TestUser', 'test1234', 'TestUser', 0, 'Unknown', NULL, 2, 1, 'TEST1', 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
