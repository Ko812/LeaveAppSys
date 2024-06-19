package sg.nus.iss.com.Leaveapp.io;

import java.util.Deque;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.random.RandomGeneratorFactory;

public class Encoder {

	Random rand = new Random();
	
	private String encodingMethod;
	
	private Map<Character, Character> encoder;

	public Encoder(String encodingMethod) {
		super();
		this.encodingMethod = encodingMethod;
	}
	
	public Encoder() {
		super();
		this.encodingMethod = "defaultEncoder";
	}
	
	
	
	public String encode(String plainText) {
		char[] cipherText = plainText.toCharArray();
		for(int i = 0; i < cipherText.length; i++) {
			cipherText[i] = encoder.get(cipherText[i]);
		}
		return String.copyValueOf(cipherText);
	}
	
	private void defaultEncoderBuilder() {
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		

	}
	
	
}
