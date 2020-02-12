package com.feicheng.authority.others.controller.sql;

import com.feicheng.authority.common.response.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lenovo
 */
@Controller
@RequestMapping("sql")
public class SqlController {

    private static final SimpleDateFormat yearMonthDayFormat = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("dataBack")
    public ResponseEntity<ResponseResult<Void>> sqlDataBack() {

        String filePath = "D:\\sql\\";
        /*String fileName="批量上传模板.xlsx";*/
        String dbName = "feiblog";
        try {
            Process process = Runtime.getRuntime().exec(
                    "mysql -hlocalhost -P3306 -u root -proot " + dbName + " > "
                            + filePath + "/" + dbName + new java.util.Date().getTime()
                            + ".sql");
            //备份的数据库名字为teacher，数据库连接和密码均为root
            System.out.println("success!!!");

            return ResponseEntity.ok(new ResponseResult<>(200, "备份成功"));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.ok(new ResponseResult<>(500, "备份失败"));
        }
    }
}
