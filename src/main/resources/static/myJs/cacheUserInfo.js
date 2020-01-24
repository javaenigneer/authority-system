layui.config({
    base : "/myJs/"
}).use(['form','jquery',"address"],function() {

    var form = layui.form,

        $ = layui.jquery,

        address = layui.address;

    //判断是否修改过用户信息，如果修改过则填充修改后的信息
    var menuText = $("#top_tabs",parent.document).text();  //判断打开的窗口是否存在“个人资料”页面

    var citys,areas;

    if(window.sessionStorage.getItem('userInfo')){

        //获取省信息
        address.provinces();

        var userInfo = JSON.parse(window.sessionStorage.getItem('userInfo'));

        var citys;

        $(".realName").val(userInfo.adminRealName); //用户名

        $(".userSex input[value="+userInfo.adminSex+"]").attr("checked","checked"); //性别

        $(".userPhone").val(userInfo.adminPhone); //手机号

        $(".userBirthday").val(userInfo.adminBirthday); //出生年月

        //填充省份信息，同时调取市级信息列表
        $.get("../static/api/address.json", function (addressData) {

            $(".userAddress select[name='province']").val(userInfo.province); //省

            var value = userInfo.province;

            if (value > 0) {
               -/*--*/+
                address.citys(addressData[$(".userAddress select[name='province'] option[value='"+userInfo.province+"']").index()-1].childs);

                citys = addressData[$(".userAddress select[name='province'] option[value='"+userInfo.province+"']").index()-1].childs;

            } else {

                $('.userAddress select[name=city]').attr("disabled","disabled");

            }

            $(".userAddress select[name='city']").val(userInfo.city); //市

            //填充市级信息，同时调取区县信息列表
            var value = userInfo.city;

            if (value > 0) {

                address.areas(citys[$(".userAddress select[name=city] option[value='"+userInfo.city+"']").index()-1].childs);

            } else {

                $('.userAddress select[name=area]').attr("disabled","disabled");
            }

            $(".userAddress select[name='area']").val(userInfo.area); //区

            form.render();

        })

        for(key in userInfo){

            if(key.indexOf("like") != -1){

                $(".userHobby input[name='"+key+"']").attr("checked","checked");

            }

        }

        $(".userEmail").val(userInfo.adminEmail); //用户邮箱

        $(".myself").val(userInfo.adminSelfEvaluation); //自我评价

        form.render();
    }
})