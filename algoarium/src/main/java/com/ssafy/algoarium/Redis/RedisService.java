package com.ssafy.algoarium.Redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisRepository redisRepository;

	public void saveByRedisDto(RedisDto redisDto){
		if(redisRepository.existsById(redisDto.getRefreshToken())){
			System.out.printf("%s is already exist\n", redisDto.getRefreshToken());
		}
		else{
			redisRepository.save(redisDto);
			System.out.printf("%s save complete!!\n", redisDto.getRefreshToken());
		}
	}

	public Optional<RedisDto> findByRedisDto(String id){
		return redisRepository.findById(id);
	}


	public List<RedisDto> findAllRedisDto(){
		List<RedisDto> list = new ArrayList<>();

		Iterator<RedisDto> iterator = redisRepository.findAll().iterator();

		while (iterator.hasNext()){
			list.add(iterator.next());
		}
		return list;
	}

	public void deleteById(String id){
		redisRepository.deleteById(id);
		System.out.printf("Redis %s delete complite!!\n", id);
	}

	public void updateRedisDto(RedisDto redisDto){
		if(redisRepository.existsById(redisDto.getRefreshToken().toString())){
			redisRepository.save(redisDto);
		}
		else{
			System.out.printf("%s is not exist!!",redisDto.getRefreshToken());
		}
		System.out.println("Upd ate DB Process is complete!!");
	}

}
