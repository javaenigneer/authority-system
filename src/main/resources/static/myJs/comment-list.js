layui.use(['form', 'table'], function () {

    var $ = layui.jquery,

        form = layui.form,

        table = layui.table;


    // 评论列表
  var tableIns = table.render({

        elem: '#currentTableId',

        url: '/comment/list',

        cols: [
            [
                {type: "checkbox", width: 50, fixed: "left"},

                {field: 'id', width: 150, title: 'ID', sort: true},

                {field: 'commentUserName', width: 150, title: '评论用户名'},

                {field: 'commentUserEmail', width: 200, title: '评论用户邮箱', sort: true},

                {field: 'commentContent', width: 170, title: '评论的内容'},

                {
                    field: 'commentDate', title: '评论时间', align: 'center', minWidth: 110, templet: function (d) {

                        return d.commentDate.substring(0, 10);

                    }

                },

                {field: 'articleTitle', title: '评论文章标题'},

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
        var commentUserName = $("#commentUserName").val();

        var commentUserEmail = $("#commentUserEmail").val();

        var commentContent = $("#commentContent").val();


        //执行搜索重载
        table.reload('currentTableId', {
            page: {
                curr: 1
            }
            , where: {

                commentUserName: commentUserName,

                commentUserEmail: commentUserEmail,

                commentContent: commentContent
            }
        }, 'data');

        return false;
    });

    // 编辑评论
    function editComment(edit) {

        var index = layui.layer.open({

            title: "编辑评论",

            type: 2,

            content: "/admin/comment/edit.html",

            area:['400px','400px'],

            success: function (layero, index) {

                var body = layui.layer.getChildFrame('body', index);

                if (edit) {

                    body.find("#commentEditId").val(edit.id);

                    body.find("#articleId").val(edit.commentArticleId);

                    body.find("#userId").val(edit.commentUserId);

                    body.find("#commentUserName").val(edit.commentUserName);

                    body.find("#commentUserEmail").val(edit.commentUserEmail);

                    body.find("#commentContent").val(edit.commentContent);

                    form.render();
                }
                setTimeout(function () {

                    layui.layer.tips('点击此处返回评论列表', '.layui-layer-setwin .layui-layer-close', {

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

                url: "/commentController/deleteSomeCommentById?ids=" + ids,

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

    //监听表格复选框选择
    table.on('checkbox(currentTableFilter)', function (obj) {
        console.log(obj);
    });


    // 列表操作
    table.on('tool(currentTableFilter)', function (obj) {

        var data = obj.data;

        if (obj.event === 'edit') {

            // 编辑评论
            editComment(data);

        } else if (obj.event === 'delete') {

            layer.confirm('真的删除么', function (index) {

                // 发送Ajax请求
                $.ajax({

                    type: 'post',

                    url: '/comment/delete?commentId=' + data.id,

                    data: data.id,

                    dataType: 'json',

                    success: function (msg) {

                        if (msg.info == '删除成功') {

                            layer.msg("删除成功");

                            tableIns.reload();

                            layer.close(index);
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
});