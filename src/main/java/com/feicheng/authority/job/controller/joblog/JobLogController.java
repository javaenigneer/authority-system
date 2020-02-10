package com.feicheng.authority.job.controller.joblog;

import com.feicheng.authority.job.service.JobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 任务日志Controller
 * @author Lenovo
 */
@Controller
@RequestMapping("jobLog")
public class JobLogController {


    @Autowired
    private JobLogService jobLogService;

}
