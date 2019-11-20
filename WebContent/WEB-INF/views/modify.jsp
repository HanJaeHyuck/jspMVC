<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="./layout/header.jsp"></jsp:include>
	<h2>글 수정</h2>
	<form action="/board/modify" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${data.id}" readonly>
		<div class="form-group">
			<label for="title">제목</label>
			<input type="text" name="title" id="title" value="${data.title}">
		</div>
		<div class="form-group">
			<label for="content">글내용</label>
			<textarea rows="10" cols="30" name="content" id="content">${data.content}</textarea>
		</div>
		<div class="form-group">
			<label for="file">파일첨부</label>
			<input type="file" name="file" id="file">
		</div>
		<input type="submit" value="글 수정">
	
	</form>
</body>
</html>