layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form;


    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;


    // form.verify({
    //
    //     newsName: function (val) {
    //
    //         if (val == '') {
    //
    //             return "文章标题不能为空";
    //
    //         }
    //     },
    //     content: function (val) {
    //
    //         if (val == '') {
    //
    //             return "文章内容不能为空";
    //         }
    //     }
    // });

    form.on("submit(addAdmin)", function (data) {

        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        $.post("/adminController/addAdmin",
            {

                adminName: $("#adminAddName").val(),

                adminPhone: $("#adminAddPhone").val(),

                adminEmail: $("#adminAddEmail").val()
            },
            function (res) {

                if (res.info == '请输入管理员名称') {

                    layer.msg("请输入管理员名称");

                    return;
                }

                if (res.info == '请输入管理员邮箱') {

                    layer.msg("请输入管理员邮箱");

                    return;
                }

                if (res.info == '邮箱格式错误') {

                    layer.msg("邮箱格式错误");

                    return;
                }

                if (res.info == '请输入手机号码') {

                    layer.msg("请输入手机号码");

                    return;
                }

                if (res.info == '手机号码格式错误') {

                    layer.msg("手机号码格式错误");

                    return;
                }

                if (res.info == '管理员用户名已存在'){

                    layer.msg("管理员用户名已存在");

                    return;
                }

                if (res.info == '操作失败') {

                    layer.msg("操作失败，请联系管理员");

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