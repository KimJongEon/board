var user_id = $(".n_user_id").text(); //세션에 저장된 아이디 값
var p_no = parseInt($("#p_no").val()); // 해당 글 번호
var objDragAndDrop = $(".dragAndDropDiv");
var rowCount=0;

console.log(p_no);
console.log(user_id);

//첨부 파일 목록 불러오는 함수
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
//	            	console.log(uuid);
//	            	attach_no, ori_nm, p_no, save_nm, uuid
	            	html += "<div>";
	            	html += "<a href='/upLoad/"+data[i].save_nm+"' download='"+data[i].ori_nm+"' class='ori_nm'>"+data[i].ori_nm+"</a>";
	                html += "<span class='save_nm' style='display : none;'> "+ data[i].save_nm +"</span>";
	                html += "<span class='uuid' style='display : none;'> "+ data[i].uuid +"</span>";
	                html += '<a href="javascript:void(0);" onclick=\"deleteAttach(' + '\''+ data[i].uuid + '\'' + ',' + '\'' + data[i].save_nm + '\'' + ')\" class="btn btn-dark" >X</a>';
	                html += "<br/>";
	                html += "</div>";  
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

	
	//첨부파일 삭제하는 함수
	function deleteAttach(uuid, save_nm){
		console.log(uuid);
		if(confirm("정말 삭제하시겠습니까?") == true){
			var data = {'uuid' : uuid, "save_nm" : save_nm};
			var jsonData = JSON.stringify(data);
			$.ajax({
				type : "POST",
				data : jsonData,
				dataType : "json",
				contentType : "application/json; charset=UTF-8",
				url : "/delAttach",
				success : function(data){
					console.log(data);
					attachList();// 첨부 파일 목록 불러오는 함수 호출
				}
			}); //ajax END
		}else {
			return false;
		}
		
	}; // deleteAttach function END
	
$(document).ready(function() {
	attachList();// 첨부 파일 목록 불러오는 함수 호출
	
	//파일 첨부 ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
    $(document).on("dragenter",".dragAndDropDiv",function(e){
        e.stopPropagation();
        e.preventDefault();
        $(this).css('border', '2px solid #0B85A1');
    });
    $(document).on("dragover",".dragAndDropDiv",function(e){
        e.stopPropagation();
        e.preventDefault();
    });
    $(document).on("drop",".dragAndDropDiv",function(e){
         
        $(this).css('border', '2px dotted #0B85A1');
        e.preventDefault();
        var files = e.originalEvent.dataTransfer.files;
        console.log("테스트@@@@@@@@@@@"+ files);
        handleFileUpload(files,objDragAndDrop);
        
//        $("input[type='file']").prop("files", files);
//        $(this).prop("files", files);
//        var test = 'multipleInput'+rowCount;
//        $(".multipleInput"+rowCount).prop("files", files);
        $("#input"+rowCount).prop("files", files);
    });
     
    $(document).on('dragenter', function (e){
        e.stopPropagation();
        e.preventDefault();
    });
    $(document).on('dragover', function (e){
      e.stopPropagation();
      e.preventDefault();
      objDragAndDrop.css('border', '2px dotted #0B85A1');
    });
    $(document).on('drop', function (e){
        e.stopPropagation();
        e.preventDefault();
    });
    
    function handleFileUpload(files,obj)
    {
       for (var i = 0; i < files.length; i++) 
       {
            var fd = new FormData();
            fd.append('file', files[i]);
            var status = new createStatusbar(obj); //Using this we can set progress.
            status.setFileNameSize(files[i].name,files[i].size);
            status.setProgress(100);
            
            sendFileToServer(fd,status);
//            $("input[type='file']").prop("files", files);
//            console.log($('.multipleInput'+rowCount));
       }

    }
     
    
    function createStatusbar(obj){
             
        rowCount++;
//        var row="odd";
//        if(rowCount %2 ==0) row ="even";
//    	this.statusbar = $("<div class='statusbar "+row+"'></div>");
    	this.statusbar = $("<div class='statusbar'></div>");
    	
//        this.filename = $("<div class='filename'></div>").appendTo(this.statusbar);
        
    	$("<input multiple='multiple' type='file' name='file' id='input"+rowCount+"' class='multipleInput'>").appendTo(this.statusbar);
    	
    	this.size = $("<div class='filesize'></div>").appendTo(this.statusbar);
        
        this.progressBar = $("<div class='progressBar'><div></div></div>").appendTo(this.statusbar);
        
        this.abort = $("<div class='abort'>삭제</div>").appendTo(this.statusbar);
//        $("<div class='abort'>삭제</div>").appendTo(this.statusbar);
        
        obj.after(this.statusbar);
      
        this.setFileNameSize = function(name,size){
            var sizeStr="";
            var sizeKB = size/1024;
            if(parseInt(sizeKB) > 1024){
                var sizeMB = sizeKB/1024;
                sizeStr = sizeMB.toFixed(2)+" MB";
            }else{
                sizeStr = sizeKB.toFixed(2)+" KB";
            }
      
//            this.filename.html(name);
            
            this.size.html(sizeStr);
        }
         
        this.setProgress = function(progress){       
            var progressBarWidth =progress*this.progressBar.width()/ 100;  
            this.progressBar.find('div').animate({ width: progressBarWidth }, 10).html(progress + "% ");

        }
         
        this.setAbort = function(jqxhr){
            var sb = this.statusbar;
            this.abort.click(function() //중지 클릭 부분
            {
//            	console.log(jqxhr);
            	
            	var test = $(this).parent();
            
            	test.remove();
            	
//                jqxhr.abort();
//                sb.hide();
            });
        }
    }
     
    function sendFileToServer(formData,status) {
        var uploadURL = "/fileUpload/post"; //Upload URL
        var extraData ={}; //Extra Data.
        var jqXHR=$.ajax({
                xhr: function() {
                var xhrobj = $.ajaxSettings.xhr();
                if (xhrobj.upload) {
                        xhrobj.upload.addEventListener('progress', function(event) {
                            var percent = 0;
                            var position = event.loaded || event.position;
                            var total = event.total;
                            if (event.lengthComputable) {
                                percent = Math.ceil(position / total * 100);
                            }
                            //Set progress
                            status.setProgress(percent);
                        }, false);
                    }
                return xhrobj;
            },
            url: uploadURL,
            type: "POST",
            contentType:false,
            processData: false,
            cache: false,
            data: formData,
            success: function(data){
                status.setProgress(100);
      
                //$("#status1").append("File upload Done<br>");           
            }
        }); 
      
        status.setAbort(jqXHR);
    }
//파일 첨부 끝 ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲
}); //document.ready END
