$(document).ready(function(){
	var idCheck = false;
	var pwCheck = false;
	$("input[name=user_id]").keyup(function(event){ 

		if (!(event.keyCode >=37 && event.keyCode<=40)) {
			var inputVal = $(this).val();

		    $(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
		}

	}); // user_id keyup END

	$("input[name=ph_no]").keyup(function(event){ 

		if (!(event.keyCode >=37 && event.keyCode<=40)) {
			var inputVal = $(this).val();

		    $(this).val(inputVal.replace(/[^0-9]/gi,''));
		}

	}); // ph_no keyup END
	
	$(".password").keyup(function(event){ 
//		console.log("찾아지나요");
		if (!(event.keyCode >=37 && event.keyCode<=40)) {
			var inputVal = $(this).val();

		    $(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
		}

	}); // password keyup END
	
// 비밀번호 확인
//	$(function(){
        $("input").blur(function(){
        	
            var pwd1=$("#password_01").val();
            var pwd2=$("#password_02").val();
            
            if(pwd1 != "" || pwd2 != ""){
                if(pwd1 == pwd2){
                    $("#pw-success").show();
                    $("#pw-danger").hide();
                    pwCheck = true;
                    
                    if(idCheck == true && pwCheck == true){
                    	$("#signUpBtn").attr("disabled", false);
                    }else{
                    	$("#signUpBtn").attr("disabled", true);
                    }
                    console.log("pwCheck 확인하기 : " + pwCheck);
//                    $("#signUpBtn").attr("disabled", false);
                    
                }else{
                    $("#pw-success").hide();
                    $("#pw-danger").show();
                    pwCheck = false;
                    
                    if(idCheck == true && pwCheck == true){
                    	$("#signUpBtn").attr("disabled", false);
                    }else{
                    	$("#signUpBtn").attr("disabled", true);
                    }
                    console.log("pwCheck 확인하기 : " + pwCheck);
//                    $("#signUpBtn").attr("disabled", true);
                    
                }    
            }
        }); // input keyup END
//    });
	
        //아이디 중복 체크
        $("#user_id").blur(function(){
        	var user_id = $('#user_id').val();
        	console.log(user_id);
        	
        	$.ajax({
        		type : "GET",
        		data :{ "user_id" : user_id},
        		url : "/idCheck",
        		success : function(data){
        			console.log(data);
        			
        			if(user_id == ""){
        				$("#id-write").show();
        				$("#id-success").hide();
        				$("#id-danger").hide();
        				idCheck = false;
        				
        				if(idCheck == true && pwCheck == true){
                        	$("#signUpBtn").attr("disabled", false);
                        }else{
                        	$("#signUpBtn").attr("disabled", true);
                        }
        				
        			}else if(data == "fail"){
        				$("#id-success").hide();
                        $("#id-danger").show();
                        $("#id-write").hide();
                        idCheck = false;
                        
                        if(idCheck == true && pwCheck == true){
                        	$("#signUpBtn").attr("disabled", false);
                        }else{
                        	$("#signUpBtn").attr("disabled", true);
                        }
                        
        			}else if(data == "success"){
        				$("#id-success").show();
                        $("#id-danger").hide();
                        $("#id-write").hide();
                        idCheck = true;
                        
                        if(idCheck == true && pwCheck == true){
                        	$("#signUpBtn").attr("disabled", false);
                        }else{
                        	$("#signUpBtn").attr("disabled", true);
                        }
                        console.log(idCheck);
        			}
        			
        			
        			
        		}
        	}); //ajax END
        }); //user_id keyup END
        
}); // document ready END