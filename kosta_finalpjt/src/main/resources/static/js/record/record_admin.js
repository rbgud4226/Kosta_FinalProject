// 사내 전체 추가 근무 통계
function drawOverChart() {
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
    var options = { width:600,
                   height:300};

    // Instantiate and draw the chart for Anthony's pizza.
    var chart = new google.visualization.BarChart(document.getElementById('over_chart_div'));
    chart.draw(data, options);
  }


console.log(chartObj)
//부서별 근무 시간 월별 비교 통계
function drawChart() {
    var chartDiv = document.getElementById('chart_div');

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Month');
    for(let dept in chartObj){
      console.log(dept);
      data.addColumn("number",dept)
    }
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
      width: 600,
      height: 500,
      colors: ['#a4d4ff', '#dc3912', '#ff9900'],
    };

    function drawMaterialChart() {
      var materialChart = new google.charts.Line(chartDiv);
      materialChart.draw(data, materialOptions);
    }
    drawMaterialChart();

  }
