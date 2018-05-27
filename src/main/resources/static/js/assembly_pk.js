$(function () {
    //获取服务器的时间
    var url = '/assembly/serverDate';
    var param = {};
    emsCommon.request({
        "url": url, "data": param, "callback": function (data) {
            if (data) {
                $('#dateSelect').val(data);
            }
        }
    });

    $('#dateSelect').change(function () {
        var date = $(this).val();
        if (!date) {
            return;
        }
        var url = '/assembly/listBoard';
        var param = {'IN_DATE': date};
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
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
    });

    /**
     * 日期回车触发刷新
     */
    $('#dateSelect').keyup(function (event) {
        if (event.keyCode == 13) {
            $('#dateSelect').change();
        }
    });

    //定时刷新时长
    setInterval(function () {
        $('td[name="proctTr"]').each(function () {
            var dura = $(this).text();
            $(this).text(emsCommon.addSecond(dura));
        });
    }, 1000);

    //定时刷新整个页面
    setInterval(function () {
        $('#dateSelect').change();
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
            '                        <td>{unumb}</td>\n' +
            '                        <td>{unumbText}</td>\n' +
            '                        <td>{equip}</td>\n' +
            '                        <td>{equipText}</td>\n' +
            '                        <td>{ecodeText}</td>\n' +
            '                        <td>{zstat}</td>\n' +
            '                        <td>{sname}</td>\n' +
            '                        <td>{beginTr}</td>\n' +
            '                        <td name="proctTr">{proctTr}</td>\n' +
            '                    </tr>'
        $(data).each(function (index, item) {
            html += tdTemplate.replace('{pspid}', item.pspid)
                .replace('{unumb}', item.unumb)
                .replace('{unumbText}', item.unumbText)
                .replace('{equip}', item.equip)
                .replace('{equipText}', item.equipText)
                .replace('{ecodeText}', item.ecodeText)
                .replace('{zstat}', item.zstat)
                .replace('{sname}', item.sname)
                .replace('{beginTr}', item.beginTr)
                .replace('{proctTr}', item.proctTr);
        });
        $("#prd-table tbody").html(html);
    }
});