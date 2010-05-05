package com.mob.web;
import javax.servlet.http.*;
import java.io.*;
import java.security.*;
import java.util.*;
import java.io.UnsupportedEncodingException;
import javax.servlet.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class SessionMessage{

	private static Logger log = Logger.getLogger( SessionMessage.class.getName() );
	public static final String FLASH_REQ_KEY = "FLASH";
	public static final String FLASH_SESSION_PREFIX  = "FLS_";
	public static final String FLASH_PREFIX = "FL_";

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String,String> flashMap;

	public static SessionMessage getInstance( HttpServletRequest request, HttpServletResponse response ) {
		SessionMessage flash = (SessionMessage)request.getAttribute( FLASH_REQ_KEY );
		if ( flash == null ) {
			flash = new SessionMessage( request, response );
			request.setAttribute( FLASH_REQ_KEY, flash );
		}
		return flash;
	}


	protected SessionMessage( HttpServletRequest request, HttpServletResponse response ) {
		this.request  = request;
		this.response = response;
		this.flashMap = new HashMap<String,String>();
		consumeCookies();
	}


	public void consumeCookies() {//{{{
		Cookie[] cookies = request.getCookies();
		if ( cookies != null ) {
			for ( Cookie cookie : cookies ) {
				if ( cookie.getName().startsWith( FLASH_PREFIX ) ||
					 cookie.getName().startsWith( FLASH_SESSION_PREFIX ) ) {
					// read in the cookie and trash it
					parseCookie( cookie );
					if ( cookie.getName().startsWith( FLASH_PREFIX ) )
						zapCookie( cookie );
				}
			}
		}
	}//}}}

	private void parseCookie( Cookie cookie ) {//{{{
		// get the cookie name
		String key = null;
		if ( cookie.getName().startsWith( FLASH_PREFIX ) )
			key = cookie.getName().substring( FLASH_PREFIX.length(), cookie.getName().length() );

		if ( cookie.getName().startsWith( FLASH_SESSION_PREFIX ) )
			key = cookie.getName().substring( FLASH_SESSION_PREFIX.length(), cookie.getName().length() );

		String value = cookie.getValue();

		StringTokenizer st = new StringTokenizer( value, "&" );

		String mesg   = null;
		String sig    = null;
		boolean sSide = false;

		while ( st.hasMoreTokens() ) {
			String tok = st.nextToken();

			int eq = tok.indexOf( '=' );
			if ( eq < 1 )
				continue;

			String cKey = tok.substring( 0, eq );
			String cVal = tok.substring( eq + 1 );
			
			if ( cKey.compareTo( "m" ) == 0 )
				mesg = cVal;

			if ( cKey.compareTo( "s" ) == 0 )
				sig = cVal;

			if ( cKey.compareTo( "c" ) == 0 )
				sSide = true;
		}

		// short circuit for bad data
		if ( key == null || sig == null || ( !sSide && mesg == null ) )
			return;

		// decode the key
		try {
			key  = URLDecoder.decode( key, "ISO-8859-1" );
		} catch ( UnsupportedEncodingException une ) {
			log.error( "failed to urldecode cookie value" );
		}

		if ( sSide ) {
			//if ( sig.equals( signMD5(key) ) {
				// get mesg out of cache
				//mesg = (String)MemCache.get( "Flash:" + key );
				//if ( mesg != null )
					//MemCache.remove( "Flash:" + key );

				// store in map
				//flashMap.put( key, mesg );
			//}
		} else {
			// url decode key and value
			try {
				mesg = URLDecoder.decode( mesg, "ISO-8859-1" );
			} catch ( UnsupportedEncodingException une ) {
				log.error( "failed to urldecode cookie value" );
			}

			// check the sig to make sure not tampered with
			if ( sig.equals( md5(key + ":" + mesg) ) )
				flashMap.put( key, mesg );
		}
	}//}}}


	private void setCookie( String key, String mesg, String path, boolean keep, int expiry ) {
		String value = null;
		// get a sig to prevent tampering (probably overkill)
		String sig = md5( key + ":" + mesg );

		// url encode value
		try {
			mesg = URLEncoder.encode( mesg, "ISO-8859-1" );
		} catch ( UnsupportedEncodingException une ) {
			log.error( "failed to urlencode cookie value" );
		}

		value = "m=" + mesg + "&s=" + sig;

		// url encode key
		try {
			key  = URLEncoder.encode( key, "ISO-8859-1" );
		} catch ( UnsupportedEncodingException une ) {
			log.error( "failed to urlencode cookie value" );
		}

		// set cookie
		Cookie cookie = null;
		if ( keep ) {
			cookie = new Cookie( FLASH_SESSION_PREFIX + key, value );
			if ( expiry > 0 ){
				cookie.setMaxAge( expiry );
			}else{
				cookie.setMaxAge( -1 );
			}
		} else {
			cookie = new Cookie( FLASH_PREFIX + key, value );
			cookie.setMaxAge( -1 );
		}

		//cookie.setDomain( CookieUtil.getDomain( request ) );
		cookie.setPath( (path==null || path.equals("")) ? "/" : path);
		response.addCookie( cookie );
	}

	public String get( String key ) {
		return flashMap.get( key );
	}

	public SessionMessage put( String key, String mesg ) {
		flashMap.put( key, mesg );
		setCookie( key, mesg, null, false, -1 );
		return this;
	}

	private void zapCookie( Cookie cookie ) {//{{{
		Cookie newCookie = new Cookie( cookie.getName(), "" );
		newCookie.setPath( "/" );
//		newCookie.setDomain( CookieUtil.getDomain( request ) );
		newCookie.setMaxAge( 0 );
		response.addCookie( newCookie );
	}//}}}

	private static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
		}
		return sb.toString();
	}


	public static String md5(String message){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return hex(md.digest(message.getBytes("CP1252")));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}


}

