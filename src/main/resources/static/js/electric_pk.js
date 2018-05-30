$(function () {

    function now() {
        var dateObj = new Date();
        var year = dateObj.getFullYear();//年
        var month = dateObj.getMonth()+1;//月  (注意：月份+1)
        var date = dateObj.getDate();//日
        var hours = dateObj.getHours();//小时
        var minutes = dateObj.getMinutes();//分钟
        var seconds = dateObj.getSeconds();//秒
        if(month<10){
            month = "0"+month;
        }
        if(date<10){
            date = "0"+date;
        }
        if(hours<10){
            hours = "0"+hours;
        }
        if(minutes<10){
            minutes = "0"+minutes;
        }
        if(seconds<10){
            seconds = "0"+seconds;
        }
        var newDate = year+"-"+month+"-"+date+" "+hours+":"+minutes+":"+seconds;
        $('#dateSelect').text( newDate);
    }
    now();
    setInterval(function() {now()},1000);


    loaddata();

    /**
     * 加载页面数据
     */
    function loaddata() {
        var url = '/elec/listBoard';
        emsCommon.request({
            "url": url, "data": {}, "callback": function (data) {
                if (data) {
                    if (data.code == 200) {
                        buildStatus(data.data.etStatuss);
                        refreshPTable(data.data.etPlists);
                    } else {
                        alert(data.msg);
                    }
                }
            }
        });
    }

    //定时刷新时长
    setInterval(function () {
        $('td[name="proctTr"]').each(function () {
            var dura = $(this).text();
            $(this).text(emsCommon.addSecond(dura));
        });
    }, 1000);

    //定时刷新整个页面
    setInterval(function () {
        loaddata();
    }, 60000);


    function buildStatus(data) {
        var html = "";
        var template = '<li><p>{operation}</p> <p class="{style}">{zstat}</p></li>'
        $(data).each(function (index, item) {
            var style = item.zstat == '待机' ? 'state_red' : 'state_green';
            html += template.replace('{operation}', item.operation)
                .replace('{style}', style)
                .replace('{zstat}', item.zstat);
        });
        $("#stationStatus ul").html(html);
    }

    /**
     * 刷新生产信息表
     * @param data
     */
    function refreshPTable(data) {
        var html = "";
        var tdTemplate = '<tr>\n' +
            '                        <td>{pspid}</td>\n' +
            '                        <td>{zzext}</td>\n' +
            '                        <td>{maktx}</td>\n' +
            '                        <td>{sname}</td>\n' +
            '                        <td>{ecodeText}</td>\n' +
            '                        <td>{zstat}</td>\n' +
            '                        <td>{rdate} {rtime}</td>\n' +
            '                        <td name="proctTr">{proctTr}</td>\n' +
            '                    </tr>'
        $(data).each(function (index, item) {
            html += tdTemplate.replace('{pspid}', item.pspid)
                .replace('{zzext}', item.zzext)
                .replace('{maktx}', item.maktx)
                .replace('{sname}', item.sname)
                .replace('{ecodeText}', item.ecodeText)
                .replace('{zstat}', item.zstat)
                .replace('{rdate}', item.rdate)
                .replace('{rtime}', item.rtime ? item.rtime.substr(0,5) : '00:00')
                .replace('{proctTr}', item.proctTr);
        });
        $("#prd-table tbody").html(html);
    }
});