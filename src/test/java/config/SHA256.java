package config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
	public String calculateSHA256(String secretkey, String currency, String amountInCents, String reference, String Timestamp) {
		try {
			String ConcatString ="";
			//System.out.println(Timestamp);
			if(Timestamp.equals("")) {
				ConcatString = reference + amountInCents + currency + secretkey;
			}else {
				ConcatString= reference + amountInCents + currency+Timestamp + secretkey;
			}
			//System.out.println(ConcatString);
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] hashBytes = digest.digest(ConcatString.getBytes());

			StringBuilder hashHex = new StringBuilder();
			for (byte b : hashBytes) {
				hashHex.append(String.format("%02x", b));
			}

			return hashHex.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
