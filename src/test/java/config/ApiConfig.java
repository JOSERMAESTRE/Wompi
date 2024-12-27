package config;

public class ApiConfig {
	
	private static final String BASE_URL = "https://api-sandbox.co.uat.wompi.dev/v1";
	private static final String PUB_KEY = "pub_stagtest_g2u0HQd3ZMh05hsSgTS2lUV8t3s4mOt7";
	private static final String PRV_KEY = "prv_stagtest_5i0ZGIGiFcDQifYsXxvsny7Y37tKqFWg";
	private static final String INTEGRITY_KEY = "stagtest_integrity_nAIBuqayW70XpUqJS4qf4STYiISd89Fp";
	private static final String EVENT_KEY = "stagtest_events_2PDUmhMywUkvb1LvxYnayFbmofT7w39N";
	

	public static String getBaseUrl() {
		return BASE_URL;
	}

	public static String getPublicKey() {
		return PUB_KEY;
	}

	public static String getPrivateKey() {
		return PRV_KEY;
	}
	
	public static String getIntegrityKey() {
		return INTEGRITY_KEY;
	}
	public static String getEventKey() {
		return EVENT_KEY;
	}
}
