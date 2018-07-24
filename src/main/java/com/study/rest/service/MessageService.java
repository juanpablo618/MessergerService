package com.study.rest.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.study.rest.database.DataBaseClass;
import com.study.rest.exception.DataNotFoundException;
import com.study.rest.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DataBaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1, "hello","juan"));
	}
	
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){
		
		List<Message> messagesForYear = new ArrayList();
		Calendar cal = Calendar.getInstance();
		
		for(Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
		
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		
		if(start + size > list.size()) return new ArrayList<Message>();
		
		return list.subList(start, start + size);
		
	}
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if(message == null) {
			throw new DataNotFoundException("Message with id " + id + " not found");
		}
		return message;
		//return messages.get(id);
	}
	
	public Message addMessage(Message mje) {
		mje.setId(messages.size() +1);
		messages.put(mje.getId(), mje);
	return mje;
	}
	
	public Message updateMessage(Message mje) {
		if(mje.getId() <=0) {
			return null;
		}
		messages.put(mje.getId(), mje);
		return mje;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
	
}