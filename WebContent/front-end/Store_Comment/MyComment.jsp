<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${myCommentList.size() > 0}">
	<c:forEach var="myComment" items="${myCommentList}">
		<div>
			<fmt:formatDate var="formattedDate" value="${myComment.get(0).com_time}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />
			<div class="separator text-muted">
				<time>${formattedDate}</time>
			</div>

			<article class="panel panel-primary">

				<div class="panel-heading icon">
					<i class="glyphicon glyphicon-edit"></i>
				</div>

				<div class="panel-heading">
					<h2 class="panel-title">
						<input class="commentTitle" type="text" name="com_title" value="${myComment.get(0).com_title}" maxlength="15" size="15" disabled> 
						
						<span class="starability-growRotate">
					      <input type="radio" id="rate1${myComment.get(0).com_num}" class="stars" name="stars${myComment.get(0).com_num}" value="1" ${6 - myComment.get(0).stars == 1 ? 'checked':''} disabled/>
					      <label for="rate1${myComment.get(0).com_num}"></label>
				
					      <input type="radio" id="rate2${myComment.get(0).com_num}" class="stars" name="stars${myComment.get(0).com_num}" value="2" ${6 - myComment.get(0).stars == 2 ? 'checked':''} disabled/>
					      <label for="rate2${myComment.get(0).com_num}"></label>
				
					      <input type="radio" id="rate3${myComment.get(0).com_num}" class="stars" name="stars${myComment.get(0).com_num}" value="3" ${6 - myComment.get(0).stars == 3 ? 'checked':''} disabled/>
					      <label for="rate3${myComment.get(0).com_num}"></label>
				
					      <input type="radio" id="rate4${myComment.get(0).com_num}" class="stars" name="stars${myComment.get(0).com_num}" value="4" ${6 - myComment.get(0).stars == 4 ? 'checked':''} disabled/>
					      <label for="rate4${myComment.get(0).com_num}"></label>
				
					      <input type="radio" id="rate5${myComment.get(0).com_num}" class="stars" name="stars${myComment.get(0).com_num}" value="5" ${6 - myComment.get(0).stars == 5 ? 'checked':''} disabled/>
					      <label for="rate5${myComment.get(0).com_num}"></label>
					    </span>
						
						<span class="pull-right"> 
							<button type="button" class="btn btn-info btn-sm glyphicon glyphicon-pencil"></button>
							<button type="button" class="btn btn-success btn-sm glyphicon glyphicon-ok"></button>
							<button type="button" class="btn btn-danger btn-sm glyphicon glyphicon-trash"></button>
						</span>
					</h2>
				</div>

				<div class="panel-body">
					<div class="col-xs-12 col-sm-4">
						<img class="storePic"
							src="<%=request.getContextPath()%>/getPic?sto_num=${myComment.get(1).sto_num}">
					</div>

					<div class="col-xs-12 col-sm-8">

						<h4>${myComment.get(1).sto_name}</h4>

						<textarea class="text" name="comment" rows="7" cols="62" disabled>${myComment.get(0).commentt}</textarea>

					</div>
				</div>

			</article>

			<br> <br> <input type="hidden" name="com_num" value="${myComment.get(0).com_num}">
		
		</div>
	</c:forEach>
</c:if>