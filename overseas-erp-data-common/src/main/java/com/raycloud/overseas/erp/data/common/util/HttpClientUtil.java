package com.raycloud.overseas.erp.data.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

	private static final Logger log = LoggerFactory
			.getLogger(HttpClientUtil.class);

	public static final int cache = 10 * 1024;

	public static  String WEB_ROOT;

	private HttpClientUtil() {
	}

	public static String sendGetRequest(String reqURL) {
		return sendGetRequest(reqURL, null);
	}

	/**
	 * 
	 * 发送HTTP_GET请求
	 * 
	 * 该方法会自动关闭连接,释放资源
	 * 
	 * @param reqURL
	 *            请求地址(含参数)
	 * 
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * 
	 * @return 远程主机响应正文
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 */

	public static String sendGetRequest(String reqURL, String decodeCharset) {

		long responseLength = 0; // 响应长度

		String responseContent = null; // 响应内容

		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		// 防止 java.net.URISyntaxException
		// URL url = new URL(reqURL);
		// URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(),
		// url.getQuery(), null);

		HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet

		try {

			HttpResponse response = httpClient.execute(httpGet); // 执行GET请求

			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {

				responseLength = entity.getContentLength();

				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);

				EntityUtils.consume(entity); // Consume response content

			}

			// System.out.println("请求地址: " + httpGet.getURI());

			// System.out.println("响应状态: " + response.getStatusLine());

			// System.out.println("响应长度: " + responseLength);

			// System.out.println("响应内容: " + responseContent);

		} catch (ClientProtocolException e) {

			log.error(
					"该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下",
					e);

		} catch (ParseException e) {

			log.error(e.getMessage(), e);

		} catch (IOException e) {

			log.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);

		} finally {

			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源

		}

		return responseContent;

	}

	/**
	 * 
	 * 发送HTTP_POST请求
	 *
	 * 
	 * 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 *
	 * 
	 * @param isEncoder
	 *            用于指明请求数据是否需要UTF-8编码,true为需要
	 * @throws IOException
	 */

	public static String sendPostRequest(String reqURL, String sendData,
			boolean isEncoder) throws Exception {

		return sendPostRequest(reqURL, sendData, isEncoder, null, null);

	}

	/**
	 * 
	 * 发送HTTP_POST请求
	 * 
	 * 该方法会自动关闭连接,释放资源
	 *
	 * 
	 * @param reqURL
	 *            请求地址
	 * 
	 * @param sendData
	 *            请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,
	 *            传入该参数中
	 * 
	 * @param isEncoder
	 *            请求数据是否需要encodeCharset编码,true为需要
	 * 
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * 
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * 
	 * @return 远程主机响应正文
	 * @throws IOException
	 */

	public static String sendPostRequest(String reqURL, String sendData,
			boolean isEncoder, String encodeCharset, String decodeCharset)
			throws Exception {
		String responseContent = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(reqURL);
		// httpPost.setHeader(HTTP.CONTENT_TYPE,
		// "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader(HTTP.CONTENT_TYPE,
				"application/x-www-form-urlencoded");
		try {
			if (isEncoder) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for (String str : sendData.split("&")) {
					formParams.add(new BasicNameValuePair(str.substring(0,
							str.indexOf("=")),
							str.substring(str.indexOf("=") + 1)));
				}
				httpPost.setEntity(new StringEntity(URLEncodedUtils.format(
						formParams, encodeCharset == null ? "UTF-8"
								: encodeCharset)));
			} else {
				httpPost.setEntity(new StringEntity(sendData));
			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
			throw e;
		} finally {
			httpClient.close();
		}
		return responseContent;
	}

	/**
	 * 
	 * 发送HTTP_POST请求
	 * 
	 * 该方法会自动关闭连接,释放资源
	 *
	 * 
	 * @param reqURL
	 *            请求地址
	 * 
	 * @param jsonData
	 *            请求参数,{}json形式, 传入该参数中
	 * 
	 * @param isEncoder
	 *            请求数据是否需要encodeCharset编码,true为需要
	 * 
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * 
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * 
	 * @return 远程主机响应正文
	 */

	public static String sendPostRequest4Json(String reqURL, String jsonData,
			boolean isEncoder, String encodeCharset, String decodeCharset) {

		String responseContent = null;

		HttpClient httpClient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(reqURL);

		// httpPost.setHeader(HTTP.CONTENT_TYPE,
		// "application/x-www-form-urlencoded; charset=UTF-8");

		httpPost.setHeader(HTTP.CONTENT_TYPE,
				"application/x-www-form-urlencoded");
		try {
			if (isEncoder) {
				httpPost.setEntity(new StringEntity(EncodeUtils.urlEncode(
						jsonData, encodeCharset == null ? "UTF-8"
								: encodeCharset)));
			} else {
				httpPost.setEntity(new StringEntity(jsonData));
			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	/**
	 * 
	 * @param reqURL
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static String sendPostRequest4Json(String reqURL, String data)
			throws Exception {
		return sendPostRequest4Json(reqURL, data, null);
	}

	/**
	 * 
	 * @param reqURL
	 * @param data
	 * @param contentType
	 * @return
	 * @throws IOException
	 */
	public static String sendPostRequest4Json(String reqURL, String data,
			ContentType contentType) throws Exception {
		String responseContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(reqURL);
		try {
			httpPost.setEntity(new StringEntity(data, contentType));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
			throw e;
		} finally {
			httpclient.close();
		}
		return responseContent;
	}

	/**
	 * 
	 * 发送HTTP_POST请求
	 * 
	 * 该方法会自动关闭连接,释放资源
	 *
	 * 
	 * @param reqURL
	 *            请求地址
	 * 
	 * @param params
	 *            请求参数
	 * 
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * 
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * 
	 * @return 远程主机响应正文
	 * @throws Exception
	 */
	public static String sendPostRequest(String reqURL,
			Map<String, String> params, String encodeCharset,
			String decodeCharset) throws Exception {
		String responseContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(reqURL);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 创建参数队列
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formParams.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(formParams,
					encodeCharset == null ? "UTF-8" : encodeCharset));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
			throw e;
		} finally {
			httpclient.close();
		}
		return responseContent;
	}

	/**
	 *
	 * 发送HTTP_POST请求
	 *
	 * 该方法会自动关闭连接,释放资源
	 *
	 *
	 * @param reqURL
	 *            请求地址
	 *
	 *            请求参数
	 *
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 *
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 *
	 * @return 远程主机响应正文
	 * @throws Exception
	 */
	public static String sendPostRequest(String reqURL,Map<String,String> head,
										 String data, String encodeCharset,
										 String decodeCharset) throws Exception {
		String responseContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(reqURL);
		for (Map.Entry<String, String> entry : head.entrySet()) {
			httpPost.setHeader(entry.getKey(),entry.getValue());
		}

		try {
			httpPost.setEntity(new StringEntity(data));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
			throw e;
		} finally {
			httpclient.close();
		}
		return responseContent;
	}

	/**
	 * 
	 * 发送HTTPS_POST请求
	 *
	 *
	 *
	 */

	public static String sendPostSSLRequest(String reqURL,
			Map<String, String> params) {

		return sendPostSSLRequest(reqURL, params, null, null);

	}

	/**
	 * 
	 * 发送HTTPS_POST请求
	 * 
	 * 该方法会自动关闭连接,释放资源
	 *
	 * 
	 * @param reqURL
	 *            请求地址
	 * 
	 * @param params
	 *            请求参数
	 * 
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * 
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * 
	 * @return 远程主机响应正文
	 */

	public static String sendPostSSLRequest(String reqURL,
			Map<String, String> params, String encodeCharset,
			String decodeCharset) {

		String responseContent = "";

		HttpClient httpClient = new DefaultHttpClient();

		X509TrustManager xtm = new X509TrustManager() {

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

		};

		try {

			SSLContext ctx = SSLContext.getInstance("TLS");

			ctx.init(null, new TrustManager[] { xtm }, null);

			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			httpClient.getConnectionManager().getSchemeRegistry()
					.register(new Scheme("https", 443, socketFactory));

			HttpPost httpPost = new HttpPost(reqURL);

			List<NameValuePair> formParams = new ArrayList<NameValuePair>();

			for (Map.Entry<String, String> entry : params.entrySet()) {

				formParams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));

			}

			httpPost.setEntity(new UrlEncodedFormEntity(formParams,
					encodeCharset == null ? "UTF-8" : encodeCharset));

			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			if (null != entity) {

				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);

				EntityUtils.consume(entity);

			}

		} catch (Exception e) {

			log.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);

		} finally {

			httpClient.getConnectionManager().shutdown();

		}

		return responseContent;

	}

	/**
	 * 
	 * 发送HTTP_POST请求
	 *
	 * 
	 *  本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * 
	 * @param reqURL
	 *            请求地址
	 * 
	 * @param params
	 *            发送到远程主机的正文数据,其数据类型为<code>java.util.Map<String, String></code>
	 * 
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>
	 *         若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */

	private static String sendPostRequestByJava(String reqURL,
												Map<String, String> params) {

		StringBuilder sendData = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {

			sendData.append(entry.getKey()).append("=")
					.append(entry.getValue()).append("&");

		}

		if (sendData.length() > 0) {

			sendData.setLength(sendData.length() - 1); // 删除最后一个&符号

		}

		return sendPostRequestByJava(reqURL, sendData.toString());

	}

	/**
	 * 
	 * 发送HTTP_POST请求
	 *
	 * 
	 * 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * 
	 * @param reqURL
	 *            请求地址
	 * 
	 * @param sendData
	 *            发送到远程主机的正文数据
	 * 
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>
	 *         若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */

	public static String sendPostRequestByJava(String reqURL, String sendData) {

		HttpURLConnection httpURLConnection = null;

		OutputStream out = null; // 写

		InputStream in = null; // 读

		int httpStatusCode = 0; // 远程主机响应的HTTP状态码

		try {

			URL sendUrl = new URL(reqURL);

			httpURLConnection = (HttpURLConnection) sendUrl.openConnection();

			httpURLConnection.setRequestMethod("POST");

			httpURLConnection.setDoOutput(true); // 指示应用程序要将数据写入URL连接,其值默认为false

			httpURLConnection.setUseCaches(false);

			httpURLConnection.setConnectTimeout(30000); // 30秒连接超时

			httpURLConnection.setReadTimeout(30000); // 30秒读取超时

			out = httpURLConnection.getOutputStream();

			out.write(sendData.toString().getBytes());

			// 清空缓冲区,发送数据

			out.flush();

			// 获取HTTP状态码

			httpStatusCode = httpURLConnection.getResponseCode();

			// 该方法只能获取到[HTTP/1.0 200 OK]中的[OK]

			// 若对方响应的正文放在了返回报文的最后一行,则该方法获取不到正文,而只能获取到[OK],稍显遗憾

			// respData = httpURLConnection.getResponseMessage();

			// 处理返回结果

			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpURLConnection.getInputStream()));

			String row = null;

			String respData = "";

			if ((row = br.readLine()) != null) { // readLine()方法在读到换行[\n]或回车[\r]时,即认为该行已终止

				respData = row; // HTTP协议POST方式的最后一行数据为正文数据

			}

			br.close();

			in = httpURLConnection.getInputStream();

			byte[] byteDatas = new byte[in.available()];

			in.read(byteDatas);

			return new String(byteDatas) + "`" + httpStatusCode;

		} catch (Exception e) {

			log.error(e.getMessage());

			return "Failed`" + httpStatusCode;

		} finally {

			if (out != null) {

				try {

					out.close();

				} catch (Exception e) {

					log.error("关闭输出流时发生异常,堆栈信息如下", e);

				}

			}

			if (in != null) {

				try {

					in.close();

				} catch (Exception e) {

					log.error("关闭输入流时发生异常,堆栈信息如下", e);

				}

			}

			if (httpURLConnection != null) {

				httpURLConnection.disconnect();

				httpURLConnection = null;

			}

		}

	}

	public static String sendGetRequest(String reqURL, String decodeCharset,
										Map<String, String> headPara) {

		long responseLength = 0; // 响应长度

		String responseContent = null; // 响应内容

		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例

		HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet
		if ((headPara != null) && (headPara.size() > 0)) {
			for (Map.Entry<String, String> entry : headPara.entrySet()) {
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}

		try {

			HttpResponse response = httpClient.execute(httpGet); // 执行GET请求

			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {

				responseLength = entity.getContentLength();

				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);

				EntityUtils.consume(entity); // Consume response content

			}

			// System.out.println("请求地址: " + httpGet.getURI());

			// System.out.println("响应状态: " + response.getStatusLine());

			// System.out.println("响应长度: " + responseLength);

			// System.out.println("响应内容: " + responseContent);

		} catch (ClientProtocolException e) {

			log.error(
					"该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下",
					e);

		} catch (ParseException e) {

			log.error(e.getMessage(), e);

		} catch (IOException e) {

			log.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);

		} finally {

			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源

		}

		return responseContent;

	}

	/**
	 * 根据url下载文件，保存到filepath中
	 *
	 * @param url
	 * @return
	 */
	public static String download(String url, String fileName) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = client.execute(httpget);

			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			String filepath = getFilePath(fileName);
			File file = new File(filepath);
			file.getParentFile().mkdirs();
//			logger.biz("下载路径:"+file.getPath());
			FileOutputStream fileout = new FileOutputStream(file);
			/**
			 * 根据实际运行效果 设置缓冲区大小
			 */
			byte[] buffer = new byte[cache];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			is.close();
			fileout.flush();
			fileout.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取response要下载的文件的默认路径
	 *
	 * @return
	 */
	public static String getFilePath(String fileName) {
		String filepath = WEB_ROOT + "/wish/";


		return filepath += fileName + ".csv";
	}
}
