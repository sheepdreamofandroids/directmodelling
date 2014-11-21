package com.directmodelling.demo.angular.client;

import javax.ws.rs.POST;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.google.gwt.editor.client.Editor.Path;

public interface InitService extends RestService {
	@POST
	@Path("init")
	public IInit init(MethodCallback<Init> callback);
}