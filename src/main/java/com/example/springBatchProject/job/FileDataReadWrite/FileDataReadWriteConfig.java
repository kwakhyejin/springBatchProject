package com.example.springBatchProject.job.FileDataReadWrite;

import com.example.springBatchProject.job.FileDataReadWrite.dto.Player;
import com.example.springBatchProject.job.FileDataReadWrite.dto.PlayerYears;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.List;
/*
 *  desc : 파일 읽고 쓰기
 *  run : --spring.batch.job.names=fileReadWriteJob
 * */
@Configuration
@RequiredArgsConstructor
public class FileDataReadWriteConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job fileReadWriteJob(Step fileReadWriteStep){
        return jobBuilderFactory.get("fileReadWriteJob")
                .incrementer(new RunIdIncrementer())
                .start(fileReadWriteStep)
                .build();
    }

    @JobScope
    @Bean
    public Step fileReadWriteStep(ItemReader playerItemReader,
                                  ItemProcessor playerItemProcessor,
                                  ItemWriter playerItemWriter){
        return stepBuilderFactory.get("fileReadWriteStep")
                .<Player, PlayerYears>chunk(5)
                .reader(playerItemReader)
//                .writer(new ItemWriter() { => reader가 잘 가지고 오는지 먼저 프린트로 확인하기!!!
//                    @Override
//                    public void write(List items) throws Exception {
//                        items.forEach(System.out::println);
//                    }
//                })
                .processor(playerItemProcessor) // 경험년도 구해서 playerYears 객체 집어넣기
                .writer(playerItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemWriter<PlayerYears> playerItemWriter(){

        BeanWrapperFieldExtractor<PlayerYears> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"ID","lastName","position","yearsExperience"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<PlayerYears> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        FileSystemResource outputResource = new FileSystemResource("player-output.csv");

        return new FlatFileItemWriterBuilder<PlayerYears>()
                .name("playerItemWriter")
                .resource(outputResource)
                .lineAggregator(lineAggregator)
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Player, PlayerYears> playerItemProcessor(){
        return new ItemProcessor<Player, PlayerYears>() {
            @Override
            public PlayerYears process(Player item) throws Exception {
                return new PlayerYears(item);
            }
        };
    }
    @StepScope
    @Bean
    public FlatFileItemReader<Player> playerItemReader(){
        return new FlatFileItemReaderBuilder<Player>()
                .name("playerItemReader")
                .resource(new FileSystemResource("Players.csv")) // 루트에 있기때문ㅇ에 파일명만 작성해주면됨
                .lineTokenizer(new DelimitedLineTokenizer())
                .fieldSetMapper(new PlayerFieldSetMapper())
                .linesToSkip(1)
                .build();
    }

}
