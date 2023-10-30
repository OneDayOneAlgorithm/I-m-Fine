package com.ssafy.algoarium.Tag;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

/**
 * TagDTO 클래스는 태그 정보를 전달하는 데이터 전송 객체입니다.
 */
public class TagDTO {
    private Long tagId; // 태그 고유 식별자
    private String tagName; // 태그 이름

    /**
     * TagDTO 객체를 TagEntity 객체로 변환합니다.
     *
     * @param tagDTO 변환할 TagDTO 객체
     * @return TagEntity 객체
     */
    public static TagEntity toTagEntity(TagDTO tagDTO) {
        return TagEntity.builder()
                .tagName(tagDTO.getTagName())
                .build();
    }
}
