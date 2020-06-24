<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

	<title>电影信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
<link rel="stylesheet" type="text/css" href="styles.css">
-->
	<script>
		function deleteFilm() {
			console.log("1111");
			//获取表单，提交
			document.getElementById("deleteForm").submit();
		}
	</script>
</head>

<body>
    <form action="${ctx}/deleteFilms" id="deleteForm">
        <input type="button" onclick="deleteFilm()" value="批量删除">
        <input type="hidden" name="typeid" value="${typeid}"/>
        <table border="1" bordercolor="#66CCCC" align="center" width="60%">
            <tr align="center">
                <td></td>
                <td>
                    电影名称
                </td>
                <td>
                    类型
                </td>
                <td>
                    主演
                </td>
                <td>
                    导演
                </td>
                <td>
                    票价
                </td>
                <td>操作</td>
            </tr>
            <c:choose>
                <c:when test="${empty requestScope.result}">
                    <tr>
                        <td colspan="5">
                            <c:out value="未找到符合条件的电影" />
                        </td>
                    </tr>
                </c:when>
                <c:when test="${!empty requestScope.result}">
                    <c:forEach items="${requestScope.result}" var="film">
                        <tr>
                            <td><input type="checkbox" name="filmIds" value="${film.filmid}"/> </td>
                            <td>
								${film.filmname}
                            </td>
                            <td>
								${film.filmtype.typename}
                            </td>
                            <td>
								${film.actor}
						    </td>
						    <td>
								${film.director}
						    </td>
						    <td>
								${film.ticketprice}
						    </td>
                            <td><a href="${ctx}/queryDetails/${film.filmid}/${film.filmname}">详情</a> </td>
					    </tr>
				    </c:forEach>
			    </c:when>
		    </c:choose>
	    </table>

        <p align="center">
            总记录数：${info.total}&nbsp;总页数：${info.pages}&nbsp;当前页数：${pageNum}&nbsp;
            <c:choose>
                <c:when test="${info.pageNum == 1}">
                    第一页  上一页
                    <a href="${ctx}/findFilms?pageNum=${info.nextPage}&typeid=${typeid}">下一页</a>
                    <a href="${ctx}/findFilms?pageNum=${info.pages}&typeid=${typeid}">尾页</a>
                </c:when>

                <c:when test="${info.pageNum == info.pages}">
                    <a href="${ctx}/findFilms?pageNum=1&typeid=${typeid}">第一页</a>
                    <a href="${ctx}/findFilms?pageNum=${info.prePage}&typeid=${typeid}">上一页</a>
                    下一页 尾页
                </c:when>
                <c:otherwise>
                    <a href="${ctx}/findFilms?pageNum=1&typeid=${typeid}">第一页</a>
                    <a href="${ctx}/findFilms?pageNum=${info.prePage}&typeid=${typeid}">上一页</a>
                    <a href="${ctx}/findFilms?pageNum=${info.nextPage}&typeid=${typeid}">下一页</a>
                    <a href="${ctx}/findFilms?pageNum=${info.pages}&typeid=${typeid}">尾页</a>
                </c:otherwise>
            </c:choose>
            <a href="toCinema" style="text-decoration: none">返回主页</a>
        </p>
    </form>
</body>
</html>
