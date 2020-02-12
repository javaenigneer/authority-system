package com.feicheng.authority.job.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 页面控制Controller
 * @author Lenovo
 */
@Controller("job")
@RequestMapping("admin")
public class ViewController {


    /**
     * 任务调度显示界面
     * @return
     */
    @RequestMapping("job.html")
    public String showJob(){

        return "authority/job/job-list";
    }

    /**
     * 任务调度编辑界面
     * @return
     */
    @RequestMapping("job/edit.html")
    public String showJobEdit(){

        return "authority/job/job-edit";
    }


    /**
     * 任务调度添加界面
     * @return
     */
    @RequestMapping("job/add.html")
    public String showJobAdd(){

        return "authority/job/job-add";
    }

    /**
     * 任务调度日志界面
     * @return
     */
    @RequestMapping("jobLog.html")
    public String showJobLog(){

        return "authority/jobLog/jobLog-list";
    }


}
