package com.study.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.study.rest.database.DataBaseClass;
import com.study.rest.model.Comment;
import com.study.rest.model.ErrorMessage;
import com.study.rest.model.Message;

public class CommentService {

	private Map<Long, Message> messages = DataBaseClass.getMessages();
	
	public List<Comment> getAllComments(Long messageId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(Long messageId, Long commentId) {
		
		Throwable ex = null;
		ErrorMessage errorMessage = new ErrorMessage("not found", 404, "http://google.com");
		
		Response response = Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
			
		Message message = messages.get(messageId);
		if(message == null) {
			throw new WebApplicationException(response);
		}
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		
		Comment comment = comments.get(commentId);
		if(comment == null) {
			throw new WebApplicationException(response);
		}
	return comment;
	}
	
	public Comment addComment(Long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() +1 );
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(Long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId() <=0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(Long messageId, Long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
	
}