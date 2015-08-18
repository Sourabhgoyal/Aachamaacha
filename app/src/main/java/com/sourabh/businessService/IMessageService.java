package com.sourabh.businessService;

import java.util.ArrayList;

import com.sourabh.entity.Message;

public interface IMessageService {

	public ArrayList<Message> fetchMessages(String location);
}
