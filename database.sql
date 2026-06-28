/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50739
 Source Host           : localhost:3306
 Source Schema         : smart-campus-study

 Target Server Type    : MySQL
 Target Server Version : 50739
 File Encoding         : 65001

 Date: 17/06/2026 22:39:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class_info
-- ----------------------------
DROP TABLE IF EXISTS `class_info`;
CREATE TABLE `class_info`  (
  `class_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '班级名称',
  `class_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '班级编码（唯一）',
  `major_id` int(11) NOT NULL COMMENT '所属专业ID',
  `grade` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年级（如 2024）',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '班级描述',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`class_id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`class_code`) USING BTREE,
  INDEX `idx_major_id`(`major_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '班级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_chapter_info
-- ----------------------------
DROP TABLE IF EXISTS `course_chapter_info`;
CREATE TABLE `course_chapter_info`  (
  `chapter_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '章节ID（UUID）',
  `course_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属课程ID',
  `chapter_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '章节名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '章节描述',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重',
  PRIMARY KEY (`chapter_id`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '章节信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_class
-- ----------------------------
DROP TABLE IF EXISTS `course_class`;
CREATE TABLE `course_class`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `course_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程ID',
  `class_id` int(11) NOT NULL COMMENT '班级ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_course_class`(`course_id`, `class_id`) USING BTREE,
  INDEX `idx_class_id`(`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程班级关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_info
-- ----------------------------
DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info`  (
  `course_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程ID（UUID）',
  `course_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名称',
  `teacher_id` int(11) NOT NULL COMMENT '授课教师用户ID',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程描述',
  `cover_resource_id` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面对应的resource_info的资源ID',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-启用, 0-禁用',
  `record_status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：0-录制中, 1-录制完成',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`course_id`) USING BTREE,
  INDEX `idx_teacher_id`(`teacher_id`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_lesson_info
-- ----------------------------
DROP TABLE IF EXISTS `course_lesson_info`;
CREATE TABLE `course_lesson_info`  (
  `lesson_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课时ID（UUID）',
  `course_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属课程ID',
  `chapter_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属章节ID',
  `lesson_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课时名称',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重',
  PRIMARY KEY (`lesson_id`) USING BTREE,
  INDEX `idx_chapter_id`(`chapter_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课时信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_lesson_resource
-- ----------------------------
DROP TABLE IF EXISTS `course_lesson_resource`;
CREATE TABLE `course_lesson_resource`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联ID（UUID）',
  `lesson_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属课时ID',
  `resource_type` tinyint(4) NOT NULL COMMENT '资源类型：1-视频, 2-课件, 3-课后习题',
  `resource_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联资源ID（resource_info 或 paper_info）',
  `is_primary` int(11) NULL DEFAULT 0 COMMENT '是否是主资源1:是  0:不是',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_lesson_id`(`lesson_id`) USING BTREE,
  INDEX `idx_resource_type`(`resource_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课时资源关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for faculty_info
-- ----------------------------
DROP TABLE IF EXISTS `faculty_info`;
CREATE TABLE `faculty_info`  (
  `faculty_id` int(11) NOT NULL AUTO_INCREMENT,
  `faculty_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '院系名称',
  `faculty_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '院系编码（唯一）',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '院系描述',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`faculty_id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`faculty_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '院系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for major_info
-- ----------------------------
DROP TABLE IF EXISTS `major_info`;
CREATE TABLE `major_info`  (
  `major_id` int(11) NOT NULL AUTO_INCREMENT,
  `major_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专业名称',
  `major_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专业编码（唯一）',
  `faculty_id` int(11) NOT NULL COMMENT '所属院系ID',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专业描述',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`major_id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`major_code`) USING BTREE,
  INDEX `idx_faculty_id`(`faculty_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '专业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for paper_info
-- ----------------------------
DROP TABLE IF EXISTS `paper_info`;
CREATE TABLE `paper_info`  (
  `paper_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '试卷ID（UUID）',
  `paper_title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '试卷名称',
  `paper_type` tinyint(4) NOT NULL COMMENT '试卷类型：1-课后习题, 2-考试试卷',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '试卷描述/说明',
  `total_score` int(11) NULL DEFAULT 0 COMMENT '试卷总分',
  `duration` int(11) NULL DEFAULT 0 COMMENT '考试时长（分钟，考试试卷使用）',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-启用, 0-禁用',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建人用户ID',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`paper_id`) USING BTREE,
  INDEX `idx_paper_type`(`paper_type`) USING BTREE,
  INDEX `idx_create_user_id`(`create_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试卷信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for paper_question
-- ----------------------------
DROP TABLE IF EXISTS `paper_question`;
CREATE TABLE `paper_question`  (
  `paper_question_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联ID（UUID）',
  `parent_paper_question_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父ID',
  `group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组名称',
  `paper_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '试卷ID',
  `question_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '习题ID',
  `score` int(11) NULL DEFAULT 5 COMMENT '该题分值（可覆盖习题默认分值）',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '题目排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`paper_question_id`) USING BTREE,
  INDEX `idx_paper_id`(`paper_id`) USING BTREE,
  INDEX `idx_question_id`(`question_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试卷题目关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question_info
-- ----------------------------
DROP TABLE IF EXISTS `question_info`;
CREATE TABLE `question_info`  (
  `question_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '习题ID（UUID）',
  `question_title` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '题干内容',
  `question_type` tinyint(4) NOT NULL COMMENT '题型：1-单选题, 2-多选题, 3-判断题, 4-简答题',
  `difficulty` tinyint(4) NULL DEFAULT 1 COMMENT '难度：1-简单, 2-中等, 3-困难',
  `answer` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '正确答案（客观题存选项key如A/B/AB，主观题存参考答案文本，判断题存T/F）',
  `analysis` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '答案解析',
  `score` int(11) NULL DEFAULT 5 COMMENT '默认分值',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建人用户ID',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`question_id`) USING BTREE,
  INDEX `idx_question_type`(`question_type`) USING BTREE,
  INDEX `idx_difficulty`(`difficulty`) USING BTREE,
  INDEX `idx_create_user_id`(`create_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '习题信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question_option
-- ----------------------------
DROP TABLE IF EXISTS `question_option`;
CREATE TABLE `question_option`  (
  `option_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项ID（UUID）',
  `question_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属习题ID',
  `option_content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项内容',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重（按标识字母顺序排序）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`option_id`) USING BTREE,
  INDEX `idx_question_id`(`question_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目选项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for resource_info
-- ----------------------------
DROP TABLE IF EXISTS `resource_info`;
CREATE TABLE `resource_info`  (
  `resource_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源ID（UUID）',
  `resource_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `node_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '节点类型：1-目录, 2-文件',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '父级资源ID（0=根目录）',
  `resource_type` tinyint(4) NULL DEFAULT NULL COMMENT '资源类型（仅文件使用）：1-视频, 2-图片, 3-文档, 4-压缩包, 5-其他',
  `resource_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源存储路径/URL（仅文件使用）',
  `original_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原始文件名（仅文件使用）',
  `file_size` bigint(20) NULL DEFAULT 0 COMMENT '文件大小（字节，仅文件使用）',
  `file_ext` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件扩展名（仅文件使用）',
  `mime_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MIME类型（仅文件使用）',
  `duration` int(11) NULL DEFAULT 0 COMMENT '视频时长（秒，仅视频使用）',
  `cover_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图URL（仅视频使用）',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源描述',
  `upload_user_id` int(11) NULL DEFAULT NULL COMMENT '上传用户ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（仅文件使用）：1-上传中, 2-转码中, 3-上传成功, 4-转码失败, 5-上传失败',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序权重（仅目录使用）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`resource_id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE,
  INDEX `idx_is_folder`(`node_type`) USING BTREE,
  INDEX `idx_resource_type`(`resource_type`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE,
  INDEX `idx_upload_user_id`(`upload_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源信息表（目录+文件统一存储）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户编号/工号/学号（登录账号，唯一）',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码（加密存储）',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实姓名',
  `role_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色类型：admin-管理员, teacher-教师, student-学生',
  `class_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学生所属班级ID,老师所属班级多个ID用逗号隔开',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像URL',
  `gender` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别：M-男, F-女',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-启用, 0-禁用',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uk_user_no`(`user_no`) USING BTREE,
  UNIQUE INDEX `uk_phone`(`phone`) USING BTREE,
  INDEX `idx_role_type`(`role_type`) USING BTREE,
  INDEX `idx_class_id`(`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表（管理员/教师/学生统一存储）' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;





-- 1. 插入用户数据 (管理员、教师、学生)
-- 密码统一为 123456 (已使用 MD5 加密，实际生产请用更强的加密方式)
INSERT INTO `user_info` (`user_no`, `password`, `real_name`, `role_type`, `class_id`, `phone`, `email`, `avatar`, `gender`) VALUES 
('ADMIN001', 'e10adc3949ba59abbe56e057f20f883e', '张伟', 'admin', NULL, '13800000001', 'zhangwei@school.edu', 'https://via.placeholder.com/100', 'M'),
('TEA001', 'e10adc3949ba59abbe56e057f20f883e', '李芳', 'teacher', '1', '13800000002', 'lifang@school.edu', 'https://via.placeholder.com/100', 'F'),
('STU001', 'e10adc3949ba59abbe56e057f20f883e', '王强', 'student', '1', '13800000003', 'wangqiang@student.edu', 'https://via.placeholder.com/100', 'M'),
('STU002', 'e10adc3949ba59abbe56e057f20f883e', '刘敏', 'student', '1', '13800000004', 'liumin@student.edu', 'https://via.placeholder.com/100', 'F');

-- 2. 插入院系数据
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`) VALUES 
('计算机科学与技术学院', 'CS2024', '负责计算机相关专业的教学与科研'),
('外国语学院', 'FL2024', '负责语言类专业的教学');

-- 3. 插入专业数据 (关联院系)
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`) VALUES 
('软件工程', 'SE2024', 1, '专注于软件开发与工程管理'),
('英语语言文学', 'ENG2024', 2, '专注于英语语言与文学研究');

-- 4. 插入班级数据 (关联专业)
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`) VALUES 
('软件工程2024级1班', 'SE202401', 1, '2024', '大一新生班级');

-- 5. 插入课程数据 (关联教师)
INSERT INTO `course_info` (`course_id`, `course_name`, `teacher_id`, `description`, `status`, `record_status`) VALUES 
('COURSE001', '高等数学', 2, '大一基础必修课', 1, 1),
('COURSE002', '大学英语', 2, '大一基础必修课', 1, 1);

-- 6. 关联课程与班级 (关联课程和班级)
INSERT INTO `course_class` (`course_id`, `class_id`) VALUES 
('COURSE001', 1),
('COURSE002', 1);

-- 7. 插入章节数据 (关联课程)
INSERT INTO `course_chapter_info` (`chapter_id`, `course_id`, `chapter_name`, `description`) VALUES 
('CHAP001', 'COURSE001', '第一章：函数与极限', '基础概念讲解'),
('CHAP002', 'COURSE001', '第二章：导数与微分', '计算方法讲解');

-- 8. 插入课时数据 (关联章节)
INSERT INTO `course_lesson_info` (`lesson_id`, `course_id`, `chapter_id`, `lesson_name`) VALUES 
('LESSON001', 'COURSE001', 'CHAP001', '1.1 映射与函数'),
('LESSON002', 'COURSE001', 'CHAP001', '1.2 数列的极限');

-- 9. 插入资源数据 (模拟视频/课件文件)
INSERT INTO `resource_info` (`resource_id`, `resource_name`, `node_type`, `parent_id`, `resource_type`, `resource_path`, `original_name`, `status`) VALUES 
('RES001', '高数导论视频', 2, '0', 1, '/videos/intro.mp4', 'intro.mp4', 3),
('RES002', '第一章PPT', 2, '0', 3, '/docs/chapter1.pptx', 'chapter1.pptx', 3);

-- 10. 关联课时与资源 (关联课时和视频/课件)
INSERT INTO `course_lesson_resource` (`id`, `lesson_id`, `resource_type`, `resource_id`, `is_primary`) VALUES 
('REL001', 'LESSON001', 1, 'RES001', 1),
('REL002', 'LESSON001', 2, 'RES002', 0);

-- 11. 插入试卷数据 (关联创建人)
INSERT INTO `paper_info` (`paper_id`, `paper_title`, `paper_type`, `total_score`, `duration`, `create_user_id`) VALUES 
('PAPER001', '高数第一章随堂测验', 1, 100, 60, 2);

-- 12. 插入习题数据 (关联创建人)
INSERT INTO `question_info` (`question_id`, `question_title`, `question_type`, `difficulty`, `answer`, `analysis`, `create_user_id`) VALUES 
('Q001', '微积分的基本定理是什么？', 4, 2, '参考答案：牛顿-莱布尼茨公式...', '解析：该公式建立了定积分与不定积分之间的联系...', 2);

-- 13. 插入试卷题目关联数据 (组卷)
INSERT INTO `paper_question` (`paper_question_id`, `paper_id`, `question_id`, `score`) VALUES 
('PQ001', 'PAPER001', 'Q001', 20);

-- 14. 插入题目选项 (如果是选择题则需要)
-- 这里补充一道选择题作为演示
INSERT INTO `question_info` (`question_id`, `question_title`, `question_type`, `difficulty`, `answer`, `analysis`, `create_user_id`) VALUES 
('Q002', '2 + 2 等于多少？', 1, 1, 'C', '简单的加法运算', 2);

INSERT INTO `question_option` (`option_id`, `question_id`, `option_content`) VALUES 
('O001', 'Q002', 'A. 3'),
('O002', 'Q002', 'B. 5'),
('O003', 'Q002', 'C. 4'),
('O004', 'Q002', 'D. 6');