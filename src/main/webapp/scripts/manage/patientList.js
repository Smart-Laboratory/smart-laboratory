/*
 *  打开历史记录曲线图展示弹窗
 */
function openChartDialog() {
    layer.open({
        type: 1,
        shade: 0.4,
        skin: 'layui-layer-lan',
        area: ['680px', '540px'],
        title: '样本项目试剂信息和历史记录',
        content: $('#chartDialog'),
        cancel: function (index) {
            layer.close(index);
            //关闭浏览器右键
            document.oncontextmenu = function () {
                return true;
            };
        }
    });
}

function show_knowledge(item) {
    jQuery.ajax({
        type: 'GET',
        url: encodeURI('/item.jsp?page=' + item),
        dataType: 'html',
        success: function (data) {
            openKnowledgeLayer(data);
        }
    });
}

function openKnowledgeLayer(data) {
    var dataArray = data.split('<div class="tab-');
    var title = [];
    for (var i = 0; i < dataArray.length; i++) {
        var str = dataArray[i].replace('">', "!@#$%^&*");
        if (i != 0) {
            var arr = str.split("!@#$%^&*");
            title[i] = arr[0];
            dataArray[i] = arr[1].replace("<\/div>", "");
        }
        if (i == dataArray.length - 1) {
            dataArray[i] = dataArray[i].replace("<\/div>", "");
            dataArray[i] = dataArray[i].replace("<\/div>", "");
        }
    }
    var jsonArr = [];
    for (var j = 0; j < dataArray.length; j++) {
        if (j != 0) {
            var jsonObj = {};
            jsonObj["title"] = title[j];
            jsonObj["content"] = dataArray[j].replace("<\/div>", "");
            jsonArr.push(jsonObj)
        }
    }
    layer.tab({
        area: ['1000px', '360px'],
        tab: jsonArr
    });

}

function getPatient(docNo) {
    $.get("../manage/patientList/patient", {id: docNo}, function (data) {
        $("#midContent").css('display', 'block');
        $("#sampleTitle").html(data.examinaim);
        $("#rowed3").jqGrid("setCaption", data.examinaim);
        $("#audit_reason").html(data.reason);
        $("#pName").html(data.name);
        $("#pAge").html(data.age);
        $("#blh").html(data.blh);
        $("#pId").html(data.id);
        $("#pSex").html(data.sex);
        $("#pSection").html(data.section);
        $("#pType").html(data.type);
        $("#diagnostic").html(data.diagnostic);
        $("#patientId").html(data.patientId);
        $("#requester").html(data.requester);
        if (data.bed == null) {
            $("#pBedText").css('display', 'none');
            $("#pBedLabel").css('display', 'none');
        } else {
            $("#pBed").html(data.bed);
            $("#pBedText").css('display', 'inline');
            $("#pBedLabel").css('display', 'inline');
        }

        $("#hiddenDocId").val(docNo);
    }, "json");
}

