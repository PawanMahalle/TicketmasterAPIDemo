package com.ticketmaster.test.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ticketmaster.api.TicketMasterAPICaller;
import com.ticketmaster.entity.Event;

public class TicketMasterAPICallerTest {

	/**
	 * Should pass the test without error or exception
	 */
	@Test
	public void getEventsTest_PositiveScanario() {
		Map<String, String> apiInputMap = new HashMap<>();
		apiInputMap.put("keyword", "Tempe ASU");
		
		List<Event> events = TicketMasterAPICaller.getEvents(apiInputMap);
		System.out.println(events);
	}

	/**
	 * Should pass the test without error or exception
	 * @throws IOException 
	 */
	@Test
	public void getEventDetails_PositiveScanario() throws IOException {
		
		//Check if eventID is valid or not.
		String eventID = "0B004F3ED3EC5A9D";
		Event event = TicketMasterAPICaller.getEventDetails(eventID);
		System.out.println(event);
	}

	
	
}
