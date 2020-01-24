layui.use(['form', 'layedit', 'laydate'], function () {

    var form = layui.form,

        layer = parent.layer === undefined ? layui.layer : top.layer,

        $ = layui.jquery,

        laydate = layui.laydate,

        laytpl = layui.laytpl,

        table = layui.table;


    //监听提交
    form.on("submit(editAdmin)", function (data) {

        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        $.post("/admin/edit",
            {
                // 管理员Id
                adminId: $("#adminEditId").val(),

                // 管理员名称
                adminName: $("#adminEditName").val(),

                // 性别
                adminSex: $('input[name="sex"]:checked ').val(),

                // 管理员邮箱
                adminEmail: $("#adminEditEmail").val(),

                // 管理员手机
                adminPhone: $("#adminEditPhone").val()

            },

            function (res) {

                if (res.code == '400') {

                    layer.msg(res.msg);

                    return;
                }

                if (res.code == '500') {

                    layer.msg(res.msg);

                    return;
                }


                if (res.code == '200') {

                    setTimeout(function () {

                        top.layer.close(index);

                        top.layer.msg(res.msg);

                        layer.closeAll("iframe");

                        //刷新父页面

                        parent.location.reload();

                    }, 500);
                }
            });
        return false;
    })

});