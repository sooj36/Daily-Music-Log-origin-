
  
![UML 318](https://github.com/user-attachments/assets/3f1f54b3-9880-47f3-82bc-4be5f48d0632)

<div align="center">
  
# Daily Music Log

 </div>
 
## ▷ 기간 및 인원
- 2024.08.19 ~ 2024.10.10 (2개월)  /  AOS 1명(개인 프로젝트)


## ▷ 소개
- 오늘의 노래를 검색하고 간단한 메모와 함께 저장할 수 있는 어플



------------

## (1) 검색 페이지 / 선택 트랙 페이지 / 로컬 DB 저장 트랙 로드 페이지

<div align="center">
  
![image](https://github.com/user-attachments/assets/eb8726f1-a9cd-4454-b08f-5cb6713db8c3)   

 </div>

<div align="center">
  
## 트러블 슈팅(1)

### < 상황 및 과제 >

> ①-[track.search] API의 { Track명 / Artist명 / 포스터URL } 의 실시간 데이터 값을 UI에 업데이트 했을 때,

> **앨범 포스터 URL이 모두 기본 이미지로 로드되는 문제**

> (이미지 URL이 모두 기본 이미지)

</div>

- ②-[track.getInfo] API에서 실제 포터스터 URL이 나오는 것을 발견
- Track / Artist 객체를 어떻게 담을 수 있을지 고민하다 기존 ① 요청 결과로 두 메서드의 값을 알 수 있으니 API①의 결과값으로, API② 데이터를 요청 받는 것으로 구상
- 먼저 두 API를 활용하여 데이터가 불러와지는 것을 확인하고 싶었기에
**"클릭한 데이터" 기준의 URL을 가져오기로 결정**
  
<div align="center">
  
### 해결 및 과제

**[데이터 클래스 설계 오류]**

"하나의 검색어에 여러 앨범 데이터가 반환된다"는 가정하에 요청 받은 앨범 포스터 URL 반환 값을 List<Album> 으로 설정
▷ 두 APi를 호출하는 과정에서 반환 값이 틀리는 에러

<div align="center"> 
  
**(T 과제)**

</div>
API 반환 구조와 실제 데이터의 관계에 따라 정확한 데이터 반환하도록 수정

<div align="center"> 
  
**→(A 행동)**

</div>

받을 Album 데이터 "들" 이라고 생각해 List로 표현했던 문제
하나의 검색어에 대한 여러 개의 앨범 정보를 담는 것이 아니라,
한 데이터당 한 앨범의 고유 정보를 담아
각 데이터들의 List<Track>이 만들어 진다는 것을 발견
2번째 API(포스터앨범)을 반환 받기 위해 List<Track2> 로 변경  

<div align="center"> 
  
**(A 행동)-1**

</div>

mapNotNull 을 활용하여 유효한 데이터 리스트만 담으려 했지만,
mapNotNull은 결과를 List로 묶기에 반환 값이 List<List<Track2>>가 되는 문제로 flatten으로 평탄화 해주려 함

다만, 앨범 포스터 URL 하나만 가져오면 되는데, List로 가져오는 게 비효율적이라는 생각이 듦
필요 이상의 많은 데이터를 수집하게 됨으로 코드 변경

<div align="center"> 

**(A 행동)-2**

</div>

특정 트랙의 앨범 포스터 URL만 가져오는 것에 집중하여 구조 최적화
"선택한 트랙 정보" 하나에 집중하여,
단일 API 호출로 해당 트랙의 앨범 이미지 URL만 가져오는 방식으로 변경

-----------------------------------------------------------

![image](https://github.com/user-attachments/assets/2c80def8-a250-4b73-b56b-79d5de7f5bac)

-----------------------------------------------------------

- **(R)**
불필요한 데이터 리스트가 아닌 필요한 앨범 포스터 URL만 가져오는 구조로 최적화하여 필요한 
데이터를 즉시 가져와 UI 업데이트 원활하게 이루어지도록 개선

---


#### **Coil과 Glide 비교**

 </div>. 
 
**Glide가 메모리 사용량이 더 낮은 이유**

▶ 다층 캐싱 구조로 로드 전 메모리와 디스크 캐시를 확인하여 이미 로드한 이미지가 있으면 즉시 반환하여 네트워크 호출 및 메모리 사용량을 효율적으로 관리

▶ 뷰의 크기에 맞춰 이미지 리사이징

**Coil 선택 이유**

(1) 데이터가 많아질수록 더 빠르게 이미지 로드

▶ 코루틴을 사용하여 비동기 작업 효율적으로 처리

▶ Okhttp 등을 활용하여 네트워크 요청과 이미지 처리를 최적화

(2) Jetpack Compose에 최적화

▶ 'AsyncImage' 컴포저블을 사용하여 이미지를 쉽게 로드 가능하며,

빈번히 재구성되는 환경에서 생명주기가 호환되게 설계되어

컴포넌트가 화면에서 사라질 때 자동으로 취소되어

리소스도 자동으로 해제된다는 점과 메모리 누수 위험이 덜 발생




------------

## (2) 선택한 트랙 페이지

![image](https://github.com/user-attachments/assets/f750b781-b395-4cce-8522-04085113015f)

------------

## (3) 수정 페이지

![image](https://github.com/user-attachments/assets/6168b91e-27b2-4aa4-949f-1a4e5e709d29)


## ▷ 회고
![image](https://github.com/user-attachments/assets/a9de9e65-0f48-48d6-a6bb-7790ac231bd8)

<div align="center">
  
: ViewModel과 Repository 클래스가 주입된 의존성을 직접 생성하지 않고, 외부에서 주입받아 사용하는 것 외부 종속성이 있는 ViewModel 생성을 위한 Factory 클래스를 작성하지 않아 코드 자체도 간결 해졌고, 어노테이션을 통한 스코프 지정으로 생명주기를 자동으로 관리해 준다는 점
또한 별도의 코드 작성 없이 @Singleton 스코프를 적용해 전역적으로 관리하는 객체를 쉽게 주입할 수 있어 전체적인 흐름이 보여 가독성 면에서 한눈에 보여 좋았습니다.

</div>

![image](https://github.com/user-attachments/assets/346bed48-5de7-4edd-958c-db3c6bd72ca7)

<div align="center">

: Daily Music Log에서 앱이 크러쉬 되는 상황을 직접 맞닥트리면서 메모리 관리에 대해 직접적으로 배울 수 있는 시간이었습니다.
그동안 비동기 프로그래밍 자체만으로 효율적인 메모리 사용이라고 생각했었습니다
이번 프로젝트로 코루틴 적용 시 코루틴 스코프에서 디스패처를 따로 설정해 주지 않으면 메인 스레드에서 실행된다는 것을 배웠고, 
역할에 따라  해당하는 스레드에서 처리할 수 있게 하는 Dispatchers 용도,
도메인 계층에는 코루틴 스코프 객체에서 중단 함수를 노출시켜서는 안되고, 그 구분은 표현 계층(Impl)에서 해주는 것으로 
도메인 계층에서 코루틴을 시작하려면 다른 중단 함수를 호출하는 중단함수로 표현해 줘야 한다는 것을 배웠습니다.
특히나 코루틴에 관심이 처음 생겼을 때 [코틀린 코루틴]이라는 책을 도서관에서 처음 펼쳤을 때에 눈에 담기지 않던 활자였지만,
프로젝트를 진행하며 구매해 필요한 부분들을 찾아 읽어가던 때의 느낌은 막연했던 개념들이 구체화되어 학습의 갈피가 잡히는 경험이었습니다

</div>
 

##### UI (User Interface) : Compose , Coil
##### 아키텍처 (Architecture) : Clean Architecture, MVVM (Model-View-ViewModel)
##### 네트워킹 (Networking) : REST API, Retrofit2, OkHttp3
##### 데이터 관리 (Data Management) : Room
##### 비동기 처리 및 데이터 흐름 (Asynchronous Processing and Data Flow) :Coroutines/Flow
##### 의존성 주입 (Dependency Injection) :Dagger Hilt



