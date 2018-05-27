/**
 * Created by 大神鬼佐 on 2018/5/25.
 */
var emsCommon;

function EmsCommon() {
};

EmsCommon.prototype = {

    test: function () {
        /*监听加载更多*/
        $(document).on('click', '.js-load-more', function () {
            console.log(1212);
        });
    },

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
        if ($(document).height()) {
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
        emsCommon.loadingShow(true);
        $.post(option.url, _option.data, function (data) {
            //$(_option.selector).empty().html(data);
            if (typeof _option.callback == "function") {
                _option.callback(data);
            }
            emsCommon.loadingHide();
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
});