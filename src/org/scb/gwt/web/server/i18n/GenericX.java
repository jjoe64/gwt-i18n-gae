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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.Properties;

/**
 *
 */
public abstract class GenericX implements InvocationHandler {
	/**
	 * @param c1 c
	 * @param c2 c
	 * @return r
	 */
	public static boolean isA(Class<?> c1, Class<?> c2) {
		return c2.isAssignableFrom(c1);
	}
	protected final Properties properties = new Properties();

	protected final Class<?> itf;

	/**
	 * @param _itf _itf
	 * @param lang lang
	 * @throws IOException err
	 * @throws InvalidParameterException err
	 */
	public GenericX(Class<?> _itf, String lang) throws IOException, InvalidParameterException {
		itf = _itf;
		fillProperties(itf, lang);
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}

	protected void fillProperties(Class<?> itf, String lang) throws IOException {
		for (Class<?> superItf : itf.getInterfaces()) {
			fillProperties(superItf, lang);
		}
		String suffix = lang == null ? "" : ("_" +lang); //$NON-NLS-1$ //$NON-NLS-2$
		String baseName = itf.getName().replace('.', '/');
		InputStream in = load(baseName + suffix + ".properties"); //$NON-NLS-1$
		if (in == null) {
			in = load(baseName + ".properties"); //$NON-NLS-1$
		}
		if (in != null) {
			properties.load(new InputStreamReader(in, "UTF-8")); //$NON-NLS-1$
		}
	}

	@Override
	public int hashCode() {
		return properties.size();
	}

	@Override
	public abstract Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

	protected InputStream load(String s) throws FileNotFoundException {
		InputStream in = null;
		in = loadPropertiesGAE(s);


		return in;
	}

	private InputStream loadPropertiesGAE(String s) {
		try {
			return new FileInputStream("nls_server/"+s.substring(s.lastIndexOf('/')+1)); //$NON-NLS-1$
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}
