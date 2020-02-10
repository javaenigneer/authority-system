/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.40-log : Database - febs_quartz
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`febs_quartz` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `febs_quartz`;

/*Table structure for table `qrtz_blob_triggers` */

DROP TABLE IF EXISTS `qrtz_blob_triggers`;

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_blob_triggers` */

/*Table structure for table `qrtz_calendars` */

DROP TABLE IF EXISTS `qrtz_calendars`;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_calendars` */

/*Table structure for table `qrtz_cron_triggers` */

DROP TABLE IF EXISTS `qrtz_cron_triggers`;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_cron_triggers` */

insert  into `qrtz_cron_triggers`(`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`CRON_EXPRESSION`,`TIME_ZONE_ID`) values ('FC_Scheduler','TASK_1','DEFAULT','0/1 * * * * ?','Asia/Shanghai'),('FC_Scheduler','TASK_2','DEFAULT','0/10 * * * * ?','Asia/Shanghai'),('FC_Scheduler','TASK_3','DEFAULT','0/1 * * * * ?','Asia/Shanghai'),('FC_Scheduler','TASK_4','DEFAULT','0/1 * * * * ?','Asia/Shanghai'),('FEBS_Scheduler','TASK_1','DEFAULT','0/1 * * * * ?','Asia/Shanghai'),('FEBS_Scheduler','TASK_2','DEFAULT','0/10 * * * * ?','Asia/Shanghai'),('FEBS_Scheduler','TASK_3','DEFAULT','0/1 * * * * ?','Asia/Shanghai');

/*Table structure for table `qrtz_fired_triggers` */

DROP TABLE IF EXISTS `qrtz_fired_triggers`;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_fired_triggers` */

/*Table structure for table `qrtz_job_details` */

DROP TABLE IF EXISTS `qrtz_job_details`;

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_job_details` */

