layui.use(['form', 'layer', 'laydate', 'table', 'laytpl'], function () {
    var form = layui.form,

        layer = parent.layer === undefined ? layui.layer : top.layer,

        $ = layui.jquery,

        laydate = layui.laydate,

        laytpl = layui.laytpl,

        table = layui.table;

    //留言列表
    var tableIns = table.render({

        elem: '#contactsList',

        url: '/contact/list',

        cellMinWidth: 95,

        page: true,

        height: "full-125",

        limit: 20,

        limits: [10, 15, 20, 25],

        id: "newsListTable",

        cols: [
            [
                {type: "checkbox", fixed: "left", width: 50},

                {field: 'id', title: 'ID', width: 60, align: "center"},

                {field: 'contactContent', title: '评论内容', width: 350},

                {
                    field: 'contactDate', title: '评论时间', align: 'center', minWidth: 110, templet: function (d) {

                        return d.contactDate.substring(0, 10);

                    }
                },

                {title: '操作', width: 170, templet: '#newsListBar', fixed: "right", align: "center"}
            ]
        ]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        if ($(".searchVal").val() != '') {
            table.reload("newsListTable", {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    contactContent: $(".searchVal").val()  //搜索的关键字
                }
            })
        } else {
            layer.msg("请输入搜索的内容");
        }
    });

    //编辑留言
    function editNews(edit) {

        var index = layui.layer.open({

            title: "编辑留言",

            type: 2,

            content: "/admin/contact/edit.html",

            success: function (layero, index) {

                var body = layui.layer.getChildFrame('body', index);

                if (edit) {

                    body.find("#contactContent").val(edit.contactContent);

                    body.find("#contactId").attr("value", edit.id);

                    form.render();
                }
                setTimeout(function () {
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
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

    //批量删除
    $(".delAll_btn").click(function () {

        var checkStatus = table.checkStatus('newsListTable'),

            data = checkStatus.data,

            newsIds = "";

        if (data.length > 0) {

            for (var i = 0; i < data.length; i++) {

                newsIds = newsIds + data[i].id + ","

            }

            newsIds = newsIds.substr(0, newsIds.length - 1);

            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {

                $.get("/articleController/deleteSomeArticle", {

                    newsIds: newsIds //将需要删除的newsId作为参数传入

                }, function (data) {

                    if (data.info == '删除成功') {

                        layer.msg("删除成功");

                        tableIns.reload();

                        layer.close(index);
                    }

                    if (data.info == '操作失败') {

                        layer.msg("操作失败，请联系管理员");

                        return;
                    }
                })
            })
        } else {
            layer.msg("请选择需要删除的留言");
        }
    })

    //列表操作
    table.on('tool(newsList)', function (obj) {
        var layEvent = obj.event,

            data = obj.data;

        if (layEvent === 'edit') { //编辑

            editNews(data);

        } else if (layEvent === 'del') { //删除

            layer.confirm('确定删除此留言？', {icon: 3, title: '提示信息'}, function (index) {

                $.post("/contact/delete", {

                        contactId: data.id  //将需要删除的contactId作为参数传入

                    },
                    function (data) {
                        if (data.info == '参数错误'){

                            layer.msg("删除失败，请稍后再试");

                            layer.close(index);
                        }

                        if (data.info == '删除成功') {

                            layer.msg("删除成功");

                            tableIns.reload();

                            layer.close(index);
                        }

                        if (data.info == '操作失败') {

                            layer.msg("删除失败，请稍后再试");

                            layer.close(index);
                        }

                    })
            });
        }
    });

})