package me.lignum.jkrist;

import org.json.JSONObject;

import java.io.*;
import java.net.*;

class HTTPHelper {
	private static HttpURLConnection getConnection(String url) {
		try {
			URL lurl = new URL(url);
			HttpURLConnection conn;
			
			switch (lurl.getProtocol().toLowerCase()) {
			case "https":
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

			br.close();
			return response;
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static String post(String url, JSONObject body) {
		HttpURLConnection conn = getConnection(url);

		try {
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			String jsonBody = body.toString(0);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Content-Length", String.valueOf(jsonBody.length()));

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(jsonBody);
			bw.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String response = "";

			String line;
			while ((line = br.readLine()) != null) {
				response += line + "\n";
			}

			br.close();
			return response;
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String encodeURL(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}
}