function getSample(sampleNo) {
    var cl = "";
    var width = $("#patientinfo").width();
    var clientHeight = $(window).innerHeight();
    var height = clientHeight - $('#head').height() - $('.form-inline').height() - $('.footer-content').height() - 210;
    jQuery("#rowed3").jqGrid({
        url: baseUrl + "/audit/sample?id=" + sampleNo,
        datatype: "json",
        jsonReader: {repeatitems: false},
        colNames: ['ID', '项目', '结果', '历史', '历史1', '历史2', '参考范围', '单位', 'KNOWLEDGE'],
        colModel: [
            {name: 'id', index: 'id', hidden: true},
            {name: 'name', index: 'name', width: '250', sortable: false},
            {name: 'result', index: 'result', width: '100', sortable: false},
            {name: 'last', index: 'last', width: '100', sortable: false},
            {name: 'last1', index: 'last1', width: '100', hidden: true, sortable: false},
            {name: 'last2', index: 'last2', width: '100', hidden: true, sortable: false},
            {name: 'scope', index: 'scope', width: '100', sortable: false},
            {name: 'unit', sortable: false, width: '100', index: 'unit'},
            {name: 'knowledgeName', index: 'knowledgeName', hidden: true}
        ],
        width: width,
        height: height,
        viewrecords: true,
        shrinkToFit: false,
        repeatitems: false,
        rowNum: 300,
        rownumbers: true,
        caption: "结果信息",
        onRightClickRow: function (id) {
            //关闭浏览器右键
            document.oncontextmenu = function () {
                return false;
            };
            var sample = $("#hiddenSampleNo").val();
            $.get(baseUrl + "/audit/ajax/singleChart", {id: id, sample: sample}, function (data) {
                $("#chartAlert").css("display", "none");
                openChartDialog();
                $("#hmInfo").empty();
                for (var i = 0; i < data.hmList.length; i++) {
                    $("#hmInfo").append(data.hmList[i]);
                }

                if (data.num > 1) {
                    $("#tongji_max").html(data.max);
                    $("#tongji_min").html(data.min);
                    $("#tongji_mid").html(data.mid);
                    $("#tongji_ave").html(data.ave);
                    $("#tongji_sd").html(data.sd);
                    $("#tongji_cov").html(data.cov);
                    var xset = data.time;
                    var yset1 = data.lo;
                    var yset2 = data.re;
                    var yset3 = data.hi;
                    var chart = new Highcharts.Chart({
                        title: {
                            text: data.name
                        },
                        credits: {
                            enabled: false
                        },
                        chart: {
                            renderTo: 'singleChartPanel',
                            type: 'line',
                            marginRight: 130,
                            marginBottom: 25
                        },
                        xAxis: {
                            type: 'datetime',
                            categories: xset
                        },
                        yAxis: {
                            title: {
                                text: '结果'
                            },
                            plotLines: [{
                                value: 0,
                                width: 1,
                                color: '#808080'
                            }]
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'middle',
                            borderWidth: 0
                        },
                        series: [{
                            name: '低值',
                            data: yset1
                        }, {
                            name: '检验结果',
                            data: yset2
                        }, {
                            name: '高值',
                            data: yset3
                        }]
                    });
                } else {
                    $("#chartAlert").css("display", "block");
                }
            });
        },
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                updatePagerIcons(table);
            }, 0);

            if ($("#sampleTitle").html() == "") {
//					$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
            }
            var hisDate = jQuery("#rowed3").jqGrid("getGridParam", "userData").hisDate;
            var sameSample = jQuery("#rowed3").jqGrid("getGridParam", "userData").sameSample;
            var isLastYear = jQuery("#rowed3").jqGrid("getGridParam", "userData").isLastYear;
            var isLastTwoYear = jQuery("#rowed3").jqGrid("getGridParam", "userData").isLastTwoYear;
            $("#rowed3_last").html("历史");
            $("#rowed3_last1").html("历史");
            $("#rowed3_last2").html("历史");
            $("#rowed3_last").css('color', '#000000');
            $("#rowed3_last1").css('color', '#000000');
            $("#rowed3_last2").css('color', '#000000');
            if (hisDate != null && hisDate != "") {
                $("#hisLastResult").val(1);
                var his = hisDate.split(",");
                if (his.length == 1) {
                    $("#rowed3_last").html(his[0].split(":")[0]);
                    $("#rowed3_last").attr('title', his[0].split(":")[1]);
                } else if (his.length == 2) {
                    $("#rowed3_last").html(his[0].split(":")[0]);
                    $("#rowed3_last").attr('title', his[0].split(":")[1]);
                    $("#rowed3_last1").html(his[1].split(":")[0]);
                    $("#rowed3_last1").attr('title', his[1].split(":")[1]);
                } else if (his.length >= 3) {
                    $("#rowed3_last").html(his[0].split(":")[0]);
                    $("#rowed3_last").attr('title', his[0].split(":")[1]);
                    $("#rowed3_last1").html(his[1].split(":")[0]);
                    $("#rowed3_last1").attr('title', his[1].split(":")[1]);
                    $("#rowed3_last2").html(his[2].split(":")[0]);
                    $("#rowed3_last2").attr('title', his[2].split(":")[1]);
                }
            } else {
                $("#hisLastResult").val(0);
            }
            $.each(jQuery('#rowed3').jqGrid('getCol', 'id', false), function (k, v) {
                var ret = jQuery("#rowed3").jqGrid('getRowData', v);
                var hl = ret.scope.split("-");
                var h = parseFloat(hl[1]);
                var l = parseFloat(hl[0]);
                if (hl.length == 3) {
                    var h = parseFloat(hl[2]);
                    var l = parseFloat(hl[1]) * (-1);
                }
                var color = "<div class='";
                if (ret.color == 1) {
                    color += "diff_td'>";
                } else if (ret.color == 2) {
                    color += "ratio_td'>";
                } else if (ret.color == 3) {
                    color += "dan_td'>";
                } else if (ret.color == 4) {
                    color += "re_td'>";
                } else if (ret.color == 5) {
                    color += "al2_td'>";
                } else if (ret.color == 6) {
                    color += "al3_td'>";
                } else if (ret.color == 7) {
                    color += "ex_td'>";
                } else {
                    color += "'>";
                }

                jQuery("#rowed3").jqGrid('setRowData', v, {
                    name: "<a href='javascript:show_knowledge(\"" + ret.knowledgeName + "\")'>" + ret.name + "</a>",
                    ab: "<a href='javascript:show_knowledge(\"" + ret.knowledgeName + "\")'>" + ret.ab + "</a>"
                });

                if (hl.length != 2 && hl.length != 3) {
                    jQuery("#rowed3").jqGrid('setRowData', v, {
                        result: color + "<span class='result_span'>" + ret.result + "</span></div>"
                    });
                    return true;
                }

                var va = parseFloat(ret.result.replace("<", "").replace(">", ""));
                var la = parseFloat(ret.last.replace("<", "").replace(">", ""));
                var la1 = parseFloat(ret.last1.replace("<", "").replace(">", ""));
                var la2 = parseFloat(ret.last2.replace("<", "").replace(">", ""));
                var res = "";
                var res1 = "";
                var res2 = "";
                var res3 = "";

                if (!isNaN(h) && !isNaN(l)) {
                    if (!isNaN(va)) {
                        if (va < l) {
                            res = "<font color='red'>↓</font>";
                        } else if (va > h) {
                            res = "<font color='red'>↑</font>";
                        }
                    }

                    if (!isNaN(la)) {
                        if (la < l) {
                            res1 = "<font color='red'>↓</font>";
                        } else if (la > h) {
                            res1 = "<font color='red'>↑</font>";
                        }
                    }

                    if (!isNaN(la1)) {
                        if (la1 < l) {
                            res2 = "<font color='red'>↓</font>";
                        } else if (la1 > h) {
                            res2 = "<font color='red'>↑</font>";
                        }
                    }

                    if (!isNaN(la2)) {
                        if (la2 < l) {
                            res3 = "<font color='red'>↓</font>";
                        } else if (la2 > h) {
                            res3 = "<font color='red'>↑</font>";
                        }
                    }
                    if (ret.editMark != 0 && ret.editMark % 33 == 0) {
                        jQuery("#rowed3").jqGrid('setRowData', v, {
                            result: color + "<span class='result_span'>" + ret.result + "</span>" + res + "</div>",
                            last: "<span class='last_span'>" + ret.last + "</span>" + res1,
                            last1: "<span class='last_span'>" + ret.last1 + "</span>" + res2,
                            last2: "<span class='last_span'>" + ret.last2 + "</span>" + res3
                        });
                    } else {
                        jQuery("#rowed3").jqGrid('setRowData', v, {
                            result: color + "<span class='result_span'>" + ret.result + "</span>" + res + "</div>",
                            last: "<span class='last_span'>" + ret.last + "</span>" + res1,
                            last1: "<span class='last_span'>" + ret.last1 + "</span>" + res2,
                            last2: "<span class='last_span'>" + ret.last2 + "</span>" + res3
                        });
                    }
                }
            });
        },
        editurl: "../audit/edit?sampleNo=" + sampleNo
    });
}

