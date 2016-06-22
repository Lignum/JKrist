package me.lignum.jkrist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class HTTPHelper {
	private static HttpURLConnection getConnection(String url) {
		try {
			URL lurl = new URL(url);
			HttpURLConnection conn;
			
			switch (lurl.getProtocol().toLowerCase()) {
			case "https":
				conn = (HttpsURLConnection)lurl.openConnection();
				break;
				
			case "http":
				conn = (HttpURLConnection)lurl.openConnection();
				break;
				
			default:
				throw new IllegalArgumentException("URL protocol must be HTTP/HTTPS");
			}
			
			return conn;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String get(String url) {
		HttpURLConnection conn = getConnection(url);
		
		try {
			conn.setRequestMethod("GET");
			
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String response = "";
			
			String line;
			while ((line = br.readLine()) != null) {
				response += line + "\n";
			}
			
			return response;
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
