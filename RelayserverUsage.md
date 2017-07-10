# Relay Server 사용법

- 3-1 반에 해당하는 모든 제어시스템과 RFI 들의 id 와 ip를 저장하고 조회하는 기능을 제공합니다.



 ### - 기본정보

| title        | desc                                     |
| ------------ | ---------------------------------------- |
| Resource URI | 10.156.145.163:8080/service?{ parameter } |
| HTTP Method  | Get Method                               |
<br>
<br>

 ### - Querystring Parameters

| Name | Data Type | Example                                  | Description                              |
| ---- | --------- | :--------------------------------------- | ---------------------------------------- |
| type | String    | "get_info"                               | 기능의 옵션 정보 입니다. <br><br> get_info : 입력된 id에 맞는 ip 를 반환<br>get_info_list : RFI 혹은 제어시스템의 모든 info 들을 반환 <br>reg_info : ip 와 id 값을 등록(변경) |
| data | String    | data : [<br> ip : 10.156.145.163,<br> id: RFI_30101<br>] | 필요한 data 파라미터 들의 묶음 입니다. <br> <br>  &lt; request 예시 &gt; <br> get_info :  <br>  &nbsp; data : { <br>&nbsp; &nbsp;  id : RFI_30107 <br> &nbsp; } <br><br> get_info_list : <br> &nbsp; data : { <br> &nbsp; &nbsp; id : RFI ( or PC) <br> &nbsp; }<br><br> reg_info :<br> &nbsp; data : { <br> &nbsp; &nbsp; id : RFI_30107, <br> &nbsp; &nbsp; ip : 10.156.145.163  <br> &nbsp; } |
<br>
<br>

 ### - Response Parameters

| Name | Data Type | Example                                  | Description                              |
| ---- | --------- | :--------------------------------------- | ---------------------------------------- |
| type | String    | "get_info_resp"                          | 기능의 옵션 정보 응답 입니다. <br><br> get_info_resp : 입력된 id에 맞는 ip 를 반환<br>get_info_list_resp : RFI 혹은 제어시스템의 모든 info 들을 반환 <br>reg_info_reps : ip 와 id 값을 등록(변경) |
| data | String    | data : [<br> ip : 10.156.145.163,<br> id: RFI_30101<br>] | 각 동작의 성공적 응답의 예시 입니다. <br> <br>  &lt; response 예시 &gt; <br> get_info :  <br>  &nbsp; data : { <br>&nbsp; &nbsp;  id : RFI_30107, <br> &nbsp; &nbsp; ip :10.156.145.163  <br> &nbsp; } <br><br> get_info_list : <br> &nbsp;data : [ <br> &nbsp;&nbsp;  { <br> &nbsp;&nbsp;&nbsp; &nbsp; id : RFI_30107,  <br> &nbsp; &nbsp; &nbsp; &nbsp; ip : 10.156.175.45<br> &nbsp;&nbsp;&nbsp;&nbsp; }, <br> &nbsp; &nbsp; { &nbsp; &nbsp; <br> &nbsp;&nbsp;  id : RFI_30106, <br> &nbsp; &nbsp; &nbsp; ip : 10.156.175.45 <br> &nbsp; &nbsp; } <br> &nbsp; ]&nbsp; <br> &nbsp; } <br><br> reg_info :<br> &nbsp; data : { <br> &nbsp; &nbsp; msg : 성공적으로 처리됨  <br> &nbsp; } |
<br>
<br>

 ### - Error code

| Message        | HTTP Status Code          |
| -------------- | ------------------------- |
| 시스템 오류 입니다.    | 500 Internal Server Error |
| 요청 데이터 오류 입니다. | 400 Bad Request           |
| 성공적으로 수행되었습니다. | 200 OK                    |
