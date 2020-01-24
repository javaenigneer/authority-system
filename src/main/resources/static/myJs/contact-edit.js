layui.use(['form', 'layedit', 'laydate'], function () {

    var form = layui.form,

        layer = parent.layer === undefined ? layui.layer : top.layer,

        $ = layui.jquery,

        laydate = layui.laydate,

        laytpl = layui.laytpl,

        table = layui.table;


    //监听提交
    form.on("submit(editComment)", function (data) {

        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        $.post("/contact/edit",
            {
                // 留言Id
                id: $("#contactId").val(),

                // 留言内容
                contactContent: $("#contactContent").val()
            },

            function (res) {

                if (res.info == '请输入留言内容') {

                    layer.msg("请输入留言内容");

                    return;
                }


                if (res.info == '操作失败') {

                    layer.msg("操作失败，请联系管理员");

                    return;
                }

                if (res.info == '修改成功') {

                    setTimeout(function () {

                        top.layer.close(index);

                        top.layer.msg("修改成功！");

                        layer.closeAll("iframe");

                        //刷新父页面

                        parent.location.reload();

                    }, 500);
                }
            });

        return false;
    })

});