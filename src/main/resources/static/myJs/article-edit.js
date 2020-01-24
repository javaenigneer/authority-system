var success = 0;

layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form;


    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    var id = $("#id").val();

    //
    // //用于同步编辑器内容到textarea
    // layedit.sync(editIndex);

    //多图片上传
    upload.render({

        elem: '#test2'

        , url: '/upload/file'

        , multiple: true

        , before: function (obj) {

            var flag = true;

            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {

                var img = new Image();

                img.src = result;

                img.onload = function (ev) {

                    // 判断图片上传的数量
                    if (success > 1) {

                        flag = false;

                        return flag;

                    } else {

                        // 初始化完成后获取上传图片的宽高，判断限制图片的大小
                        if (img.width <= 800 && img.height <= 800) {

                            $('#demo2').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img"  id="image">');

                            $("#cleanImgs").attr("class", "layui-btn layui-btn-radius layui-btn-normal");

                            success++;

                            flag = true;

                        } else {

                            flag = false;

                            layer.msg("你上传的小图片大小必须为86*86尺寸！");

                            return flag;
                        }
                    }
                }
            });
        }
        , done:

            function (res) {

                if (success >= 1) {

                    layer.msg("上传图片已到上限！");

                    $("#image").attr("src", res.data.src);

                    $("#test2").attr("class", "layui-btn layui-btn-radius layui-btn-disabled");

                }

                //上传完毕
                if (res.info == '文件为空') {

                    layer.msg("未选择任何图片");

                    return;
                }

                if (res.info == '文件类型不合法') {

                    layer.msg("文件类型不合法");

                    return;
                }

                if (res.info == '文件内容不合法') {

                    layer.msg("文件内容不合法");

                    return;
                }

            },
        error: function () {

            layer.msg("上传失败");

            return;
        }
    })
    ;


    cleanImage();

    /**
     * 清空图片浏览
     */
    function cleanImage() {

        $("#cleanImgs").click(function () {

            // 将图片的src置为空
            $("#cleanImgs").attr("class", "layui-btn layui-btn-radius layui-btn-disabled");

            $("#test2").attr("class", "layui-btn layui-btn-radius layui-btn-normal");

            layer.msg("清空图片浏览成功");

            success = 0;

            $('#demo2').html("");

            return;

        });
    }


//格式化时间
    function filterTime(val) {
        if (val < 10) {
            return "0" + val;
        } else {
            return val;
        }
    }

