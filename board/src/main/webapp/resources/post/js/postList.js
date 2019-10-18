$(document).ready(function(){
	$(".postListTable tr").click(function(){
		var tr = $(this);
		var td = tr.children();
		var p_no = parseInt(td.eq(0).text());

		console.log("p_no : "+p_no + typeof(p_no));
//		window.location.href="/postDetail";
		window.location.href="/postDetail/?p_no=" +p_no;
		
	}); // click function END
	
}); // document ready END