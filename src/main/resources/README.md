  mysql  - 8.0.11
  java - 1.8
  spring - 2.7.11
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