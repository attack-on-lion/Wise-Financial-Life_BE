<div align="center">

<!-- logo -->
<img src="https://user-images.githubusercontent.com/80824750/208554611-f8277015-12e8-48d2-b2cc-d09d67f03c02.png" width="400"/>

### Project: 슬기로운 소비생활(Slgm)

<br/> [<img src="https://img.shields.io/badge/프로젝트 기간-2025.07.25~2025.08.24-green?style=flat&logo=&logoColor=white" />]()

</div> 

## 📝 소개
이 앱은 무조건 아끼는 절약이 아니라, '낭비를 줄이고 가치 있는, 실용성 있는 '현명한 선택'을 할 수 있게 도와주고자 합니다.

- 프로젝트 소개
- 프로젝트 화면 구성 또는 프로토 타입
- 프로젝트 API 설계
- 사용한 기술 스택
- 프로젝트 아키텍쳐
- 기술적 이슈와 해결 과정
- 프로젝트 팀원



<br />


## 🗂️ APIs
작성한 API는 아래에서 확인할 수 있습니다.

👉🏻 [API 바로보기](https://band-collar-06a.notion.site/APIs-256cf133eeae80ae9e1cea382453b671?source=copy_link)


<br />

## ⚙ 기술 스택
### Back-end
<div>
<img src="https://img.icons8.com/?size=100&id=90519&format=png&color=000000" width="80">
<img src="https://img.icons8.com/?size=100&id=A3Ulk2RcONKs&format=png&color=000000" width="80">
<img src="https://img.icons8.com/?size=100&id=JRnxU7ZWP4mi&format=png&color=000000" width="80">
</div>

### Infra
<div>
<img src="https://img.icons8.com/?size=100&id=e6uRfPIDgoXi&format=png&color=000000" width="80">
</div>

### Tools
<div>
<img src="https://img.icons8.com/?size=100&id=3tC9EQumUAuq&format=png&color=000000" width="80">
<img src="https://img.icons8.com/?size=100&id=nvtEH6DpqruC&format=png&color=000000" width="80">
</div>

<br />


<br />

## 🤔 기술적 이슈와 해결 과정
- Over-Fetch 와 Under-Fetch 개념 및 대안
  - [오버 페치(Over-Fetch)와 언더 페치(Under-Fetch)란?](https://happydhkim.tistory.com/entry/%EC%98%A4%EB%B2%84-%ED%8E%98%EC%B9%98Over-Fetch%EC%99%80-%EC%96%B8%EB%8D%94-%ED%8E%98%EC%B9%98Under-Fetch%EB%9E%80)
- 순환참조 해결을 위한 CQRS 패턴
  - [Spring CQRS 패턴](https://rebugs.tistory.com/895)
- 글로벌 예외 처리
  - [Spring Java 예외(Exception) 처리 전략](https://turtledev.tistory.com/67)
- 트랜잭션에서 외부 API 호출(ai) 
  - [트랜잭션 내에서 외부 API 호출을 하겠다고요?!!! ](https://engineerinsight.tistory.com/412)


<br />

## 👥 백엔드 팀원
|Backend|Backend|Backend|
|:---:|:---:|:---:|
| ![](https://github.com/holychann.png?size=120) | ![](https://github.com/todaysunny612.png?size=120) | ![](https://github.com/higakaga.png?size=120)|
|[조성찬](https://github.com/holychann)|[최희선](https://github.com/todaysunny612)|[이동호](https://github.com/higakaga)

<br />

| 이름 | 담당 |
|------|------|
| 조성찬 | 유저, 결제내역, 포인트, 캐릭터, 아이템, 카테고리, 솔루션, 알림 도메인 <br />CI/CD 파이프라인 / 인프라 관리 (AWS S3, ECR, App Runner) / API, ERD 설계 |
| 최희선 | 유저, 제휴, 기프티콘 도메인 / API, ERD 설계 |
| 이동호 | 챌린지 도메인 / API, ERD 설계 |

<br />

### ✍️ My Contribution 

<details>
<summary><b>조성찬 (DevOps)</b></summary>
<br />
<br />
  

> \[!NOTE]
> 아래 항목들은 “담당 파트/스택” 섹션을 **반복하지 않고**, 제가 **직접 주도/기여**한 내용과 **문제 해결 중심**으로 정리했습니다.

<br />

---
## 하이라이트

<br />

* **글로벌 예외 처리 & 검증 체계 설계/구현**  
  `BusinessException` + `ErrorCode` 기반으로 예외를 상태코드/커스텀_상태코드/메시지 로 일관화 하였습니다.
  
* **API 에러 구체화(Concretization)**  
  검증 실패/도메인 제약 으로 원인을 세분화하여 디버깅 시간 단축 및 클라이언트 피드백에 신경을 썼습니다.
  
* **CQRS 패턴 적용**  
  순환 참조가 발생하여 해결 방법을 찾았고, `Command`/`Query` 를분리하여 **순환 참조** 를 제거하였습니다.
  
* **비용 최적화**  
  AWS 를 처음 사용하는 상황에서 VPC 등으로 private 한 환경을 구축했으나, 예상하지 못한 과금으로 대안을 찾던 중 public 의 존쟁 확인 후 변경.
  배포 비용 절감, 배포 유지기간 증가
    
* **의사결정&트레이드오프 → 최적화 vs 편리함**  
  해커톤 맥락(낮은 트래픽, 짧은 기간)을 고려해 **컴포넌트별 개별 호출** 채택(일부 화면 최대 7회 요청).
  

<br />

---

## 문제 → 해결 → 결과

<details>
<summary><b>1. 순환 참조 이슈</b> → CQRS 도입 → <i>서비스 역할 분담으로 해결</i></summary>

* **Problem**:  서비스 간 상호 호출로 인해 **순환 참조** 가 일어남.
  
* **Solution**:  `CommandService`(변경/트랜잭션)와 `QueryService`를 분리하여 역참조 제거
  
* **Outcome**:  **빌드 안정성↑**, **테스트 독립성↑**, 기능 추가 시 영향 범위 명확화.

</details>

<details>
<summary><b>2. AWS 과금 이슈</b> → Public 존재 확인 및 적용 → <i>저렴한 배포 비용</i></summary>

* **Problem**:  VPC 연동이 기본이라고 생각하여 **네트워킹 비용 발생**.(해당 해커톤 AWS 사용비용 지원)
  
* **Solution**:  해커톤 맥락에서 **Public 배포 옵션**을 재검토/적용.
  
* **Outcome**:  **월 비용 절감**, 배포/테스트 속도 개선.

</details>


<br />

---

## 의사결정&트레이드오프 → 최적화 vs 편리함

<br />

> [!IMPORTANT]  
> **상황**: 특정 화면에서 **최대 7회**의 API 호출 발생(캐시 도입 예정).  
> **논의 주제**: 화면 단위 **집계·조합 1회 응답** vs. **컴포넌트별 개별 호출**.

### 선택지 요약
- **A. 단일 복합 응답(도메인 집계/조합)**: 서버가 필요한 데이터를 모아 **1회 응답** (Composite DTO 등)
- **B. 컴포넌트별 개별 호출**: 화면의 각 위젯/모듈이 **필요한 API를 독립 호출**

### 나의 관점(A 지지)
- REST API 를 최적화 하여 오버페치 / 언더페치 제거(네트워크 낭비, 불필요한 데이터)
- 자주 요청이 일어나는 화면에서 **네트워크 비용 절감** 가능

### 팀장(프론트 리드)의 관점(B 지지)
- **DTO 변동·버전 관리 비용** 최소화 → 개발 속도↑
- **역할 분담 명확**, 컴포넌트 재사용 용이

### 결론(해커톤 맥락)
- **B 채택**: 낮은 트래픽, 개발 기간, 개발 편의성 우선, 불필요한 논쟁 회피
- 결과: **개발 속도·협업 명확성** 를 확보하였습니다.(서버 최적화 관점의 트레이드오프는 수용함)

### 사후 계획(출시 이후)
- REST API 최적화를 **트래픽 높은 화면**부터 순차적으로 도입 예정입니다.
- 필요 시 **GraphQL**로 오버페치 제어

<br />

---

## 아키텍처/패턴

* **CQRS**패턴 도입으로 도메인 쓰기와 읽기를 분리하여 **확장성/안정성**을 확보하였습니다.
* **글로벌 예외/검증 레이어**로 예외 일관성을 유지하였습니다.

<br />

---

## 협업&리더쉽

* **API 명세 주도**: 엔드포인트/페이로드/에러 케이스 주도하여 관리하였습니다.
* **코드 리뷰 & 컨벤션 정립**: 브랜치 전략, 커밋 메시지 규칙, 패키지 구조/네이밍 가이드 등을 정리하여 팀원과 공유하였습니다.
* **문서화**: README/ERD 등을 직접 관리하고 변경사항이 생기는 즉시 적용 및 공유하였습니다.

<br />

---

</details>

<!-- 여기는 주석입니다. details 로 드롭다운 구역이 나뉩니다. 자신의 칸에서 자유롭게 작성해주세요.  -->

<details>
<summary><b>최희선 (Backend)</b></summary>

---
## 하이라이트

<br />

* **기프티콘 전체 조회 정렬 기준 변경**  
  기존에는 `createdAt + id DESC` 기준으로 최신순 조회하던 것을,  
  **브랜드명(가나다 오름차순) → 기프티콘명(사전식 오름차순)** 순으로 조회하도록 변경하였습니다.

* **Cursor 기반 페이지네이션 개선**  
  Cursor DTO에 `lastStoreName`, `lastGifticonName` 필드를 추가하여  
  프론트가 다음 페이지 요청 시 그대로 파라미터로 활용할 수 있게 했습니다.

* **프론트-백엔드 협업 편의성 강화**  
  API 명세에 "첫 페이지 호출 → nextCursor 활용한 다음 페이지 호출" 과정을 문서화하여,  
  프론트에서 페이지네이션 동작 방식을 이해하기 쉽게 하였습니다.

* **정규표현식 기반의 상세 필드 검증 설계**  
  전화번호(010-0000-0000), 이메일 형식, 성별(남/여/남성/여성/남자/여자) 등 중요 입력값에 대해 커스텀 정규식과 메시지 적용을 통한 사용자의 입력 오류를 사전에 차단하였습니다.

<br />

---

## 문제 → 해결 → 결과

<details>
<summary><b>1. 전체 조회 UX 불편</b> → Cursor 정렬 기준 변경 → <i>브랜드 단위 가독성 상승</i></summary>

* **Problem**:  기프티콘 전체 조회 시 단순 등록일 기준이라, 사용자가 보기에는 브랜드별 묶임이 없어 불편함.

* **Solution**:  정렬 기준을 *브랜드명(가나다 오름차순) -> 상품명* 으로 변경.

* **Outcome**:  사용자가 기대하는 *브랜드 단위 UI*가 구현되어 가독성과 사용성이 개선됨.

</details>

<details>
<summary><b>2. 불필요한 필드 덮어쓰기 이슈 발생 </b> → 필드 전용 DTO + 부분 업데이트 방식 적용 → <i> 의도된 변경만 안전하게 반영 </i></summary>

* **Problem**:  클라이언트에서 이름만 수정해도 나머지 필드가 빈 문자열로 덮이는 이슈가 발생함. 이는 setter 남용에서 기인했음.

* **Solution**:  수정 가능한 필드만 가진 `UserRequestDTO`를 별도로 설계하고,  
  Converter 내부에서 `Entity.updateXXX()` 식의 부분 업데이트 방식으로 전환.  
  또한 유효성 검증을 통해 **빈 값이 다른 필드를 덮지 않도록 설계**.

* **Outcome**:  
  - 실제 수정 의도와 무관한 필드 변경 방지
  - 클라이언트 측 실수에 대한 내구성 증가
  - 유지보수성과 테스트 용이성 증가

</details>


<br />

---

## 의사결정&트레이드오프 → 최적화 vs 편리함

<br />

> [!IMPORTANT]  
> **상황**: 전체 조회는 **브랜드 가나다순**기준으로 바꾸었지만, 브랜드별 조회는 여전히 **createdAt** 기준.

### 선택지 요약
- **A. 두 API 모두 정렬 기준을 통일**
- **B. 전체 조회는 브랜드 기준, 브랜드별 조회는 등록일 기준**

### 선택 근거
- 사용자 입장 : 전체 조회 -> 브랜드별 분류 필요, 브랜드 상세 -> 최신 등록순 확인 필요.
- 개발 난이도 : Cursor 조건 분기 복잡성을 최소화.


### 결론
- **B 채택**: 사용자 경험에 맞춰 조회 목적에 따라 정렬 기준을 다르게 유지
- 결과: API 역할이 명확해지고, 프론트에서 구현할 때 혼동이 줄어듦.

<br />

---

## 아키텍처/패턴

* **Cusor 기반 페이지네이션** : 무한 스크롤 대응 및 정렬 일관성을 확보하였습니다.

<br />

---

## 협업&리더쉽

* **API 명세서 업데이트**: 프론트 팀에 페이지네이션 동작(첫 호출/다음 호출 규칙)을 문서화하여 전달하였습니다.
* **리뷰 대응**: 리뷰어 피드백 반영 후 정렬 기준, 커서 구조를 개선하였습니다.
* **변경사항 공유**: 깃 이슈/PR/코멘트에 변경 배경과 효과를 설명해 리뷰와 의사결정을 원활하게 진행하였습니다. 

<br />

---



<br />

—-- 

</details>

<!-- 여기는 주석입니다. details 로 드롭다운 구역이 나뉩니다. 자신의 칸에서 자유롭게 작성해주세요. -->

<details>
<summary><b>이동호 (Backend)</b></summary>
<br />
<br />

> \[!NOTE]
> 아래 항목들은 “담당 파트/스택” 섹션을 **반복하지 않고**, 제가 **직접 주도/기여**한 내용과 **문제 해결 중심**으로 정리했습니다.

<br />

---
## 하이라이트

<br />

* **OpenAI API 프롬프트 엔지니어링**  
  소비 패턴 데이터(결제 이력, 카테고리별 지출 등)를 기반으로 챌린지 생성 프롬프트를 설계하여, 개인 맞춤형 챌린지를 자동으로 생성했습니다.  

* **REST API 통신**  
  OpenAI 모델 결과를 가공하여 REST API 응답으로 제공, 클라이언트에서 즉시 챌린지를 등록/조회할 수 있도록 구현했습니다.

<br />

---

## 문제 → 해결 → 결과

<details>
<summary><b>1. 챌린지 생성 품질 저하</b> → 프롬프트 엔지니어링 → <i>정확도/활용도 개선</i></summary>

* **Problem**:  단순한 프롬프트 설계로 인해 **일관성 없는 챌린지**가 생성됨  
* **Solution**:  소비 카테고리·금액·기간을 구조화하여 프롬프트에 삽입, 기대 출력 포맷(JSON Schema)까지 명시  
* **Outcome**:  챌린지 생성의 **일관성 확보**, 사용자 맞춤도 ↑  

</details>

<details>
<summary><b>2. 모델 출력 활용 어려움</b> → REST API 통신 → <i>서비스 연계 자동화</i></summary>

* **Problem**:  OpenAI API의 결과가 텍스트 형태로만 제공 → 서비스에서 바로 활용 불가  
* **Solution**:  Spring 기반 REST API 서버에서 모델 응답을 가공 후 JSON 응답으로 반환  
* **Outcome**:  프론트엔드와 실시간 연계, 챌린지 자동 생성/저장 프로세스 완성  

</details>

<br />

---

## 아키텍처/패턴

* **프롬프트 엔지니어링 패턴화**: 데이터 기반 템플릿 설계 및 JSON Schema 기반 출력 제어  
* **Spring + REST API 구조**: 챌린지 생성 결과를 서비스 전반에 공유 가능  

<br />

---

## 협업&리더쉽

* **API 명세 주도**: 챌린지 생성/조회 엔드포인트 정의 및 공유  
* **프롬프트 템플릿 관리**: 프론트/기획 팀과 협의하여 사용자 친화적 챌린지 생성 로직 설계  

<br />

---

</details>


<details>
<summary><b>○○○ (DevOps)</b></summary>

- CI/CD 파이프라인 구축 (GitHub Actions)  
- AWS 인프라 구성 (VPC, S3, CloudFront)  
- 모니터링 및 로그 관리  

</details>

<br />

