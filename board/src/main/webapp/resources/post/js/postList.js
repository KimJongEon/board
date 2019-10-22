$(document).ready(function() {
	var path = window.location.pathname;

	$(".postListTable tr").click(function() {
		var tr = $(this);
		var td = tr.children();
		var p_no = parseInt(td.eq(0).text());

		console.log("p_no : " + p_no + typeof (p_no));
		// window.location.href="/postDetail";
		window.location.href = "/postDetail/?p_no=" + p_no;

	}); // click function END

}); // document ready END

// 페이징 처리▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
// 이전 버튼 이벤트
function fn_prev(page, range, rangeSize) {
	var path = window.location.pathname;
	var page = ((range - 2) * rangeSize) + 1;
	var range = range - 1;

	var url = path;

	url = url + "?page=" + page;
	url = url + "&range=" + range;

	location.href = url;
}

// 페이지 번호 클릭
function fn_pagination(page, range, rangeSize) {
	var path = window.location.pathname;
	var url = path;

	url = url + "?page=" + page;
	url = url + "&range=" + range;

	location.href = url;
}

// 다음 버튼 이벤트
function fn_next(page, range, rangeSize) {
	var path = window.location.pathname;
	var page = parseInt((range * rangeSize)) + 1;
	var range = parseInt(range) + 1;

	var url = path;

	url = url + "?page=" + page;
	url = url + "&range=" + range;

	location.href = url;
}