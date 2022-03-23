package com.fastcam.programming.dmaker.service;

import com.fastcam.programming.dmaker.entity.Developer;
import com.fastcam.programming.dmaker.repository.DeveloperRepository;
import com.fastcam.programming.dmaker.type.DeveloperLevel;
import com.fastcam.programming.dmaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DmakerService {
    //@RequiredArgsConstructor를 써주면
    // RequiredArgsConstructor에 Injection 해줄 수 있음.
    // 자동으로 생성자를 만들어줌
    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper(){
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNGNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(2)
                .name("jeongkyun")
                .memberId("1")
                .age(5)
                .build(); // build 하면 끝.

        developerRepository.save(developer); //save를 사용하여 DB 영속화
    }
}
