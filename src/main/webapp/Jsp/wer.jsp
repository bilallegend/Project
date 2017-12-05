<!doctype html>
<html>
<body>

<% 
 response.addCookie(new Cookie("hdfoias","qweqweqweqwe"));
 %>
<form action='uploadad' method="post" enctype="multipart/form-data">
<input type = 'file' name='file' >
<input type='submit'>
</form>
</body>

</html>