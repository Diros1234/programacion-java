package urlInjectionTest;
import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.util.StringTokenizer;

public class UrlInjection {
	static String charset = "UTF-8";
	static String param1 = "entry.2100120705";
	static String param2 = "DanielV";
//this exercise is about read all content of the page in html code.
	public void ejercicio1() throws IOException {
		URL url = new URL(
				"https://docs.google.com/forms/d/e/1FAIpQLSdDg3AiR3pHTOeGg4cT5vVLc73xBffprxq5A4ii6Ap7IefVrQ/formResponse");
		URLConnection con = null;
		try {
			con = url.openConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String linea;
		try {
			while ((linea = br.readLine()) != null) {
				System.out.println(linea);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//entrys profe
	
	//entry.835030737 texto
	//entry.1616686619 radiobutton
	//entrys mios
	
	//entry.2100120705 texto
	//entry.815475579 radiobutton
	
	
	//----------------------------------------------------------
	//this exercise is about manipulation of google forms,
	//this first case write "DanielV" in field wiht id entry.835030737
	//in the second case have "No" or "Si" for put in the radioButtons the id is entry.1616686619.
	//----------------------------------------------------------
	public void ejercicio2() throws IOException {
		String data = URLEncoder.encode("entry.835030737", "UTF-8") + "=" + URLEncoder.encode("DanielV", "UTF-8");
		data += "&" + URLEncoder.encode("entry.1616686619", "UTF-8") + "=" +
		URLEncoder.encode("Si", "UTF-8");
		for (int i = 0; i < 1000; i++) {
			URL url = new URL(
					"https://docs.google.com/forms/d/e/1FAIpQLScE6sxLFb3BmCmT2TKHQH5ormS0qvjHwO-uTAR8MXaagBvSSQ/formResponse");
			URLConnection con = url.openConnection();
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(con.getInputStream()));
			HttpURLConnection httpCon = (HttpURLConnection) con;
			httpCon.setRequestMethod("POST");
			httpCon.setDoOutput(true);
			// Para saber el content type
			// System.out.println(htppCon.getContentType());
			httpCon.setRequestProperty("Accept-Charset", charset);
			httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			DataOutputStream dos = new DataOutputStream(httpCon.getOutputStream());
			dos.write(data.getBytes(charset));
			dos.flush();
			dos.close();
			System.out.println(httpCon.getHeaderField("entry.835030737"));
			httpCon.disconnect();
			// Thread.sleep((long) (Math.random()*1+1));
			System.out.println(i);
		}
		System.out.println("Tarea finalizada");
	}
	//Este hace la injeccion de codigo a la enquesta del rac de la pizza de piña
	
	
	
	//----------------------------------------------------------------
	//This exercise is about,manipulation votes of whats is better a pizza with pineapple or whitout.
	//In this case ,this code "{\"options\":[{\"id\":0,\"votes\":1},{\"id\":1,\"votes\":0}]}" is piece of
	//code for send at the page, and register the vote
	//The url should be caught using a sniffer the next url is the address where they going all votes.
	//"http://revolok.grupogodo.com/v1/polls/R1/df94eae6-f8ce-11e6-a208-4395726f36fc/options/"
	//----------------------------------------------------------------
	public void formPrueba() throws IOException, InterruptedException{
//		String data = ":[["+URLEncoder.encode("id", "UTF-8") + ": " + URLEncoder.encode("0", "UTF-8");
//		data += "&" + URLEncoder.encode("id", "UTF-8") + "=" +
//		URLEncoder.encode("1", "UTF-8");
		String data = "{\"options\":[{\"id\":0,\"votes\":1},{\"id\":1,\"votes\":0}]}";
		
		for (int i = 0; i < 100; i++) {
			URL url = new URL(
					"http://revolok.grupogodo.com/v1/polls/R1/df94eae6-f8ce-11e6-a208-4395726f36fc/options/");
			URLConnection con = url.openConnection();
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(con.getInputStream()));
			HttpURLConnection httpCon = (HttpURLConnection) con;
			httpCon.setRequestMethod("POST");
			httpCon.setDoOutput(true);
			// Para saber el content type
			// System.out.println(htppCon.getContentType());
			//httpCon.setRequestProperty("Accept-Charset", charset);
			httpCon.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
			httpCon.setRequestProperty("Content-Type", "application/json");
			DataOutputStream dos = new DataOutputStream(httpCon.getOutputStream());
			dos.write(data.getBytes(charset));
			dos.flush();
			dos.close();
			System.out.println(httpCon.getHeaderField("entry.835030737"));
			httpCon.disconnect();
			//Thread.sleep((long) (Math.random()*5000+1000));
			System.out.println(i);
		}
		System.out.println("Tarea finalizada");
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		UrlInjection testeo = new UrlInjection();
		testeo.formPrueba();
	}
}