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
    // 任务调度日志列表
    var tableIns = table.render({

        elem: '#currentTableId',

        url: '/jobLog/list',

        toolbar: '#toolbarDemo',

        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可

            title: '提示'

            , layEvent: 'LAYTABLE_TIPS'

            , icon: 'layui-icon-tips'
        }],

        cols: [
            [
                {type: "checkbox", width: 50, fixed: "left"},

                {field: 'logId', width: 100, title: 'ID', sort: true},

                {field: 'jobId', width: 100, title: '任务ID', sort: true},

                {field: 'beanName', width: 100, title: 'Bean名称'},

                {field: 'methodName', width: 100, title: '方法名称'},

                {field: 'params', width: 170, title: '方法参数'},

                {field: 'error', width: 150, title: '异常信息'},

                {field: 'times', width: 250, title: '耗时'},

                {
                    field: 'createTime', title: '执行时间', align: 'center', minWidth: 110, templet: function (d) {

                        return d.createTime.substring(0, 10);

                    }

                },

                {field: 'status', title: '状态', align: 'center', templet: "#jobStatus"},

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


    // 监听删除操作
    $(".data-delete-btn").on("click", function () {

        var ids = [];

        var checkStatus = table.checkStatus('currentTableId');

        $(checkStatus.data).each(function (i, o) { // o即为表格中选中的一行数据

            ids.push(o.logId);
        });

        if (ids.length <= 0) {

            layer.msg('请至少选择一条数据', {icon: 1});

            return false;
        }

        ids = ids.join(",");

        layer.confirm('确认要删除吗？', function (obj) {

            $.post("/jobLog/deleteIds", {

                    jobLogIds: ids  //将需要删除的admin作为参数传入

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


    // 列表操作
    table.on('tool(currentTableFilter)', function (obj) {

        var data = obj.data;

        if (obj.event === 'edit') {

            // 编辑管理员
            console.log("编辑");

        } else if (obj.event === 'delete') {

            layer.confirm('真的删除该任务日志吗?', function (index) {

                $.post("/jobLog/delete", {

                        jobLogId: data.logId  //将需要删除的JobId作为参数传入

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