insert  into `qrtz_job_details`(`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`JOB_CLASS_NAME`,`IS_DURABLE`,`IS_NONCONCURRENT`,`IS_UPDATE_DATA`,`REQUESTS_RECOVERY`,`JOB_DATA`) values ('FC_Scheduler','TASK_1','DEFAULT',NULL,'com.feicheng.authority.job.util.ScheduleJob','0','0','0','0','¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0%com.feicheng.authority.job.entity.Job÷óN$<Ôb\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.sql.Timestamp&ÕÈS¿e\0I\0nanosxr\0java.util.DatehjKYt\0\0xpw\0\0aÆëppx\0\0\0\0t\0\r0/1 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0testt\0mrbirdt\0\Zæœ‰å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•~~t\01x\0'),('FC_Scheduler','TASK_2','DEFAULT',NULL,'com.feicheng.authority.job.util.ScheduleJob','0','0','0','0','¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0%com.feicheng.authority.job.entity.Job÷óN$<Ôb\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.sql.Timestamp&ÕÈS¿e\0I\0nanosxr\0java.util.DatehjKYt\0\0xpw\0\0aÇ2˜x\0\0\0\0t\00/10 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0test1pt\0æ— å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•t\01x\0'),('FC_Scheduler','TASK_3','DEFAULT',NULL,'com.feicheng.authority.job.util.ScheduleJob','0','0','0','0','¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0%com.feicheng.authority.job.entity.Job÷óN$<Ôb\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.sql.Timestamp&ÕÈS¿e\0I\0nanosxr\0java.util.DatehjKYt\0\0xpw\0\0aÏ¹¦x\0\0\0\0t\0\r0/1 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0testt\0hello worldt\0+æœ‰å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•,æ¯éš”ä¸€ç§’è§¦å‘t\01x\0'),('FC_Scheduler','TASK_4','DEFAULT',NULL,'com.feicheng.authority.job.util.ScheduleJob','0','0','0','0','¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0%com.feicheng.authority.job.entity.Job÷óN$<Ôb\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.sql.Timestamp&ÕÈS¿e\0I\0nanosxr\0java.util.DatehjKYt\0\0xpw\0\0p-ïß x\0\0\0\0t\0\r0/1 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0testt\0feichengt\0æµ‹è¯•ä»»åŠ¡æ·»åŠ t\01x\0'),('FEBS_Scheduler','TASK_1','DEFAULT',NULL,'cc.mrbird.febs.job.util.ScheduleJob','0','0','0','0','¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0cc.mrbird.febs.job.entity.JobR¬“£\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.util.DatehjKYt\0\0xpw\0\0aÆëppxt\0\r0/1 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0testt\0mrbirdt\0\Zæœ‰å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•~~t\01x\0'),('FEBS_Scheduler','TASK_2','DEFAULT',NULL,'cc.mrbird.febs.job.util.ScheduleJob','0','0','0','0','¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0cc.mrbird.febs.job.entity.JobR¬“£\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.util.DatehjKYt\0\0xpw\0\0aÇ2˜xt\00/10 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0test1pt\0æ— å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•t\01x\0'),('FEBS_Scheduler','TASK_3','DEFAULT',NULL,'cc.mrbird.febs.job.util.ScheduleJob','0','0','0','0','¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0cc.mrbird.febs.job.entity.JobR¬“£\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.util.DatehjKYt\0\0xpw\0\0aÏ¹¦xt\0\r0/1 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0testt\0hello worldt\0+æœ‰å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•,æ¯éš”ä¸€ç§’è§¦å‘t\01x\0');

/*Table structure for table `qrtz_locks` */

DROP TABLE IF EXISTS `qrtz_locks`;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_locks` */

insert  into `qrtz_locks`(`SCHED_NAME`,`LOCK_NAME`) values ('FC_Scheduler','STATE_ACCESS'),('FC_Scheduler','TRIGGER_ACCESS'),('FEBS_Scheduler','STATE_ACCESS'),('FEBS_Scheduler','TRIGGER_ACCESS');

/*Table structure for table `qrtz_paused_trigger_grps` */

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_paused_trigger_grps` */

/*Table structure for table `qrtz_scheduler_state` */

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_scheduler_state` */

insert  into `qrtz_scheduler_state`(`SCHED_NAME`,`INSTANCE_NAME`,`LAST_CHECKIN_TIME`,`CHECKIN_INTERVAL`) values ('FC_Scheduler','DESKTOP-P23KTQ51581323521748',1581323703840,15000),('FEBS_Scheduler','DESKTOP-P23KTQ51581309533671',1581323700006,15000);

/*Table structure for table `qrtz_simple_triggers` */

DROP TABLE IF EXISTS `qrtz_simple_triggers`;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_simple_triggers` */

/*Table structure for table `qrtz_simprop_triggers` */

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_simprop_triggers` */

/*Table structure for table `qrtz_triggers` */

DROP TABLE IF EXISTS `qrtz_triggers`;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_triggers` */

insert  into `qrtz_triggers`(`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`NEXT_FIRE_TIME`,`PREV_FIRE_TIME`,`PRIORITY`,`TRIGGER_STATE`,`TRIGGER_TYPE`,`START_TIME`,`END_TIME`,`CALENDAR_NAME`,`MISFIRE_INSTR`,`JOB_DATA`) values ('FC_Scheduler','TASK_1','DEFAULT','TASK_1','DEFAULT',NULL,1581255010000,-1,5,'PAUSED','CRON',1581255010000,0,NULL,2,'¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0%com.feicheng.authority.job.entity.Job÷óN$<Ôb\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.sql.Timestamp&ÕÈS¿e\0I\0nanosxr\0java.util.DatehjKYt\0\0xpw\0\0aÆëppx\0\0\0\0t\0\r0/1 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0testt\0mrbirdt\0\Zæœ‰å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•~~t\01x\0'),('FC_Scheduler','TASK_2','DEFAULT','TASK_2','DEFAULT',NULL,1581255010000,-1,5,'PAUSED','CRON',1581255010000,0,NULL,2,'¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0%com.feicheng.authority.job.entity.Job÷óN$<Ôb\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.sql.Timestamp&ÕÈS¿e\0I\0nanosxr\0java.util.DatehjKYt\0\0xpw\0\0aÇ2˜x\0\0\0\0t\00/10 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0test1pt\0æ— å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•t\01x\0'),('FC_Scheduler','TASK_3','DEFAULT','TASK_3','DEFAULT',NULL,1581323565000,1581323564000,5,'PAUSED','CRON',1581255010000,0,NULL,2,'¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0%com.feicheng.authority.job.entity.Job÷óN$<Ôb\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.sql.Timestamp&ÕÈS¿e\0I\0nanosxr\0java.util.DatehjKYt\0\0xpw\0\0aÏ¹¦x\0\0\0\0t\0\r0/1 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0testt\0hello worldt\0.æœ‰å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•,æ¯éš”ä¸€ç§’è§¦å‘å“ˆt\00x\0'),('FC_Scheduler','TASK_4','DEFAULT','TASK_4','DEFAULT',NULL,1581318731000,-1,5,'PAUSED','CRON',1581318731000,0,NULL,2,''),('FEBS_Scheduler','TASK_1','DEFAULT','TASK_1','DEFAULT',NULL,1572489582000,-1,5,'PAUSED','CRON',1572489582000,0,NULL,2,'¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0cc.mrbird.febs.job.entity.JobR¬“£\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.util.DatehjKYt\0\0xpw\0\0aÆëppxt\0\r0/1 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0testt\0mrbirdt\0\Zæœ‰å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•~~t\01x\0'),('FEBS_Scheduler','TASK_2','DEFAULT','TASK_2','DEFAULT',NULL,1572489590000,-1,5,'PAUSED','CRON',1572489582000,0,NULL,2,'¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0cc.mrbird.febs.job.entity.JobR¬“£\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.util.DatehjKYt\0\0xpw\0\0aÇ2˜xt\00/10 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0test1pt\0æ— å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•t\01x\0'),('FEBS_Scheduler','TASK_3','DEFAULT','TASK_3','DEFAULT',NULL,1581310219000,1581310218000,5,'PAUSED','CRON',1572489582000,0,NULL,2,'¬í\0sr\0org.quartz.JobDataMapŸ°ƒè¿©°Ë\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap‚èÃûÅ](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMapæ.­(v\nÎ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMapÚÁÃ`Ñ\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0cc.mrbird.febs.job.entity.JobR¬“£\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statusq\0~\0	xpt\0testTasksr\0java.util.DatehjKYt\0\0xpw\0\0aÏ¹¦xt\0\r0/1 * * * * ?sr\0java.lang.Long;‹äÌ#ß\0J\0valuexr\0java.lang.Number†¬•”à‹\0\0xp\0\0\0\0\0\0\0t\0testt\0hello worldt\0+æœ‰å‚ä»»åŠ¡è°ƒåº¦æµ‹è¯•,æ¯éš”ä¸€ç§’è§¦å‘t\00x\0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
