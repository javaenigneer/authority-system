layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form;

    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;



    form.on("submit(addMenu)", function (data) {

        var id = $("#id").val();

        console.log(id);

        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        // 添加菜单的URL
        var url = "/menu/edit";

        // 判断Id是否有值
        if (id == -2) {

            url = "/menu/add";

        }

        $.post(url,
            {
                authorityId: id, // 菜单id

                parentId: $("#parentId").val(),  //上级ID

                authorityName: $("#authorityName").val(),  //菜单名称

                isMenu: data.field.type, // 类型 0--菜单   1--按钮

                menuUrl: $('#menuUrl').val(), // 菜单Url

                authority: $('#authority').val(),    //权限

                orderNumber: $('#orderNumber').val()  //排序
            },

            function (res) {

                if (res.code != 200) {

                    layer.msg("更新失败");

                    return;

                }
            });

        setTimeout(function () {

            top.layer.close(index);

            top.layer.msg("菜单更新成功！");

            layer.closeAll("iframe");

            //刷新父页面

            parent.location.reload();

        }, 500);

        return false;
    });

    // 监听单选按钮
    form.on("radio(type)", function (data) {

        // 获取选中的值
        var value = data.value;

        if (value == '0') {

            $('#authorityId').after('<div class="layui-form-item" id="menuHidden">\n' +
                '                <label class="layui-form-label">URL</label>\n' +
                '                <div class="layui-input-block">\n' +
                '                    <input type="text" name="menuUrl" id="menuUrl" lay-verify="required" lay-reqtext=""\n' +
                '                           placeholder="菜单URL" autocomplete="off" class="layui-input">\n' +
                '                </div>\n' +
                '            </div>');

        }

        if (value == '1') {

            // 移除菜单URL
            $("#menuHidden").remove();

            // 移除排序
            $("#orderNumberHidden").remove();
        }
    })
});