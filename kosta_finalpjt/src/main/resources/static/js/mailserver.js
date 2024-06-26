$(document).ready(function (){

    function maillist(){
        $.ajax({
            url:"/mail/list",
            dataType: 'json',
            method: 'get',
            data:{

            }
        }).done(function (list){

        })
    }
});