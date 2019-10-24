$(document).ready(function(){
    $(".fileDrop").on("dragenter dragover", function(event){
        event.preventDefault(); // 기본효과를 막음
    });
    // event : jQuery의 이벤트
    // originalEvent : javascript의 이벤트
    $(".fileDrop").on("drop", function(event){
        event.preventDefault(); // 기본효과를 막음
        // 드래그된 파일의 정보
        var files = event.originalEvent.dataTransfer.files;
        // 첫번째 파일
        var file = files[0];
        // 콘솔에서 파일정보 확인
        console.log(file);

        // ajax로 전달할 폼 객체
        var formData = new FormData();
        // 폼 객체에 파일추가, append("변수명", 값)
        formData.append("file", file);
        
        console.log(formData.get("file"));

        
//        $.ajax({
//            type: "post",
//            url: "/upload/uploadAjax",
//            data: formData,
//            // processData: true=&gt; get방식, false =&gt; post방식
//            dataType: "text",
//            // contentType: true =&gt; application/x-www-form-urlencoded, 
//            //                false =&gt; multipart/form-data
//            processData: false,
//            contentType: false,
//            success: function(data){
//                alert(data);
//                
//                var str = "";
//                // 이미지 파일이면 썸네일 이미지 출력
//                if(checkImageType(data)){ 
//                    str = "<div><a href='${path}/upload/displayFile?fileName="+getImageLink(data)+"'>";
//                    str += "<img src='${path}/upload/displayFile?fileName="+data+"'></a>";
//                // 일반파일이면 다운로드링크
//                } else { 
//                    str = "<div><a href='${path}/upload/displayFile?fileName="+data+"'>"+getOriginalName(data)+"</a>";
//                }
//                // 삭제 버튼
//                str += "<span data-src="+data+">[삭제]</span></div>";
//                $(".uploadedList").append(str);
//            }
//        }); //ajax END
    }); // drop function END
    
        
//    // 이미지파일 형식을 체크하기 위해
//    function checkImageType(fileName) {
//        // i : ignore case(대소문자 무관)
//        var pattern = /jpg|gif|png|jpeg/i; // 정규표현식
//        return fileName.match(pattern); // 규칙이 맞으면 true
//    }
    
    
});; //document ready END