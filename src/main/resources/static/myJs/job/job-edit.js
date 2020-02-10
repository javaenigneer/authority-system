layui.use(['form', 'layedit', 'laydate'], function () {

    var form = layui.form,

        layer = parent.layer === undefined ? layui.layer : top.layer,

        $ = layui.jquery,

        laydate = layui.laydate,

        laytpl = layui.laytpl,

        table = layui.table;


    //监听提交
    form.on("submit(editJob)", function (data) {

        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        $.post("/job/edit",
            {
                // 任务调度的Id
                jobId: $("#jobEditId").val(),

                // Bean名称
                beanName: $("#beanNameEdit").val(),

                // 方法名称
                methodName: $('#methodNameEdit').val(),

                // 参数名称
                params: $("#paramsEdit").val(),

                // cron表达式
                cronExpression: $("#cronExpressionEdit").val(),

                // 备注
                remark: $("#remarkEdit").val()
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