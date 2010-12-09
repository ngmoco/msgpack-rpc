//
// MessagePack-RPC for Java
//
// Copyright (C) 2010 FURUHASHI Sadayuki
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package org.msgpack.rpc.reflect;

import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Method;

public class Reflect {
	private static final Reflect instance = new Reflect();

	public static <T> Proxy<T> reflectProxy(Class<T> iface) {
		return instance.getProxy(iface);
	}

	public static Invoker reflectInvoker(Method method) {
		return InvokerBuilder.build(method);
	}

	private Reflect() {
	}

	private Map<Class<?>, Proxy<?>> proxyCache = new HashMap<Class<?>, Proxy<?>>();
	private Map<Method, Invoker> invokerCache = new HashMap<Method, Invoker>();

	private synchronized <T> Proxy<T> getProxy(Class<T> iface) {
		Proxy<?> proxy = proxyCache.get(iface);
		if(proxy == null) {
			proxy = ProxyBuilder.build(iface);
			proxyCache.put(iface, proxy);
		}
		return (Proxy<T>)proxy;
	}

	private synchronized Invoker getInvoker(Method method) {
		Invoker invoker = invokerCache.get(method);
		if(invoker == null) {
			invoker = InvokerBuilder.build(method);
			invokerCache.put(method, invoker);
		}
		return invoker;
	}
}
