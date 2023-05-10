  mysql  - 8.0.11
  java - 1.8
  spring - 2.7.11
  
깃헙 주소 : https://github.com/junngo/spring-batch-tutorial

  - 1강
1. 일단 실행 - Hello, World(스프링 배치 구조 익히기)
   1. start.spring.io 프로젝트 생성
   2. gradle 프로젝트 인텔리제이 open
   3. application.yaml 작성
   4. 메인 클래스 @EnableBatchProcessing 작성
   5. HelloWorldJobConfig 작성
   6. Edit Configuration > Modify Options > program arguments = --job.name=helloWorldJob 작성후 실행

- 2강
1. 배치 실행 시 파라미터(파일 이름) 받기 및 (csv) 검증
   1. ValidatedParamConfig 파일 생성
   2. 파라미터로 job step tasklet 주입하기
   3. 검증
      1. job 메서드에서 validate() 하기
      2. 클래스를 사용하여 job메서드에서 주입하여 validate()하기
      3. 확장) multiValidator()를 이용하여 여러개의 검증 validate()하기
   4. 실행
      1. Edit Configuration... > Program arguments > --spring.batch.job.names=ValidatedParamJob -fileName=test.csv 입력
      2. 실행. validate 검증 로직 확인

- 3강
1. 배치 작업 실행 전, 후 로그 추가를 위한 리스너
   1.  JobLoggerListener 클래스 생성
      1. JobExecutionListener implements 하기
      2. beforeJob, afterJob 오버라이드 하기
      3. beforeJob => 로그 찍기 / 
      4. afterJob => 로그 찍기 / 실패시 로그 찍어주기=> 실제로 실패시 메신저나 이메일을 보내줄수 있음.

- 4강
1. DB 데이터 이관 하기( DB 데이터 읽고 쓰기)
   1. RepositoryWriterBuilder 이용하여 데이터 저장
   2. ItemWriter 직접 jpa 메서드 호출하여 저장

- 5강
1. 배치 작업의 기본, 파일 읽기와 쓰기
   1. 샘플 데이터 만들기 ( csv 파일 )  
      1. 참고 데이터 => https://docs.spring.io/spring-batch/docs/current/reference/html/index-single.html#simpleDelimitedFileReadingExample > #Simple Delimited File Reading Example
`     2. Players.csv 파일 만들어 복사 붙어넣기 
      3. Player Dto 만들기 > class Player => 롬복 어노테이션 @Data 를 이용하여 getter,setter,toString까지 생성될수 있음
      4. PlayerDataReadWriteConfig 클래스 만들기
         1. job만들기 (fileReadWriteJob)
         2. step 만들기 (fileReadWriteStep)
         3. reader 만들기 (playItemReader)
            1. resource 메서드 => path가 기본이 루트로 잡혀 있음.
            2. fieldSetMapper메서드 => PlayerFieldSetMapper 클래스 생성하기
            3. linesToSkip(1) => csv파일에 첫번째 줄은 설명이기때문에 스킵
            4. reader로 잘 읽어오는지 확인후에 writer를 하는것이 좋음 (System.out.println)
         4. processor 만들기 
            1. experienceYear 만들기
         5. Writer 만들기 => 새로운 파일 만들기 ( Player-output.csv )
* FieldExtractor 부분 읽기

- 6강
1. 여러개의 step 구동 및 실행 상태에 따른 분기처리
   1. 여러개의 step을 단계 별로 구현 => MultipleStepJobConfig
   2. step 내에서 다음 step 으로 데이터를 전달 ExecutionContext 이용
   3. step 실행시 분기 처리 => ConditionalStepJobConfig


*** 강의 다 듣고 나면 처음부터 다시 듣고 정리해서 블로그 옮기기