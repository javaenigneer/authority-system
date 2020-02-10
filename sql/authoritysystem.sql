/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.40-log : Database - authoritysystem
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`authoritysystem` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `authoritysystem`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `admin_id` bigint(100) NOT NULL AUTO_INCREMENT COMMENT '管理员的主键Id',
  `admin_name` varchar(255) DEFAULT NULL COMMENT '管理员的名称',
  `admin_password` varchar(255) DEFAULT NULL COMMENT '管理员的密码',
  `admin_email` varchar(255) DEFAULT NULL COMMENT '管理员的邮箱',
  `admin_sex` varchar(1) DEFAULT NULL COMMENT '管理员的性别',
  `admin_status` int(11) DEFAULT NULL COMMENT '管理员的状态',
  `admin_delete` int(11) DEFAULT NULL COMMENT '管理员的是否删除',
  `admin_phone` varchar(11) DEFAULT NULL COMMENT '管理员的手机号码',
  `admin_create_time` timestamp NULL DEFAULT NULL COMMENT '管理员的加入时间',
  `admin_update_time` timestamp NULL DEFAULT NULL COMMENT '管理员的更新时间',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `t_admin` */

insert  into `t_admin`(`admin_id`,`admin_name`,`admin_password`,`admin_email`,`admin_sex`,`admin_status`,`admin_delete`,`admin_phone`,`admin_create_time`,`admin_update_time`) values (1,'feifei','123456','1692454247@qq.com','男',1,1,'15281223316','2020-01-24 11:47:31','2020-01-24 11:47:31'),(15,'admin','123456','1692454247@qq.com','\0',1,1,NULL,'2020-02-06 13:07:54',NULL);

/*Table structure for table `t_admin_role` */

DROP TABLE IF EXISTS `t_admin_role`;

