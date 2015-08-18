package com.sourabh.events.businessService;

import java.util.ArrayList;

import com.sourabh.events.entity.Event;

public interface IEventService {

	public ArrayList<Event> fetchEvents(String lastEventId,String locationCity);
}
