<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tagPage.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>首页</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script type="text/javascript">
        $(function(){
            var url = window.location.host;
            setTimeout(function(){
                $.ajax({
                    type : "POST",
                    url : "/weiXinController/shareWX",
                    contentType : "application/json",
                    data : JSON.stringify({
                        "shareurl" :location.href
                    }),
                    success : function(data) {
                        wx.config({
                            debug: false,////生产环境需要关闭debug模式
                            appId: data.data.appid,//appId通过微信服务号后台查看
                            timestamp: data.data.timestamp,//生成签名的时间戳
                            nonceStr: data.data.nonceStr,//生成签名的随机字符串
                            signature: data.data.signature,//签名
                            jsApiList: [//需要调用的JS接口列表
                                'checkJsApi',//判断当前客户端版本是否支持指定JS接口
                                'onMenuShareTimeline',//分享给朋友圈
                                'onMenuShareAppMessage',//分享到好友
                                'hideMenuItems',
                            ]
                        });
                    },
                    error: function(xhr, status, error) {
                        //alert(status);
                        //alert(xhr.responseText);
                    }
                });
                wx.ready(function(){
                    var obj = {
                        title: '分享标题', // 分享标题
                        desc: '分享描述！', // 分享描述
                        link: 'http://'+url+'/index.jsp', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                        // 该链接是重定向链接，因为需要获取用户code，但是该链接又无法直接写微信获取code的链接，
                        // 所以需要点击后重新加载新的页面，来实现重定向，重新打开获取code的微信链接，实现获取用户信息的功能；
                        imgUrl: 'http://'+url+'/分享图标', // 分享图标
                        fail: function (res) {
                            alert(JSON.stringify(res));
                        }
                    };
                    //通过ready接口处理成功验证,需要把相关接口放在ready函数中调用来确保正确执行
                    //1.获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
                    wx.onMenuShareTimeline(obj);
                    //2.获取“分享给朋友”按钮点击状态及自定义分享内容接口
                    wx.onMenuShareAppMessage(obj);
                    wx.onMenuShareWeibo(obj);
                    wx.hideMenuItems({ menuList: ['menuItem:share:QZone',
                        'menuItem:share:qq',
                        'menuItem:favorite',
                        'menuItem:copyUrl',
                        'menuItem:openWithQQBrowser',
                        'menuItem:openWithSafari',
                    ]
                    });
                });
            },1000)
        });
    </script>
</head>
<body>
<h1>
    <a href="/auth/authUrl">回调页面</a>
</h1>

<h2>
    <a href="http://code.YouMeek.com" target="_blank">Hello YouMeek</a>
</h2>

<br>

<a href="/sysUserController/showUserToJspById/1" target="_blank">查询用户信息并跳转到一个JSP页面</a>

<br>

<a href="/sysUserController/showUserToJSONById/1" target="_blank">查询用户信息并直接输出JSON数据</a>

</body>
</html>
