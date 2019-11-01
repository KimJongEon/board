$(document).ready(function() {
	var user_id = $(".n_user_id").text(); //세션에 저장된 아이디 값
	var p_no = parseInt($(".p_no").text()); // 해당 글 번호
	
	replyList(); // 댓글 목록 불러오는 함수 호출
	attachList();// 첨부 파일 목록 불러오는 함수 호출
	
	// 첨부 파일 목록 불러오는 함수
	function attachList(){
		var data = {'p_no' : p_no};
		var jsonData = JSON.stringify(data);
		$.ajax({
			type : "POST",
			data : jsonData,
			dataType : "json",
			contentType : "application/json; charset=UTF-8",
			url : "/attachList",
			success : function(data){
				console.log(data);
				
				var html = "";
				
				if(data.length > 0){   
	                for(i=0; i<data.length; i++){
	                	var test = data[i].uuid + ","+ data[i].save_nm;
	                	var onclickData = "javascript:deleteAttach" + "('" + test + "')";
//	                	console.log(uuid); 
//	                	attach_no, ori_nm, p_no, save_nm, uuid
	                	html += "<div>";
	                	html += "<a href='/upLoad/"+data[i].save_nm+"' download='"+data[i].ori_nm+"' class='ori_nm'>"+data[i].ori_nm+"</a>";
	                    html += "<span class='save_nm' style='display : none;'> "+ data[i].save_nm +"</span>";
	                    html += "<span class='uuid' style='display : none;'> "+ data[i].uuid +"</span>";
	                    html += "<br/>";
	                    html += "</div>";
	                    
//	                    html += "<span class='r_no' style='display : none;'> "+ data[i].r_no +"</span>";
//	                    html += "<span class='r_user_id'>" +data[i].user_id+ "</span>";
//	                    html += "<span style='position: absolute; right: 14%;'>" +
//	                    		"<span class='r_dt' ></span>";
	                    
	                }
	                
	            } else { // 첨부파일이 없을 때
	                
	                html += "<div>";
	                html += "<div><table class='table'><h6><strong>등록된 첨부파일이 없습니다.</strong></h6>";
	                html += "</table></div>";
	                html += "</div>";
	                
	            }
	            
                html += "<hr/>";
	            $(".attachDiv").html(html);
			}
		}); //ajax END
	}; //attachList function END
	
	// 댓글 목록 
	function replyList(){
		var data = {'p_no' : p_no};
		var jsonData = JSON.stringify(data);
		$.ajax({
			type : "POST",
			data : jsonData,
			datatType : "json",
			contentType : "application/json; charset=UTF-8",
			url : "/replyList",
			success : function(data) {
				var html = "";
//				console.log(data);
				if(data.length > 0){   
	                for(i=0; i<data.length; i++){
	                	var sysdate = new Date(data[i].r_dt);
	                	sysdate = date_to_str(sysdate);
	                    html += "<div>";
	                    html += "<span class='r_no' style='display : none;'> "+ data[i].r_no +"</span>";
	                    html += "<span class='r_user_id'>" +data[i].user_id+ "</span>";
	                    html += "<span style='position: absolute; right: 14%;'>" +
	                    		"<span class='r_dt' >" + sysdate + "</span>";
//	                    if(user_id == data[i].user_id){
//	                    	html+= "<a href='#' onclick='delReply(); return false;'>테스트</a>";
//		                    html+="<input type='button' class='btn btn-dark r_delBtn' value='삭제' name=" + data[i].r_no + "/>";
//	                    }
	                    html +=	"</span>";
	                    html += "<br/>";
	                    html += "<span class='r_content'>" + data[i].r_content + " </span>";
	                    html += "<hr/>";
	                    
	                    html += "</div>";
	                    
//	                    console.log(html);
	                }
	                
	            } else {
	                
	                html += "<div>";
	                html += "<div><table class='table'><h6><strong>등록된 댓글이 없습니다.</strong></h6>";
	                html += "</table></div>";
	                html += "</div>";
	                
	            }
	            
	            $(".replyList").html(html);
				
			}
		});
	};
	
	
	
	
	// 댓글 등록 ajax
	$(".replyRegi").click(function() {
		var r_content = $("#replyTextArea").val();
		var data = {'user_id' : user_id, 'p_no' : p_no, 'r_content' : r_content};
		var jsonData = JSON.stringify(data);
//		console.log(jsonData);

		$.ajax({
			type : "POST",
			data : jsonData,
			dataType : 'json',
			contentType : "application/json; charset=UTF-8",
			url : "/replyRegi",
			success : function(result) {
//				console.log(result);
				if(result == 1){
					replyList();
	                $("#replyTextArea").val("");
				} else{
					alert("댓글 등록 실패");
					window.location.href="/";
				}
			}
		}); // ajax END
	}); // click function END
	
	
	//글 삭제
	$("#postDelBtn").click(function(){
		if (confirm("정말 삭제하시겠습니까??") == true){    //확인
			$.ajax({
				type : "GET",
				data : {p_no},
				dataType : "text",
				url : "/postDelBtn",
				success : function(data) {
					if(data == 'success'){
						alert("삭제가 완료 되었습니다.");
						window.location.href="/";
					}else{
						alert("글 삭제 실패");
					}
				}
			}); // ajax END
			
		}else{   //취소
		    
		}
		

	}); //click function END
	
	
	// 글 수정
	$("#postEditPage").click(function(){
//		window.location.href="/postDetail";
		window.location.href="/postEditPage/?p_no=" +p_no;
	}); //click function END
	
	
	//날짜 변환 함수
	function date_to_str(format)
	{
	    var year = format.getFullYear();
	    var month = format.getMonth() + 1;
	    if(month<10) month = '0' + month;
	    var date = format.getDate();
	    if(date<10) date = '0' + date;
	    var hour = format.getHours();
	    if(hour<10) hour = '0' + hour;
	    var min = format.getMinutes();
	    if(min<10) min = '0' + min;
	    var sec = format.getSeconds();
	    if(sec<10) sec = '0' + sec;

	    return year + "-" + month + "-" + date + " " + hour + ":" + min + ":" + sec;
	}
}); //document.ready END

////댓글 삭제
//function delReply(){
//	if(confirm("정말 삭제하시겠습니까?") == true){
//		
//	}else{
//		
//	}
//}
