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

**(S)**
- "하나의 검색어에 여러 앨범 데이터가 반환된다"는 가정하에 요청 받은 앨범 포스터 URL 반환 값을 List<Album>으로 설정
▷ **두 API 호출하는 과정에서 반환 값이 틀리는 에러**

**(T)**
- API 반환 구조와 실제 데이터의 관계에 따라 정확한 데이터 반환하도록 수정

**(A)**
- 받을 Album 데이터 "들" 이라고 생각해 List로 표현했던 문제

**(A)-1**
- mapNotNull을 활용하여 유효한 데이터 리스트만 담으려 했지만,
mapNotNull은 결과를 List 로 묶기에 반환 값이 List<List<Track2>>가
되는 문제로 flatten으로  평탄화 해주려 함

다만, 앨범 포스터 URL 하나만 가져오면 되는데, List로 가져오는 게 비효율적이라는 생각이 듦
필요 이상의 많은 데이터를 수집하게 됨으로 코드 변경

**(A)-2**
- **특정 트랙의 앨범 포스터 URL만 가져오는 것에 집중하여** 구조 최적화
"선택 트랙 정보" 하나에 집중하여,
단일 API 호출로 트랙의 앨범 이미지 URL만 가져오는 방식으로 변경

----------------------------------------------------------------

특정 트랙 선택
       ↓
선택한 track / artist을 활용한 앨범 포스터 URL 요청 함수 호출
      ↓
요청 받은 URL을 가져와 Dispatchers.Main으로 메인 스레드로 전환하여
UI 상태 업데이트
       ↓
가져온 URL을 _getAlbumImage 상태 변수에 저장하여 UI 최신 값 로드

----------------------------------------------------------------


**(R)**
- 불필요한 데이터 리스트가 아닌 필요한 앨범 포스터 URL만 가져오는 구조로 최적화하여 필요한 데이터를 즉시 가져와 UI 업데이트 원활하게 이루어지도록 개선

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



