<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>register validate</title>
<script type="text/javascript">
	function ex(){
		//window页面请求servlet
		window.location="action?method=downDoc";
	}
</script>
</head>
<body>
	<form action="action?method=downExcel" method="get" id="form">
		<center>
		    <input type="submit" value="下载EXCEL" />
		    <input type="button" value="下载WORD" onclick="ex()"/>
		</center>
	</form>
</body>
</html>