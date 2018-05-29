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
            $('#equip-input').focus();
        }
    });

    /**
     * 设备号失去焦点：1.请求单元信息，2.请求订单信息
     */
    $('#equip-input').blur(function () {
        loaddataByZzext();
    });

    /**
     * 设备号回车触发
     */
    $('#equip-input').keyup(function (event) {
        if (event.keyCode == 13) {
            loaddataByZzext();
        }
    });

    function loaddataByZzext() {
        var equip = $('#equip-input').val();
        if (!equip) {
            return;
        }
        refreshUnitTable(equip);
        buildOrder(equip)
    }


    /**
     * 开始上机
     */
    $('#startBtn').click(function () {
        var ecode = $('#station-input').val();
        var stats = $("input[name='stats-input']:checked").val();
        var pernr = $('#pernr-input').val();
        var equip = $('#equip-input').val();
        var unumb = $("input[name='unitSelect']:checked").val();
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
        if (!equip) {
            alert('请输入设备号!')
            return;
        }
        if (!unumb) {
            unumb = '';
        }
        var tip = '机台号:' + ecode + '\n' +
            '状态:' + stats + '\n' +
            '工号:' + pernr + '\n' +
            '设备号:' + equip + '\n';
        if (unumb != '') {
            tip += '单元号:' + unumb + '\n';
        }
        tip += '确认上机？';
        if (confirm(tip)) {
            start(ecode, stats, pernr, equip, unumb);
        }
    });

    /**
     * 停止
     */
    $('#prd-table').on('click', 'a[name="stopBtn"]', function () {
        $(this).closest('tr').addClass('selected');
        var dura = $(this).closest('tr').find('td[name="proctTr"]').eq(0).text();
        var sname = $(this).closest('tr').find('td[name="snameTd"]').eq(0).text();
        if (confirm(sname + '已经执行任务' + dura + ',确定结束当前任务？')) {
            var eveid = $(this).attr('data-eveid');
            var ecode = $(this).attr('data-ecode');
            stop(eveid, ecode);
        }
        $(this).closest('tr').removeClass('selected');
    });

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
        var statsUrl = '/assembly/listEtStats';
        var param = {'ECODE': ecode};
        emsCommon.request({
            "url": statsUrl, "data": param, "callback": function (data) {
                if (data) {
                    if (data.code == 200) {
                        var html = "";
                        var radioTemplate = '<label><input type="radio" name="stats-input" value="{value}" >{value}</label>';
                        $(data.data).each(function (index, item) {
                            html += radioTemplate.replace(/{value}/g, item.zstat);
                        });
                        $("#operateType").html(html);
                    } else {
                        $("#operateType").html('');
                        console.log(data.msg);
                    }
                }
            }
        });
    }

    /**
     * 刷新生产信息表
     * @param ecode
     */
    function refreshPTable(ecode) {
        var url = '/assembly/listEtPlist';
        var param = {'ECODE': ecode};
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
                if (data) {
                    if (data.code == 200) {
                        var html = "";
                        var tdTemplate = '<tr>\n' +
                            '                        <td>{unumb}</td>\n' +
                            '                        <td>{equip}</td>\n' +
                            '                        <td>{ecodeText}</td>\n' +
                            '                        <td name="snameTd">{sname}</td>\n' +
                            '                        <td>{rdate} {rtime}</td>\n' +
                            '                        <td name="proctTr">{proctTr}</td>\n' +
                            '                        <td>{zstat}</td>\n' +
                            '                        <td><a name="stopBtn" data-eveid="{eveid}"  data-ecode="{ecode}" href="javascript:void(0)" >结束</a></td>\n' +
                            '                    </tr>'
                        $(data.data).each(function (index, item) {
                            html += tdTemplate.replace('{unumb}', item.unumb)
                                .replace('{equip}', item.equip)
                                .replace('{ecodeText}', item.ecodeText)
                                .replace('{sname}', item.sname)
                                .replace('{rdate}', item.rdate)
                                .replace('{rtime}', item.rtime ? item.rtime.substr(0,5) : '00:00')
                                .replace('{proctTr}', item.proctTr)
                                .replace('{zstat}', item.zstat)
                                .replace('{eveid}', item.eveid)
                                .replace('{ecode}', ecode);
                        });
                        $("#prd-table tbody").html(html);

                        timerId && clearTimeout(timerId);
                        timerId = setTimeout(function () {
                            refreshPTable(ecode)
                        }, 60000);
                    } else {
                        $("#prd-table tbody").html('');
                        console.log(data.msg);
                    }
                }
            }
        });
    }

    /**
     * 刷新单元信息
     * @param zzext
     */
    function refreshUnitTable(equip) {
        var url = '/assembly/listEtUnit';
        var param = {'IN_EQUIP': equip};
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
                if (data) {
                    if (data.code == 200) {
                        var html = "";
                        var tdTemplate = '<tr><td>{unumb}</td><td>{uname}</td><td><div class="cb" ><input type="radio" name="unitSelect" value="{unumb}"/></div></td></tr>'
                        $(data.data).each(function (index, item) {
                            html += tdTemplate.replace(/{unumb}/g, item.unumb)
                                .replace('{uname}', item.uname);
                        });
                        $("#unitInfTable tbody").html(html);
                    } else {
                        clearUnitTable();
                        console.log(data.msg);
                    }
                }
            }
        });
    }

    /**
     * 清空历史表
     */
    function clearUnitTable() {
        $("#unitInfTable tbody").html('');
    }

    /**
     * 设置订单信息
     * @param zzext
     */
    function buildOrder(equip) {
        var url = '/assembly/listEsOrder';
        var param = {'IN_EQUIP': equip};
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
                if (data) {
                    if (data.code == 200) {
                        $('#order_pspid').text(data.data.pspid);
                        $('#order_matnr').text(data.data.matnr);
                        $('#order_maktx').text(data.data.maktx);
                        $('#order_psmng').text(data.data.psmng);
                        $('#order_usr08').text(data.data.usr08);
                        $('#order_usr09').text(data.data.usr09);
                    } else {
                        clearOrder();
                        console.log(data.msg);
                    }
                }
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
    function start(ecode, zstat, pernr, equip, unumb) {
        var url = '/assembly/start';
        var param = {'IN_ECODE': ecode, 'IN_ZSTAT': zstat, 'IN_PERNR': pernr, 'IN_EQUIP': equip, 'IN_UNUMB': unumb};
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
                if (data) {
                    if (data.code == 200) {
                        !$('#station-input').prop('disabled') && $('#station-input').val('');
                        $("input[name='stats-input']:checked").prop('checked', '');
                        $('#pernr-input').val('');
                        $('#equip-input').val('');
                        clearUnitTable();
                        clearOrder();
                        refreshPTable(ecode);
                    } else {
                        alert(data.msg);
                    }
                }
            }
        });
    }

    function stop(eveid, ecode) {
        var url = '/assembly/stop';
        var param = {'IN_EVEID': eveid};
        emsCommon.request({
            "url": url, "data": param, "callback": function (data) {
                if (data) {
                    if (data.code == 200) {
                        refreshPTable(ecode);
                    } else {
                        alert(data);
                    }
                }
            }
        });
    }
});
