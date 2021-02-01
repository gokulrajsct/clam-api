package nz.co.datacom.mlc.api.clamav;
import java.io.IOException;
import fi.solita.clamav.ClamAVClient;
//import nz.co.datacom.mlc.api.security.MLCRoleManager;
import java.util.Base64;
import java.util.Base64.Decoder;

//import javax.servlet.http.HttpServletRequest;

//import nz.co.datacom.mlc.api.config.MLCEnvironmentVars;
//import nz.co.datacom.mlc.api.config.MLCStrings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


@RestController
public class ClamAVProxy {

	@Value("${clamd.host}")
	private String hostname;

	@Value("${clamd.port}")
	private int port;

	@Value("${clamd.timeout}")
	private int timeout;
//	@Autowired
//    HttpServletRequest request;
//	@Autowired
//    MLCEnvironmentVars vars;

	/**
	 * @return Clamd status.
	 */
	@RequestMapping("/")
	public String ping() throws IOException {
		ClamAVClient a = new ClamAVClient(hostname, port, timeout);
		return "Clamd responding: " + a.ping() + "\n";
	}

	/**
	 * @return Clamd scan result
	 */
	@RequestMapping(value = "/v1/clamav/scan", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestHeader("name") String name, @RequestBody String data)
			throws IOException {
		//MLCRoleManager.checkRoles(request);
		Object obj=JSONValue.parse(data);  
		JSONObject json = (JSONObject) obj;
		String body = (String) json.get("file");
		System.out.println(json);
		if (!body.isEmpty()) {
			
			System.out.println("this is a text");
			ClamAVClient a = new ClamAVClient(hostname, port, timeout);
			Decoder decoder = Base64.getMimeDecoder();
			byte[] decodedBytes = decoder.decode(body);
			byte[] r = a.scan(decodedBytes);
			return "Everything ok : " + ClamAVClient.isCleanReply(r) + "\n";
		} else
			throw new IllegalArgumentException("empty file");
	}

	/**
	 * @return Clamd scan reply
	 */
	/*@RequestMapping(value = "/scanReply", method = RequestMethod.POST)
	public @ResponseBody String handleFileUploadReply(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			ClamAVClient a = new ClamAVClient(hostname, port, timeout);
			return new String(a.scan(file.getInputStream()));
		} else
			throw new IllegalArgumentException("empty file");
	}*/
}