CREATE TABLE `t_admin_role` (
  `admin_role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`admin_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

/*Data for the table `t_admin_role` */

insert  into `t_admin_role`(`admin_role_id`,`admin_id`,`role_id`) values (1,1,1),(2,2,2),(3,5,79),(17,15,2);

/*Table structure for table `t_dept` */

DROP TABLE IF EXISTS `t_dept`;

CREATE TABLE `t_dept` (
  `DEPT_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `PARENT_ID` bigint(20) NOT NULL COMMENT '上级部门ID',
  `DEPT_NAME` varchar(100) NOT NULL COMMENT '部门名称',
  `ORDER_NUM` bigint(20) DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`DEPT_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门表';

/*Data for the table `t_dept` */

insert  into `t_dept`(`DEPT_ID`,`PARENT_ID`,`DEPT_NAME`,`ORDER_NUM`,`CREATE_TIME`,`MODIFY_TIME`) values (4,0,'采购部',2,'2019-06-14 20:59:56',NULL),(5,0,'财务部',3,'2019-06-14 21:00:08',NULL),(6,0,'销售部',4,'2019-06-14 21:00:15',NULL),(7,0,'工程部',5,'2019-06-14 21:00:42',NULL),(8,0,'行政部',6,'2019-06-14 21:00:49',NULL),(9,0,'人力资源部',8,'2019-06-14 21:01:14','2019-06-14 21:01:34'),(10,0,'系统部',7,'2019-06-14 21:01:31',NULL),(13,0,'测试部门',1,'2020-02-06 22:12:00','2020-02-06 22:12:00'),(14,13,'测试部门1',1,'2020-02-06 22:12:03','2020-02-06 22:12:03'),(15,13,'测试部门2',1,'2020-02-06 22:12:39','2020-02-06 22:12:39'),(23,13,'未命名1',1,'2020-02-06 22:40:18','2020-02-06 22:40:18');

/*Table structure for table `t_job` */

DROP TABLE IF EXISTS `t_job`;

CREATE TABLE `t_job` (
  `JOB_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `BEAN_NAME` varchar(50) NOT NULL COMMENT 'spring bean名称',
  `METHOD_NAME` varchar(50) NOT NULL COMMENT '方法名',
  `PARAMS` varchar(50) DEFAULT NULL COMMENT '参数',
  `CRON_EXPRESSION` varchar(20) NOT NULL COMMENT 'cron表达式',
  `STATUS` varchar(10) NOT NULL COMMENT '任务状态  0：正常  1：暂停',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`JOB_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务表';

/*Data for the table `t_job` */

insert  into `t_job`(`JOB_ID`,`BEAN_NAME`,`METHOD_NAME`,`PARAMS`,`CRON_EXPRESSION`,`STATUS`,`REMARK`,`CREATE_TIME`) values (3,'testTask','test','hello world','0/1 * * * * ?','0','有参任务调度测试,每隔一秒触发哈','2018-02-26 09:28:26');

/*Table structure for table `t_job_log` */

DROP TABLE IF EXISTS `t_job_log`;

CREATE TABLE `t_job_log` (
  `LOG_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `JOB_ID` bigint(20) NOT NULL COMMENT '任务id',
  `BEAN_NAME` varchar(100) NOT NULL COMMENT 'spring bean名称',
  `METHOD_NAME` varchar(100) NOT NULL COMMENT '方法名',
  `PARAMS` varchar(200) DEFAULT NULL COMMENT '参数',
  `STATUS` varchar(2) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `ERROR` text COMMENT '失败信息',
  `TIMES` decimal(11,0) DEFAULT NULL COMMENT '耗时(单位：毫秒)',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`LOG_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3642 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='调度日志表';

/*Data for the table `t_job_log` */

insert  into `t_job_log`(`LOG_ID`,`JOB_ID`,`BEAN_NAME`,`METHOD_NAME`,`PARAMS`,`STATUS`,`ERROR`,`TIMES`,`CREATE_TIME`) values (3472,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 14:26:15'),(3473,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 14:26:16'),(3474,3,'testTask','test','hello world','0',NULL,'6','2020-02-10 16:01:11'),(3475,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:12'),(3476,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:13'),(3477,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:14'),(3478,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:15'),(3479,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:01:16'),(3480,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:01:17'),(3481,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:18'),(3482,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:19'),(3483,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:01:20'),(3484,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:01:21'),(3485,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:01:22'),(3486,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:23'),(3487,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:24'),(3488,3,'testTask','test','hello world','0',NULL,'4','2020-02-10 16:01:25'),(3489,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:01:26'),(3490,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:27'),(3491,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:28'),(3492,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:29'),(3493,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:30'),(3494,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:31'),(3495,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:32'),(3496,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:01:33'),(3497,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:01:34'),(3498,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:01:35'),(3499,3,'testTask','test','hello world','0',NULL,'4','2020-02-10 16:02:21'),(3500,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:22'),(3501,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:23'),(3502,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:02:24'),(3503,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:25'),(3504,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:26'),(3505,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:02:27'),(3506,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:28'),(3507,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:29'),(3508,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:30'),(3509,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:02:31'),(3510,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:32'),(3511,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:33'),(3512,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:34'),(3513,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:35'),(3514,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:02:36'),(3515,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:02:37'),(3516,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:38'),(3517,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:39'),(3518,3,'testTask','test','hello world','0',NULL,'15','2020-02-10 16:02:40'),(3519,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:41'),(3520,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:42'),(3521,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:43'),(3522,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:02:44'),(3523,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:45'),(3524,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:46'),(3525,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:02:47'),(3526,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:02:48'),(3527,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:49'),(3528,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:02:50'),(3529,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:51'),(3530,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:52'),(3531,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:53'),(3532,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:54'),(3533,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:55'),(3534,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:02:56'),(3535,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:57'),(3536,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:02:58'),(3537,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:02:59'),(3538,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:03:00'),(3539,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:03:01'),(3540,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:03:02'),(3541,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:03:03'),(3542,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:14:32'),(3543,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:33'),(3544,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:34'),(3545,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:14:35'),(3546,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:14:36'),(3547,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:37'),(3548,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:38'),(3549,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:39'),(3550,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:14:40'),(3551,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:41'),(3552,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:42'),(3553,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:14:43'),(3554,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:44'),(3555,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:14:45'),(3556,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:46'),(3557,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:47'),(3558,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:14:48'),(3559,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:14:49'),(3560,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:50'),(3561,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:51'),(3562,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:52'),(3563,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:53'),(3564,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:54'),(3565,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:14:55'),(3566,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:14:56'),(3567,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:57'),(3568,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:58'),(3569,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:14:59'),(3570,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:15:00'),(3571,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:15:01'),(3572,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:15:02'),(3573,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:15:03'),(3574,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:15:04'),(3575,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:15:05'),(3576,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:15:06'),(3577,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:15:07'),(3578,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:15:08'),(3579,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:15:09'),(3580,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:15:10'),(3581,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:15:11'),(3582,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:21:08'),(3583,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:21:09'),(3584,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:21:10'),(3585,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:21:11'),(3586,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:21:12'),(3587,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:21:13'),(3588,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:21:14'),(3589,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:21:15'),(3590,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:21:16'),(3591,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:21:17'),(3592,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:21:18'),(3593,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:21:19'),(3594,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:21:20'),(3595,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:21:21'),(3596,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:21:22'),(3597,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:21:23'),(3598,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:21:24'),(3599,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:21:25'),(3600,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:32:04'),(3601,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:05'),(3602,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:06'),(3603,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:07'),(3604,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:08'),(3605,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:09'),(3606,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:10'),(3607,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:11'),(3608,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:12'),(3609,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:32:13'),(3610,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:32:14'),(3611,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:15'),(3612,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:16'),(3613,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:17'),(3614,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:18'),(3615,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:19'),(3616,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:20'),(3617,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:21'),(3618,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:22'),(3619,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:23'),(3620,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:24'),(3621,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:25'),(3622,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:26'),(3623,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:27'),(3624,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:28'),(3625,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:29'),(3626,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:32:30'),(3627,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:31'),(3628,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:32'),(3629,3,'testTask','test','hello world','0',NULL,'3','2020-02-10 16:32:33'),(3630,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:34'),(3631,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:35'),(3632,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:36'),(3633,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:32:37'),(3634,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:38'),(3635,3,'testTask','test','hello world','0',NULL,'0','2020-02-10 16:32:39'),(3636,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:40'),(3637,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:41'),(3638,3,'testTask','test','hello world','0',NULL,'2','2020-02-10 16:32:42'),(3639,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:43'),(3640,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:44'),(3641,3,'testTask','test','hello world','0',NULL,'1','2020-02-10 16:32:55');

/*Table structure for table `t_member` */

DROP TABLE IF EXISTS `t_member`;

CREATE TABLE `t_member` (
  `member_id` bigint(100) NOT NULL AUTO_INCREMENT COMMENT '会员Id',
  `member_name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  `member_email` varchar(255) DEFAULT NULL COMMENT '会员邮箱',
  `member_phone` varchar(11) DEFAULT NULL COMMENT '会员手机',
  `member_sex` varchar(1) DEFAULT NULL COMMENT '会员性别',
  `member_createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '会员加入时间',
  `member_updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '会员更新时间',
  `member_status` int(11) DEFAULT NULL COMMENT '会员状态',
  `member_delete` int(11) DEFAULT NULL COMMENT '会员删除状态',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_member` */

/*Table structure for table `t_menu` */

DROP TABLE IF EXISTS `t_menu`;

CREATE TABLE `t_menu` (
  `authority_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
  `parent_id` bigint(20) NOT NULL COMMENT '上级菜单ID',
  `authority_name` varchar(50) NOT NULL COMMENT '菜单/按钮名称',
  `menu_url` varchar(50) DEFAULT NULL COMMENT '菜单URL',
  `authority` text COMMENT '权限标识',
  `menu_icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `is_menu` char(2) NOT NULL COMMENT '类型 0菜单 1按钮',
  `order_number` bigint(20) DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `checked` int(10) DEFAULT '0',
  PRIMARY KEY (`authority_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

/*Data for the table `t_menu` */

insert  into `t_menu`(`authority_id`,`parent_id`,`authority_name`,`menu_url`,`authority`,`menu_icon`,`is_menu`,`order_number`,`create_time`,`update_time`,`checked`) values (1,-1,'系统管理','/system/comment','system:view',NULL,'0',NULL,'2019-12-22 19:31:27','2019-12-22 19:31:27',NULL),(3,1,'用户管理','/system/user','user:view','layui-icon-meh','0',3,'2017-12-27 16:47:13','2019-06-13 11:13:55',0),(4,1,'角色管理','/system/role','role:view','','0',4,'2017-12-27 16:48:09','2019-06-13 08:57:19',0),(5,1,'菜单管理','/system/menu','menu:view','','0',5,'2017-12-27 16:48:57','2019-06-13 08:57:34',0),(9,3,'新增用户',NULL,'user:add',NULL,'1',9,'2017-12-27 17:02:58',NULL,0),(10,3,'修改用户',NULL,'user:edit',NULL,'1',10,'2019-12-20 10:22:49','2019-12-20 10:22:49',NULL),(11,3,'删除用户',NULL,'user:delete',NULL,'1',11,'2017-12-27 17:04:58',NULL,0),(12,4,'新增角色',NULL,'role:add',NULL,'1',12,'2017-12-27 17:06:38',NULL,0),(13,4,'修改角色',NULL,'role:edit',NULL,'1',13,'2017-12-27 17:06:38',NULL,0),(14,4,'删除角色',NULL,'role:delete',NULL,'1',14,'2017-12-27 17:06:38',NULL,0),(15,5,'新增菜单',NULL,'menu:add',NULL,'1',15,'2017-12-27 17:08:02',NULL,0),(16,5,'删除菜单',NULL,'menu:delete',NULL,'1',16,'2017-12-27 17:08:02',NULL,0),(18,5,'修改菜单',NULL,'menu:edit',NULL,'1',18,'2017-12-27 17:08:02',NULL,0),(184,-1,'文章管理','/system/article','article:view',NULL,'0',184,'2019-12-24 16:47:48','2019-12-24 16:47:48',NULL),(185,184,'添加文章',NULL,'article:add',NULL,'1',185,'2019-12-24 16:48:15','2019-12-24 16:48:15',NULL),(186,184,'修改文章',NULL,'article:edit',NULL,'1',186,'2019-12-24 16:48:37','2019-12-24 16:48:37',NULL),(187,184,'删除文章',NULL,'article:delete',NULL,'1',187,'2019-12-24 16:49:06','2019-12-24 16:49:06',NULL),(192,-1,'管理员管理','/admin/admin/list','admin:view',NULL,'0',192,'2020-01-02 10:26:29','2020-01-02 10:26:29',NULL),(193,192,'添加管理员',NULL,'admin:add',NULL,'1',193,'2020-01-02 10:28:25','2020-01-02 10:28:25',NULL),(194,192,'修改管理员',NULL,'admin:edit',NULL,'1',194,'2020-01-02 10:28:42','2020-01-02 10:28:42',NULL),(195,192,'删除管理员',NULL,'admin:delete',NULL,'1',195,'2020-01-02 10:28:58','2020-01-02 10:28:58',NULL),(199,-1,'部门管理','/admin/dept.html','dept:view',NULL,'0',NULL,'2020-01-26 19:16:54','2020-01-26 19:16:54',NULL),(200,199,'添加部门',NULL,'dept:add',NULL,'1',NULL,'2020-02-07 09:58:01','2020-02-07 09:58:01',NULL),(201,199,'修改部门',NULL,'dept:edit',NULL,'1',NULL,'2020-02-07 09:58:23','2020-02-07 09:58:23',NULL),(202,199,'删除部门',NULL,'dept:delete',NULL,'1',NULL,'2020-02-07 09:58:36','2020-02-07 09:58:36',NULL);

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

/*Data for the table `t_role` */

insert  into `t_role`(`role_id`,`role_name`,`remark`,`create_time`,`modify_time`) values (1,'系统管理员','系统管理员，拥有所有操作权限 ，嘿嘿','2020-02-07 11:02:34','2020-02-07 11:02:34'),(2,'注册账户','注册账户，拥有查看，新增权限（新增用户除外）和导出Excel权限','2020-02-07 11:54:09','2020-02-07 11:54:09'),(77,'Redis监控员','负责Redis模块','2019-06-14 20:49:22',NULL),(78,'系统监控员','负责整个系统监控模块','2019-06-14 20:50:07',NULL),(79,'跑批人员','负责任务调度跑批模块','2019-06-14 20:51:02',NULL),(80,'开发人员','拥有代码生成模块的权限','2019-06-14 20:51:26',NULL),(81,'网站用户','用户只能查看，不做其他修改','2019-12-21 16:13:31','2019-12-21 16:13:31');

/*Table structure for table `t_role_menu` */

DROP TABLE IF EXISTS `t_role_menu`;

CREATE TABLE `t_role_menu` (
  `role_menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单/按钮ID',
  PRIMARY KEY (`role_menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=430 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色菜单关联表';

/*Data for the table `t_role_menu` */

insert  into `t_role_menu`(`role_menu_id`,`role_id`,`menu_id`) values (2,81,9),(3,81,10),(4,81,11),(5,81,5),(6,81,15),(7,81,16),(8,81,18),(9,81,6),(10,81,19),(11,81,178),(12,81,179),(333,1,3),(334,1,9),(335,1,10),(336,1,11),(337,1,4),(338,1,12),(339,1,13),(340,1,14),(341,1,5),(342,1,15),(343,1,16),(344,1,18),(345,1,184),(346,1,185),(347,1,186),(348,1,187),(349,1,192),(350,1,193),(351,1,194),(352,1,195),(353,1,199),(354,1,200),(355,1,201),(356,1,202),(425,2,9),(426,2,10),(427,2,11),(428,2,12),(429,2,15);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
