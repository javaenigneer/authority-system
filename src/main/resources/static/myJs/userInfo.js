var form, $, areaData;

layui.config({

    base: "/myJs/"

}).extend({

    "address": "address"

})
layui.use(['form', 'layer', 'upload', 'laydate', "address"], function () {

    form = layui.form;

    $ = layui.jquery;

    var layer = parent.layer === undefined ? layui.layer : top.layer,

        upload = layui.upload,

        laydate = layui.laydate,

        address = layui.address;

    // 本管理员基本表id
    var adminId = $("#editAdminInfoId").val();

    // 本管理员扩展表id
    var adminExpendId = $("#editAdminExpendId").val();


    //添加验证规则
    form.verify({

        userBirthday: function (value) {


            if (!/^(\d{4})[\u4e00-\u9fa5]|[-\/](\d{1}|0\d{1}|1[0-2])([\u4e00-\u9fa5]|[-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/.test(value)) {

                return "出生日期格式不正确！";
            }
        }
    })

    //选择出生日期
    laydate.render({

        elem: '.userBirthday',

        format: 'yyyy-MM-dd',

        trigger: 'click',

        max: 0,

        mark: {"0-12-15": "生日"},

        done: function (value, date) {

            if (date.month === 9 && date.date === 3) { //点击每年09月03日，弹出提示语

                layer.msg('今天是飞飞的生日，快来送上祝福吧！');
            }
        }
    });

    //获取省信息
    address.provinces();

    //提交个人资料
    form.on("submit(changeUser)", function (data) {

        var index = layer.msg('提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        //将填写的用户信息存到session以便下次调取
        var key, userInfoHtml = '';

        var techlogy = new Array();

        var technology = '';

        $("input:checkbox[name='like']:checked").each(function(i){

            techlogy[i] = $(this).val();

        });

        technology = techlogy.join(",");//将数组合并成字符串

        userInfoHtml = {

            'id': adminId,

            'adminExpendId':adminExpendId,

            'adminRealName': $(".realName").val(),

            'adminSex': data.field.adminSex,

            'adminPhone': $(".userPhone").val(),

            'adminBirthday': $(".userBirthday").val(),

            'province': data.field.province,

            'city': data.field.city,

            'area': data.field.area,

            'adminEmail': $(".userEmail").val(),

            'adminSelfEvaluation': $(".myself").val(),

            'technology':technology
        };

        for(key in data.field){

            if(key.indexOf("like") != -1){

                userInfoHtml[key] = "on";
            }
        }



        window.sessionStorage.setItem("userInfo", JSON.stringify(userInfoHtml));

        $.ajax({

            type: 'post',

            url: '/adminController/editAdminInfo',

            data: userInfoHtml,

            dataType: 'json',

            success: function (msg) {


            }

        });

        setTimeout(function () {
            layer.close(index);

            layer.msg("提交成功！");

        }, 2000);

        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })



    //修改密码
    form.on("submit(changePwd)", function (data) {

        var index = layer.msg('提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        setTimeout(function () {

            layer.close(index);

            layer.msg("密码修改成功！");

            $(".pwd").val('');

        }, 2000);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。

    })
})