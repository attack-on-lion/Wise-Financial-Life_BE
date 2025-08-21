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
- Stream 써야할까?
  - [Stream API에 대하여](https://velog.io/@yewo2nn16/Java-Stream-API)
- Gmail STMP 이용하여 이메일 전송하기
  - [gmail 보내기](https://velog.io/@yewo2nn16/Email-이메일-전송하기with-첨부파일)
- AWS EC2에 배포하기
  - [서버 배포하기-1](https://velog.io/@yewo2nn16/SpringBoot-서버-배포)
  - [서버 배포하기-2](https://velog.io/@yewo2nn16/SpringBoot-서버-배포-인텔리제이에서-jar-파일-빌드해서-배포하기)


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
<summary><b>조성찬 (Backend)</b></summary>
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

- React 기반 UI 개발  
- 사용자 친화적 UX 설계  
- Axios를 통한 API 연동  
- 기프티콘/스토어 화면 구현  

</details>

<!-- 여기는 주석입니다. details 로 드롭다운 구역이 나뉩니다. 자신의 칸에서 자유롭게 작성해주세요. -->

<details>
<summary><b>이동호 (Backend)</b></summary>

- 소비 습관 분석 모델링 (XGBoost, LSTM)  
- 데이터 정제 및 학습 파이프라인 구축  
- 모델 결과를 REST API 형태로 제공  

</details>

<details>
<summary><b>○○○ (DevOps)</b></summary>

- CI/CD 파이프라인 구축 (GitHub Actions)  
- AWS 인프라 구성 (VPC, S3, CloudFront)  
- 모니터링 및 로그 관리  

</details>

<br />

