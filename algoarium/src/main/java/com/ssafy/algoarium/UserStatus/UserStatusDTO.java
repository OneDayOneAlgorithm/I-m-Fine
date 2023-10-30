package com.ssafy.algoarium.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserStatusDTO {
	private long userStatusId;
	private long userId;
	private int userStatus1;
	private int userStatus2;
	private int userStatus3;
	private int userStatus4;
	private int userStatus5;

}
