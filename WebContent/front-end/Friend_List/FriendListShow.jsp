<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="loginInfo" scope="page" class="login.test.LoginInfo" />

<c:forEach var="memVOFriends" items="${requestScope.friendList}"
	varStatus="m">
	<div class="col-sm-4 friendList">
		<div class="card hovercard">
			<c:if test="${memVOFriends.sex=='男'}" var="varName">
				<div class="man cardheader"></div>
			</c:if>
			<c:if test="${!varName}">
				<div class="women cardheader"></div>
			</c:if>
			<div class="avatar">
				<img class="memPic" alt="" src="<%=request.getContextPath()%>/getPic?mem_num=${memVOFriends.mem_num}">
			</div>
			<div class="info">
				<div class="title">${memVOFriends.mem_name}</div>
				<div class="desc">${memVOFriends.sex}</div>
				<div class="desc">${memVOFriends.email}</div>
			</div>
			<div class="bottom"></div>
			<div class="bottom">
				<button type="submit" class="btn btn-danger deleteFriend" name="action" value="DELETE">刪除好友</button>
			</div>
		</div>
		<input type="hidden" id="${memVOFriends.mem_num}" name="invd_mem_num" value="${memVOFriends.mem_num}">
	</div>
</c:forEach>
