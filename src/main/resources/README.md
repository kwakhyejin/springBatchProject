  mysql  - 8.0.11
  java - 1.8
  spring - 2.7.11
1. start.spring.io 프로젝트 생성
2. gradle 프로젝트 인텔리제이 open
3. application.yaml 작성
4. 메인 클래스 @EnableBatchProcessing 작성
5. HelloWorldJobConfig 작성
6. Edit Configuration > Modify Options > program arguments = --job.name=helloWorldJob 작성후 실행