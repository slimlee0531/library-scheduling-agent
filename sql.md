-- 创建数据库
CREATE DATABASE IF NOT EXISTS library_agent
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;

USE library_agent;

-- ==============================================
-- 1. 全局系统配置表（仅5个核心配置）
-- ==============================================
CREATE TABLE system_configs (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
config_key VARCHAR(50) NOT NULL UNIQUE,
config_value VARCHAR(200) NOT NULL,
description VARCHAR(200) NOT NULL
) COMMENT '全局系统配置表';

-- 初始化配置
INSERT INTO system_configs (config_key, config_value, description) VALUES
('weekly_shifts', '4', '每人每周值班次数'),
('buffer_minutes', '20', '值班结束后通勤缓冲时间(分钟)，同时也是允许提前上班的时间'),
('closed_weekday', '3', '每周闭馆日(1=周一,7=周日)'),
('closed_start', '16:00:00', '闭馆开始时间'),
('closed_end', '18:00:00', '闭馆结束时间');

-- ==============================================
-- 2. 课程节次配置表（全局唯一时间标准）
-- ==============================================
CREATE TABLE course_periods (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
period_number INT NOT NULL UNIQUE COMMENT '节次号(1,2,3,4...)',
start_time TIME NOT NULL COMMENT '该节标准开始时间',
end_time TIME NOT NULL COMMENT '该节标准结束时间',
description VARCHAR(50) COMMENT '说明(如"第1-2节(大节)")'
) COMMENT '课程节次时间配置表';

-- 初始化国内大学标准节次时间（完全匹配你的例子）
INSERT INTO course_periods (period_number, start_time, end_time, description) VALUES
(1, '08:00:00', '08:45:00', '第1小节'),
(2, '08:55:00', '09:40:00', '第2小节'),
(3, '10:00:00', '10:45:00', '第3小节'),
(4, '10:55:00', '11:40:00', '第4小节'),
(5, '14:00:00', '14:45:00', '第5小节'),
(6, '14:55:00', '15:40:00', '第6小节'),
(7, '16:00:00', '16:45:00', '第7小节'),
(8, '16:55:00', '17:40:00', '第8小节'),
(9, '19:00:00', '19:45:00', '第9小节'),
(10, '19:55:00', '20:40:00', '第10小节'),
(11, '20:50:00', '21:35:00', '第11小节');

-- ==============================================
-- 3. 学生信息表（可选字段设计，支持管理员批量导入）
-- ==============================================
CREATE TABLE students (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL COMMENT '学生姓名（唯一必填）',
student_no VARCHAR(20) COMMENT '学号（可选，用于登录）',
phone VARCHAR(20) COMMENT '联系电话（可选）',
password VARCHAR(100) COMMENT '登录密码（可选）',
status TINYINT DEFAULT 1 COMMENT '1=在职 0=离职',
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) COMMENT '学生信息表';

-- ==============================================
-- 4. 学生课表表（仅3个字段，无任何冗余）
-- ==============================================
CREATE TABLE student_courses (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
student_id BIGINT NOT NULL COMMENT '关联学生ID',
day_of_week TINYINT NOT NULL COMMENT '1=周一 7=周日',
period_number INT NOT NULL COMMENT '第几节课(关联course_periods表)',
UNIQUE KEY uk_stu_course (student_id, day_of_week, period_number),
FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
FOREIGN KEY (period_number) REFERENCES course_periods(period_number)
) COMMENT '学生课表表';

-- ==============================================
-- 5. 值班时段配置表（分时段人数规则）
-- ==============================================
CREATE TABLE shift_configs (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
day_type TINYINT NOT NULL COMMENT '1=工作日 2=周六 3=周日',
start_time TIME NOT NULL COMMENT '标准开始时间',
end_time TIME NOT NULL COMMENT '标准结束时间',
min_stu INT NOT NULL DEFAULT 1,
max_stu INT NOT NULL DEFAULT 1,
UNIQUE KEY uk_day_time (day_type, start_time, end_time)
) COMMENT '值班时段配置表';

-- 初始化你的图书馆值班规则
INSERT INTO shift_configs (day_type, start_time, end_time, min_stu, max_stu) VALUES
-- 工作日（周一至周五）
(1,'08:00:00','10:00:00',1,2),
(1,'10:00:00','12:00:00',1,1),
(1,'14:00:00','16:00:00',1,2),
(1,'16:00:00','18:00:00',1,1),
(1,'18:00:00','20:00:00',1,1),
(1,'20:00:00','22:00:00',1,1),
-- 周六
(2,'08:00:00','10:00:00',1,1),
(2,'14:00:00','16:00:00',1,1),
-- 周日
(3,'08:00:00','10:00:00',1,1),
(3,'14:00:00','16:00:00',1,1);

-- ==============================================
-- 6. 最终排班结果表（核心输出）
-- ==============================================
CREATE TABLE duty_assignments (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
student_id BIGINT NOT NULL,
student_name VARCHAR(50) NOT NULL,
duty_date DATE NOT NULL,
start_time TIME NOT NULL,
end_time TIME NOT NULL,
week_num INT NOT NULL COMMENT '年度第几周',
is_early TINYINT DEFAULT 0 COMMENT '1=提前上班',
UNIQUE KEY uk_stu_date (student_id, duty_date, start_time),
FOREIGN KEY (student_id) REFERENCES students(id)
) COMMENT '值班排班结果表';