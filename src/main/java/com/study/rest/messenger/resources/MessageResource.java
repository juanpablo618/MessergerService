package com.study.rest.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.study.rest.messenger.resources.beans.MessageFilterBean;
import com.study.rest.model.Message;
import com.study.rest.service.MessageService;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {
	MessageService messageService = new MessageService();
		
@GET
@Produces(MediaType.APPLICATION_JSON)
public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean) {

	if(filterBean.getYear() > 0 ) {
		return messageService.getAllMessagesForYear(filterBean.getYear());
	}
	
	if( filterBean.getStart() >=0 && filterBean.getSize() >=0) {
		return messageService.getAllMessagesPaginated(filterBean.getStart(),filterBean.getSize());
	}
	return messageService.getAllMessages();
}

@GET
@Produces(MediaType.TEXT_XML)
public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean) {

	if(filterBean.getYear() > 0 ) {
		return messageService.getAllMessagesForYear(filterBean.getYear());
	}
	if( filterBean.getStart() >=0 && filterBean.getSize() >=0) {
		return messageService.getAllMessagesPaginated(filterBean.getStart(),filterBean.getSize());
	}
	return messageService.getAllMessages();
}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("/{messageId}")
public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {
	
	Message message = messageService.getMessage(messageId);
	message.addLink(getUriForSelf(uriInfo, message),"self");
	return message;
}

private String getUriForSelf(UriInfo uriInfo, Message message) {
	String url = uriInfo.getBaseUriBuilder().path(MessageResource.class)
	.path(Long.toString(message.getId())).build().toString();
	return url;
}

@POST
public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
	Message newMessage = messageService.addMessage(message);
	
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		
	 return Response.created(uri).entity(newMessage).build();
}

@PUT
@Path("/{messageId}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Message updateMessage(@PathParam("messageId") long messageId , Message message) {
		message.setId(messageId);
	return messageService.updateMessage(message);
}

@DELETE
@Path("/{messageId}")
@Produces(MediaType.APPLICATION_JSON)
public void deleteMessage(@PathParam("messageId") long messageId) {
	messageService.removeMessage(messageId);
}

@Path("/{messageId}/comments")
public CommentResource getCommentResource() {
	return new CommentResource();
}

}