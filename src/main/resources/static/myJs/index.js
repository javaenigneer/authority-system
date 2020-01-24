layui.use(['form', 'element', 'layer', 'jquery'], function () {

    $ = layui.$;

    // 获取该管理员的id
    var adminId = $("#adminId").val();


    // 点击打开窗口

    $("#adminInfo").click(function () {

        addAdminInFo();
    });


    /**
     * 弹出管理员详细窗口
     */
    function addAdminInFo() {

        var index = layui.layer.open({

            title: "详细信息",

            type: 2,

            area:['800px','700px'],

            content: "/adminController/showAdminInfo?adminId=" + adminId

        });

    }
});


