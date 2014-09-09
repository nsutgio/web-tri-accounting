<%@ tag %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <my:header/>
    <jsp:invoke fragment="head"/>
</head>
<body>
<div class="container">
    <my:nav />
    <hr/>
    <jsp:invoke fragment="body"/>
    <my:footer />
</div>
</body>
</html>