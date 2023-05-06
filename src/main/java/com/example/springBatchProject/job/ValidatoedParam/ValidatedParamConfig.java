package com.example.springBatchProject.job.ValidatoedParam;

import com.example.springBatchProject.job.ValidatoedParam.Validator.FileParamValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/*
 *  desc : 파일 이름 파라미터 전달 그리고 검증
 *  run : --spring.batch.job.names=ValidatedParamJob -fileName=test.csv
 * */
@Configuration
@RequiredArgsConstructor
public class ValidatedParamConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job validatedParamJobConfig(Step validatedParamStep) {
        return jobBuilderFactory.get("ValidatedParamJob")
                .incrementer(new RunIdIncrementer())
//                .validator() 파일 검증할때 job에서 검증 가능 그러나 지금은 validator 파일을 통해서 검증할것임!!
//                .validator(new FileParamValidator()) // => 다른 job에서도 재사용 가능함.
                .validator(multiValidator()) // 다수의 validator 등록하기 !
                 .start(validatedParamStep)
                .build();
    }

    private CompositeJobParametersValidator multiValidator(){
       CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
       validator.setValidators(Arrays.asList(new FileParamValidator()));    // 여기서 배열로 다수의 validator로 등록을 할수 있다.
        return validator;
    }

    @JobScope
    @Bean
    public Step validatedParamStep(Tasklet validatedParamTasklet) {
        return stepBuilderFactory.get("validatedParamStep")
                .tasklet(validatedParamTasklet)
                .build();
    }

    /*
    * @Value는 롬복이 아닌 스프링 어노테이션 사용
    * */
    @StepScope
    @Bean
    public Tasklet validatedParamTasklet(@Value("#{jobParameters['fileName']}") String fileName) {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println(fileName);
                System.out.println("validated Param Tasklet");
                return RepeatStatus.FINISHED;
            }
        };
    }
}






