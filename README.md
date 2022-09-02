OClock은 MVVM 아키텍처에 기반하여 Hilt, Coroutines, Flow, Jetpack, Material Design 등으로 개발된 모던 안드로이드 앱입니다.

## Tech stack & Open-source libraries
- Kotlin 1.7.1
- Coroutines + Flow
- Jetpack
  - LifeCycle
  - ViewModel
  - DataBinding
  - Hilt
- Architecture
  - MVVM
  - Bindables : UI 데이터 업데이트를 위한 Databinding 오픈소스
  - Repository 패턴
- Network
  - Retrofit2 & OkHttp3
  - Sandwich : 네트워크 응답처리를 위한 인터페이스
  - Moshi : 코틀린 및 자바를 위한 모던 JSON 라이브러리

## Architecture
![Architecture](https://user-images.githubusercontent.com/53829792/188106174-8a1c9b0a-87e5-467c-a9ad-75c37a6336b8.png)
[Google 공식 아키텍처 가이드](https://developer.android.com/topic/architecture)에 기반한 MVVM, Repository 패턴을 사용했습니다.

![Overview](https://user-images.githubusercontent.com/53829792/188106212-48008f27-c931-47c8-becf-b3e04515638f.png)
전체 아키텍처는 UI와 데이터, 두 개의 계층으로 구성됩니다.
각 계층은 [단방향 이벤트/데이터 흐름](https://developer.android.com/topic/architecture/ui-layer#udf);에 따라 UI 계층은 데이터 계층에 이벤트를 전송하고 데이터 계층은 스트림(flow)으로 데이터를 업데이트 합니다.
데이터 계층은 다른 계층에 의지하지 않고 독립적으로 동작합니다.

이러한 loose coupling 을 통해 컴포넌트 재사용과 앱 확장성을 높일 수 있습니다.

## UI 계층
![UI Layer](https://user-images.githubusercontent.com/53829792/188106200-1607aada-7415-49da-bfc3-d5b38195887c.png)
UI 계층은 유저 인터페이스를 구성하는 UI 요소와 앱 상태를 유지하고 구성 변경 시 데이터를 복원하는 [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)로 구성됩니다.
UI 요소는 MVVM 아키텍처에서 가장 중요한 [DataBinding](https://developer.android.com/topic/libraries/data-binding)을 통해 데이터를 관찰합니다. 
OClock은 Android DataBinding Kit인 [Bindables](https://github.com/skydoves/bindables)를 통해 데이터 바인딩을 구현했습니다.

## 데이터 계층
데이터 계층은 로컬 데이터 및 네트워크 데이터와 같은 비즈니스 로직을 포함하는 Repository로 구성됩니다.

## 모듈화
![Modularization](https://user-images.githubusercontent.com/53829792/188106217-a6cc59bc-e2ed-44ed-947f-87be9a896c35.png)
[안드로이드 앱 모듈화 가이드](https://developer.android.com/topic/modularization)에 따라 모듈화하였습니다.

- **재사용성**: 재사용 가능한 코드를 적절히 모듈화하면 코드 공유가 가능해져 다른 모듈에서 재사용 가능해집니다.
- **병렬빌드**: 각 모듈을 병렬로 실행할 수 있어 빌드 시간이 단축됩니다.
- **엄격한 접근 관리**: 모듈화를 통해 다른 계층의 접근을 제한할 수 있습니다.
- **분산형 집중**: 각 개발자 팀은 전용 모듈을 할당 받아 자체 모듈 개발에 집중할 수 있습니다.