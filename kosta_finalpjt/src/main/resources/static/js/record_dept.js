
//출근기록 조회
const deptrecord = (num)=>{
  console.log(num);
	arrow_btn(num)
	$.ajax({
		url:"/auth/record/list",  //서버주소
		type:"get",   				//전송방식
		dataType:'json',			//응답데이터 형태
		data:{dept:dept,cnt:cnt},
		success:function(res){		//응답 정상일때
			deptTable_draw(res)
		},
		error:function(){			//응답 에러일때
			console.log('error');
		}
	});
}


let dept_table = $(".dept_record_list")[0];

const deptTable_draw = (arr)=>{	
	while(dept_table.rows.length > 0){
		dept_table.deleteRow(0);
	}
  
	for(let a of arr){
			const tr_row = dept_table.insertRow();

	        cell = tr_row.insertCell(0);
	        cell.textContent = a.name
	        cell++

	        cell = tr_row.insertCell(1);
	        cell.textContent = a.deptNum;
	        cell++
	        
	        cell = tr_row.insertCell(2);
	        cell.textContent = a.joblv;
	        cell++
	        
	        cell = tr_row.insertCell(3);
	        cell.textContent = a.totalRecords;
          cell++

          cell = tr_row.insertCell(4);
	        cell.textContent = a.lateCount;
	        cell++

          cell = tr_row.insertCell(5);
	        cell.textContent = a.workTime;
          cell++

          cell = tr_row.insertCell(6);
	        cell.textContent = a.overWork;
	}
}












// 부서별 월별 근무 평균 통계
function drawSarahChart() {
    // Create the data table for Sarah's pizza.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    data.addRows([
      ['Mushrooms', 1],
      ['Onions', 1],
      ['Olives', 2],
      ['Zucchini', 2],
      ['Pepperoni', 1]
    ]);

    // Set options for Sarah's pie chart.
    var options = {title:'How Much Pizza Sarah Ate Last Night',
                   width:400,
                   height:300};

    // Instantiate and draw the chart for Sarah's pizza.
    var chart = new google.visualization.PieChart(document.getElementById('Sarah_chart_div'));
    chart.draw(data, options);
  }

  // Callback that draws the pie chart for Anthony's pizza.
  function drawAnthonyChart() {

    // Create the data table for Anthony's pizza.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    data.addRows([
      ['Mushrooms', 2],
      ['Onions', 2],
      ['Olives', 2],
      ['Zucchini', 0],
      ['Pepperoni', 3]
    ]);

    // Set options for Anthony's pie chart.
    var options = {title:'How Much Pizza Anthony Ate Last Night',
                   width:400,
                   height:300};

    // Instantiate and draw the chart for Anthony's pizza.
    var chart = new google.visualization.BarChart(document.getElementById('Anthony_chart_div'));
    chart.draw(data, options);
  }


  function drawChart() {
    var chartDiv = document.getElementById('chart_div');

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Month');
    data.addColumn('number', "1차 확인 선");
    data.addColumn('number', "2차 확인 선");
    data.addColumn('number', "3차 확인 선");

    data.addRows([
      ["월", -.5, 5.7,3.2],
      ["2024.01", .4, 8.7,5.2],
      ["2024.02", .5, 12,7.8],
      ["2024.03", 2.9, 15.3,0],
      ["2024.04", 6.3, 18.6,0],
      ["2024.05", 9, 20.9,12.6],
      ["2024.06", 10.6, 19.8,9.5],
      ["2024.07", 10.3, 16.6,8.5],
      ["2024.08", 7.4, 13.3,6.5],
      ["2024.09", 4.4, 9.9,7.6],
      ["2024.10", 1.1, 6.6,6.4],
      ["2024.11", -.2, 4.5,0]
    ]);

    var materialOptions = {
      width: 900,
      height: 500,
      colors: ['#a4d4ff', '#dc3912', '#ff9900'],
    };

    function drawMaterialChart() {
      var materialChart = new google.charts.Line(chartDiv);
      materialChart.draw(data, materialOptions);
    }
    drawMaterialChart();

  }
