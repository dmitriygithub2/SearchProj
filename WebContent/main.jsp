<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <form method="post" accept-charset="UTF-8">
    <table style="width: 800px; margin: auto; text-align: center;">
        <tr>
            <td>
                <table style="width: 100%;">
                    <tr>
                        <td><span>Текст 1</span></td>
                        <td><input type="text" name="text1" value='<c:out value="${text1}"/>'></td>
                    </tr>
                    <c:if test="${not empty error1}">
                        <tr><td colspan="2" style="color:red;">Текст 1 не прошел валидацию</td></tr>
                    </c:if>
                    <tr>
                        <td><span>Текст 2</span></td>
                        <td><input type="text" name="text2"  value="${text2}"></td>
                    </tr>
                    <c:if test="${not empty error2}">
                        <tr><td colspan="2" style="color:red;">Текст 2 не прошел валидацию</td></tr>
                    </c:if>
                    <tr>
                        <td><span>Текст 3</span></td>
                        <td><input type="text" name="text3"  value="${text3}"></td>
                    </tr>
                    <c:if test="${not empty error3}">
                        <tr><td colspan="2" style="color:red;">Текст 3 не прошел валидацию</td></tr>
                    </c:if>
                    <tr>
                        <td><span>Текст 4</span></td>
                        <td><input type="text" name="text4"  value="${text4}"></td>
                    </tr>
                    <c:if test="${not empty error4}">
                        <tr><td colspan="2" style="color:red;">Текст 4 не прошел валидацию</td></tr>
                    </c:if>
                    <tr>
                        <td><span>Текст 5</span></td>
                        <td><input type="text" name="text5"  value="${text5}"></td>
                    </tr>
                    <c:if test="${not empty error5}">
                        <tr><td colspan="2" style="color:red;">Текст 5 не прошел валидацию</td></tr>
                    </c:if>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Поиск пересечений">
            </td>
        </tr>
        <tr><td>
        <c:if test="${not empty result}">
            <table style="width: 100%; border-collapse: collapse;">
                <tr>
                    <th style="border: 1px solid black;">Слово</th>
                    <th style="border: 1px solid black;">Количество текстов</th>
                    <th style="border: 1px solid black;">Номера текстов</th>
                </tr>
                <c:forEach items="${result}" var="row">
                    <tr>
                        <td style="border: 1px solid black;">${row.word}</td>
                        <td style="border: 1px solid black;">${row.count}</td>
                            <td style="border: 1px solid black;">${row.where}</td>
                        </tr>
                </c:forEach>
            </table>
        </c:if>
        </td></tr>
    </table>
    </form>
</body>
</html>