<%@page import="java.util.List"%>
<%@page import="com.ticketmaster.entity.Event"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<title>Events</title>
</head>
<body>

	<div class="container">
		<h2 align="center">Events</h2>
	</div>


	<div class="container">

		<%
			List<Event> events = (List<Event>) request.getAttribute("events");

			if (events.size() == 0) {
				out.println("<h4 align=\"center\" style=\"color:gray;\">Oops!<h4>");
				out.println("<h5 align=\"center\">No event found! </h5>");
			} else {

				for (int eventCounter = 0; eventCounter < events.size(); eventCounter += 3) {
		%><div class="row">
			<%
				for (int i = eventCounter; i < eventCounter + 3 && i < events.size(); i++) {
			%>
			<div class="col-md-4">
				<div class="thumbnail"
					onmouseover="this.style.background='#FAFAFA';"
					onmouseout="this.style.background='white';">
					<p align="center">
						<b><%=events.get(i).getName()%></b>
					</p>
					<p align="center">
						Date:
						<%=(events.get(i).getStartDate() == null) ? "N/A" : events.get(i).getStartDate()%></p>
					<p align="center">
						Time:
						<%=(events.get(i).getStartTime() == null) ? "N/A" : events.get(i).getStartTime()%></p>

					<p align="center">
						Venue:
						<%=(events.get(i).getVenue() == null) ? "N/A" : events.get(i).getVenue()%></p>

					<%
						if (null != events.get(i).getEventURL()) {
					%>
					<p align="center">
						<button type="button" class="btn btn-info"
							onclick="parent.location='<%=events.get(i).getEventURL()%>'">Visit
							Event</button>
					</p>
					<%
						}
					%>

				</div>

			</div>
			<%
				}
			%>
		</div>
		<%
			}
		}
		%>
	
</body>
</html>