package com.study.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.study.rest.model.ErrorMessage;

	public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

		@Override
		public Response toResponse(Throwable exception) {
			
			Throwable ex = null;
			ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 500, "http://google.com");
			
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(errorMessage)
					.build();
		}
}