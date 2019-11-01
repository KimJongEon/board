$(document).ready(function(){
//	$('.abort').click(function(){
//		console.log('hi');
//	  	var test = $(this).parent();
//	    
//		test.remove();
//	});

var objDragAndDrop = $(".dragAndDropDiv");
var rowCount=0;                

// 글 등록 클릭시 제목, 내용 빈칸체크
$("#btnSave").click(function(){
	if($("#title").val().length == 0){
		alert("제목을 입력해주세요"); //알림창 띄우기
		$("#title").focus(); //제목창으로 포커스
		return false; //submit을 막기위해 리턴 false 해줌
	}
	if($("#content").val().length == 0){
		alert("내용을 입력해주세요");
		$("#content").focus();
		return false;
	}
	
	
}); //click function END

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
                    
//                    $("input[type='file']").prop("files", files);
//                    $(this).prop("files", files);
//                    var test = 'multipleInput'+rowCount;
//                    $(".multipleInput"+rowCount).prop("files", files);
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
//                        $("input[type='file']").prop("files", files);
//                        console.log($('.multipleInput'+rowCount));
                   }

                }
                 
                
                function createStatusbar(obj){
                         
                    rowCount++;
//                    var row="odd";
//                    if(rowCount %2 ==0) row ="even";
//                	this.statusbar = $("<div class='statusbar "+row+"'></div>");
                	this.statusbar = $("<div class='statusbar'></div>");
                	
//                    this.filename = $("<div class='filename'></div>").appendTo(this.statusbar);
                    
                	$("<input multiple='multiple' type='file' name='file' id='input"+rowCount+"' class='multipleInput'>").appendTo(this.statusbar);
                	
                	this.size = $("<div class='filesize'></div>").appendTo(this.statusbar);
                    
                    this.progressBar = $("<div class='progressBar'><div></div></div>").appendTo(this.statusbar);
                    
                    this.abort = $("<div class='abort'>삭제</div>").appendTo(this.statusbar);
//                    $("<div class='abort'>삭제</div>").appendTo(this.statusbar);
                    
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
                  
//                        this.filename.html(name);
                        
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
//                        	console.log(jqxhr);
                        	
                        	var test = $(this).parent();
                        
                        	test.remove();
                        	
//                            jqxhr.abort();
//                            sb.hide();
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
}); // document ready END

