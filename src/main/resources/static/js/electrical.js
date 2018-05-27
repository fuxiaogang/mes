/**
 * 电气上机
 */
$(function () {

    /**
     * 锁定/解锁
     */
    $('#lock').click(function () {
        if ($('#stationId').prop("disabled") == true) {
            $('#stationId').prop("disabled", false).focus();
        } else {
            $('#stationId').prop("disabled", true)
        }
    });

    /**
     * 机台号失去焦点事件，加载状态、生产信息
     */
    $('#stationId').blur(function(){
        alert($(this).val());
    });
})