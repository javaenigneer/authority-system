layui.use(['form', 'table', 'laydate'], function () {

    var $ = layui.jquery,

        form = layui.form,

        table = layui.table,

        laydate = layui.laydate;

    //日期
    laydate.render({

        elem: '#start'

    });

    laydate.render({

        elem: '#end'

    });
    // 任务调度列表
    var tableIns = table.render({

        elem: '#currentTableId',

        url: '/job/list',

        toolbar: '#toolbarDemo',

        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可

            title: '提示'

            , layEvent: 'LAYTABLE_TIPS'

            , icon: 'layui-icon-tips'
        }],

        cols: [
            [
                {type: "checkbox", width: 50, fixed: "left"},

                {field: 'jobId', width: 80, title: 'ID', sort: true},

                {field: 'beanName', width: 100, title: 'Bean名称'},

                {field: 'methodName', width: 100, title: '方法名称'},

                {field: 'params', width: 170, title: '方法参数'},

                {field: 'cronExpression', width: 150, title: 'cron表达式'},

                {field: 'remark', width: 250, title: '备注'},

                {
                    field: 'createTime', title: '创建时间', align: 'center', minWidth: 110, templet: function (d) {

                        return d.createTime.substring(0, 10);

                    }

                },

                {field: 'status', title: '状态', width: 100, templet: '#switchTpl', unresize: true},

                {title: '操作', minWidth: 50, templet: '#currentTableBar', fixed: "right", align: "center"}

            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],

        limit: 15,

        page: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {

        // 获取搜索框的值

        var start = $("#start").val();

        var end = $("#end").val();

        var beanName = $("#beanName").val();

        var methodName = $("#methodName").val();

        var status = $(".jobStatus").val();

        //执行搜索重载
        table.reload('currentTableId', {
            page: {
                curr: 1
            }
            , where: {

                beanName: beanName,

                methodName: methodName,

                status: status,

                start: start,

                end: end
            }
        }, 'data');

        return false;
    });

    // 编辑任务调度
    function editJob(edit) {

        var index = layui.layer.open({

            title: "编辑任务调度",

            type: 2,

            content: "/admin/job/edit.html",

            area: ['1000px', '500px'],

            success: function (layero, index) {

                var body = layui.layer.getChildFrame('body', index);

                if (edit) {

                    body.find("#jobEditId").val(edit.jobId);

                    body.find("#beanNameEdit").val(edit.beanName);

                    body.find("#methodNameEdit").val(edit.methodName);

                    body.find("#paramsEdit").val(edit.params);

                    body.find("#cronExpressionEdit").val(edit.cronExpression);

                    body.find("#remarkEdit").val(edit.remark);

                    form.render();
                }
                setTimeout(function () {

                    layui.layer.tips('点击此处返回管理员列表', '.layui-layer-setwin .layui-layer-close', {

                        tips: 3
                    });

                }, 500)
            }
        });

    }

    //监听任务调度的启动和暂停操作
    form.on('switch(jobStatus)', function (obj) {

        // layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);

        var index = layer.msg('修改中，请稍候', {icon: 16, time: false, shade: 0.8});

        setTimeout(function () {

            layer.close(index);

            // 启动任务
            if (obj.elem.checked) {

                // 调用后台接口
                $.post('/job/resume', {

                    jobId: obj.value

                }, function (data) {

                    // 启动成功
                    if (data.code == 200) {

                        layer.msg(data.msg);

                        return;
                    }

                    // 参数错误
                    if (data.code == 400) {

                        layer.msg(data.msg);

                        return;
                    }

                    // 启动失败
                    if (data.code == 500) {

                        layer.msg(data.msg);

                        return;
                    }

                })
                // 暂停任务
            } else {

                // 调用后台接口
                $.post('/job/end', {

                    jobId: obj.value

                }, function (data) {

                    // 暂停成功
                    if (data.code == 200) {

                        layer.msg(data.msg);

                        return;
                    }

                    // 参数错误
                    if (data.code == 400) {

                        layer.msg(data.msg);

                        return;
                    }

                    // 暂停失败
                    if (data.code == 500) {

                        layer.msg(data.msg);

                        return;
                    }

                })
            }
        }, 500);

    });

    // 监听添加操作
    $(".data-add-btn").on("click", function () {

        // 打开添加界面
        layui.layer.open({

            title: "新增任务调度",

            type: 2,

            content: "/admin/job/add.html",

            area: ['1000px', '500px']
        });
    });


    // 监听删除操作
    $(".data-delete-btn").on("click", function () {

        var ids = [];

        var checkStatus = table.checkStatus('currentTableId');

        $(checkStatus.data).each(function (i, o) { // o即为表格中选中的一行数据

            ids.push(o.jobId);
        });

        if (ids.length <= 0) {

            layer.msg('请至少选择一条数据', {icon: 1});

            return false;
        }

        ids = ids.join(",");

        layer.confirm('确认要删除吗？', function (obj) {

            $.post("/job/deleteIds", {

                    jobIds: ids  //将需要删除的admin作为参数传入

                },
                function (data) {

                    if (data.code == '200') {

                        layer.msg(data.msg);

                        tableIns.reload();

                        layer.close(index);
                    }

                    if (data.code == '400') {

                        layer.msg(data.msg);

                        return;
                    }

                    if (data.code == '500') {

                        layer.msg(data.msg);

                        return;
                    }
                })
        });
    });

    // 监听执行任务操作
    $("#start_task").on("click", function () {

        var ids = [];

        var checkStatus = table.checkStatus('currentTableId');

        $(checkStatus.data).each(function (i, o) { // o即为表格中选中的一行数据

            ids.push(o.jobId);
        });

        if (ids.length <= 0) {

            layer.msg('请至少选择一条数据', {icon: 1});

            return false;
        }

        ids = ids.join(",");

        layer.confirm('确认要执行选中的任务吗？', function (obj) {

            $.post("/job/start", {

                    jobIds: ids  //将需要删除的admin作为参数传入

                },
                function (data) {

                    if (data.code == '200') {

                        layer.msg(data.msg);

                        tableIns.reload();

                        layer.close(index);
                    }

                    if (data.code == '400') {

                        layer.msg(data.msg);

                        return;
                    }

                    if (data.code == '500') {

                        layer.msg(data.msg);

                        return;
                    }
                })
        });
    });

    //监听表格复选框选择
    table.on('checkbox(currentTableFilter)', function (obj) {
        console.log(obj);
    });


    // 列表操作
    table.on('tool(currentTableFilter)', function (obj) {

        var data = obj.data;

        if (obj.event === 'edit') {

            // 编辑管理员
            editJob(data);

        } else if (obj.event === 'delete') {

            layer.confirm('真的删除任务调度吗?', function (index) {

                $.post("/job/delete", {

                        jobId: data.jobId  //将需要删除的JobId作为参数传入

                    },
                    function (data) {

                        if (data.code == '200') {

                            layer.msg(data.msg);

                            tableIns.reload();

                            layer.close(index);
                        }

                        if (data.code == '400') {

                            layer.msg(data.msg);

                            return;
                        }

                        if (data.code == '500') {

                            layer.msg(data.msg);

                            return;
                        }
                    })
            });
        }
    });

});