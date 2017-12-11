<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="memberProfileService" scope="page" class="com.member_profile.model.MemberProfileService" />

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>揪茶趣</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/index_base.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/friend_list.css">
<script src="https://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/friend_list.js"></script>
</head>
<body>

	<jsp:include page="/front-end/member_top.jsp" />
	
	<!-- =============================Body==================================== -->
	<div class="container body">
		<div class="col-xs-12 col-sm-12">
			<section>
				<div class="bs-calltoaction bs-calltoaction-default">
					<h2 class="outsi">我的好友</h2>
					<div class="row">
						<div class="board">
							<div class="board-inner">
								
								<img src="<%=request.getContextPath()%>/img/talkNew.png" style="border:none" id="addFriendRequest">
								
								<ul class="nav nav-tabs">
									<li class="active">
										<a href="#friends" data-toggle="tab" title="好友專區" style="border:none"> 
											<span class="round-tabs one"> 
												<i class="glyphicon glyphicon-user"></i>
											</span>
										</a>
									</li>
									<li class="">
										<a href="#friendchecks" data-toggle="tab" title="待確認" id="friendcheckPag" style="border:none"> 
											<span class="round-tabs two"> 
												<i class="glyphicon glyphicon-question-sign"></i>
											</span>
										</a>
									</li>
									<li class="">
										<a href="#searchmember" data-toggle="tab" title="會員搜尋" style="border:none">
										 <span class="round-tabs three"> 
										 	<i class="glyphicon glyphicon-search"></i>
										</span>
									</a></li>
								</ul>
							</div>

							<div class="tab-content">
							
								<div class="tab-pane fade in active" id="friends">
									<jsp:include page="/front-end/Friend_List/FriendListShow.jsp" flush="true" />
								</div>

								<div class="tab-pane fade" id="friendchecks">
									<jsp:include page="/front-end/Friend_List/FriendCheckShow.jsp" flush="true" />
								</div>

								<div class="tab-pane fade" id="searchmember">
										<div class="input-group searchbar" style="width: 40%">
											<input type="text" class="form-control" placeholder="請輸入會員名稱" name="searchName"> 
											<span class="input-group-btn">
												<button id="searchMember" class="btn" type="submit" name="action" value="SEARCHMEMBER">
													<span class=" glyphicon glyphicon-search"></span>
												</button>
											</span>
										</div>
										<span id="searchContent">
											<jsp:include page="/front-end/Friend_List/MemberList.jsp" flush="true" />
										</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>

		</div>
	</div>
	
	<!-- =============================Body==================================== -->

	<jsp:include page="/front-end/member_foot.jsp" />

</body>
</html>