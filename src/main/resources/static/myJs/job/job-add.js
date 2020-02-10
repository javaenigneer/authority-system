layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form;


    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    form.on("submit(addJob)", function (data) {

        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        $.post("/job/add",
            {

                beanName: $("#beanNameAdd").val(),

                methodName: $("#methodNameAdd").val(),

                params: $("#paramsAdd").val(),

                cronExpression: $("#cronExpressionAdd").val(),

                remark: $("#remarkAdd").val()
            },
            function (data) {

                if (data.code == '400') {

                    layer.msg(data.msg);

                    return;
                }

                if (data.code == '500') {

                    layer.msg(data.msg);

                    return;
                }

                if (data.code == '200') {

                    setTimeout(function () {

                        top.layer.close(index);

                        top.layer.msg(data.msg);

                        layer.closeAll("iframe");

                        //刷新父页面

                        parent.location.reload();

                    }, 500);
                }


            });

        return false;
    })

});