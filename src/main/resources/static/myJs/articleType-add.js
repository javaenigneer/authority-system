layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form;


    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;


    form.verify({

        articleTypeName: function (val) {

            if (val == '') {

                return "分类名不能为空";

            }
        }
    });

    form.on("submit(addArticleType)", function (data) {

        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        $.post("/articleType/add",
            {

                articleTypeName: $("#articleTypeName").val(),

            },
            function (res) {

                if (res.info == '请输入分类名称') {

                    layer.msg("请输入分类名称");

                    return;
                }

                if (res.info == '添加失败') {

                    layer.msg("添加失败，请联系管理员");

                    return;
                }

                if (res.info == '添加成功') {

                    setTimeout(function () {

                        top.layer.close(index);

                        top.layer.msg("添加成功！");

                        layer.closeAll("iframe");

                        //刷新父页面

                        parent.location.reload();

                    }, 500);
                }

            });

        return false;
    })

})