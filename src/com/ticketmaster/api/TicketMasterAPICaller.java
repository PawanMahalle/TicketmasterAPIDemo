package com.ticketmaster.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ticketmaster.entity.Event;

public class TicketMasterAPICaller {

	private static final String API_KEY = "7tpLOfbZfWekxORbpHkrdYH3GrAT7wVA";

	private static final String EVENT_API_CALL_PREFIX = new StringBuilder(
			"https://app.ticketmaster.com/discovery/v1/events.json?apikey=").append(API_KEY).toString();

	private static final String EVENT_DETAILS_API_CALL_PREFIX = "https://app.ticketmaster.com/discovery/v1/events/";
	private static final String EVENT_DETAILS_API_CALL_SUFFIX = new StringBuilder(".json?apikey=").append(API_KEY)
			.toString();

	/**
	 * returns events based on input key-value pairs by calling event search
	 * API.
	 * 
	 * @param apiInputMap
	 * @return
	 */
	public static List<Event> getEvents(Map<String, String> apiInputMap) {

		List<Event> events = null;

		// create API call by appending key-values in API request format
		StringBuilder APICall = new StringBuilder(EVENT_API_CALL_PREFIX);
		for (String key : apiInputMap.keySet()) {
			if (!"".equals(apiInputMap.get(key))) {
				APICall.append("&").append(key).append("=").append(apiInputMap.get(key).replace(" ", "%20"));
			}
		}

		try {
			events = extractEvents(getRespose(APICall.toString()));
		} catch (Exception e) {

			// generate empty response
			events = new ArrayList<>();

			// TODO: log events
			e.printStackTrace();
		}
		return events;
	}

	private static JSONObject getRespose(String APICall) throws IOException {

		JSONObject jsonObject = null;

		URL url = new URL(APICall);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		} else {
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder responseStrBuilder = new StringBuilder();

			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				responseStrBuilder.append(inputStr);

			jsonObject = new JSONObject(handleDuplicateKey(responseStrBuilder.toString()));

		}

		return jsonObject;
	}

	private static List<Event> extractEvents(JSONObject jsonObject) {

		List<Event> events = new ArrayList<>();

		JSONObject embeddedObj = jsonObject.getJSONObject("_embedded");

		JSONArray eventsArray = embeddedObj.getJSONArray("events");

		for (int i = 0; i < eventsArray.length(); i++) {

			Event event = null;
			try {
				event = TicketMasterAPICaller.getEventDetails(eventsArray.getJSONObject(i).getString("id"));
				if (null != event) {
					events.add(event);
				}
			} catch (IOException e) {
				// TODO Log event
				e.printStackTrace();
			}
		}

		return events;
	}

	public static Event getEventDetails(final String EVENT_ID) throws IOException {

		Event event = null;

		StringBuilder APICall = new StringBuilder(
				EVENT_DETAILS_API_CALL_PREFIX + EVENT_ID + EVENT_DETAILS_API_CALL_SUFFIX);

		event = extractEventDetails(getRespose(APICall.toString()));
		return event;
	}

	/**
	 * reforms JSON when duplicate key is detected.
	 * 
	 * @param string
	 * @return
	 */
	private static String handleDuplicateKey(String string) {
		// TODO: Merge duplicate key values into JSONArray if duplicate keys are
		// present.
		
		//TODO: This is WORKAROUND :(
		return string.replaceFirst("_links", "_links_duplicate");
	}

	private static Event extractEventDetails(JSONObject eventDetailsJSONObj) {
		Event event = new Event(eventDetailsJSONObj.getString("id"));

		try {
			event.setName(eventDetailsJSONObj.getString("name"));
			try {
				event.setEventURL(eventDetailsJSONObj.getString("eventUrl"));
			} catch (JSONException ex) {
				// TODO: Log NOo event URL present
			}

			try {
				JSONArray venues = eventDetailsJSONObj.getJSONObject("_embedded").getJSONArray("venue");
				StringBuilder address = new StringBuilder();

				for (int i = 0; i < venues.length(); i++) {
					if (i != 0) {
						address.append("<BR>AND<BR>");
					}
					address.append(venues.getJSONObject(i).get("name")).append(", ");
					address.append(venues.getJSONObject(i).get("marketId")).append("<BR>");
					address.append(venues.getJSONObject(i).getJSONObject("city").get("name")).append(", ");
					address.append(venues.getJSONObject(i).getJSONObject("country").get("countryCode")).append("<BR>");

				}
				event.setVenue(address.toString());

			} catch (JSONException ex) {
				// TODO: Log NOo event URL present
			}

			try {
				JSONObject startTiming = eventDetailsJSONObj.getJSONObject("dates").getJSONObject("start");

				event.setStartDate(startTiming.get("localDate").toString());
				event.setStartTime(startTiming.get("localTime").toString());
			} catch (JSONException ex) {
				// TODO: Log NO date info
			}
		} catch (Exception e) {
			// TODO: Add event logging
		}
		return event;
	}

}
