<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.request.contextPath }/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	var isEnd = false;
	var page = 0;
	var render = function(vo, where) {

		//
		// 현업에서는 이부분 template library 사용 , ex> ejs
		//
		var htmls = "<li id='gb-"+vo.no+"'>" + 
					"<table>" + 
					"<tr>" +
					"<td>" + vo.name + "</td>"+ 
					"<td>" + vo.reg_date + "</td>" +
					"<td><a href ='' data-id="+vo.no+">삭제</a></td>"+
				    "</tr>" + "<tr>" + "<td colspan=4>" + vo.content + "</td>"+
				    "</tr>" + "</table>" + "<br>" + "</li>";

		if (where == "prepend") {
			$("#list-guestbook").prepend(htmls);
		} else if (where == "append") {
			$("#list-guestbook").append(htmls);
		}
	}
	var fetchList = function() {
		if (isEnd == true) {
			return;
		}
		++page;
		$
				.ajax({
					url : "${pageContext.request.contextPath }/api/guestbook?a=ajax-list&p="
							+ page,
					type : "get",
					dataType : "json",
					success : function(response) {
						if (response.result != "success") {
							console.error(response.message);
							inEnd = true;
							return;
						}

						// redering
						$(response.data).each(function(index, vo) {
							var htmls =

							render(vo, "append");

							console.log(vo);
						});

						if (response.data.length < 5) { // 글 갯수 정해서 가져오기
							isEnd = true;
							$("#btn-fetch").prop("disabled", true);
						}
					},
					error : function(jqXHR, status, e) {
						console.log(status + ":" + e);
					}
				});
	}

	$(function() {
		var no = 0;
		//삭제버튼 클릭 이벤트 (live event)
		$(document).on("click", "#list-guestbook li a", function(event){
			
			no= $(this).data("id");
			
			event.preventDefault();
			console.log(no);
			
			dialog = $("#dialog").dialog({
				autoOpen : false,
				show : {
					effect : "blind",
					duration : 1000
				},
				hide : {
					effect : "fade",
					duration : 1000
				},
				buttons: {
			        "삭제": function() {
			
			        					$.ajax({
										url : "${pageContext.request.contextPath }/api/guestbook",
										type : "post",
										dataType : "json",
										data : "a=ajax-delete"+
												"&no="+no+
												"&pass="+$("#pass1").val(),
												

										success : function(response) {
											$("#wr").hide();
											if (response.result != "success") {
												return;
											}
											if(response.data==true){
												$("#gb-"+no).remove();
												dialog.dialog( "close" );
											}
											else{
												$("#wr").show();
												$("#pass1").val("").focus();
											}
										
										},
										error : function(jqXHR, status, e) {
											console.log(status + ":" + e);
										}
									});

			        },
			        "취소": function() {
			          $( this ).dialog( "close" );
			        }
			      }
			}).dialog( "open" );
			
	
		});

		$("#add-form")
				.submit(
						function(event) {

							event.preventDefault();

							
									$.ajax({
										url : "${pageContext.request.contextPath }/api/guestbook",
										type : "post",
										dataType : "json",
										data : "a=ajax-add&name="
												+ $("#name").val() + "&pass="
												+ $("#pass").val()
												+ "&content="
												+ $("textarea#content").val(),

										success : function(response) {
											if (response.result != "success") {
												console.error(response.message);
												inEnd = true;
												return;
											}

											// redering

											var htmls = render(response.data,
													"prepend");

										},
										error : function(jqXHR, status, e) {
											console.log(status + ":" + e);
										}
									});
						});
		$(window).scroll(function() {
			var $window = $(this);
			var scrollTop = $window.scrollTop();
			var windowHeight = $window.height();
			var documentHeight = $(document).height();

			//스크롤 바가 바닥까지 왔을때(20px 덜왔을때)
			if (scrollTop + windowHeight + 20 > documentHeight) {
				fetchList();
			}
		});

		//1번째 리스트 가져오기
		fetchList();
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form id="add-form" action="" method="post">
					<input type="hidden" name="a" value="add">
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name" id="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="pass" id="pass"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td coslspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul id="list-guestbook">
				</ul>
				<button id="btn-fetch" style="margin-top: 20px">가져오기</button>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
	<div id="dialog" title="삭제">
		<br>
		<p>삭제하시겠습니까</p>
		<br>
		<form method="post" action="${pageContext.request.contextPath }/guestbook">
					<input id="a" type="hidden" name="a" value="ajax-delete">
					<input id="no" type='hidden' name="no"><label id="wr" style="display:none">비밀번호가 틀렸습니다.</label>
					<label>비밀번호</label>
					<input id="pass1" type="password" name="password">
		</form>
		<p></p>
	</div>
</body>
</html>