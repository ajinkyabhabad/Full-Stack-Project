<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TheAdda</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>

<div class="container">
<div class="col-centered">
<h3>Add Category</h3>
  <div class="jumbotron">
        
        <p><font color="red">${error}</font></p>
        <form action="/myapp/addcategorydetails.htm" method="post">
          <div class="form-group">
          <div class="form-group">
            Category Type:     <input type="text" name="cttype" class="form-control" placeholder="Enter Category Type" >
             
             <small id="emailHelp" class="form-text text-muted">Please enter the Category Type.</small>
             </div>
             <div class="form-group">
             Category Max Items: <input type="number" name="catmax" class="form-control" placeholder="How many category items can be stored?"  >
             </div>
             <div class="form-group">
             Transport Accessibility:     <input type="text" name="transport" class="form-control" placeholder="Specify the transport accessibility">
            </div>
            <br>
            <input type="submit" name="submit" class="form-control" value="Submit">
          </div>
        <p class="text-left"><a href="/myapp/OwnerDashboard.htm">Go Back</a></p>
        </form>
      </div>  
  </div>
  </div>


</body>
</html>