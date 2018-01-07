<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>echarts统计图</title>
		
	<script src="jquery.js"></script>
	<script src="echarts.min.js"></script>
</head>
<body>
	<div id="brokenLineBarDiv" style="width:600px; height: 400px;float:left;margin:20px;" ></div>
	<div id="pieDiv" style="width:600px; height: 400px;float:right;margin:20px;"></div>
</body>
	 <script type="text/javascript">   
        //初始化echarts实例
        var brokenLineBar = echarts.init(document.getElementById('brokenLineBarDiv'));     
        //异步加载的配置项和数据显示图表
         $.ajax({
             url: "<%=request.getContextPath()%>/echartsServlet",
             type: "POST",
             dataType: "json",
             success: function(data) {
            	 brokenLineBar.setOption({
                     title:{
                         text:'ECharts 折线、柱状'
                     },
                     tooltip:{},
                     legend:{
                    	 x:'right',
                    	 y:'top',
                         data:['折线','折线2','柱状']
                     },
                     xAxis:{
                         data:data.name
                     },
                     yAxis:{},
                     series:[
                         {
                           name:'折线',
                           type:'line',
                           data:data.data,
                           //系列中的数据标注内容    
                           markPoint: {    
                               data: [    
                                   {type: 'max', name: '最大值'},    
                                   {type: 'min', name: '最小值'}    
                               ]    
                           },    
                           //系列中的数据标线内容    
                           markLine: {    
                               data: [    
                                   {type: 'average', name: '平均值'}    
                               ]
                           } 
                         },{
                             name:'折线2',
                             type:'line',
                             data:data.data2
                         },{
                             name:'柱状',
                             type:'bar',
                             data:data.data2
                         }
                     ]
                 })
             }, 
			error: function(error) {
				alert("加载失败"+error);
	        }
		});
        brokenLineBar.on('click', function (params) {
        	if (params.componentType == 'markPoint') {
        		alert(params.name);
            } else if (params.componentType == 'markLine') {
        		alert(params.name);
            } else if (params.componentType == 'series') {
            	alert(params.name + ":" + params.data);
                /* if (params.seriesType == 'line') {
                   // 点击到了 graph 的 node（节点）上。
                   alert(params.name + ":" + params.data);
                } */
            }
  		});
		
       //初始化echarts实例
       var pie = echarts.init(document.getElementById('pieDiv'));     
       //加载数据函数
       function bindData(){
           //为了效果明显，我们做了延迟读取数据
           setTimeout(function(){  
		       //异步加载的配置项和数据显示图表
		        $.ajax({
		            url: "<%=request.getContextPath()%>/echartsPieServlet",
		            type: "POST",
		            dataType: "json",
		            success: function(data) {
		            	pie.setOption({
		                    title:{
		                        text:'ECharts 饼状'
		                    },
		                    tooltip:{},
		                    legend:{
		                        data:['饼状']
		                    },
		                    series:[{
		                        name:'饼状',
		                        type:'pie',
		                        radius:'60%', 
		                        center: ['50%', '60%'],
		                        data:function(){
		                            var res=[];
		                            var names = data.name;
		                            var datas = data.data; 
		                            var all = 0;
		                            for(var i=0;i<names.length;i++){
			                            res.push({
				                            value:datas[i],
				                            name:names[i]
			                            });
		                            };
		                            return res;
		                        }()
		                    }]
		                });
		                pie.hideLoading();
		            }, 
					error: function(error) {
						alert("加载失败"+error);
			        }
				})},2000)
       }
       pie.showLoading({
    	   text:"加载中... ..."
       });
       bindData();
       pie.on('click', function (params) {
	       if (params.componentType == 'series') {
	       		alert(params.name + ":" + params.data.value);
	       }
 	   });
    </script>
</html>