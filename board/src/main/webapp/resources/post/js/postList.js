$(document).ready(function(){
	$(".postListTable tr").click(function(){
		var tr = $(this);
		var td = tr.children();
		var p_no = parseInt(td.eq(0).text());

		console.log("p_no : "+p_no + typeof(p_no));
		window.location.href="/postDetail/?p_no=" +p_no;
//		$(location).attr('href', '/postView/?p_no='+ p_no);
//		$.ajax({
//    		type : "GET",
//    		data :{ "p_no" : p_no},
//    		url : "/postClick",
//    		success : function(data){
//    			console.log(data);
//    		},
//    		error: function(e){
//    			if(e.status == 500){ // 세션이 null이고 ajax요청시 500에러를 받아 logInPage로 이동시킴
//    				window.location.href="/logInPage";
//    			}
//    		}
//    	}); //ajax END
		
	}); // click function END
	
}); // document ready END