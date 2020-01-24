layui.use(['form', 'table'], function () {

    var $ = layui.jquery,

        form = layui.form,

        table = layui.table;


    // 文章分类列表
    var tableIns = table.render({

        type:'GET',

        elem: '#currentTableId',

        url: '/articleType/list',

        cols: [
            [
                {type: "checkbox", width: 50, fixed: "left"},

                {field: 'id', width: 150, title: 'ID', sort: true},

                {field: 'articleTypeName', width: 150, title: '文章分类名'},

                {field: 'articleCount', width: 150, title: '文章数量', sort: true},

                {title: '操作', minWidth: 50, templet: '#currentTableBar', fixed: "right", align: "center"}

            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],

        limit: 15,

        page: true
    });

    // 编辑文章类别
    function editArticleType(edit) {

        var index = layui.layer.open({

            title: "编辑文章类别",

            type: 2,

            area:['400px','400px'],

            content: "/admin/articleType/edit.html",

            success: function (layero, index) {

                var body = layui.layer.getChildFrame('body', index);

                if (edit) {

                    body.find("#articleTypeId").val(edit.id);

                    body.find("#articleTypeName").val(edit.articleTypeName);

                    form.render();
                }
                setTimeout(function () {

                    layui.layer.tips('点击此处返回文章分类列表', '.layui-layer-setwin .layui-layer-close', {

                        tips: 3
                    });

                }, 500)
            }
        })

        layui.layer.full(index);

        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）

        $(window).on("resize", function () {

            layui.layer.full(index);

        })
    }


    //监听表格复选框选择
    table.on('checkbox(currentTableFilter)', function (obj) {
        console.log(obj);
    });


    // 列表操作
    table.on('tool(currentTableFilter)', function (obj) {

        var data = obj.data;

        if (obj.event === 'edit') {

            // 编辑文章类别
            editArticleType(data);

        } else if (obj.event === 'delete') {

            layer.confirm('真的删除么', function (index) {

                // 发送Ajax请求
                $.ajax({

                    type: 'post',

                    url: '/articleType/delete?articleTypeId=' + data.id,

                    data: data.id,

                    dataType: 'json',

                    success: function (msg) {

                        if (msg.info == '删除成功') {

                            layer.msg("删除成功");

                            tableIns.reload();

                            layer.close(index);
                        }

                        if (msg.info == '不能删除，该分类名已被使用！'){

                            layer.msg("不能删除，该分类名已被使用！");

                            return;
                        }

                        if (msg.info == '删除失败') {

                            layer.msg("操作失败，请联系管理员");

                            layer.close(index);
                        }

                    },
                    error: function () {

                        layer.msg("操作失败", {icon: 5});

                        return;
                    }
                });

            });
        }
    });

    /*
    添加点击事件
     */
    $("#addArticleType").click(function () {

        addArticleType();

    });

    /**
     * 打开添加界面
     */
    function addArticleType() {

        var index = layui.layer.open({

            title: "添加文章分类",

            type: 2,

            content: "/admin/articleType/add.html"

        });

        layui.layer.full(index);

        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize", function () {

            layui.layer.full(index);

        })
    }

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

            $.ajax({

                type: "post",

                url: "/articleTypeController/deleteSomeArticleTypeById?ids=" + ids,

                data: ids,

                dataType: 'json',

                success: function (msg) {

                    if (msg.info == '删除成功') {

                        layer.close('loading');

                        parent.layer.msg('删除成功！', {icon: 1, time: 2000, shade: 0.2});

                        location.reload(true);

                        return;
                    }

                    if (msg.info == '删除失败') {

                        layer.msg("删除失败，请联系管理员", {icon: 1});

                        return;
                    }
                },
                error: function () {

                    layer.msg("操作失败", {icon: 1});

                    return;
                }
            });
        });
    });
});