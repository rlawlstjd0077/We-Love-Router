<h2>프로토콜 정의</h2>

최상위는 type, data(모든 데이터) 로 되어 있다.<br>
REPORT 때의 type 뒤에는 "_report"를 추가한다.<br>
ex) type: set_ap_report<br>

- REQUEST : Pi->System
- RESPONSE : System->Pi
- REPORT : Pi ->all System<br><br>

<table>
    <tbody>
    <tr>
        <th></th>
        <th align=center>Req</th>
        <th align=center>Res</th>
        <th align=center>Rep</th>
    </tr>
    <tr>
        <td>ADD ON</td>
        <td align="center">-</td>
        <td>
           Server 에서 리스트 가지고 있음
        </td>
        <td></td>
    </tr>
    <tr>
        <td>Check Power</td>
        <td>
        </td>
        <td>
            type : chk_conn (every 30 sec)
        </td>
        <td>
        </td>
    </tr>
    <tr>
        <td>AP Setting</td>
        <td>
            type : set_ap_ack,<br>
            value: 1
        </td>
        <td>
            type : set_ap,<br>
            ssid : 123,<br>
            psw:123,<br>
            open : true
        </td>
        <td>
            request와 동일
        </td>
    </tr>
    <tr>
        <td>DHCP Setting</td>
        <td>
            type : set_dhcp_ack,<br>
            value: 1
        </td>
        <td>
            type : set_dhcp,<br>
            start_ip : 192.168.191.1,<br>
            end_ip : 192.168.191.255,<br>
            rental_time : 100000(ms)
        </td>
        <td>
            request와 동일
        </td>
    </tr>
    <tr>
        <td>Connect Device</td>
        <td></td>
        <td>
            type : ack,<br>
            value: 1
        </td>
        <td>
             type : device_list,<br>
             connList:[{<br>
             &nbsp;&nbsp;ip : 192.168.0.1,<br>
             &nbsp;&nbsp;mac: 0X3ASDW<br>
             },{<br>
             &nbsp;&nbsp;ip : 192.168.0.2,<br>
             &nbsp;&nbsp;mac: 0X3ASDSSW<br>
             }...]
        </td>
    </tr>
    <tr>
        <td>PortForwarding Setting</td>
        <td></td>
        <td>
            type : set_portfwd,<br>
            pattern:[{<br>
            &nbsp;&nbsp;name : first_pattrern,<br>
            &nbsp;&nbsp;ip : 192.168.12.10,<br>
            &nbsp;&nbsp;in_port : 80,<br>
            &nbsp;&nbsp;out_port:20<br>
            },{<br>
            &nbsp;&nbsp;name : first_pattrern,<br>
            &nbsp;&nbsp;ip : 192.168.12.10,<br>
            &nbsp;&nbsp;in_port : 80,<br>
            &nbsp;&nbsp;out_port:20<br>
            }...]
        </td>
        <td>
             request와 동일
        </td>
    </tr>
    <tr>
        <td>QOS Setting</td>
        <td></td>
        <td>
             type: set_qos,<br>
             down : [{<br>
             &nbsp;&nbsp;min: (최소속도),<br>
             &nbsp;&nbsp;max: (최대속도),<br>
             }],<br>
             up : [{<br>
             &nbsp;&nbsp;min: (최소속도),<br>
             &nbsp;&nbsp;max: (최대속도)<br>
             }]
        </td>
        <td>
             request와 동일
        </td>
    </tr>
    <tr>
        <td>StartPage Setting</td>
        <td></td>
        <td>
             type: set_start_page,<br>
             url : www.naver.com
        </td>
        <td>
             request와 동일
        </td>
    </tr>
    <tr>
        <td>Reset Setting</td>
        <td></td>
        <td>
             type: set_reset
        </td>
        <td>
             기본 설정 파일
        </td>
    </tr>
    <tr>
        <td>File Upload</td>
        <td></td>
        <td>
        </td>
        <td>
             type : upload_file,<br>
             fileInfo:[{<br>
             &nbsp;&nbsp;name : temp.txt,<br>
             &nbsp;&nbsp;size:1.51MB<br>
             }]
        </td>
    </tr>
</tbody>
</table>

// 네트워크 다중화<br>