//定时发布
    var time = new Date();

    var submitTime = time.getFullYear() + '-' + filterTime(time.getMonth() + 1) + '-' + filterTime(time.getDate()) + ' ' + filterTime(time.getHours()) + ':' + filterTime(time.getMinutes()) + ':' + filterTime(time.getSeconds());

    laydate.render({

        elem: '#release',

        type: 'datetime',

        trigger: "click",

        done: function (value, date, endDate) {

            submitTime = value;

        }
    });

    form.on("radio(release)", function (data) {

        if (data.elem.title == "定时发布") {

            $(".releaseDate").removeClass("layui-hide");

            $(".releaseDate #release").attr("lay-verify", "required");

        } else {
            $(".releaseDate").addClass("layui-hide");

            $(".releaseDate #release").removeAttr("lay-verify");

            submitTime = time.getFullYear() + '-' + (time.getMonth() + 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes() + ':' + time.getSeconds();
        }
    });

    form.verify({

        newsName: function (val) {

            if (val == '') {

                return "文章标题不能为空";

            }
        },
        content: function (val) {

            if (val == '') {

                return "文章内容不能为空";
            }
        }
    });


    form.on("submit(addNews)", function (data) {

        //截取文章内容中的一部分文字放入文章摘要
        var abstract = layedit.getText(ieditor).substring(0, 50);

        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        $.post("/article/edit",
            {
                id: $("#id").val(), // 文章id

                articleName: $(".newsName").val(),  //文章标题

                articleAbstract: $(".abstract").val(),  //文章摘要

                articleContent: layedit.getContent(ieditor).split('<audio controls="controls" style="display: none;"></audio>')[0],  //文章内容

                articleLook: data.field.openness,

                articleTypeId: $('.articleTypeId select').val(),

                articleStatus: $('.newsStatus select').val(),    //发布状态

                articleDate: submitTime,    //发布时间

                articleImage: $('#image')[0].src,

                articleTop: data.field.newsTop == "on" ? "checked" : "",    //是否置顶
            },

            function (res) {

                if (res.code != 200) {

                    layer.msg("更新失败");

                    return;

                }
            });

        setTimeout(function () {

            top.layer.close(index);

            top.layer.msg("文章更新成功！");

            layer.closeAll("iframe");

            //刷新父页面

            parent.location.reload();

        }, 500);

        return false;
    })

//预览
    form.on("submit(look)", function () {
        layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问");
        return false;
    });

    /**
     * 富文本编辑器
     */
    layedit.set({

        //暴露layupload参数设置接口 --详细查看layupload参数说明

        uploadImage: {

            url: '/upload/file',

            accept: 'image',

            acceptMime: 'image/*',

            exts: 'jpg|png|gif|bmp|jpeg',

            size: 1024 * 10,

            data: {

                name: "测试参数",

                age: 99
            }
            , done: function (data) {

                console.log(data);

            }
        },
        uploadVideo: {

            url: 'your url',

            accept: 'video',

            acceptMime: 'video/*',

            exts: 'mp4|flv|avi|rm|rmvb',

            size: 1024 * 10 * 2,

            done: function (data) {

                console.log(data);
            }
        }
        , uploadFiles: {

            url: 'your url',

            accept: 'file',

            acceptMime: 'file/*',

            size: '20480',

            autoInsert: true, //自动插入编辑器设置

            done: function (data) {

                console.log(data);
            }
        }
        //右键删除图片/视频时的回调参数，post到后台删除服务器文件等操作，
        //传递参数：
        //图片： imgpath --图片路径
        //视频： filepath --视频路径 imgpath --封面路径
        //附件： filepath --附件路径
        , calldel: {

            url: 'your url',

            done: function (data) {

                console.log(data);
            }
        }
        , rightBtn: {

            type: "layBtn",//default|layBtn|custom  浏览器默认/layedit右键面板/自定义菜单 default和layBtn无需配置customEvent

            customEvent: function (targetName, event) {

                //根据tagName分类型设置
                switch (targetName) {

                    case "img":

                        alert("this is img");

                        break;
                    default:

                        alert("hello world");

                        break;
                }
                ;
                //或者直接统一设定
                //alert("all in one");
            }
        }
        //测试参数
        , backDelImg: true

        //开发者模式 --默认为false
        , devmode: true

        //是否自动同步到textarea
        , autoSync: true

        //内容改变监听事件
        , onchange: function (content) {

            console.log(content);

        }
        //插入代码设置 --hide:false 等同于不配置codeConfig
        , codeConfig: {

            hide: true,  //是否隐藏编码语言选择框

            default: 'javascript', //hide为true时的默认语言格式

            encode: true //是否转义

            , class: 'layui-code' //默认样式

        }
        //新增iframe外置样式和js

        , quote: {

            style: ['Content/css.css'],

            //js: ['/Content/Layui-KnifeZ/lay/modules/jquery.js']
        }
        //自定义样式-暂只支持video添加
        //, customTheme: {
        //    video: {
        //        title: ['原版', 'custom_1', 'custom_2']
        //        , content: ['', 'theme1', 'theme2']
        //        , preview: ['', '/images/prive.jpg', '/images/prive2.jpg']
        //    }
        //}
        //插入自定义链接
        , customlink: {

            title: '插入layui官网'

            , href: 'https://www.layui.com'
            ,

            onmouseup: ''


        }
        , facePath: 'http://knifez.gitee.io/kz.layedit/Content/Layui-KnifeZ/'

        , devmode: true

        , videoAttr: ' preload="none" '

        //预览样式设置，等同layer的offset和area规则，暂时只支持offset ,area两个参数
        //默认为 offset:['r'],area:['50%','100%']
        //, previewAttr: {
        //    offset: 'r'
        //    ,area:['50%','100%']
        //}
        , tool: [
            'html', 'undo', 'redo', 'code', 'strong', 'italic', 'underline', 'del', 'addhr', '|', 'removeformat', 'fontFomatt', 'fontfamily', 'fontSize', 'fontBackColor', 'colorpicker', 'face'
            , '|', 'left', 'center', 'right', '|', 'link', 'unlink', 'images', 'image_alt', 'video', 'attachment', 'anchors'
            , '|'
            , 'table', 'customlink'
            , 'fullScreen', 'preview'
        ]
        , height: '500px'
    });
    var ieditor = layedit.build('layeditDemo');

    // //设置编辑器内容
    // layedit.setContent(ieditor, "<h1>hello layedit</h1><p>对layui.layedit的拓展，基于layui v2.4.3.增加了HTML源码模式、插入table、批量上传图片、图片插入、超链接插入功能优化、视频插入功能、全屏功能、段落、字体颜色、背景色设置、锚点设置等功能。</p><br><br><div>by KnifeZ </div>", false);

    $("#openlayer").click(function () {
        layer.open({
            type: 2,
            area: ['700px', '500px'],
            fix: false, //不固定
            maxmin: true,
            shadeClose: true,
            shade: 0.5,
            title: "title",
            content: 'add.html'
        });
    })

})