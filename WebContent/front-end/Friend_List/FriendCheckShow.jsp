<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="memVOFriendCheck"
	items="${requestScope.friendCheckList}">
	<div class="col-sm-4 friendchecksList">
		<div class="card hovercard">
			<c:if test="${memVOFriendCheck.sex=='男'}" var="varName">
				<div class="man cardheader"></div>
			</c:if>
			<c:if test="${!varName}">
				<div class="women cardheader"></div>
			</c:if>
			<div class="avatar">
				<img alt="" src="<%=request.getContextPath()%>/getPic?mem_num=${memVOFriendCheck.mem_num}">
			</div>
			<div class="info">
				<div class="title">${memVOFriendCheck.mem_name}</div>
				<div class="desc">${memVOFriendCheck.sex}</div>
				<div class="desc">${memVOFriendCheck.email}</div>
			</div>
			<div class="bottom">
				<button type="submit" class="btn btn-info confirmFriend" name="action" value="CONFIRM">確認邀請</button>
				<button type="submit" class="btn btn-danger rejectFriend" name="action" value="REJECT">拒絕邀請</button>
			</div>
		</div>
		<input type="hidden" name="invd_mem_num" value="${memVOFriendCheck.mem_num}">
	</div>
</c:forEach>
