-- ============================================================
-- Smart Campus Demo Data
-- 基于招生计划数据生成的院系、专业、班级样本数据
-- 生成日期: 2026-06-28
-- 说明: 全新完整数据, 不依赖任何已有记录, 直接 source 即可
-- ============================================================

SET NAMES utf8mb4;

-- ============================================================
-- 1. 院系数据 (11 个)
-- ============================================================

-- 1. 水利工程学院
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('水利工程学院', 'SL', '水利工程相关专业的教学与科研', 1);
SET @fid_sl = LAST_INSERT_ID();

-- 2. 环境科学与工程学院
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('环境科学与工程学院', 'HJ', '环境科学与工程相关专业的教学与科研', 2);
SET @fid_hj = LAST_INSERT_ID();

-- 3. 测绘科学与技术学院
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('测绘科学与技术学院', 'CH', '测绘与地理信息相关专业的教学与科研', 3);
SET @fid_ch = LAST_INSERT_ID();

-- 4. 建筑工程学院
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('建筑工程学院', 'JZ', '建筑与土木工程相关专业的教学与科研', 4);
SET @fid_jz = LAST_INSERT_ID();

-- 5. 电气工程学院
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('电气工程学院', 'DQ', '电气与自动化相关专业的教学与科研', 5);
SET @fid_dq = LAST_INSERT_ID();

-- 6. 机械工程学院
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('机械工程学院', 'JX', '机械与智能制造相关专业的教学与科研', 6);
SET @fid_jx = LAST_INSERT_ID();

-- 7. 计算机科学与技术学院
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('计算机科学与技术学院', 'JSJ', '计算机相关专业的教学与科研', 7);
SET @fid_jsj = LAST_INSERT_ID();

-- 8. 经济与管理学院
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('经济与管理学院', 'JG', '经济管理相关专业的教学与科研', 8);
SET @fid_jg = LAST_INSERT_ID();

-- 9. 人文与外国语学院
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('人文与外国语学院', 'RW', '人文与语言类专业的教学与科研', 9);
SET @fid_rw = LAST_INSERT_ID();

-- 10. 尼尔森马尔伯勒智能工程学院
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('尼尔森马尔伯勒智能工程学院', 'NEM', '中外合作办学智能工程相关专业的教学与科研', 10);
SET @fid_nem = LAST_INSERT_ID();

-- 11. 体育教学部
INSERT INTO `faculty_info` (`faculty_name`, `faculty_code`, `description`, `sort_order`) VALUES
('体育教学部', 'TY', '体育相关专业的教学与训练', 11);
SET @fid_ty = LAST_INSERT_ID();

-- ============================================================
-- 2. 专业数据 (43 个)
-- ============================================================

