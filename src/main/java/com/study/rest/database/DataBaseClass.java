package com.study.rest.database;

import java.util.HashMap;
import java.util.Map;

import com.study.rest.model.Message;
import com.study.rest.model.Profile;

public class DataBaseClass {

	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();
	
	public static Map<Long, Message> getMessages() {
		return messages;
	}
	public static void setMessages(Map<Long, Message> messages) {
		DataBaseClass.messages = messages;
	}
	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
	public static void setProfiles(Map<String, Profile> profiles) {
		DataBaseClass.profiles = profiles;
	}
}