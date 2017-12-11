<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>糾茶趣</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/index_base.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/starability-css/starability-all.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/store_comment.css">
<script src="https://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/store_comment.js"></script>
</head>
<body>
	<jsp:include page="/front-end/member_top.jsp" flush="true" />
	
	<!-- =============================Body==================================== -->
	<div class="container">
		<div class="col-sm-10 col-sm-offset-1">
			<div class="page-header">
				<h2 class="outsi">我的評論</h2>
				<h3>
					<i id="date"></i>
				</h3>
			</div>

			<div class="timeline">

				<div class="line text-muted"></div>
				
				<jsp:include page="/front-end/Store_Comment/MyComment.jsp" flush="true" />
				
			</div>

		</div>
	</div>
	
	<img class="modal fade modal-sm" role="dialog" id="alert" src="<%=request.getContextPath()%>/img/loading.gif">
	<!-- =============================Body==================================== -->

	<jsp:include page="/front-end/member_foot.jsp" flush="true" />

</body>
</html>