-- === 水利工程学院 (5 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('水利水电工程', 'SLSD', @fid_sl, '培养水利水电工程领域高级技术人才', 1);
SET @mid_slsd = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('农业水利工程', 'SLNY', @fid_sl, '培养农业水利工程领域应用型人才', 2);
SET @mid_slny = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('水文与水资源工程', 'SLSW', @fid_sl, '培养水文与水资源领域专业技术人才', 3);
SET @mid_slsw = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('港口航道与海岸工程', 'SLGK', @fid_sl, '培养港口航道与海岸工程领域技术人才', 4);
SET @mid_slgk = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('智慧水利', 'SLZH', @fid_sl, '培养智慧水利与信息化领域复合型人才', 5);
SET @mid_slzh = LAST_INSERT_ID();

-- === 环境科学与工程学院 (2 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('环境生态工程', 'HJST', @fid_hj, '培养环境生态工程领域应用型人才', 1);
SET @mid_hjst = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('给排水科学与工程', 'HJPS', @fid_hj, '培养给排水科学与工程领域技术人才', 2);
SET @mid_hjps = LAST_INSERT_ID();

-- === 测绘科学与技术学院 (3 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('测绘工程', 'CHCH', @fid_ch, '培养测绘工程领域高级技术人才', 1);
SET @mid_chch = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('地理信息科学', 'CHDL', @fid_ch, '培养地理信息科学领域复合型人才', 2);
SET @mid_chdl = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('遥感科学与技术', 'CHYG', @fid_ch, '培养遥感科学与技术领域专业人才', 3);
SET @mid_chyg = LAST_INSERT_ID();

-- === 建筑工程学院 (6 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('土木工程', 'JZTM', @fid_jz, '培养土木工程领域高级技术人才', 1);
SET @mid_jztm = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('工程造价', 'JZZJ', @fid_jz, '培养工程造价领域应用型人才', 2);
SET @mid_jzzj = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('工程管理', 'JZGL', @fid_jz, '培养工程管理领域复合型人才', 3);
SET @mid_jzgl = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('建筑环境与能源应用工程', 'JZJN', @fid_jz, '培养建筑环境与能源应用领域技术人才', 4);
SET @mid_jzjn = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('道路桥梁与渡河工程', 'JZDL', @fid_jz, '培养道路桥梁与渡河工程领域技术人才', 5);
SET @mid_jzdl = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('智能建造', 'JZZN', @fid_jz, '培养智能建造领域复合型人才', 6);
SET @mid_jzzn = LAST_INSERT_ID();

-- === 电气工程学院 (5 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('电气工程及其自动化', 'DQDQ', @fid_dq, '培养电气工程及其自动化领域高级技术人才', 1);
SET @mid_dqdq = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('新能源科学与工程', 'DQXN', @fid_dq, '培养新能源科学与工程领域技术人才', 2);
SET @mid_dqxn = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('自动化', 'DQZD', @fid_dq, '培养自动化领域应用型人才', 3);
SET @mid_dqzd = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('智能电网信息工程', 'DQDW', @fid_dq, '培养智能电网信息工程领域复合型人才', 4);
SET @mid_dqdw = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('智能感知工程', 'DQCG', @fid_dq, '培养智能感知工程领域专业人才', 5);
SET @mid_dqcg = LAST_INSERT_ID();

-- === 机械工程学院 (6 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('机械设计制造及其自动化', 'JXJX', @fid_jx, '培养机械设计制造及其自动化领域高级技术人才', 1);
SET @mid_jxjx = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('机械设计制造及其自动化（中外合作）', 'JXJH', @fid_jx, '培养具有国际视野的机械设计制造及其自动化人才', 2);
SET @mid_jxjh = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('材料成型及控制工程', 'JXCL', @fid_jx, '培养材料成型及控制工程领域技术人才', 3);
SET @mid_jxcl = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('车辆工程', 'JXCX', @fid_jx, '培养车辆工程领域应用型人才', 4);
SET @mid_jxcx = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('机器人工程', 'JXJQ', @fid_jx, '培养机器人工程领域复合型人才', 5);
SET @mid_jxjq = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('智能制造工程', 'JXZN', @fid_jx, '培养智能制造工程领域专业人才', 6);
SET @mid_jxzn = LAST_INSERT_ID();

-- === 计算机科学与技术学院 (5 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('软件工程', 'JSJRJ', @fid_jsj, '培养软件工程领域高级技术人才', 1);
SET @mid_jsjrj = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('物联网工程', 'JSJWL', @fid_jsj, '培养物联网工程领域复合型人才', 2);
SET @mid_jsjwl = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('数字媒体技术', 'JSJSM', @fid_jsj, '培养数字媒体技术领域应用型人才', 3);
SET @mid_jsjsm = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('人工智能', 'JSJRG', @fid_jsj, '培养人工智能领域高级技术人才', 4);
SET @mid_jsjrg = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('网络空间安全', 'JSJWK', @fid_jsj, '培养网络空间安全领域专业人才', 5);
SET @mid_jsjwk = LAST_INSERT_ID();

-- === 经济与管理学院 (5 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('人力资源管理', 'JGRL', @fid_jg, '培养人力资源管理领域应用型人才', 1);
SET @mid_jgrl = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('国际商务', 'JGGJ', @fid_jg, '培养国际商务领域复合型人才', 2);
SET @mid_jggj = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('物流工程', 'JGWL', @fid_jg, '培养物流工程领域技术人才', 3);
SET @mid_jgwl = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('金融工程', 'JGJR', @fid_jg, '培养金融工程领域专业人才', 4);
SET @mid_jgjr = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('农林经济管理', 'JGNL', @fid_jg, '培养农林经济管理领域应用型人才', 5);
SET @mid_jgnl = LAST_INSERT_ID();

-- === 人文与外国语学院 (2 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('商务英语', 'RWSW', @fid_rw, '培养商务英语领域应用型人才', 1);
SET @mid_rwsw = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('翻译', 'RWFY', @fid_rw, '培养翻译领域专业人才', 2);
SET @mid_rwfy = LAST_INSERT_ID();

-- === 尼尔森马尔伯勒智能工程学院 (3 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('土木工程（中外合作）', 'NEMTM', @fid_nem, '培养具有国际视野的土木工程人才', 1);
SET @mid_nemtm = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('物联网工程（中外合作）', 'NEMWL', @fid_nem, '培养具有国际视野的物联网工程人才', 2);
SET @mid_nemwl = LAST_INSERT_ID();

INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('数字媒体技术（中外合作）', 'NEMSM', @fid_nem, '培养具有国际视野的数字媒体技术人才', 3);
SET @mid_nemsm = LAST_INSERT_ID();

-- === 体育教学部 (1 个专业) ===
INSERT INTO `major_info` (`major_name`, `major_code`, `faculty_id`, `description`, `sort_order`) VALUES
('休闲体育', 'TYXT', @fid_ty, '培养休闲体育领域应用型人才', 1);
SET @mid_tyxt = LAST_INSERT_ID();

-- ============================================================
-- 3. 班级数据 (按分班方案生成, 共 107 个班)
-- ============================================================

-- === 水利工程学院 - 水利水电工程 (5 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('水利水电工程2024级1班', 'SLSD202401', @mid_slsd, '2024', '大一新生班级', 1),
('水利水电工程2024级2班', 'SLSD202402', @mid_slsd, '2024', '大一新生班级', 2),
('水利水电工程2024级3班', 'SLSD202403', @mid_slsd, '2024', '大一新生班级', 3),
('水利水电工程2024级4班', 'SLSD202404', @mid_slsd, '2024', '大一新生班级', 4),
('水利水电工程2024级5班', 'SLSD202405', @mid_slsd, '2024', '大一新生班级', 5);

-- === 水利工程学院 - 农业水利工程 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('农业水利工程2024级1班', 'SLNY202401', @mid_slny, '2024', '大一新生班级', 1),
('农业水利工程2024级2班', 'SLNY202402', @mid_slny, '2024', '大一新生班级', 2);

-- === 水利工程学院 - 水文与水资源工程 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('水文与水资源工程2024级1班', 'SLSW202401', @mid_slsw, '2024', '大一新生班级', 1),
('水文与水资源工程2024级2班', 'SLSW202402', @mid_slsw, '2024', '大一新生班级', 2);

-- === 水利工程学院 - 港口航道与海岸工程 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('港口航道与海岸工程2024级1班', 'SLGK202401', @mid_slgk, '2024', '大一新生班级', 1),
('港口航道与海岸工程2024级2班', 'SLGK202402', @mid_slgk, '2024', '大一新生班级', 2);

-- === 水利工程学院 - 智慧水利 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('智慧水利2024级1班', 'SLZH202401', @mid_slzh, '2024', '大一新生班级', 1),
('智慧水利2024级2班', 'SLZH202402', @mid_slzh, '2024', '大一新生班级', 2);

-- === 环境科学与工程学院 - 环境生态工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('环境生态工程2024级1班', 'HJST202401', @mid_hjst, '2024', '大一新生班级', 1),
('环境生态工程2024级2班', 'HJST202402', @mid_hjst, '2024', '大一新生班级', 2),
('环境生态工程2024级3班', 'HJST202403', @mid_hjst, '2024', '大一新生班级', 3);

-- === 环境科学与工程学院 - 给排水科学与工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('给排水科学与工程2024级1班', 'HJPS202401', @mid_hjps, '2024', '大一新生班级', 1),
('给排水科学与工程2024级2班', 'HJPS202402', @mid_hjps, '2024', '大一新生班级', 2),
('给排水科学与工程2024级3班', 'HJPS202403', @mid_hjps, '2024', '大一新生班级', 3);

-- === 测绘科学与技术学院 - 测绘工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('测绘工程2024级1班', 'CHCH202401', @mid_chch, '2024', '大一新生班级', 1),
('测绘工程2024级2班', 'CHCH202402', @mid_chch, '2024', '大一新生班级', 2),
('测绘工程2024级3班', 'CHCH202403', @mid_chch, '2024', '大一新生班级', 3);

-- === 测绘科学与技术学院 - 地理信息科学 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('地理信息科学2024级1班', 'CHDL202401', @mid_chdl, '2024', '大一新生班级', 1),
('地理信息科学2024级2班', 'CHDL202402', @mid_chdl, '2024', '大一新生班级', 2);

-- === 测绘科学与技术学院 - 遥感科学与技术 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('遥感科学与技术2024级1班', 'CHYG202401', @mid_chyg, '2024', '大一新生班级', 1),
('遥感科学与技术2024级2班', 'CHYG202402', @mid_chyg, '2024', '大一新生班级', 2);

-- === 建筑工程学院 - 土木工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('土木工程2024级1班', 'JZTM202401', @mid_jztm, '2024', '大一新生班级', 1),
('土木工程2024级2班', 'JZTM202402', @mid_jztm, '2024', '大一新生班级', 2),
('土木工程2024级3班', 'JZTM202403', @mid_jztm, '2024', '大一新生班级', 3);

-- === 建筑工程学院 - 工程造价 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('工程造价2024级1班', 'JZZJ202401', @mid_jzzj, '2024', '大一新生班级', 1),
('工程造价2024级2班', 'JZZJ202402', @mid_jzzj, '2024', '大一新生班级', 2);

-- === 建筑工程学院 - 工程管理 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('工程管理2024级1班', 'JZGL202401', @mid_jzgl, '2024', '大一新生班级', 1),
('工程管理2024级2班', 'JZGL202402', @mid_jzgl, '2024', '大一新生班级', 2),
('工程管理2024级3班', 'JZGL202403', @mid_jzgl, '2024', '大一新生班级', 3);

-- === 建筑工程学院 - 建筑环境与能源应用工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('建筑环境与能源应用工程2024级1班', 'JZJN202401', @mid_jzjn, '2024', '大一新生班级', 1),
('建筑环境与能源应用工程2024级2班', 'JZJN202402', @mid_jzjn, '2024', '大一新生班级', 2),
('建筑环境与能源应用工程2024级3班', 'JZJN202403', @mid_jzjn, '2024', '大一新生班级', 3);

-- === 建筑工程学院 - 道路桥梁与渡河工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('道路桥梁与渡河工程2024级1班', 'JZDL202401', @mid_jzdl, '2024', '大一新生班级', 1),
('道路桥梁与渡河工程2024级2班', 'JZDL202402', @mid_jzdl, '2024', '大一新生班级', 2),
('道路桥梁与渡河工程2024级3班', 'JZDL202403', @mid_jzdl, '2024', '大一新生班级', 3);

-- === 建筑工程学院 - 智能建造 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('智能建造2024级1班', 'JZZN202401', @mid_jzzn, '2024', '大一新生班级', 1),
('智能建造2024级2班', 'JZZN202402', @mid_jzzn, '2024', '大一新生班级', 2);

-- === 电气工程学院 - 电气工程及其自动化 (4 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('电气工程及其自动化2024级1班', 'DQDQ202401', @mid_dqdq, '2024', '大一新生班级', 1),
('电气工程及其自动化2024级2班', 'DQDQ202402', @mid_dqdq, '2024', '大一新生班级', 2),
('电气工程及其自动化2024级3班', 'DQDQ202403', @mid_dqdq, '2024', '大一新生班级', 3),
('电气工程及其自动化2024级4班', 'DQDQ202404', @mid_dqdq, '2024', '大一新生班级', 4);

-- === 电气工程学院 - 新能源科学与工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('新能源科学与工程2024级1班', 'DQXN202401', @mid_dqxn, '2024', '大一新生班级', 1),
('新能源科学与工程2024级2班', 'DQXN202402', @mid_dqxn, '2024', '大一新生班级', 2),
('新能源科学与工程2024级3班', 'DQXN202403', @mid_dqxn, '2024', '大一新生班级', 3);

-- === 电气工程学院 - 自动化 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('自动化2024级1班', 'DQZD202401', @mid_dqzd, '2024', '大一新生班级', 1),
('自动化2024级2班', 'DQZD202402', @mid_dqzd, '2024', '大一新生班级', 2);

-- === 电气工程学院 - 智能电网信息工程 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('智能电网信息工程2024级1班', 'DQDW202401', @mid_dqdw, '2024', '大一新生班级', 1),
('智能电网信息工程2024级2班', 'DQDW202402', @mid_dqdw, '2024', '大一新生班级', 2);

-- === 电气工程学院 - 智能感知工程 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('智能感知工程2024级1班', 'DQCG202401', @mid_dqcg, '2024', '大一新生班级', 1),
('智能感知工程2024级2班', 'DQCG202402', @mid_dqcg, '2024', '大一新生班级', 2);

-- === 机械工程学院 - 机械设计制造及其自动化 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('机械设计制造及其自动化2024级1班', 'JXJX202401', @mid_jxjx, '2024', '大一新生班级', 1),
('机械设计制造及其自动化2024级2班', 'JXJX202402', @mid_jxjx, '2024', '大一新生班级', 2),
('机械设计制造及其自动化2024级3班', 'JXJX202403', @mid_jxjx, '2024', '大一新生班级', 3);

-- === 机械工程学院 - 机械设计制造及其自动化（中外合作）(2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('机械设计制造及其自动化（中外合作）2024级1班', 'JXJH202401', @mid_jxjh, '2024', '大一新生班级', 1),
('机械设计制造及其自动化（中外合作）2024级2班', 'JXJH202402', @mid_jxjh, '2024', '大一新生班级', 2);

-- === 机械工程学院 - 材料成型及控制工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('材料成型及控制工程2024级1班', 'JXCL202401', @mid_jxcl, '2024', '大一新生班级', 1),
('材料成型及控制工程2024级2班', 'JXCL202402', @mid_jxcl, '2024', '大一新生班级', 2),
('材料成型及控制工程2024级3班', 'JXCL202403', @mid_jxcl, '2024', '大一新生班级', 3);

-- === 机械工程学院 - 车辆工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('车辆工程2024级1班', 'JXCX202401', @mid_jxcx, '2024', '大一新生班级', 1),
('车辆工程2024级2班', 'JXCX202402', @mid_jxcx, '2024', '大一新生班级', 2),
('车辆工程2024级3班', 'JXCX202403', @mid_jxcx, '2024', '大一新生班级', 3);

-- === 机械工程学院 - 机器人工程 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('机器人工程2024级1班', 'JXJQ202401', @mid_jxjq, '2024', '大一新生班级', 1),
('机器人工程2024级2班', 'JXJQ202402', @mid_jxjq, '2024', '大一新生班级', 2);

-- === 机械工程学院 - 智能制造工程 (1 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('智能制造工程2024级1班', 'JXZN202401', @mid_jxzn, '2024', '大一新生班级', 1);

-- === 计算机科学与技术学院 - 软件工程 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('软件工程2024级1班', 'JSJRJ202401', @mid_jsjrj, '2024', '大一新生班级', 1),
('软件工程2024级2班', 'JSJRJ202402', @mid_jsjrj, '2024', '大一新生班级', 2);

-- === 计算机科学与技术学院 - 物联网工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('物联网工程2024级1班', 'JSJWL202401', @mid_jsjwl, '2024', '大一新生班级', 1),
('物联网工程2024级2班', 'JSJWL202402', @mid_jsjwl, '2024', '大一新生班级', 2),
('物联网工程2024级3班', 'JSJWL202403', @mid_jsjwl, '2024', '大一新生班级', 3);

-- === 计算机科学与技术学院 - 数字媒体技术 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('数字媒体技术2024级1班', 'JSJSM202401', @mid_jsjsm, '2024', '大一新生班级', 1),
('数字媒体技术2024级2班', 'JSJSM202402', @mid_jsjsm, '2024', '大一新生班级', 2);

-- === 计算机科学与技术学院 - 人工智能 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('人工智能2024级1班', 'JSJRG202401', @mid_jsjrg, '2024', '大一新生班级', 1),
('人工智能2024级2班', 'JSJRG202402', @mid_jsjrg, '2024', '大一新生班级', 2);

-- === 计算机科学与技术学院 - 网络空间安全 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('网络空间安全2024级1班', 'JSJWK202401', @mid_jsjwk, '2024', '大一新生班级', 1),
('网络空间安全2024级2班', 'JSJWK202402', @mid_jsjwk, '2024', '大一新生班级', 2);

-- === 经济与管理学院 - 人力资源管理 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('人力资源管理2024级1班', 'JGRL202401', @mid_jgrl, '2024', '大一新生班级', 1),
('人力资源管理2024级2班', 'JGRL202402', @mid_jgrl, '2024', '大一新生班级', 2),
('人力资源管理2024级3班', 'JGRL202403', @mid_jgrl, '2024', '大一新生班级', 3);

-- === 经济与管理学院 - 国际商务 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('国际商务2024级1班', 'JGGJ202401', @mid_jggj, '2024', '大一新生班级', 1),
('国际商务2024级2班', 'JGGJ202402', @mid_jggj, '2024', '大一新生班级', 2),
('国际商务2024级3班', 'JGGJ202403', @mid_jggj, '2024', '大一新生班级', 3);

-- === 经济与管理学院 - 物流工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('物流工程2024级1班', 'JGWL202401', @mid_jgwl, '2024', '大一新生班级', 1),
('物流工程2024级2班', 'JGWL202402', @mid_jgwl, '2024', '大一新生班级', 2),
('物流工程2024级3班', 'JGWL202403', @mid_jgwl, '2024', '大一新生班级', 3);

-- === 经济与管理学院 - 金融工程 (3 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('金融工程2024级1班', 'JGJR202401', @mid_jgjr, '2024', '大一新生班级', 1),
('金融工程2024级2班', 'JGJR202402', @mid_jgjr, '2024', '大一新生班级', 2),
('金融工程2024级3班', 'JGJR202403', @mid_jgjr, '2024', '大一新生班级', 3);

-- === 经济与管理学院 - 农林经济管理 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('农林经济管理2024级1班', 'JGNL202401', @mid_jgnl, '2024', '大一新生班级', 1),
('农林经济管理2024级2班', 'JGNL202402', @mid_jgnl, '2024', '大一新生班级', 2);

-- === 人文与外国语学院 - 商务英语 (4 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('商务英语2024级1班', 'RWSW202401', @mid_rwsw, '2024', '大一新生班级', 1),
('商务英语2024级2班', 'RWSW202402', @mid_rwsw, '2024', '大一新生班级', 2),
('商务英语2024级3班', 'RWSW202403', @mid_rwsw, '2024', '大一新生班级', 3),
('商务英语2024级4班', 'RWSW202404', @mid_rwsw, '2024', '大一新生班级', 4);

-- === 人文与外国语学院 - 翻译 (2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('翻译2024级1班', 'RWFY202401', @mid_rwfy, '2024', '大一新生班级', 1),
('翻译2024级2班', 'RWFY202402', @mid_rwfy, '2024', '大一新生班级', 2);

-- === 尼尔森马尔伯勒智能工程学院 - 土木工程（中外合作）(2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('土木工程（中外合作）2024级1班', 'NEMTM202401', @mid_nemtm, '2024', '大一新生班级', 1),
('土木工程（中外合作）2024级2班', 'NEMTM202402', @mid_nemtm, '2024', '大一新生班级', 2);

-- === 尼尔森马尔伯勒智能工程学院 - 物联网工程（中外合作）(2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('物联网工程（中外合作）2024级1班', 'NEMWL202401', @mid_nemwl, '2024', '大一新生班级', 1),
('物联网工程（中外合作）2024级2班', 'NEMWL202402', @mid_nemwl, '2024', '大一新生班级', 2);

-- === 尼尔森马尔伯勒智能工程学院 - 数字媒体技术（中外合作）(2 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('数字媒体技术（中外合作）2024级1班', 'NEMSM202401', @mid_nemsm, '2024', '大一新生班级', 1),
('数字媒体技术（中外合作）2024级2班', 'NEMSM202402', @mid_nemsm, '2024', '大一新生班级', 2);

-- === 体育教学部 - 休闲体育 (1 个班) ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('休闲体育2024级1班', 'TYXT202401', @mid_tyxt, '2024', '大一新生班级', 1);

-- ============================================================
-- 4. 2023级 / 2025级 补充班级 (每个专业各 1 个班)
-- ============================================================

-- === 水利工程学院 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('水利水电工程2023级1班', 'SLSD202301', @mid_slsd, '2023', '大二学生班级', 1),
('水利水电工程2025级1班', 'SLSD202501', @mid_slsd, '2025', '大一新生班级', 1),
('农业水利工程2023级1班', 'SLNY202301', @mid_slny, '2023', '大二学生班级', 1),
('农业水利工程2025级1班', 'SLNY202501', @mid_slny, '2025', '大一新生班级', 1),
('水文与水资源工程2023级1班', 'SLSW202301', @mid_slsw, '2023', '大二学生班级', 1),
('水文与水资源工程2025级1班', 'SLSW202501', @mid_slsw, '2025', '大一新生班级', 1),
('港口航道与海岸工程2023级1班', 'SLGK202301', @mid_slgk, '2023', '大二学生班级', 1),
('港口航道与海岸工程2025级1班', 'SLGK202501', @mid_slgk, '2025', '大一新生班级', 1),
('智慧水利2023级1班', 'SLZH202301', @mid_slzh, '2023', '大二学生班级', 1),
('智慧水利2025级1班', 'SLZH202501', @mid_slzh, '2025', '大一新生班级', 1);

-- === 环境科学与工程学院 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('环境生态工程2023级1班', 'HJST202301', @mid_hjst, '2023', '大二学生班级', 1),
('环境生态工程2025级1班', 'HJST202501', @mid_hjst, '2025', '大一新生班级', 1),
('给排水科学与工程2023级1班', 'HJPS202301', @mid_hjps, '2023', '大二学生班级', 1),
('给排水科学与工程2025级1班', 'HJPS202501', @mid_hjps, '2025', '大一新生班级', 1);

-- === 测绘科学与技术学院 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('测绘工程2023级1班', 'CHCH202301', @mid_chch, '2023', '大二学生班级', 1),
('测绘工程2025级1班', 'CHCH202501', @mid_chch, '2025', '大一新生班级', 1),
('地理信息科学2023级1班', 'CHDL202301', @mid_chdl, '2023', '大二学生班级', 1),
('地理信息科学2025级1班', 'CHDL202501', @mid_chdl, '2025', '大一新生班级', 1),
('遥感科学与技术2023级1班', 'CHYG202301', @mid_chyg, '2023', '大二学生班级', 1),
('遥感科学与技术2025级1班', 'CHYG202501', @mid_chyg, '2025', '大一新生班级', 1);

-- === 建筑工程学院 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('土木工程2023级1班', 'JZTM202301', @mid_jztm, '2023', '大二学生班级', 1),
('土木工程2025级1班', 'JZTM202501', @mid_jztm, '2025', '大一新生班级', 1),
('工程造价2023级1班', 'JZZJ202301', @mid_jzzj, '2023', '大二学生班级', 1),
('工程造价2025级1班', 'JZZJ202501', @mid_jzzj, '2025', '大一新生班级', 1),
('工程管理2023级1班', 'JZGL202301', @mid_jzgl, '2023', '大二学生班级', 1),
('工程管理2025级1班', 'JZGL202501', @mid_jzgl, '2025', '大一新生班级', 1),
('建筑环境与能源应用工程2023级1班', 'JZJN202301', @mid_jzjn, '2023', '大二学生班级', 1),
('建筑环境与能源应用工程2025级1班', 'JZJN202501', @mid_jzjn, '2025', '大一新生班级', 1),
('道路桥梁与渡河工程2023级1班', 'JZDL202301', @mid_jzdl, '2023', '大二学生班级', 1),
('道路桥梁与渡河工程2025级1班', 'JZDL202501', @mid_jzdl, '2025', '大一新生班级', 1),
('智能建造2023级1班', 'JZZN202301', @mid_jzzn, '2023', '大二学生班级', 1),
('智能建造2025级1班', 'JZZN202501', @mid_jzzn, '2025', '大一新生班级', 1);

-- === 电气工程学院 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('电气工程及其自动化2023级1班', 'DQDQ202301', @mid_dqdq, '2023', '大二学生班级', 1),
('电气工程及其自动化2025级1班', 'DQDQ202501', @mid_dqdq, '2025', '大一新生班级', 1),
('新能源科学与工程2023级1班', 'DQXN202301', @mid_dqxn, '2023', '大二学生班级', 1),
('新能源科学与工程2025级1班', 'DQXN202501', @mid_dqxn, '2025', '大一新生班级', 1),
('自动化2023级1班', 'DQZD202301', @mid_dqzd, '2023', '大二学生班级', 1),
('自动化2025级1班', 'DQZD202501', @mid_dqzd, '2025', '大一新生班级', 1),
('智能电网信息工程2023级1班', 'DQDW202301', @mid_dqdw, '2023', '大二学生班级', 1),
('智能电网信息工程2025级1班', 'DQDW202501', @mid_dqdw, '2025', '大一新生班级', 1),
('智能感知工程2023级1班', 'DQCG202301', @mid_dqcg, '2023', '大二学生班级', 1),
('智能感知工程2025级1班', 'DQCG202501', @mid_dqcg, '2025', '大一新生班级', 1);

-- === 机械工程学院 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('机械设计制造及其自动化2023级1班', 'JXJX202301', @mid_jxjx, '2023', '大二学生班级', 1),
('机械设计制造及其自动化2025级1班', 'JXJX202501', @mid_jxjx, '2025', '大一新生班级', 1),
('机械设计制造及其自动化（中外合作）2023级1班', 'JXJH202301', @mid_jxjh, '2023', '大二学生班级', 1),
('机械设计制造及其自动化（中外合作）2025级1班', 'JXJH202501', @mid_jxjh, '2025', '大一新生班级', 1),
('材料成型及控制工程2023级1班', 'JXCL202301', @mid_jxcl, '2023', '大二学生班级', 1),
('材料成型及控制工程2025级1班', 'JXCL202501', @mid_jxcl, '2025', '大一新生班级', 1),
('车辆工程2023级1班', 'JXCX202301', @mid_jxcx, '2023', '大二学生班级', 1),
('车辆工程2025级1班', 'JXCX202501', @mid_jxcx, '2025', '大一新生班级', 1),
('机器人工程2023级1班', 'JXJQ202301', @mid_jxjq, '2023', '大二学生班级', 1),
('机器人工程2025级1班', 'JXJQ202501', @mid_jxjq, '2025', '大一新生班级', 1),
('智能制造工程2023级1班', 'JXZN202301', @mid_jxzn, '2023', '大二学生班级', 1),
('智能制造工程2025级1班', 'JXZN202501', @mid_jxzn, '2025', '大一新生班级', 1);

-- === 计算机科学与技术学院 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('软件工程2023级1班', 'JSJRJ202301', @mid_jsjrj, '2023', '大二学生班级', 1),
('软件工程2025级1班', 'JSJRJ202501', @mid_jsjrj, '2025', '大一新生班级', 1),
('物联网工程2023级1班', 'JSJWL202301', @mid_jsjwl, '2023', '大二学生班级', 1),
('物联网工程2025级1班', 'JSJWL202501', @mid_jsjwl, '2025', '大一新生班级', 1),
('数字媒体技术2023级1班', 'JSJSM202301', @mid_jsjsm, '2023', '大二学生班级', 1),
('数字媒体技术2025级1班', 'JSJSM202501', @mid_jsjsm, '2025', '大一新生班级', 1),
('人工智能2023级1班', 'JSJRG202301', @mid_jsjrg, '2023', '大二学生班级', 1),
('人工智能2025级1班', 'JSJRG202501', @mid_jsjrg, '2025', '大一新生班级', 1),
('网络空间安全2023级1班', 'JSJWK202301', @mid_jsjwk, '2023', '大二学生班级', 1),
('网络空间安全2025级1班', 'JSJWK202501', @mid_jsjwk, '2025', '大一新生班级', 1);

-- === 经济与管理学院 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('人力资源管理2023级1班', 'JGRL202301', @mid_jgrl, '2023', '大二学生班级', 1),
('人力资源管理2025级1班', 'JGRL202501', @mid_jgrl, '2025', '大一新生班级', 1),
('国际商务2023级1班', 'JGGJ202301', @mid_jggj, '2023', '大二学生班级', 1),
('国际商务2025级1班', 'JGGJ202501', @mid_jggj, '2025', '大一新生班级', 1),
('物流工程2023级1班', 'JGWL202301', @mid_jgwl, '2023', '大二学生班级', 1),
('物流工程2025级1班', 'JGWL202501', @mid_jgwl, '2025', '大一新生班级', 1),
('金融工程2023级1班', 'JGJR202301', @mid_jgjr, '2023', '大二学生班级', 1),
('金融工程2025级1班', 'JGJR202501', @mid_jgjr, '2025', '大一新生班级', 1),
('农林经济管理2023级1班', 'JGNL202301', @mid_jgnl, '2023', '大二学生班级', 1),
('农林经济管理2025级1班', 'JGNL202501', @mid_jgnl, '2025', '大一新生班级', 1);

-- === 人文与外国语学院 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('商务英语2023级1班', 'RWSW202301', @mid_rwsw, '2023', '大二学生班级', 1),
('商务英语2025级1班', 'RWSW202501', @mid_rwsw, '2025', '大一新生班级', 1),
('翻译2023级1班', 'RWFY202301', @mid_rwfy, '2023', '大二学生班级', 1),
('翻译2025级1班', 'RWFY202501', @mid_rwfy, '2025', '大一新生班级', 1);

-- === 尼尔森马尔伯勒智能工程学院 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('土木工程（中外合作）2023级1班', 'NEMTM202301', @mid_nemtm, '2023', '大二学生班级', 1),
('土木工程（中外合作）2025级1班', 'NEMTM202501', @mid_nemtm, '2025', '大一新生班级', 1),
('物联网工程（中外合作）2023级1班', 'NEMWL202301', @mid_nemwl, '2023', '大二学生班级', 1),
('物联网工程（中外合作）2025级1班', 'NEMWL202501', @mid_nemwl, '2025', '大一新生班级', 1),
('数字媒体技术（中外合作）2023级1班', 'NEMSM202301', @mid_nemsm, '2023', '大二学生班级', 1),
('数字媒体技术（中外合作）2025级1班', 'NEMSM202501', @mid_nemsm, '2025', '大一新生班级', 1);

-- === 体育教学部 ===
INSERT INTO `class_info` (`class_name`, `class_code`, `major_id`, `grade`, `description`, `sort_order`) VALUES
('休闲体育2023级1班', 'TYXT202301', @mid_tyxt, '2023', '大二学生班级', 1),
('休闲体育2025级1班', 'TYXT202501', @mid_tyxt, '2025', '大一新生班级', 1);

-- ============================================================
-- 统计:
--   院系: 11 个
--   专业: 43 个
--   班级: 107 (24级) + 86 (23/25级) = 193 个
--   覆盖总招生计划: 4300+ 人
-- ============================================================
