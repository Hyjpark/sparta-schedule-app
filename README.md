#  일정 관리 API 명세서
> Base URL: `http://localhost:8080`

---

### 응답 필드 설명

| 필드명 | 타입 | 설명 |
|--------|-----|------|
| id | Long | 일정 ID |
| title | String | 일정 제목 |
| contents | String | 일정 내용 |
| author | String | 작성자명 |
| createdAt | LocalDateTime | 생성 시각 |
| updatedAt | LocalDateTime | 수정 시각 |
| comments | List | 해당 일정의 댓글 목록 |

#### comments 안의 각 항목:

| 필드명 | 타입 | 설명 |
|--------|------|------|
| id | Long | 댓글 ID |
| scheduleId | Long | 연관된 일정 ID |
| contents | String | 댓글 내용 |
| author | String | 댓글 작성자 |
| createdAt | LocalDateTime | 생성 시각 |
| updatedAt | LocalDateTime | 수정 시각 |

### 공통 오류 응답 형식

| 상태 코드 | 형식 | 설명 |
|-----------|------|------|
| 400 | JSON | 입력값 오류, 필수값 누락 등 |
| 401 | JSON | 비밀번호 불일치 등 인증 실패 |
| 404 | JSON | 해당 자원 없음 (예: 일정 ID 존재하지 않음) |

```json
{
  "code": "INVALID_PARAMETER",
  "message": "필수값이 누락되었습니다."
}
```
```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "요청한 리소스를 찾을 수 없습니다."
}
```
```json
{
    "code": "PASSWORD_MISMATCH",
    "message": "비밀번호가 일치하지 않습니다."
}
```
```json
{
  "timestamp": "2025-08-04T04:28:46.544+00:00",
  "status": 404,
  "error": "Not Found",
  "path": "/schedules/54/comments/1"
}
```
---
## 일정 (Schedule)

### 일정 생성
- **URL:** `POST /schedules`
- **설명:** 새로운 일정을 생성합니다.
- **요청 바디:**
```json
{
  "title": "일정제목",
  "contents": "일정내용",
  "author": "작성자명",
  "password": "1111"
}
```
- **입력 조건(Validation):**
  - `title`: 필수, 최대 30자
  - `contents`: 필수, 최대 200자
  - `author`: 필수 
  - `password`: 필수

- **응답 (201 Created):** 
```json
{
  "id": 1,
  "title": "일정제목",
  "contents": "일정내용",
  "author": "작성자명",
  "createdAt": "2025-08-04T10:21:23.7411568",
  "updatedAt": "2025-08-04T10:21:23.7411568"
}
```
- **응답 (400 Bad Request):**
```json
{
  "code": "INVALID_PARAMETER",
  "message": "필수값이 누락되었습니다."
}
```
```json
{
  "code": "TITLE_LENGTH_LIMITS",
  "message": "제목은 30자 이내로 제한됩니다."
}
```
```json
{
  "code": "CONTENTS_LENGTH_LIMITS",
  "message": "내용은 200자 이내로 제한됩니다."
}
```
---
### 전체 일정 조회
- **URL:** `GET /schedules`
- **설명:** 모든 일정을 수정일 기준으로 내림차순 정렬하여 조회합니다.
- **Query Parameters:**
  - `author` (string, optional): 작성자명으로 필터링
- **요청 예시:**
  - 전체 조회: `GET /schedules`
  - 작성자 필터: `GET /schedules?author=작성자`
- **응답 (200 OK):** 
```json
[
    {
        "id": 1,
        "title": "일정제목",
        "contents": "일정내용",
        "author": "작성자",
        "createdAt": "2025-08-04T10:21:24",
        "updatedAt": "2025-08-04T10:21:24"
    }
]
```
---

### 선택 일정 조회
- **URL:** `GET /schedules/{id}`
- **설명:** 특정 일정 1건과 해당 일정의 댓글 목록을 함께 조회합니다.
- **Path Variable:**
    - `id` (Long): 조회할 일정의 ID
