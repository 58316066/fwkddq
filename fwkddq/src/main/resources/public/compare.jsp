<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Compare Page</title>
</head>
<body>
<!--<form action="home" method="post">
<input type="text" name="userName"><br>
<input type="submit" value="Login">
</form>-->

<h1>Upload example</h1>
<form method="post" action="/compare" enctype="multipart/form-data">
    <input type="file" name="files1" multiple>
    <input type="file" name="files2" multiple>
	<BR>
    <button>Submit</button>
</form>
</body>
</html>