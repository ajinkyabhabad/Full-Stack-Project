<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TheAdda</title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
            $(function () {
                /* $("#datepicker").datepicker(); */
                $("#datepicker").datepicker({ minDate: 0 });
            });
        </script>
</head>
<body>
<div class="container">
<div class="col-centered">
<h3>Create Item</h3>
  <div class="jumbotron">
        
        <p><font color="red">${error}</font></p>
        <form action="/myapp/additemdetails.htm" method="post">
          <div class="form-group">
          
          	<div class="form-group">
             Item Name      <input type="text" name="eventname" class="form-control" placeholder="Enter Item name" >
             </div>
             
             <div class="form-group">
             Item Category: <input type="text" name="venue" class="form-control" value="${venue.getLocation()}" readonly="readonly" >
             </div>
             
             
            <div class="form-group">
             Item Details:     <input type="text" name="details" class="form-control" placeholder="Details about the item">
             <small id="emailHelp" class="form-text text-muted">Write a short description of item.</small>
            </div>
            
            <div class="form-group">
             Items Availability:     <input type="number" name="capacity" class="form-control" placeholder="Capacity">
            </div>
            
            <div class="form-group">
            
             Date: <input type="text" name="eventdate" id="datepicker" class="form-control">
            </div>
            <p><font color="red">${booked}</font></p>
            
           <!--  <select class="custom-select" size="3" name="status">
			  <option value="2">Completed</option>
			  <option value="3">Canceled</option>
			</select> -->
            
            <div class="form-group">
             <input type="hidden" name="venueid" class="form-control" value="${venue.getId()}" readonly="readonly" >
             </div>
            
            <br>
            <input type="submit" name="submit" class="form-control" value="Submit">
          </div>
        <p class="text-left"><a href="/myapp/UserDashboard.htm">Go Back</a></p>
        </form>
      </div>  
  </div>
  </div>

</body>
</html>