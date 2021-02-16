package com.example.parser;

import com.example.parser.config.ParserConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.example.parser.config.ParserConfig.SHOP_URL;

@Service
@AllArgsConstructor
public class Parser {

	private final RestTemplate restTemplate;

	public boolean itemHasInstruction(String itemCode) {
		for (String shopCode : ParserConfig.SHOP_CODES) {
			String shopUrl = String.format(SHOP_URL, itemCode, itemCode, shopCode);
			try {
				ResponseEntity<String> response = restTemplate.getForEntity(shopUrl, String.class);
				HttpStatus statusCode = response.getStatusCode();
				boolean isFound = statusCode == HttpStatus.OK;
				if (isFound) {
					return true;
				}
			} catch (HttpClientErrorException e) {
				// ignore
			}
		}
		return false;
	}

}
