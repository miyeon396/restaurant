## 프로젝트 개요
- Spring Batch를 활용하여 CSV 파일을 읽고 데이터베이스(MySQL)에 CSV 데이터를 저장한다.

## 프로젝트 결과
- Job 구성 
  - etlRestaurantJob
    - partitionedFileProcessingStep 
- Table 구성
  - TBL_RESTAURANT_INFO_L : CSV 파싱 데이터 
  - TBL_RESTAURANT_ERROR_L : 에러 관리 테이블
- 최종 결과 소요 시간 : 약 5m
- 처리 카운트 : 2,157,824(Data 오류로 인한 Skip 3)

## 상세 내용

### 개발 버전
- JDK 17 (Amazon Corretto 17.0.10)
- Spring Boot 3.3.2
- Spring Framework 6.1.3
- Spring Batch 5.1.2
- mysql 8.0.32
- gradle 8.8

### 프로젝트 설정 및 실행 가이드
1. 프로젝트 추가
   1) git-lfs 설치  
      - 업로드 파일 용량 제한으로 git-lfs 사용하여 csv 업로드 진행 
      - Clone시 git-lfs가 local에 설치 되어있어야 csv 원본 파일 다운로드가 가능
      - 설치 방법
        - brew install git-lfs
        - git lfs intall
   2) GitHub clone
   3) Project import
2. Local DB 추가
   - Local에 사용중인 MySQL을 사용할 경우
     - application.yml의 spring.datasource.url(url+database), username, password를 로컬 설정에 맞도록 변경 
   - Local에 사용중인 MySQL이 없는 경우
     1) MySQL 이미지 다운로드
     ```shell
     docker pull mysql:8.0.32
     ```
     2) MySQL 컨테이너 실행
     ```shell
     docker run --name mysql8-container -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=RESTAURANT -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbuser -p 3306:3306 -d mysql:8.0.32
     ```
      - 코드상 설정과 동일하게 MySQL 컨테이너 설정
      - Database : RESTAURANT
      - User : dbuser
      - Password : dbuser
     3) DB Tool을 (ex.DBeaver) 이용하여 접속 

3. Table 추가 
   - Spring Batch 관련 DDL은 별도 추가 없이 자동으로 생성되도록 적용 (batch.jdbc.initialize-schema=always)
   - 데이터베이스 접속 후 [README_DDL.md](README_DDL.md)의 스크립트 추가
4. Run Application
   - Application 실행 후 아래 배치 수행 방법을 선택
     1) 배치 자동 실행 - Application 실행 시 자동 수행
        - applcation.yml의 batch.job.enabled: true로 변경
        - Application 재실행 시 배치 동작
     2) 배치 자동 실행 - Schedule 자동 실행
        - 매일 19시 동작(KST)
     3) 배치 수동 실행 - postman
        - GET http://localhost:8080/start/etl-restaurant-job
     4) 배치 수동 실행 - CURL
        - curl --location 'http://localhost:8080/start/etl-restaurant-job'
5. 결과 확인
   - DB TBL_RESTAURANT_INFO_L 테이블 데이터 확인

### 테스트 가이드 
- 테스트의 경우 test.csv 파일로 진행합니다. (원본 데이터의 169038 ~ 169043 부분)

#### 기능 단위 테스트
- 구성
  - CsvPartitionerTest : 파티션 기능 테스트
  - CsvReaderTest : CSV Reader 기능 테스트
  - CsvWriterTest : 데이터 Save 기능 테스트
- 실행 방법
  - 각 테스트 파일에서 Run Test

#### 통합 테스트 (전체 배치 작업 실행)
- 구성
  - BatchConfigTest : Job 수행 통합 테스트 
- 실행 방법
  - BatchConfigTest 파일에서 Run Test

