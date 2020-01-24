layui.use(['form', 'layer', 'laydate', 'table', 'laytpl'], function () {
    var form = layui.form,


        $ = layui.jquery,

        laydate = layui.laydate,

        laytpl = layui.laytpl,

        table = layui.table;

    //角色列表
    var tableIns = table.render({

        elem: '#rolesList',

        url: '/role/list',

        cellMinWidth: 95,

        page: true,

        height: "full-125",

        limit: 20,

        limits: [10, 15, 20, 25],

        id: "newsListTable",

        cols: [
            [
                {type: "checkbox", fixed: "left", width: 50},

                {field: 'roleId', title: 'ID', width: 60, align: "center"},

                {field: 'roleName', title: '角色名称', width: 350},

                {field: 'remark', title: '描述', align: 'center'},

                {
                    field: 'createTime', title: '创建时间', align: 'center', minWidth: 110, templet: function (d) {

                        return d.createTime.substring(0, 10);

                    }
                },

                {title: '操作', width: 300, templet: '#newsListBar', fixed: "right", align: "center"}
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
                    articleName: $(".searchVal").val()  //搜索的关键字
                }
            })
        } else {
            layer.msg("请输入搜索的内容");
        }
    });

    //编辑角色
    function editRole(edit) {

        var index = layer.open({

            title: "编辑角色",

            type: 2,

            area: ['1000px', '800px'],

            content: "/admin/role/edit.html",

            success: function (layero, index) {

                var body = layui.layer.getChildFrame('body', index);

                if (edit) {

                    body.find("#roleName").val(edit.roleName);

                    body.find("#remark").val(edit.remark);

                    body.find("#roleId").attr("value", edit.roleId);

                    form.render();
                }
            }
        })
    }


    /**
     * 添加角色按钮操作
     */
    $(".addRole_btn").click(function () {

        addRole();

    });


    /**
     * 添加角色页面
     */
    function addRole() {

        var index = layui.layer.open({

            title: "添加角色",

            type: 2,

            area: ['1000px', '800px'],


            content: "/admin/role/add.html"
        });
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
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(rolesList)', function (obj) {
        var layEvent = obj.event,

            data = obj.data;

        if (layEvent === 'edit') { //编辑

            editRole(data);

        } else if (layEvent === 'del') { //删除

            layer.confirm('确定删除此文章？', {icon: 3, title: '提示信息'}, function (index) {

                $.post("/article/delete", {

                        articleId: data.id  //将需要删除的articleId作为参数传入

                    },
                    function (data) {
                        if (data.info == '删除成功') {

                            layer.msg("删除成功");

                            tableIns.reload();

                            layer.close(index);
                        }

                        if (data.info == '服务器错误') {

                            layer.msg("删除失败，请稍后再试");

                            layer.close(index);
                        }

                    })
            });
        } else if (layEvent === 'look') { //预览

            articleLook(data);
        }
    });

})