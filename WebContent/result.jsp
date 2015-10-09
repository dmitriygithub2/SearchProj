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
    <form method="get">
    <table style="width: 800px; margin: auto; text-align: center;">
        <tr>
            <td>
                <table style="width: 100%;">
                    <tr>
                        <td><span>Текст 1</span></td>
                        <td><input type="text" readonly="readonly" value="${text1}"></td>
                    </tr>
                    <tr>
                        <td><span>Текст 2</span></td>
                        <td><input type="text" readonly="readonly" value="${text2}"></td>
                    </tr>
                    <tr>
                        <td><span>Текст 3</span></td>
                        <td><input type="text" readonly="readonly" value="${text3}"></td>
                    </tr>
                    <tr>
                        <td><span>Текст 4</span></td>
                        <td><input type="text" readonly="readonly" value="${text4}"></td>
                    </tr>
                    <tr>
                        <td><span>Текст 5</span></td>
                        <td><input type="text" readonly="readonly" value="${text5}"></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Новый поиск">
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