package com.java4.sd20303.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VideoDeleteResponse {
	private String message;
	private boolean status;
}
