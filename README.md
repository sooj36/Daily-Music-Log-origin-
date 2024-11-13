### UML

# DailyMusicLog
#### 기간 및 인원
- 2024.08.19 ~ 2024.10.10 (2개월)  /  AOS 1명(개인 프로젝트)

#### 소개
- 오늘의 노래를 검색하여 저장하고 간단한 메모를 함께 저장할 수 있는 어플

#### 트러블 슈팅(1)
#### 상황 및 과제
- ①-[track.search] API의 { Track명 / Artist명 / 포스터URL } 의 실시간 데이터 값을 UI에 업데이트 했을 때, **앨범 포스터 URL이 모두 기본 이미지로 로드되는 문제**
(이미지 URL이 모두 기본 이미지)

- ②-[track.getInfo] API에서 실제 포터스터 URL이 나오는 것을 발견
- Track / Artist 객체를 어떻게 담을 수 있을지 고민하다 기존 ① 요청 결과로 두 메서드의 값을 알 수 있으니 API①의 결과값으로, API② 데이터를 요청 받는 것으로 구상
- 먼저 두 API를 활용하여 데이터가 불러와지는 것을 확인하고 싶었기에
**"클릭한 데이터" 기준의 URL을 가져오기로 결정**

#### 해결 및 과제
**[데이터 클래스 설계 오류]**

![image](https://github.com/user-attachments/assets/58cdde3d-a5df-44bb-9350-875087fdd70a)



![image](https://github.com/user-attachments/assets/2334da79-4859-4cea-b62d-796c979d30b3)

----------------------------------------------------------------

![image](https://github.com/user-attachments/assets/2c80def8-a250-4b73-b56b-79d5de7f5bac)

----------------------------------------------------------------

![image](https://github.com/user-attachments/assets/d29a0f7f-79e6-4d3b-bc54-5e833c13a073)


## 기술스택
1. Compose
2. Clean Architecture
3. MVVM
4. Coil
5. REST API, Retrofit2, Okhttp3
6. Room
7. Coroutines / Flow
8. Dagger hilt

##### UI (User Interface) : Compose , Coil
##### 아키텍처 (Architecture) : Clean Architecture, MVVM (Model-View-ViewModel)
##### 네트워킹 (Networking) : REST API, Retrofit2, OkHttp3
##### 데이터 관리 (Data Management) : Room
##### 비동기 처리 및 데이터 흐름 (Asynchronous Processing and Data Flow) :Coroutines/Flow
##### 의존성 주입 (Dependency Injection) :Dagger Hilt