function getExplain(docNo) {
    var width = $("#rightContent").width();
    jQuery("#audit_information").jqGrid({
        url: "../manage/patientList/explain?id=" + docNo,
        datatype: "json",
        jsonReader: {repeatitems: false},
        colNames: ['ID', 'OLDRESULT', '解释', '原因', 'RANK'],
        colModel: [{name: 'id', index: 'id', sortable: false, hidden: true},
            {name: 'oldResult', index: 'oldResult', sortable: false, hidden: true},
            {name: 'result', index: 'result', width: 90, sortable: false},
            {name: 'content', index: 'content', width: 100, sortable: false},
            {name: 'rank', index: 'rank', sortable: false, hidden: true}],
        height: '100%',
        width: width
    });
}

function getList(patientId, blh) {
    var width = $("#leftContent").width();
    var isFirstTime = true;
    var isFirstTimeForResult = true;
    var clientHeight = $(window).innerHeight();
    var height = clientHeight - $('#head').height() - $('.form-inline').height() - $('.footer-content').height() - 100;
    var mygrid = jQuery("#list").jqGrid({
        url: "../manage/patientList/data?type=0&patientId=" + patientId + "&blh=" + blh,
        datatype: "json",
        width: width,
        colNames: ['ID', '样本号', '检验目的', '状态'],
        colModel: [
            {name: 'id', index: 'id', hidden: true},
            {name: 'sample', index: 'sample', width: 120, sortable: false},
            {name: 'examinaim', index: 'examinaim', width: 80, sortable: false},
            {name: 'type', index: 'type', width: 40, sortable: false}],
        rowNum: 16,
        height: height,
        jsonReader: {repeatitems: false},
        mtype: "GET",
        multiselect: true,
        pager: '#pager',
        onSelectRow: function (id) {
            var ret = jQuery("#list").jqGrid('getRowData', id);
            getPatient(ret.id);
            $("#hiddenSampleNo").val(ret.sample);
            $("#hisLastResult").val(null);
            if (isFirstTime) {
                getSample(ret.sample);
                isFirstTime = false;
            } else {
                jQuery("#rowed3").jqGrid("setGridParam", {url: baseUrl + "/audit/sample?id=" + ret.sample}).trigger("reloadGrid");
            }

            if ($("#historyTabs").tabs('option', 'active') == 0) {
                jQuery("#audit_information").jqGrid("setGridParam", {
                    url: "../manage/patientList/explain?id=" + ret.id
                }).trigger("reloadGrid");
            } else {
                jQuery("#rowed3").setGridParam().showCol("last1");
                jQuery("#rowed3").setGridParam().showCol("last2");
            }
            $("#historyTabs").css('display', 'block');
        },
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                updatePagerIcons(table);
            }, 0);
            var firstDocId, firstSampleNo;
            $.each(jQuery('#list').jqGrid('getCol', 'id', false), function (k, v) {
                var ret = jQuery("#list").jqGrid('getRowData', v);
                if (k == 0) {
                    firstDocId = ret.id;
                    firstSampleNo = ret.sample;
                }

            });
            $("#list").setSelection(firstDocId, true);
        }
    }).trigger("reloadGrid");
    jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false, search: false, refresh: false});
    jQuery("#list").jqGrid('navButtonAdd', "#pager", {
        caption: "",
        title: "",
        buttonicon: 'ui-icon-pin-s',
        onClickButton: function () {
            mygrid[0].toggleToolbar()
        }
    });
}

