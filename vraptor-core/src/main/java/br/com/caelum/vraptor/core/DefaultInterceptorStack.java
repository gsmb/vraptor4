/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.caelum.vraptor.core;

import java.util.LinkedList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.controller.ControllerMethod;

/**
 * Default implementation of a interceptor stack.
 *
 * @author guilherme silveira
 *
 */
@RequestScoped
public class DefaultInterceptorStack implements InterceptorStack {

	private static final Logger logger = LoggerFactory.getLogger(DefaultInterceptorStack.class);
	private LinkedList<InterceptorHandler> interceptors;

	@Deprecated
	public DefaultInterceptorStack() {
		super();
	}

	@Inject
	public DefaultInterceptorStack(InterceptorStackHandlersCache cache) {
		interceptors = cache.getInterceptors();
	}

	public void next(ControllerMethod method, Object controllerInstance) throws InterceptionException {
		
		if (interceptors.isEmpty()) {
			logger.debug("All registered interceptors have been called. End of VRaptor Request Execution.");
			return;
		}
		InterceptorHandler handler = interceptors.poll();
		handler.execute(this, method, controllerInstance);
	}

	public void add(Class<?> type) {
		//FIXME EEEE		
	}

	// XXX this method will be removed soon
	public void addAsNext(Class<?> type) {
		//FIXME EEEE
	}

	@Override
	public String toString() {
		return "DefaultInterceptorStack " + interceptors;
	}

}