### Processing Time 
| 시도     | 상세 내용                                                                                                                                               | 소요시간     | Execute Time | 처리 결과                        | 비고                                           |
|--------|------------------------------------------------------------------------------|------------|--------------|------------------------------|------------------------------------------------|
| 1      | Chunk 1000                                                                   | 20m2s151ms  | 2024-08-13 00:07:14.774968 <br/> 2024-08-13 00:27:16.926867             | 2,157,824<br/> (skip 3)  | 너무 느림                                      |
| 2      | JPA Batch Insert 50개 추가                                                      | 18m1s555ms  | 2024-08-13 00:54:14.660754 <br/> 2024-08-13 01:12:16.216284               | 2,157,824<br/> (skip 3)  | 약간 시간은 단축되었으나, 큰 개선은 없음      |
| 3      | 병렬 처리 추가(TaskExecutor) <br/> 쓰레드풀 활용 여러 쓰레드가 step을 병렬로 처리              | 8m44s266ms   |  2024-08-13 01:22:25.440823 <br/> 2024-08-13 01:31:09.707325            | 2,157,824<br/> (skip 3)  | 많이 단축되었으나, FlatFileItemReader가 쓰레드 세이프 하지 않음 |
| 4      | 병렬처리 롤백 파티션 추가 (Size4) <br/> 4개의 파티션으로 나누고 각 파티션이 별도의 스레드에서 병렬 처리 | 35m53s410ms  |  2024-08-13 14:25:52.852065 <br/> 2024-08-13 15:01:46.262659            | 2,157,824<br/> (skip 6)  | 설정 오류 max값을 IntMax로 두어 하나의 파티션에 작업이 몰림. 파티션도 너무 작게 나눈거 같음 |
| 5      | 파티션 조정 Size10, maxRow 3000만                                                     | 8m1s252ms   |  2024-08-13 15:07:05.197553 <br/> 2024-08-13 15:15:06.449792            | 2,157,824<br/> (skip 6)  | 일을 하지 않고 노는 파티션이 있음             |
| 6      | 파티션 조정 Size10, maxRow220만, Core쓰레드5, Max쓰레드10                                     | 7m43s958ms  |  2024-08-13 15:20:25.370811 <br/> 2024-08-13 15:28:09.329512            | 2,157,824<br/> (skip 6)  | 나름 균등한 작업 분배지만 시간이 조금 아쉬움   |
| 7      | JPA Batch Inert 사이즈 100 조정                                                     | 8m52s768ms  |  2024-08-13 15:29:55.036049 <br/> 2024-08-13 15:38:47.827065            | 2,157,824<br/> (skip 6)  | Core가 5개라 대기가 있음                      |
| 8      | 7차 시도 + 파티션, 쓰레드 풀 조정 <br/> 파티션Size10, Core쓰레드10, Max쓰레드10                    | 5m47s171ms  |  2024-08-13 15:41:22.876491 <br/> 2024-08-13 15:47:10.047757            | 2,157,824<br/> (skip 6)  | 지금까지 제일 빠른 결과지만 쓰레드 여분을 두는게 안전할 것 같음 |
| 9      | 파티션, 쓰레드 풀 조정 <br/> 파티션Size15, Core쓰레드10, Max쓰레드15                             | 7m49s808ms  |  2024-08-13 15:48:35.656271 <br/> 2024-08-13 15:56:25.515787            | 2,157,824<br/> (skip 6)  | 엑셀 개수를 최적화를 시켜놓아서인지 여유 쓰레드가 많을수록 분배 + 대기 작업으로 속도가 느려짐 |
| 10     | 파티션, 쓰레드 풀 조정 <br/> 파티션Size12, Core쓰레드10, Max쓰레드12 <br/> Skip Count 중복 집계 처리     | 8m24s492ms  |  2024-08-13 15:58:16.830079 <br/> 2024-08-13 16:06:41.355699            | 2,157,824<br/> (skip 3)  | 큰 개선은 없어서 아예 동시 처리량이 많으면 개선이 될 것 같음 |
| 11     | 파티션, 쓰레드 풀 조정 <br/> 파티션Size20, Core쓰레드20, Max쓰레드20                                | 6m26s533ms  |  2024-08-13 16:09:10.557878 <br/> 2024-08-13 16:15:37.122560            | 1,992,825<br/> (skip 3)  | 디비가 힘들어함. 일부 파티션에서 DB 트랜잭션 얻지 못해 Fail |
| 12     | 파티션, 쓰레드 풀 조정 <br/> 파티션Size15, Core쓰레드15, Max쓰레드15                              | 7m43s638ms  |  2024-08-13 16:17:22.551454 <br/> 2024-08-13 16:25:06.222524            | 2,157,824<br/> (skip 3)  | maximum 15개가 적당한 걸로 판단               |
| 13     | 파티션, 쓰레드 풀 조정 <br/> 파티션Size20, Core쓰레드15, Max쓰레드20                               | 7m51s370ms  |  2024-08-13 16:34:45.857638 <br/> 2024-08-13 16:42:37.252293            | 2,157,824<br/> (skip 3)  | 최대 이 정도까지가 적당한 수치로 판단         |
| 14     | 파티션, 쓰레드 풀 기존 수치로 조정 <br> 파티션Size10, Core쓰레드10, Max쓰레드15                      | 6m30s857ms  |  2024-08-13 16:46:02.387795 <br/> 2024-08-13 16:52:33.276782            | 2,157,824<br/> (skip 3)  | 파티션 조정은 비슷할 것 같아서 이정도 조정으로 마무리. 다른 튜닝 포인트를 찾아야함 |
| 15     | JPA insert 전 select 현상 개선 <br> Persistable 구현                                    | 1m23s901ms  |  2024-08-13 17:49:04.308752 <br/> 2024-08-13 17:50:28.248287            | 2,157,824<br/> (skip 3)  | 성능은 아주 좋으나, 매 배치마다 새로운 데이터보다는 업데이트가 되어야하는 성격의 업무로 판단 |
| **16** | Persistable 제거 <br> 새데이터 Insert, 변경데이터 Update                                | 5m48s390ms   | 2024-08-13 19:43:23.622244 <br/> 2024-08-13 19:49:12.065694             | 2,157,824<br/>  (skip 3) | 최종 진행                                      |

