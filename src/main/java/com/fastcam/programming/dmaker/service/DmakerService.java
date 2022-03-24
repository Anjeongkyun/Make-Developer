package com.fastcam.programming.dmaker.service;

import com.fastcam.programming.dmaker.dto.CreateDeveloper;
import com.fastcam.programming.dmaker.entity.Developer;
import com.fastcam.programming.dmaker.exception.DMakerException;
import com.fastcam.programming.dmaker.repository.DeveloperRepository;
import com.fastcam.programming.dmaker.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.fastcam.programming.dmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.fastcam.programming.dmaker.exception.DMakerErrorCode.LEVEL_EXEPERIENCE_YEARS_NOT_MATCHED;

@Service
@RequiredArgsConstructor
public class DmakerService {
    //@RequiredArgsConstructor를 써주면
    // RequiredArgsConstructor에 Injection 해줄 수 있음.
    // 자동으로 생성자를 만들어줌
    private final DeveloperRepository developerRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
            validateCreateDeveloperRequest(request);

        //business logic start
        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .name(request.getName())
                .age(request.getAge())
                .build(); // build 하면 끝.

        developerRepository.save(developer); //save를 사용하여 DB 영속화
        //business logic end

        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        //business validation
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();

        if(developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10){
            //throw new RuntimeException("SENIOR need 10 years experience.");
            throw new DMakerException(LEVEL_EXEPERIENCE_YEARS_NOT_MATCHED);
        }

        if(developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)){
            throw new DMakerException(LEVEL_EXEPERIENCE_YEARS_NOT_MATCHED);
        }

        if(developerLevel == DeveloperLevel.JUNIOR
                && experienceYears > 4){
            throw new DMakerException(LEVEL_EXEPERIENCE_YEARS_NOT_MATCHED);
        }

        developerRepository.findByMemberId(request.getMemberId())
        .ifPresent((developer -> {
            throw new DMakerException(DUPLICATED_MEMBER_ID);
        }));
    }
}
