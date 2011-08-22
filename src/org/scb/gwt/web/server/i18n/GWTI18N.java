/**
 * Copyright (C) 2010 Sebastien Chassande-Barrioz
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.scb.gwt.web.server.i18n;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.Messages;

/**
 *
 */
public class GWTI18N {

	/**
	 *
	 */
	public final static Map<String, Object> cache = new HashMap<String, Object>();
	/** */
	public static boolean useCache = true;
	/**
	 * @param <T> x
	 * @param itf x
	 * @return x
	 * @throws IOException err
	 */
	public static <T> T create(Class<T> itf) throws IOException {
		return create(itf, null);
	}

	/**
	 * @param <T> x
	 * @param itf x
	 * @param lang x
	 * @return r
	 * @throws IOException err
	 */
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> itf, String lang) throws IOException {
		final String key = itf.getName() + (lang == null ? "" : ("_" + lang)); //$NON-NLS-1$ //$NON-NLS-2$
		if (useCache) {
			T msg = (T) cache.get(key);
			if (msg == null) {
				msg = createProxy(itf, lang);
				cache.put(key, msg);
			}
			return msg;
		} else {
			return createProxy(itf, lang);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T createProxy(Class<T> itf, String lang) throws IOException {
		InvocationHandler ih;
		if (GenericX.isA(itf, Constants.class)) {
			ih = new GenericConstants(itf, lang);
		} else if (GenericX.isA(itf, Messages.class)) {
			ih = new GenericMessages(itf, lang);
		} else {
			throw new InvalidParameterException("Class " + itf.getName() + " is not a GWT i18n subclass"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return (T) Proxy.newProxyInstance(itf.getClassLoader(), new Class[] { itf }, ih);
	}
}
