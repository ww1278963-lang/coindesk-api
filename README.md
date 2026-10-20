# coindesk-api

Spring Boot 專案，提供：

1. 幣別對應中文名稱資料表的 CRUD API
2. 呼叫 coindesk API，原樣回傳
3. 呼叫 coindesk API 並轉換資料，組成新的 API（更新時間＋幣別、中文名稱、匯率）

## 技術

| 項目 | 版本 |
|---|---|
| JDK | 8 |
| Build Tool | Maven（附 Maven Wrapper，不用另外安裝） |
| Framework | Spring Boot 2.7.18 |
| 資料庫 | H2（in-memory） |
| ORM | Spring Data JPA |
| 外部 API 呼叫 | RestTemplate |

## 執行

```bash
# Windows 用 mvnw.cmd
./mvnw spring-boot:run   # 啟動，預設 http://localhost:8080
./mvnw test              # 跑全部測試
```

H2 Console：`http://localhost:8080/h2-console`（JDBC URL `jdbc:h2:mem:coindesk`，帳號 `sa`，密碼空白）

Windows cmd 跑測試時中文若變亂碼，先執行 `chcp 65001` 切換成 UTF-8。

## 資料庫

- 建表：[`src/main/resources/schema.sql`](src/main/resources/schema.sql)
- 初始資料：[`src/main/resources/data.sql`](src/main/resources/data.sql)（USD 美元、GBP 英鎊、EUR 歐元）

```sql
CREATE TABLE currency (
    code    VARCHAR(3)  NOT NULL,
    name_zh VARCHAR(50) NOT NULL,
    PRIMARY KEY (code)
);
```

## API

### 幣別 CRUD `/api/currencies`

| 方法 | 路徑 | 說明 | 成功 | 失敗 |
|---|---|---|---|---|
| GET | `/api/currencies` | 查詢全部（依代碼排序） | 200 | |
| GET | `/api/currencies/{code}` | 查詢單筆 | 200 | 404 |
| POST | `/api/currencies` | 新增（代碼自動轉大寫） | 201 | 400、409 已存在 |
| PUT | `/api/currencies/{code}` | 修改中文名稱 | 200 | 400、404 |
| DELETE | `/api/currencies/{code}` | 刪除 | 204 | 404 |

```http
POST /api/currencies
Content-Type: application/json

{ "code": "JPY", "nameZh": "日圓" }
```

錯誤回應格式：`{ "message": "幣別不存在: JPY" }`

### coindesk 原始資料 `GET /api/coindesk/original`

原樣回傳 `https://kengp3.github.io/blog/coindesk.json` 的內容。

### 轉換後的 API `GET /api/coindesk/transformed`

```json
{
  "time": "2024/09/02 15:07:20",
  "bpi": {
    "EUR": { "code": "EUR", "nameZh": "歐元", "rate": 52243.2865 },
    "GBP": { "code": "GBP", "nameZh": "英鎊", "rate": 43984.0203 },
    "USD": { "code": "USD", "nameZh": "美元", "rate": 57756.2984 }
  }
}
```

轉換規則：

- 時間：取 `time.updatedISO`，轉成台灣時間，格式 `yyyy/MM/dd HH:mm:ss`
- 匯率：取 `rate_float`，用 `BigDecimal` 存
- 中文名稱：先查 `currency` 資料表，查不到用 JDK 內建的幣別中文名稱，都沒有就是 `null`
- 幣別依代碼排序
- coindesk 連不上時回 `502`

## 專案結構

```
controller/   接收請求
service/      商業邏輯（介面）、service/impl 實作
client/       呼叫外部 coindesk API
repository/   存取資料表
entity/       資料表對應的物件
dto/          請求、回應格式
exception/    自訂例外與統一錯誤處理
```

## 測試

`./mvnw test`，API 測試都會用 `print()` 印出請求與回應內容。

| 需求 | 測試 |
|---|---|
| 資料轉換的單元測試 | `CoindeskServiceImplTest`（用 Mockito 假造 coindesk 回應與資料表） |
| 呼叫幣別 CRUD API 並顯示內容 | `CurrencyControllerTest`（含 400、404、409 等錯誤情況） |
| 呼叫 coindesk API 並顯示內容 | `CoindeskControllerTest#getOriginal` |
| 呼叫轉換後的 API 並顯示內容 | `CoindeskControllerTest#getTransformed` |

`CoindeskControllerTest` 會實際連到外部 coindesk 網址，需要網路。
