package me.jspark.springdev;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//기본 생성자 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//생성자 자동 생성
@AllArgsConstructor
//겟 메서드 자동생성
@Getter
//엔티티클래스 어노테이션
@Entity
public class Member {
    //PK 지정
    @Id
    //값 자동생성
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
}
