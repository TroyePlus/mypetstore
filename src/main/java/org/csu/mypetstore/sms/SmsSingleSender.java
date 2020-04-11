package org.csu.mypetstore.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.interfaces.PBEKey;

public class SmsSingleSender {
	String accesskey;
	String secretkey;
	//同时支持http和https两种协议，具体根据自己实际情况使用。
	String url = "https://live.kewail.com/sms/v1/sendsinglesms";
	//String url = "http://127.0.0.1:8080/live.kewail.com/sms/v1/sendsinglesms";

	SmsSenderUtil util = new SmsSenderUtil();

	private static final String BASE_ACCESS_KEY = "5e8f21c3efb9a3751c1caef1";
	private static final String BASE_SECRET_KEY = "03cad33d6f444c6bb1326a8830c89eb6";
	private static final String MSG_PREFIX = "【MyPetStore】尊敬的用户：您的验证码：";
	private static final String MSG_SUFFIX = "，工作人员不会索取，请勿泄漏。";

	public SmsSingleSender(String accesskey, String secretkey) throws Exception {
		this.accesskey = accesskey;
		this.secretkey = secretkey;
	}

	/**
	 * 普通单发短信接口，明确指定内容，如果有多个签名，请在内容中以【】的方式添加到信息内容中，否则系统将使用默认签名
	 * @param type 短信类型，0 为普通短信，1 营销短信
	 * @param nationCode 国家码，如 86 为中国
	 * @param phoneNumber 不带国家码的手机号
	 * @param msg 信息内容，必须与申请的模板格式一致，否则将返回错误
	 * @param extend 扩展码，可填空
	 * @param ext 服务端原样返回的参数，可填空
	 * @return {@link}SmsSingleSenderResult
	 * @throws Exception
	 */
	public SmsSingleSenderResult send(
			int type,
			String nationCode,
			String phoneNumber,
			String msg,
			String extend,
			String ext) throws Exception {
/*
请求包体
{
    "tel": {
        "nationcode": "86",
        "mobile": "13788888888"
    },
    "type": 0,
    "msg": "你的验证码是1234",
    "sig": "fdba654e05bc0d15796713a1a1a2318c",
    "time": 1479888540,
    "extend": "",
    "ext": ""
}
应答包体
{
    "result": 0,
    "errmsg": "OK",
    "ext": "",
    "sid": "xxxxxxx",
    "fee": 1
}
*/
		// 校验 type 类型
		if (0 != type && 1 != type) {
			throw new Exception("type " + type + " error");
		}
		if (null == extend) {
			extend = "";
		}
		if (null == ext) {
			ext = "";
		}

		// 按照协议组织 post 请求包体
		long random = util.getRandom();
		long curTime = System.currentTimeMillis()/1000;

		JSONObject data = new JSONObject();

		JSONObject tel = new JSONObject();
		tel.put("nationcode", nationCode);
		tel.put("mobile", phoneNumber);

		data.put("type", type);
		data.put("msg", msg);
		data.put("sig", util.strToHash(String.format(
				"secretkey=%s&random=%d&time=%d&mobile=%s",
				secretkey, random, curTime, phoneNumber)));
		data.put("tel", tel);
		data.put("time", curTime);
		data.put("extend", extend);
		data.put("ext", ext);

		// 与上面的 random 必须一致
		String wholeUrl = String.format("%s?accesskey=%s&random=%d", url, accesskey, random);
		HttpURLConnection conn = util.getPostHttpConn(wholeUrl);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
		wr.write(data.toString());
		wr.flush();

		System.out.println(data.toString());

		// 显示 POST 请求返回的内容
		StringBuilder sb = new StringBuilder();
		int httpRspCode = conn.getResponseCode();
		SmsSingleSenderResult result;
		if (httpRspCode == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			System.out.println(sb.toString());
			JSONObject json = JSONObject.parseObject(sb.toString());
			result = util.jsonToSmsSingleSenderResult(json);
		} else {
			result = new SmsSingleSenderResult();
			result.result = httpRspCode;
			result.errMsg = "http error " + httpRspCode + " " + conn.getResponseMessage();
		}

		return result;
	}

	/**
	 * 指定模板单发
	 * @param nationCode 国家码，如 86 为中国
	 * @param phoneNumber 不带国家码的手机号
	 * @param templId 信息内容
	 * @param params 模板参数列表，如模板 {1}...{2}...{3}，那么需要带三个参数
	 * @param sign 签名，如果填空，系统会使用默认签名
	 * @param extend 扩展码，可填空
	 * @param ext 服务端原样返回的参数，可填空
	 * @return {@link}SmsSingleSenderResult
	 * @throws Exception
	 */
	public SmsSingleSenderResult sendWithParam(
			String nationCode,
			String phoneNumber,
			String templId,
			ArrayList<String> params,
			String sign,
			String extend,
			String ext) throws Exception {
/*
请求包体
{
    "tel": {
        "nationcode": "86",
        "mobile": "13788888888"
    },
    "sign": "Kewail云",
    "tpl_id": 19,
    "params": [
        "验证码",
        "1234",
        "4"
    ],
    "sig": "fdba654e05bc0d15796713a1a1a2318c",
    "time": 1479888540,
    "extend": "",
    "ext": ""
}
应答包体
{
    "result": 0,
    "errmsg": "OK",
    "ext": "",
    "sid": "xxxxxxx",
    "fee": 1
}
*/
		if (null == nationCode || 0 == nationCode.length()) {
			nationCode = "86";
		}
		if (null == params) {
			params = new ArrayList<>();
		}
		if (null == sign) {
			sign = "";
		}
		if (null == extend) {
			extend = "";
		}
		if (null == ext) {
			ext = "";
		}

		long random = util.getRandom();
		long curTime = System.currentTimeMillis()/1000;

		JSONObject data = new JSONObject();

		JSONObject tel = new JSONObject();
		tel.put("nationcode", nationCode);
		tel.put("mobile", phoneNumber);

		data.put("tel", tel);
		data.put("signId", sign);
		data.put("templateId", templId);
		data.put("params", util.smsParamsToJSONArray(params));
		data.put("sig", util.calculateSigForTempl(secretkey, random, curTime, phoneNumber));
		data.put("time", curTime);
		data.put("extend", extend);
		data.put("ext", ext);
		data.put("type",0);

		String wholeUrl = String.format("%s?accesskey=%s&random=%d", url, accesskey, random);
		HttpURLConnection conn = util.getPostHttpConn(wholeUrl);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
		wr.write(data.toString());
		wr.flush();

		// 显示 POST 请求返回的内容
		StringBuilder sb = new StringBuilder();
		int httpRspCode = conn.getResponseCode();
		SmsSingleSenderResult result;
		if (httpRspCode == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			JSONObject json = JSONObject.parseObject(sb.toString());
			result = util.jsonToSmsSingleSenderResult(json);
		} else {
			result = new SmsSingleSenderResult();
			result.result = httpRspCode;
			result.errMsg = "http error " + httpRspCode + " " + conn.getResponseMessage();
		}

		return result;
	}

	public static boolean send(String nationCode,String phoneNumber, String code) {

		try {
			//初始化单发
			SmsSingleSender singleSender = new SmsSingleSender(BASE_ACCESS_KEY, BASE_SECRET_KEY);
			SmsSingleSenderResult singleSenderResult;

			//普通单发,注意前面必须为【】符号包含，置于头或者尾部。
			singleSenderResult = singleSender.send(0,nationCode, phoneNumber,
					MSG_PREFIX+code+MSG_SUFFIX,"","");
			System.out.println(singleSenderResult);

			return singleSenderResult.result==0;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