- **요청 예시:** `GET /schedules/1`
- **응답 (200 OK):**
```json
{
  "id": 1,
  "title": "일정제목",
  "contents": "일정내용",
  "author": "작성자",
  "createdAt": "2025-08-04T10:21:24",
  "updatedAt": "2025-08-04T10:21:24",
  "comments": [
    {
      "id": 1,
      "scheduleId": 1,
      "contents": "댓글내용",
      "author": "댓글작성자",
      "createdAt": "2025-08-04T10:32:08",
      "updatedAt": "2025-08-04T10:32:08"
    }
  ]
}
```
- **응답 (404 Not Found):**
```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "요청한 리소스를 찾을 수 없습니다."
}
```

---

### 선택 일정 수정
- **URL:** `PATCH /schedules/{id}`
- **설명:** 일정의 제목과 작성자명을 수정합니다. 비밀번호가 일치해야 합니다.
- **Path Variable:**
    - `id` (Long): 수정할 일정의 ID
- **요청 예시:** `PATCH /schedules/1`
- **요청 바디:**
```json
{
    "title": "수정제목",
    "author": "수정작성자명",
    "password": "1111"
}
```
- **입력 조건(Validation):**
    - `title`: 필수, 최대 30자
    - `author`: 필수
    - `password`: 필수
  
- **응답 (200 OK):**
```json
{
  "id": 1,
  "title": "수정제목",
  "contents": "일정내용",
  "author": "수정작성자명",
  "createdAt": "2025-08-04T10:21:24",
  "updatedAt": "2025-08-04T10:35:10"
}
```
- **응답 (400 Bad Request):**
```json
{
  "code": "INVALID_PARAMETER",
  "message": "필수값이 누락되었습니다."
}
```
```json
{
  "code": "TITLE_LENGTH_LIMITS",
  "message": "제목은 30자 이내로 제한됩니다."
}
```
- **응답 (401 Unauthorized):**
```json
{
  "code": "PASSWORD_MISMATCH",
  "message": "비밀번호가 일치하지 않습니다."
}
```
- **응답 (404 Not Found):**
```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "요청한 리소스를 찾을 수 없습니다."
}
```

---

### 일정 삭제
- **URL:** `DELETE /schedules/{id}`
- **설명:** 특정 일정을 삭제합니다. 비밀번호가 일치해야 합니다.
- **Path Variable:**
    - `id` (Long): 삭제할 일정의 ID
- **요청 예시:** `DELETE /schedules/1`
- **요청 바디:**
```json
{
  "password": "1111"
}
```
- **응답 (200 OK):**
```json
{}
```
- **응답 (401 Unauthorized):**
```json
{
  "code": "UNAUTHORIZED",
  "message": "비밀번호가 일치하지 않습니다."
}
```

---

## 댓글 (Comment)

### 댓글 생성
- **URL:** `POST /schedules/{id}/comments`
- **설명:** 특정 일정에 댓글을 작성합니다.
- **Path Variable:**
  - id (int): 댓글을 작성할 일정의 ID
- **요청 바디:**
```json
{
  "contents": "댓글내용",
  "author": "댓글작성자",
  "password": "1111"
}
```
- **입력 조건(Validation):**
  - `contents`: 필수, 최대 100자 
  - `author`: 필수 
  - `password`: 필수
  
- **응답 (201 Created):**
```json
{
  "id": 1,
  "scheduleId": 1,
  "contents": "댓글내용",
  "author": "댓글작성자",
  "createdAt": "2025-08-04T11:22:44.5235052",
  "updatedAt": "2025-08-04T11:22:44.5235052"
}
```
- **응답 (400 Bad Request):**
```json
{
  "code": "INVALID_PARAMETER",
  "message": "필수값이 누락되었습니다."
}
```
```json
{
  "code": "COMMENT_COUNT_EXCEEDED",
  "message": "댓글 개수는 10개까지는 허용됩니다."
}
```
```json
{
  "code": "CONTENTS_LENGTH_LIMITS",
  "message": "내용은 100자 이내로 제한됩니다."
}
```

---

# ERD
<img width="615" height="385" alt="캡처" src="https://github.com/user-attachments/assets/7f0d7d57-0a3f-4bf3-a53d-d84db36c9087" />