$(function () {
    function getQueryString(name) {
        var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        }
        return null;
    }

    $.ajaxSetup({cache: false});

    laydate.skin('molv');
    laydate({
        elem: '#from',
        event: 'focus',
        festival: true,
        format: 'YYYY-MM-DD'
    });
    laydate({
        elem: '#to',
        event: 'focus',
        festival: true,
        format: 'YYYY-MM-DD'
    });
    $("#from").val(new Date().Format("yyyy-MM-dd"));
    $("#to").val(new Date().Format("yyyy-MM-dd"));

    $("#searchBtn").click(function () {
        var from = $("#from").val();
        var to = $("#to").val();
        var select = $("#search_select").val();
        var searchText = $("#search_text").val();
        var patientType = $('#patientType').val();
        var lab = $('#labSelect_seach').val();

        jQuery("#list").jqGrid("clearGridData");
        jQuery("#list").jqGrid("setGridParam", {
            url: "../manage/patientList/data?from=" + from + "&to=" + to
            + "&text=" + searchText + "&type=" + select + "&patientType=" + patientType + "&lab="+lab
        }).trigger("reloadGrid");

    });

    $("#search_detailed_printBtn").click(function () {
        var sampeNo = $("#hiddenSampleNo").val() || '';
        if (sampeNo != '') {
            printReport(sampeNo);
        }
    });
    function printReport(sampleno) {
        $.get(baseUrl + "/print/ajax/printReport", {sampleno: sampleno, haslast: '0', type: ''}, function (data) {
            Preview(data);
        })
    }

    /**
     * 批量打印
     * add by zcw 20161103
     */
    function batchPrint() {
        var selectedIds = $("#manager_list").jqGrid("getGridParam", "selarrrow");
    }

    $("#auditPrint").dialog({
        autoOpen: false,
        resizable: false,
        modal: true,
        width: 750,
        height: 600
    });
    //------------------------------------------
    $("#historyTabs").tabs({
        active: 0,
        activate: function (event, ui) {
            var id = ui.newPanel.attr("id");
            if (id == "tabs-1") {
                jQuery("#rowed3").setGridParam().showCol("last1");
                jQuery("#rowed3").setGridParam().showCol("last2");
            } else {
//					jQuery("#rowed3").setGridParam().hideCol("last1");
//					jQuery("#rowed3").setGridParam().hideCol("last2");
                var s = jQuery("#list").jqGrid('getGridParam', 'selrow');
                getExplain(s);
            }
        }
    });

    getList(getQueryString("patientId"), getQueryString("blh"));
    getSample("");
});
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

var LODOP; //声明为全局变量
function printSetting() {
    //readPrintFile();
    if (LODOP.CVERSION) {
        LODOP.On_Return = function (TaskID, Value) {
            if (Value >= 0)
                setCookie("report_print", Value);
            else
                alert("选择失败！");
        };
        LODOP.SELECT_PRINTER();
        return;
    }
};
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
};
function setCookie(name, value) {
    var Days = 9999;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
};
function Preview(strHtml) {//打印预览
    //CreateDataBill(data)
    LODOP = getLodop();
    LODOP.PRINT_INIT("打印报告单");
    LODOP.SET_PRINT_PAGESIZE(2, 0, 0, 'A5');
    LODOP.ADD_PRINT_HTM("0", 0, "RightMargin:0cm", "BottomMargin:0mm", strHtml);
    //LODOP.ADD_PRINT_HTM(0,0,"100%","100%",strHtml);
    var index = getCookie("report_print");
    LODOP.SET_PRINTER_INDEX(index)
    LODOP.PRINT();
    //LODOP.PREVIEW();
}