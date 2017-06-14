# 프로토콜 정의



## 전원 관련

- 최상위는 type, data(모든 데이터) 로 되어 있다. 



|                        | Pi->System           | System->Pi                               | Pi -> all System                         |
| :--------------------: | :------------------- | :--------------------------------------- | :--------------------------------------- |
|         ADD ON         |                      | Server 에서 리스트 가지고 있음                     |                                          |
|      Check Power       |                      | type : chk_conn ( 30sec )                |                                          |
|       AP Setting       | type : ack, value: 1 | type : set_ap, <br> ssid : 123, <br>psw:123 <br> open : true | request와 동일                              |
|      DHCP Setting      | type : ack, value: 1 | type : set_dhcp<br> start_ip : 192.168.191.1 <br> end_ip : 192.168.191.255 <br> rental_time : 100000 | request와 동일                              |
|     Connect Device     |                      | type : ack, value: 1                     | type : device_list, <br> [ip : 192.168.0.1,<br> mac: 0X3:...] |
| PortForwarding Setting |                      | type : set_portfwd <br> [name : first_pattrern, <br> ip : 192.168.12.10, <br> in_port : 80, <br> out_port:20] | request와 동일                              |
|      QOS Setting       |                      | type: set_qos,  down : [{min: (최소속도),max: (최대속도)}],up : [{min: (최소속도),max: (최대속도)}] | request와 동일                              |
|   StartPage Setting    |                      | type:set_start_page <br> url : www.naver.com | request와 동일                              |
|     Reset Setting      |                      | type:set_reset                           | 기본 설정 파일                                 |
|      File Upload       |                      |                                          | type : upload_file, <br> [name : temp.txt, size:1.51MB] |
|                        |                      |                                          |                                          |
|                        |                      |                                          |                                          |
|                        |                      |                                          |                                          |
|                        |                      |                                          |                                          |
|                        |                      |                                          |                                          |
|                        |                      |                                          |                                          |
|                        |                      |                                          |                                          |
|                        |                      |                                          |                                          |
|                        |                      |                                          |                                          |