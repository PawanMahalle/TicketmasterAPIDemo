<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script>

	$(document).ready(function() {
		$('.field input').keyup(function() {

			var empty = false;
			$('.field input').each(function() {
				if ($(this).val().length == 0) {
					empty = true;
				}
			});

			if (empty) {
				$('.actions input').attr('disabled', 'disabled');
			} else {
				$('.actions input').attr('disabled', false);
			}
		});
	});
</script>

<style>
blockquote {
	quotes: "\201C" "\201D" "\2018" "\2019";
}

blockquote h3 {
	font-size: 50px;
}

blockquote h3:before {
	content: open-quote;
	font-weight: bold;
	font-size: 100px;
	color: #889c0b;
}

blockquote h3:after {
	content: close-quote;
	font-weight: bold;
	font-size: 100px;
	color: #889c0b;
}
</style>

<title>Ticketmaster API Demo</title>

</head>
<body>

	<br>

	<div align="center">
		<h4>What's</h4>
		<blockquote>
			<h3>Happening</h3>

		</blockquote>
		<h4>around!</h4>
	</div>

	<br>
	<br>

	<div align="center">
		<form class="form-inline" method="post" action="./EventFetcherServlet">
			<div class="form-group">
				<div class="field">
					<input type="text" class="form-control" id="keyword" name="keyword">
				</div>
			</div>
			<br> <br>
			<div class="actions">
				<input type="submit" class="btn btn-default" value="Search Events"
					disabled="disabled">
			</div>
		</form>
	</div>
</body>
</html>