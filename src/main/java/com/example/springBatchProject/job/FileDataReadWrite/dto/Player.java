package com.example.springBatchProject.job.FileDataReadWrite.dto;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data   /* 롬복에서 Data 사용시 @Getter, @Setter,
@RequiredArgsConstructor, @ToString, @EqualsAndHashCode을
한꺼번에 설정해주는 매우 유용한 어노테이션입니다. */

    public class Player {

        private String ID;
        private String lastName;
        private String firstName;
        private String position;
        private int birthYear;
        private int debutYear;

    }
