const list_search = ()=>{
    let list_input = $(".list_input").val();
    let type = $(".select_box").val();
    
    $.ajax({
		url:"/member/getdeptby",  //서버주소
		type:"get",   				//전송방식
		dataType:'json',			//응답데이터 형태
		data:{val:list_input,type:type},
		success:function(res){		//응답 정상일때
            console.log(res)
		},
		error:function(){			//응답 에러일때
			console.log('error');
		}
	});
}