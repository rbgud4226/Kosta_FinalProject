let cnt = 0;
window.onload=()=>{
    flagCheck();
}

// 출근 버튼
const workin=()=>{
	$.ajax({
		url:"/auth/record/in",  //서버주소
		type:"post",   				//전송방식
		dataType:'json',			//응답데이터 형태
		data:{Members:mem},
		success:function(res){		//응답 정상일때
            flag = res.flag;
            num = res.num;
            flagCheck();
            $("#com_end").attr("onclick","workout()");
		},
		error:function(){			//응답 에러일때
			console.log('error');
		}
	});
}

//퇴근 버튼 클릭
const workout=()=>{
	$.ajax({
		url:"/auth/record/out",  //서버주소
		type:"post",   				//전송방식
		dataType:'json',			//응답데이터 형태
		data:{Members:mem,memberid:num},
		success:function(res){		//응답 정상일때
			console.log(res);
		},
		error:function(){			//응답 에러일때
			console.log('error');
		}
	});
}

//시계 변경
let time = new Date(); 
// 날짜
let year = time.getFullYear();
let month = ('0' + (time.getMonth() + 1)).slice(-2);
let day = ('0' + time.getDate()).slice(-2);
let dateString = year + '.' + month  + '.' + day + "일 ";
// 시간
var hours = ('0' + time.getHours()).slice(-2); 
var minutes = ('0' + time.getMinutes()).slice(-2);

var timeString = hours + ':' + minutes ;
$("#record_time").html(dateString+timeString);
$(".month").html(year+"."+(month-1));
setInterval(()=>{
    time = new Date();
    var hours = ('0' + time.getHours()).slice(-2); 
    var minutes = ('0' + time.getMinutes()).slice(-2);

    var timeString = hours + ':' + minutes ;
    $("#record_time").html(dateString+timeString);
} ,60000);


// 출근 여부에 따라서 버튼,문구 변경
const flagCheck = ()=>{
    if(flag === 'true'){
        console.log("출근 완료")
        $("#record_sta").html("출근 완료")
        $("#com_start").removeClass("blue_btn").addClass('gray_btn');
        $("#com_start").removeAttr("onclick");
        $("#com_end").removeClass("gray_btn").addClass('blue_btn');
    }else{
        console.log("출근 전")
        $("#record_sta").html("출근 전")
        $("#com_end").removeAttr("onclick");
    }   
}

//출근기록 조회
const arrow_btn = (num)=>{
 	cnt = cnt + num;
	let cmonth = month;
	if(-3<cnt && cnt<0){
		$("#record_right").addClass("cursor").removeClass("arrow_off");
		$("#record_left").addClass("cursor").removeClass("arrow_off");
	}
	else if(cnt<=-3){
		cnt = -3;
		$("#record_left").addClass("arrow_off").removeClass("cursor")
	}else if (cnt>=0){
		cnt = 0;
		$("#record_right").addClass("arrow_off").removeClass("cursor")
	}

	cmonth = month - 1 + cnt;
	if(cmonth<1){
		cmonth = 12;
		year--;
	}else if(cmonth>12){
		cmonth = 1;
		year++;
	}

	$(".month").html(year+"."+cmonth);
	myrecord();
}

let table = $(".record_list")[0];

const myrecord=()=>{
	$.ajax({
		url:"/auth/record/getmonth",  //서버주소
		type:"get",   				//전송방식
		dataType:'json',			//응답데이터 형태
		data:{Members:mem,count:cnt},
		success:function(res){		//응답 정상일때
			table_draw(res.list)
		},
		error:function(){			//응답 에러일때
			console.log('error');
		}
	});
}

//테이블 그리기
const table_draw = (arr)=>{	
	while(table.rows.length > 0){
		table.deleteRow(0);
	}
	for(let a of arr){
			const tr_row = table.insertRow();

	        cell = tr_row.insertCell(0);
	        cell.textContent = a.day
	        cell++

	        cell = tr_row.insertCell(1);
	        cell.textContent = a.dayOfWeek;
	        cell++
	        
	        cell = tr_row.insertCell(2);
	        cell.textContent = a.workHours;
	        cell++
	        
	        cell = tr_row.insertCell(3);
	        cell.textContent = a.state;
	}
}