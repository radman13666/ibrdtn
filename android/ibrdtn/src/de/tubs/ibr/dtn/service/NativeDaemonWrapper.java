/*
 * NativeLibraryWrapper.java
 * 
 * Copyright (C) 2013 IBR, TU Braunschweig
 *
 * Written-by: Dominik Schürmann <dominik@dominikschuermann.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.tubs.ibr.dtn.service;

import android.util.Log;

/**
 * Native methods are implemented in
 * jni/native-library-wrapper/NativeLibraryWrapper.cpp
 */
public class NativeDaemonWrapper {
	private final static String TAG = "IBR-DTN NativeLibraryWrapper Java";

	private final static String GNUSTL_NAME = "gnustl_shared";
	private final static String CRYPTO_NAME = "crypto";
	private final static String SSL_NAME = "ssl";
	private final static String IBRCOMMON_NAME = "ibrcommon";
	private final static String IBRDTN_NAME = "ibrdtn";
	private final static String DTND_NAME = "dtnd";
	private final static String ANDROID_GLUE_NAME = "android-glue";

	/**
	 * Loads all shared libraries in the right order with System.loadLibrary()
	 */
	private static void loadLibraries()
	{
		try
		{
			System.loadLibrary(GNUSTL_NAME);

			// System.loadLibrary(CRYPTO_NAME);
			// System.loadLibrary(SSL_NAME);

			System.loadLibrary(IBRCOMMON_NAME);
			System.loadLibrary(IBRDTN_NAME);
			System.loadLibrary(DTND_NAME);

			System.loadLibrary(ANDROID_GLUE_NAME);
		} catch (UnsatisfiedLinkError e)
		{
			Log.e(TAG, "UnsatisfiedLinkError! Are you running special hardware?", e);
		} catch (Exception e)
		{
			Log.e(TAG, "Loading the libraries failed!", e);
		}
	}

	static
	{
		// load libraries on first use of this class
		loadLibraries();
	}

	// can not be initialized
	private NativeDaemonWrapper() {
	}

	public static native void daemonInitialize(String configPath, boolean enableDebug);

	public static native void daemonMainLoop();

	public static native void daemonShutdown();
	
	public static native void daemonReload();

	public static native String[] getNeighbors();

	public static native void addConnection(String eid, String protocol, String address, String port);

	public static native void removeConnection(String eid, String protocol, String address, String port);

	@Override
	protected void finalize() throws Throwable
	{
		Log.d(TAG, "NativeDaemonWrapper was finalized! GC!");
		super.finalize();
	}

}
