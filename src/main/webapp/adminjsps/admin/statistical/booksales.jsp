<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8" />
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://code.highcharts.com/highcharts.js"></script>
</head>
<body>
<div id="container" style="width: 550px; height: 400px; margin: 0 auto"></div>
<script language="JavaScript">
    $(document).ready(function() {
        var chart = {
            type: 'column'
        };
        var title = {
            text: '销售书籍排行'
        };
        var subtitle = {
            text: ''
        };
        var xAxis = {
            categories: [],
            crosshair: true
        };
        var yAxis = {
            min: 0,
            title: {
                text: '销售量'
            }
        };
        var tooltip = {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        };
        var plotOptions = {
            column: {
                pointPadding: 0.2,
                borderWidth: 0,
                pointWidth: 30
            }
        };
        var credits = {
            enabled: false
        };

        var series= [{
            data: []
        }];
        $.ajax({
            type:"GET",
            dataType:"JSON",
            contentType : "application/json",
            async:false,
            url:"<c:url value='/admin/StatisticalServlet?method=RankSaleBook'/>",
            success:function (result) {
                for(var i = 0;i<5;i++){
                    xAxis.categories[i] = result[i].bname;
                    series[0].data[i]=result[i].num;
                }
            }
        });
        var json = {};
        json.chart = chart;
        json.title = title;
        json.subtitle = subtitle;
        json.tooltip = tooltip;
        json.xAxis = xAxis;
        json.yAxis = yAxis;
        json.series = series;
        json.plotOptions = plotOptions;
        json.credits = credits;
        $('#container').highcharts(json);
    });
</script>
</body>
</html>