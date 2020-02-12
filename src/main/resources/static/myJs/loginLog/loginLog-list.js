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
    // 登录日志列表
    var tableIns = table.render({

        elem: '#currentTableId',

        url: '/loginLog/list',

        toolbar: '#toolbarDemo',

        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可

            title: '提示'

            , layEvent: 'LAYTABLE_TIPS'

            , icon: 'layui-icon-tips'
        }],

        cols: [
            [
                {type: "checkbox", width: 50, fixed: "left"},

                {field: 'id', width: 100, title: 'ID', sort: true},

                {field: 'userName', width: 100, title: '登录用户'},

                {field: 'ip', width: 100, title: 'IP地址'},

                {field: 'location', width: 200, title: '登录地点'},

                {
                    field: 'loginTime', title: '登录时间', align: 'center', minWidth: 110, templet: function (d) {

                        return d.loginTime.substring(0, 10);

                    }

                },

                {field: 'system', width: 170, title: '登录系统'},

                {field: 'browser', width: 170, title: '浏览器'},

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

        var userName = $("#userName").val();

        //执行搜索重载
        table.reload('currentTableId', {
            page: {
                curr: 1
            }
            , where: {

                userName: userName,

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

            ids.push(o.id);
        });

        if (ids.length <= 0) {

            layer.msg('请至少选择一条数据', {icon: 1});

            return false;
        }

        ids = ids.join(",");

        layer.confirm('确认要删除吗？', function (obj) {

            $.post("/loginLog/deleteIds", {

                    ids: ids  //将需要删除的id作为参数传入

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

            layer.confirm('真的删除该登录日志吗?', function (index) {

                $.post("/loginLog/delete", {

                        id: data.id  //将需要删除的JobId作为参数传入

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