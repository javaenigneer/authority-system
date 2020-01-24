layui.use(['form', 'layer', 'laydate', 'table', 'laytpl'], function () {
    var form = layui.form,

        layer = parent.layer === undefined ? layui.layer : top.layer,

        $ = layui.jquery,

        laydate = layui.laydate,

        laytpl = layui.laytpl,

        table = layui.table;

    //文章列表
    var tableIns = table.render({

        elem: '#newsList',

        url: '/article/list',

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

                {field: 'articleName', title: '文章标题', width: 350},

                {field: 'articleAuthor', title: '发布者', align: 'center'},

                {field: 'articleStatus', title: '发布状态', align: 'center', templet: "#newsStatus"},

                {field: 'articleLook', title: '浏览权限', align: 'center'},
                {
                    field: 'articleTop', title: '是否置顶', align: 'center', templet: function (d) {

                        return '<input type="checkbox" name="articleTop" lay-filter="newsTop" lay-skin="switch" lay-text="是|否" ' + d.articleTop + '>'
                    }
                },
                {
                    field: 'articleDate', title: '发布时间', align: 'center', minWidth: 110, templet: function (d) {

                        return d.articleDate.substring(0, 10);

                    }
                },

                {title: '操作', width: 300, templet: '#newsListBar', fixed: "right", align: "center"}
            ]
        ]
    });

    //是否置顶
    form.on('switch(newsTop)', function (data) {
        var index = layer.msg('修改中，请稍候', {icon: 16, time: false, shade: 0.8});

        setTimeout(function () {
            layer.close(index);
            if (data.elem.checked) {

                layer.msg("置顶成功！");

            } else {

                layer.msg("取消置顶成功！");

            }
        }, 500);
    })

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

    //编辑文章
    function editNews(edit) {

        var index = layui.layer.open({

            title: "编辑文章",

            type: 2,

            content: "/admin/article/edit.html",

            success: function (layero, index) {

                var body = layui.layer.getChildFrame('body', index);

                if (edit) {

                    body.find(".newsName").val(edit.articleName);

                    body.find(".abstract").val(edit.articleAbstract);

                    body.find("#id").attr("value", edit.id);

                    // 判断该文章是否有图片

                    if (edit.articleImage != '') {

                        $("#test2").attr("class", "layui-btn layui-btn-radius layui-btn-disabled");

                        body.find("#demo2").append('<img src="' + edit.articleImage + '" alt="" class="layui-upload-img" id="image">');

                    }

                    if (edit.articleImage == '') {

                        $("#cleanImgs").attr("class", "layui-btn layui-btn-radius layui-btn-disabled");

                    }

                    body.find("#layeditDemo").val(edit.articleContent);

                    body.find(".newsStatus select").val(edit.articleStatus);

                    body.find(".openness input[name='openness'][title='" + edit.articleLook + "']").prop("checked", "checked");

                    body.find(".newsTop input[name='newsTop']").prop("checked", edit.articleTop);

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

    // 预览文章
    function articleLook(data) {

        var index = layui.layer.open({

            title: '预览文章',

            type: 2,

            content: '/admin/article/look.html',

            success: function (layero, index) {

                var body = layui.layer.getChildFrame('body', index);

                if (data) {

                    body.find(".news_title").text(data.articleName);

                    body.find(".author").text("王若飞");

                    body.find(".timer").text(data.articleDate);

                    body.find(".news_about").text(data.articleAbstract);

                    body.find(".news_infos").html(data.articleContent);

                }
            }
        });

        layui.layer.full(index);

        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize", function () {

            layui.layer.full(index);

        })

    }

    /**
     * 添加文章按钮操作
     */
    $(".addNews_btn").click(function () {

        addNews();

    });


    /**
     * 添加文章页面
     */
    function addNews() {

        var index = layui.layer.open({
            title: "添加文章",
            type: 2,
            content: "/admin/article/add.html"
        });

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
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(newsList)', function (obj) {
        var layEvent = obj.event,

            data = obj.data;

        if (layEvent === 'edit') { //编辑

            editNews(data);

        } else if (layEvent === 'del') { //删除

            layer.confirm('确定删除此文章？', {icon: 3, title: '提示信息'}, function (index) {

                $.post("/article/delete", {

                        articleId: data.id  //将需要删除的articleId作为参数传入

                    },
                    function (data) {

                        if (data.code == '200') {

                            layer.msg(data.info);

                            tableIns.reload();

                            layer.close(index);
                        }

                        if (data.code == '400') {

                            layer.msg(data.info);

                            return;
                        }

                        if (data.code == '500') {

                            layer.msg(data.info);

                            return;
                        }

                    })
            });
        } else if (layEvent === 'look') { //预览

            articleLook(data);
        }
    });

})