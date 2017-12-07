<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<!doctype html>
<html>
<body>
    
    <%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    String url= blobstoreService.createUploadUrl("/upload");
    double sdf = Math.sin(234.3223);
    %>
     
    <form action="<%=url %>" method="post" enctype="multipart/form-data">
        <input type="file" name="myFile">
        <input type="submit" value="Submit">
    </form>
</body>

</html>

