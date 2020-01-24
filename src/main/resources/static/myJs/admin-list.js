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
    // 管理员列表
    var tableIns = table.render({

        elem: '#currentTableId',

        url: '/admin/list',

        cols: [
            [
                {type: "checkbox", width: 50, fixed: "left"},

                {field: 'adminId', width: 70, title: 'ID', sort: true},

                {field: 'adminName', width: 80, title: '登录名'},

                {field: 'adminSex', width: 80, title: '性别'},

                {field: 'adminEmail', width: 170, title: '邮箱'},

                {field: 'adminPhone', width: 150, title: '手机', sort: true},

                {
                    field: 'adminCreateTime', title: '加入时间', align: 'center', minWidth: 110, templet: function (d) {

                        return d.adminCreateTime.substring(0, 10);

                    }

                },

                {field: 'adminStatus', title: '审核状态', align: 'center', templet: "#adminStatus"},

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

        var adminName = $("#adminName").val();

        var adminPhone = $("#adminPhone").val();

        var adminEmail = $("#adminEmail").val();


        //执行搜索重载
        table.reload('currentTableId', {
            page: {
                curr: 1
            }
            , where: {

                adminName: adminName,

                adminEmail: adminEmail,

                adminPhone: adminPhone,

                start: start,

                end: end
            }
        }, 'data');

        return false;
    });

    // 编辑管理员
    function editAdmin(edit) {

        var index = layui.layer.open({

            title: "编辑管理员",

            type: 2,

            content: "/admin/edit.html",

            area: ['1000px', '500px'],

            success: function (layero, index) {

                var body = layui.layer.getChildFrame('body', index);

                if (edit) {

                    body.find("#adminEditId").val(edit.adminId);

                    body.find("#adminEditName").val(edit.adminName);

                    body.find("#adminEditEmail").val(edit.adminEmail);

                    body.find("#adminEditPhone").val(edit.adminPhone);

                    body.find(".adminSex input[name='adminSex'][title='" + edit.adminSex + "']").prop("checked", "checked");

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


    // 监听删除操作
    $(".data-delete-btn").on("click", function () {

        var ids = [];

        var checkStatus = table.checkStatus('currentTableId');

        $(checkStatus.data).each(function (i, o) { // o即为表格中选中的一行数据

            ids.push(o.adminId);
        });

        if (ids.length <= 0) {

            layer.msg('请至少选择一条数据', {icon: 1});

            return false;
        }

        ids = ids.join(",");

        layer.confirm('确认要删除吗？', function (obj) {

            $.post("/admin/deleteIds", {

                    adminIds: ids  //将需要删除的admin作为参数传入

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
            editAdmin(data);

        } else if (obj.event === 'delete') {

            layer.confirm('真的删除该管理员吗?', function (index) {

                $.post("/admin/delete", {

                        adminId: data.adminId  //将需要删除的admin作为参数传入

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