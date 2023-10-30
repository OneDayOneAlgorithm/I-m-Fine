package com.ssafy.algoarium.UserStatus;

import com.ssafy.algoarium.User.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_status")
public class UserStatusEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_status_id",nullable = false)
	private long userStatusId;

	@Setter
	//enum class 만들어서 처리
	@Column(name =  "wis" , length = 50, nullable = false)
	private int userStatus1 = 0;

	@Setter
	@Column(name =  "con" , length = 50, nullable = false)
	private int userStatus2 = 0;

	@Setter
	@Column(name =  "str" , length = 50, nullable = false)
	private int userStatus3 = 0;

	@Setter
	@Column(name =  "luk" , length = 50, nullable = false)
	private int userStatus4 = 0;

	@Setter
	@Column(name =  "sma" , length = 50, nullable = false)
	private int userStatus5 = 0;

	// 보강 필요
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id") // 연관되는 컬럼 지정
	private UserEntity user;

	@Builder
	public UserStatusEntity(long userStatusId, int userStatus1, int userStatus2
		, int userStatus3, int userStatus4, int userStatus5 , UserEntity user){
		this.user = user;
		this.userStatusId = userStatusId;
		this.userStatus1 = userStatus1;
		this.userStatus2 = userStatus2;
		this.userStatus3 = userStatus3;
		this.userStatus4 = userStatus4;
		this.userStatus5 = userStatus5;
	}

}
