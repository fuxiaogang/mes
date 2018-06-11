$(function () {

    //定时器id
    var timerId;

    /**
     * 机台失去焦点：1.请求生产信息，2.请求状态信息
     */
    $('#station-input').blur(function () {
        loaddataByEcode();
    });

    /**
     * 机台回车触发
     */
    $('#station-input').keyup(function (event) {
        if (event.keyCode == 13) {
            loaddataByEcode();
        }
    });

    function loaddataByEcode() {
        var ecode = $('#station-input').val();
        if (!ecode) {
            return;
        }
        buildStatsInput(ecode);
        refreshPTable(ecode);
    }

    /**
     * 员工号回车
     */
    $('#pernr-input').keyup(function (event) {
        if (event.keyCode == 13) {
            $('#zzext-input').focus();
        }
    });

    /**
     * 图号失去焦点：1.请求历史信息，2.请求订单信息
     */
    $('#zzext-input').blur(function () {
        loaddataByZzext();
    });

    /**
     * 图号回车触发
     */
    $('#zzext-input').keyup(function (event) {
        if (event.keyCode == 13) {
            loaddataByZzext();
        }
    });

    function loaddataByZzext() {
        var zzext = $('#zzext-input').val();
        if (!zzext) {
            return;
        }
        refreshHistoryTable(zzext);
        buildOrder(zzext)
    }


    /**
     * 开始上机
     */
    $('#startBtn').click(function () {
        var ecode = $('#station-input').val();
        var stats = $("input[name='stats-input']:checked").val();
        var pernr = $('#pernr-input').val();
        var zzext = $('#zzext-input').val();
        if (!ecode) {
            alert('请输入机台号!')
            return;
        }
        if (!stats) {
            alert('请选择状态!')
            return;
        }
        if (!pernr) {
            alert('请输入员工号!')
            return;
        }
        if (!zzext) {
            alert('请输入图号!')
            return;
        }
        var tip = '机台号:' + ecode + '\n' +
            '状态:' + stats + '\n' +
            '工号:' + pernr + '\n' +
            '图号:' + zzext + '\n';
        tip += '确认上机？';
        if (confirm(tip)) {
            start(ecode, stats, pernr, zzext);
        }
    });

    /**
     * 停止
     */
    $('#prd-table').on('click', 'a[name="stopBtn"]', function () {
        var dura = $(this).closest('tr').find('td[name="proctTr"]').eq(0).text();
        var sname = $(this).closest('tr').find('td[name="snameTd"]').eq(0).text();
        if (confirm(sname + '已经执行任务' + dura + ',确定结束当前任务？')) {
            var eveid = $(this).attr('data-eveid');
            var ecode = $(this).attr('data-ecode');
            stop(eveid, ecode);
        }
    });

    $('#prd-table').on('mouseover', 'td', function () {
        $('#prd-table tr').removeClass('selected');
        $(this).closest('tr').addClass('selected');
    });

    $('#viewImg').click(function () {
        var statsUrl = '/elec/viewImg';
        var zzext = $('#zzextData').val();
        if (!zzext) {
            alert('没有选择图号');
            return;
        }
        var param = {'IN_ZZEXT': zzext};
        emsCommon.request({
            "url": statsUrl, "data": param, "callback": function (data) {
                if (data.code == 200) {
                    var path = data.data.outPath;
                    if (path && path != '') {
                        window.open(path)
                    } else {
                        alert(zzext + '没有可查看的图纸');
                    }
                } else {
                    alert(data.msg);
                }
            }
        });
    })

    //定时刷新时长
    setInterval(function () {
        $('td[name="proctTr"]').each(function () {
            var dura = $(this).text();
            $(this).text(emsCommon.addSecond(dura));
        });
    }, 1000);


    /**
     * 请求状态信息
     * @param ecode
     */
    function buildStatsInput(ecode) {
        var statsUrl = '/elec/listEtStats';
        var param = {'ECODE': ecode};
        emsCommon.request({
            "url": statsUrl, "data": param, "callback": function (data) {
                var html = "";
                var radioTemplate = '<label><input type="radio" name="stats-input" value="{value}" >{value}</label>';
                $(data).each(function (index, item) {
                    html += radioTemplate.replace(/{value}/g, item.zstat);
                });
                $("#operateType").html(html);
            }
        });
    }

    /**
     * 刷新生产信息表
     * @param ecode
     */
    function refreshPTable(ecode) {

        var url = '/elec/listEtPlist';
        var param = {'ECODE': ecode};
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
                var html = "";
                var tdTemplate = '<tr>\n' +
                    '                        <td>{zzext}</td>\n' +
                    '                        <td>{pernr}</td>\n' +
                    '                        <td name="snameTd">{sname}</td>\n' +
                    '                        <td>{rdate} {rtime}</td>\n' +
                    '                        <td name="proctTr">{proctTr}</td>\n' +
                    '                        <td>{ecodeText}</td>\n' +
                    '                        <td>{zstat}</td>\n' +
                    '                        <td><a name="stopBtn" data-eveid="{eveid}"  data-ecode="{ecode}" href="javascript:void(0)" >结束</a></td>\n' +
                    '                    </tr>'
                $(data).each(function (index, item) {
                    html += tdTemplate.replace('{zzext}', item.zzext)
                        .replace('{pernr}', item.pernr)
                        .replace('{sname}', item.sname)
                        .replace('{rdate}', item.rdate)
                        .replace('{rtime}', item.rtime ? item.rtime.substr(0, 5) : '00:00')
                        .replace('{proctTr}', item.proctTr)
                        .replace('{ecodeText}', item.ecodeText)
                        .replace('{zstat}', item.zstat)
                        .replace('{eveid}', item.eveid)
                        .replace('{ecode}', ecode);
                });
                $("#prd-table tbody").html(html);

                timerId && clearTimeout(timerId);
                timerId = setTimeout(function () {
                    refreshPTable(ecode)
                }, 60000);

            }
        });
    }

    /**
     * 刷新历史信息
     * @param zzext
     */
    function refreshHistoryTable(zzext) {
        var url = '/elec/listEtHistory';
        var param = {'IN_ZZEXT': zzext};
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
                var html = "";
                var tdTemplate = ' <tr><td>{sname}</td><td>{rdate}</td><td>{ltxa1}</td></tr>'
                $(data).each(function (index, item) {
                    html += tdTemplate.replace('{sname}', item.sname)
                        .replace('{rdate}', item.rdate)
                        .replace('{ltxa1}', item.ltxa1);
                });
                $("#hisTable tbody").html(html);
            }
        });
    }

    /**
     * 清空历史表
     */
    function clearHistoryTable() {
        $("#hisTable tbody").html('');
    }

    /**
     * 设置订单信息
     * @param zzext
     */
    function buildOrder(zzext) {
        var url = '/elec/listEsOrder';
        var param = {'IN_ZZEXT': zzext};
        $('#zzextData').val(zzext);
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
                $('#order_pspid').text(data.pspid);
                $('#order_matnr').text(data.matnr);
                $('#order_maktx').text(data.maktx);
                $('#order_psmng').text(data.psmng);
                $('#order_usr08').text(data.usr08);
                $('#order_usr09').text(data.usr09);
            }
        });
    }

    /**
     * 清空订单信息
     */
    function clearOrder() {
        $('#order_pspid').text('');
        $('#order_matnr').text('');
        $('#order_maktx').text('');
        $('#order_psmng').text('');
        $('#order_usr08').text('');
        $('#order_usr09').text('');
    }

    /**
     * 上机
     * @param ecode
     * @param zstat
     * @param pernr
     * @param zzext
     */
    function start(ecode, zstat, pernr, zzext) {
        var url = '/elec/start';
        var param = {'IN_ECODE': ecode, 'IN_ZSTAT': zstat, 'IN_PERNR': pernr, 'IN_ZZEXT': zzext};
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
                if (data != 'success') {
                    alert(data);
                } else {
                    if($('#station-input').prop('disabled')){ //锁定
                        refreshHistoryTable(zzext);
                        buildOrder(zzext);
                        refreshPTable(ecode);
                    }else{
                        $('#station-input').val('');
                        $("input[name='stats-input']:checked").prop('checked', '');
                        $('#pernr-input').val('');
                        $('#zzext-input').val('');
                        clearHistoryTable();
                        clearOrder();
                        refreshPTable(ecode);
                    }
                }
            }
        });
    }

    function stop(eveid, ecode) {
        var url = '/elec/stop';
        var param = {'IN_EVEID': eveid};
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
                if (data != 'success') {
                    alert(data);
                } else {
                    refreshPTable(ecode);
                }
            }
        });
    }
});
