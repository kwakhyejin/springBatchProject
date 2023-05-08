package com.example.springBatchProject.job.FileDataReadWrite.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Year;

@Data   /* 롬복에서 Data 사용시 @Getter, @Setter,
@RequiredArgsConstructor, @ToString, @EqualsAndHashCode을
한꺼번에 설정해주는 매우 유용한 어노테이션입니다. */

    public class PlayerYears {

        private String ID;
        private String lastName;
        private String firstName;
        private String position;
        private int birthYear;
        private int debutYear;
        private int yearsExperience;

        public PlayerYears(Player player){
            this.ID = player.getID();
            this.lastName = player.getLastName();
            this.firstName = player.getFirstName();
            this.position = player.getPosition();
            this.birthYear = player.getBirthYear();
            this.debutYear = player.getDebutYear();
            this.yearsExperience = Year.now().getValue() - player.getDebutYear();
        }

    }
