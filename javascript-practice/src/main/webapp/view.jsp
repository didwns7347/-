<%@page import="makejson.GuestBookJson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
 	GuestBookJson gbj=new GuestBookJson();
 	gbj.findAll(10);
%>
<!DOCTYPE html>
<html>
<body>

	<h2>Make a table based on JSON data.</h2>

	<p id="demo"></p>

	<script>
		xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				myObj = JSON.parse(this.responseText);
				var txt = "<table border='1'><tr><th>작성시간</th><th>글번호</th><th>재목</th><th>내용</th></tr>";
				for (x in myObj) {
					
					for(y in myObj[x]){
						txt+="<tr>"
						for(z in myObj[x][y]){
							txt += "<td>" + myObj[x][y][z] + "</td>";
						}
						txt+="</tr>"
						
					}
				}
				txt += "</table>"
				document.getElementById("demo").innerHTML = txt;
			}
		};
		xmlhttp.open("GET", "http://localhost:8080/javascript-practice/Data.json", true);
		xmlhttp.send();
	</script>

</body>
</html>
