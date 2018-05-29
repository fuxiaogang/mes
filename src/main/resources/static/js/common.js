/**
 * Created by 大神鬼佐 on 2018/5/25.
 */
var emsCommon;

function EmsCommon() {
};

EmsCommon.prototype = {

    /*
     * @param isMask 转菊花是否添加遮罩层
     */
    loadingShow: function (isMask) {
        if (isMask) {
            emsCommon.maskElement();
        }
        if ($(".ems-loading").length == 0) {
            $("body").append('<div class="ems-loading"></div>');
        }
    },

    formatDura: function (dura) {
        var time = '';
        if (!this.isNum(dura)) {
            dura = 0;
        }
        var hour = parseInt(dura / 3600);
        var min = parseInt((dura - (hour * 3600)) / 60);
        var second = dura - (hour * 3600) - (min * 60);
        if (hour > 0) {
            time = hour + '小时';
        }
        if (min > 0) {
            time += min + '分钟';
        }
        if (second > 0) {
            time += second + '秒';
        }
        return time;
    },

    isNum: function (data) {
        return data && /^[0-9]+$/.test(data);
    },

    addSecond: function (time) {
        if (!time || time.split(':') < 3) {
            return '00:00:00';
        }
        var timeArray = time.split(':');
        var hour = parseInt(timeArray[0]);
        var min = parseInt(timeArray[1]);
        var second = parseInt(timeArray[2]);

        if (second < 59) {
            second = second + 1;
        } else {
            second = 0;
            if (min < 59) {
                min = min + 1;
            } else {
                min = 0;
                hour = hour + 1;
            }
        }
        return hour + ':' + (min < 10 ? '0' + min : min) + ':' + (second < 10 ? '0' + second : second);
    },

    /**
     * @summary loading隐藏去除方法
     */
    loadingHide: function () {
        emsCommon.unmaskElement();
        $(".ems-loading").remove();
    },

    /**
     * 增加body遮罩
     */
    maskElement: function () {
        if ($(".ems-loading-mask").length == 0) {
            $("body").append('<div class="ems-loading-mask"></div>');
        }
    },

    /**
     * 取消body遮罩
     */
    unmaskElement: function () {
        $(".ems-loading-mask").remove();
    },

    /**
     * 获取浏览器窗口高度
     */
    getBrowserHeight: function () {
        var winHeight = 0;
        if ($(window).height()) {
            winHeight = $(window).height();
        }
        else if ($(document).height()) {
            winHeight = $(document).height();
        } else if (window.innerHeight) {
            winHeight = window.innerHeight;
        } else if ((document.body) && (document.body.clientHeight)) {
            winHeight = document.body.clientHeight;
        }
        /*//通过深入Document内部对body进行检测，获取浏览器窗口高度
        if (document.documentElement && document.documentElement.clientHeight)
            winHeight = document.documentElement.clientHeight;
        //DIV高度为浏览器窗口的高度
        document.getElementById("test").style.height= winHeight +"px";*/
        return winHeight;
    },


    /**
     * @summary 发起一个请求，并将HTML放到指定的容器
     */
    request: function (option) {
        // 分4步：1遮罩，2请求URL，3执行回调函数，4取消遮罩
        var _option = {
            selector: "#table-content",
            url: "",
            data: {},
            preventTop: false,
            callback: null
        };
        $.extend(_option, option);
        $.post(option.url, _option.data, function (data) {
            //$(_option.selector).empty().html(data);
            if (typeof _option.callback == "function") {
                _option.callback(data);
            }
        });
    },


    /**
     * 加载更多数据
     * @param url
     * @param offset
     * @param size
     * @param appendData 追加内容。例如：<tr><td>data[i][id]</td><td>data[i][name]</td></tr> 其中data是json数组
     */
    getData: function (url, offset, size, appendContain, appendData) {
        $.ajax({
            type: 'GET',
            url: url,
            dataType: 'json',
            success: function (reponse) {
                var data = reponse.list;
                var sum = reponse.list.length;
                var result = '';
                /****业务逻辑块：实现拼接html内容并append到页面*********/
                //console.log(offset , size, sum);

                /*如果剩下的记录数不够分页，就让分页数取剩下的记录数
                 * 例如分页数是5，只剩2条，则只取2条
                 *
                 * 实际MySQL查询时不写这个不会有问题
                 */
                if (sum - offset < size) {
                    size = sum - offset;
                }

                /*使用for循环模拟SQL里的limit(offset,size)*/
                for (var i = offset; i < (offset + size); i++) {
                    //result += '<tr><td>data[i][id]</td><td>data[i][name]</td></tr>';
                    result += appendData;
                }

                $(appendContain).append(result);

                /*******************************************/

                /*隐藏more按钮*/
                if ((offset + size) >= sum) {
                    $(".js-load-more").hide();
                } else {
                    $(".js-load-more").show();
                }
            }
        });
    },


    /**
     * 加载更多
     * @param url
     * @param pageStart
     * @param pageSize
     * @param appendData 追加内容
     */
    loadmore: function (url, pageStart, pageSize, appendContain, appendData) {
        /*初始化*/
        var counter = 0;
        /*计数器*/
        pageStart = pageStart || 0;
        /*offset*/
        pageSize = pageSize || 10;
        /*size*/
        /*首次加载*/
        emsCommon.getData(url, pageStart, pageSize, appendData);
        /*监听加载更多*/
        $(document).on('click', '.js-load-more', function () {
            counter++;
            pageStart = counter * pageSize;
            emsCommon.getData(url, pageStart, pageSize, appendData);
        });
    },

};


$(function () {
    emsCommon = emsCommon || new EmsCommon();

   /* 对Date的扩展，将 Date 转化为指定格式的String
    月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
   年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
   例子：
   (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
   (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 */
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
});