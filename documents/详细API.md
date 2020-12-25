CODE:

200 : OK

400 : BadRequest       （请求的包体里少了参数）

401 : Unauthorized      （这个表示未登录，先放着，我们还没有搞访问Token）

403 : Forbidden      （在踢人或者看人的时候没有权限啥的）

404 : NotFound     （对于某个id未找到）

500 : Internal Server Error     （这个东西会由django框架自动给出）

------

我们的网址是http://62.234.0.67:8000/

例如我要获取123的个人信息，我就往http://62.234.0.67:8000/user/self/123/这个网址get一下

------

这是后端的三人需要注意的：

任务人员或者状态啥的发生变动的时候，都会产生一个由id=1的user发送给对应人的一个message，type为Notice

这个具体哪些时候要发，你们觉得什么时候要发就在那里写吧。。反正这就是一个通知而已。。。

------



```json
除了获取验证码，注册和登录，其它的request body里都要加两项"senderID"和"passwordAfterRSA"
例如获取个人信息的/user/self/{ID}，他的request body在下面写的是none
那么它其实应该是
{
    "senderID" : 123,
    "passwordAfterRSA" : "xxxxxx"
}

例如修改个人信息/user/info/{ID}的request body在下面写的是
{
    "username" : "abc",
    "tele" : "QQ1xxx"
}
那么它其实应该是
{
    "senderID" : 123,
    "passwordAfterRSA" : "xxxxxx",
    "username" : "abc",
    "tele" : "QQ1xxx"
}

如果出现senderID找不到人，password错误，senderID和URL上的发起者不一致等情况都会返回
response:
status_code = 401
body:
"Unauthorized"
```



------

GET类



获取个人信息

```json
URL : /user/self/{ID}

request body : none

response:
OK:
status_code = 200
body =
{
	"mailbox" : "xxxxx@pku.edu.cn",    //string
    "username" : "abc",
    "tele" : "QQ1xxxx",   //这个是联系方式，让用户填的string
	"missionIDs" : [123, 456],
    "evaluationIDs" : [123, 456], //这里的evaluationIDs是用户被做过的评价
    "violationIDs" : [123, 456],  //被举报记录
    "averageScore" : 9.87,
}

Not Found:
status_code = 404
body =
"user Not Found"
```



获取他人信息

```json
URL : /user/member/{getter_ID}/{mission_ID}/{gettee_ID}
//getter要看gettee

request body : none

response:
OK:
status_code = 200
body =
{
    "missionStatus" : "tele can be seen",    //string   //or "tele can not be seen"
    "tele" : "QQ1xxx",      //string,如果"tele can not be seen"则没有这项
    "averageScore" : 9.87
}
//当两人在mission_ID的任务里时"tele can be seen"
//当getter不在mission_ID的任务里，gettee在mission_ID的任务里时"tele can not be seen"

getter Not Found:
status_code = 404
body=
"getter Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

gettee Not Found:
status_code = 404
body=
"gettee Not Found"

//三个Not Found的优先级从高到低

Bad Request:
status_code = 400
body=
"Bad Request"
//自己看自己

Forbidden:
status_code = 403
body=
"Forbidden"
//gettee不在mission_ID的任务里
```



获取用户做过的所有评价的列表（这里是自己做出过的评价）

```json
URL : /user/evaluations/{ID}

request body = none

response:
OK:
status_code = 200
body=
[123, 456]

Not Found:
status_code = 404
body=
"user Not Found"
```



根据evaluationID获取evaluation

```json
URL : /user/evaluation/{evaluationID}

request body = none

response:
OK:
status_code = 200
body=
{
    "timeStamp" : "2020-12-01 18:23:30",
    "evaluaterID" : 123,
    "evaluateeID" : 456,
    "missionID" : 789,  //因为评分是和mission绑定的，所以这个是对应的mission的id
    "evaluationScore" : 9.87
}

NotFound:
status_code = 404
body=
"evaluation Not Found"
```



获取任务信息

```json
URL : /mission/{ID}/{mission_ID}

request body = none

response:
OK:
status_code = 200
body=
{
	"title" : "xxxxx",
	"content" : "xxxxx",
    "publisherID" : 123,
    "memberIDs" : [456, 789],
    "applicantIDs" : [1234, 5678],
    "state" : "in application",
    //or "between application and execution"
    //or "in execution"
    //or "finished"
    "publishTime" : "2020-12-01 18:23:30",   //string,按格式来
    "applicationEndTime" : "2020-12-01 18:23:30",
    "executionStartTime" : "2020-12-01 18:23:30",
    "executionEndTime" : "2020-12-01 18:23:30",
    "channels" : ["abc", "def"]
}

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"
```



获取任务列表

```json
URL : /missions/{ID}

request body:
{
    "channels" : ["123", "456"],
	"keywords" : ["abc", "def"],
	//如果为空则tags = []或keywords = []
	//两个都为空表示全体任务
    //一个为空则只看另一个
    //channels是判等于，keywords是判contain
    //多个channel或keyword是并起来，而不是交
    "startNumber" : 1,
    "endNumber" : 30
    //取得的missions按ID从大到小排序，取第startNumber个到第endNumber个
    //如果startNumber>总数，返回空表
    //endNumber对总数取min
}

response:
OK:
status_code = 200
body=
[123, 456]

user Not Found:
status_code = 404
body=
"user Not Found"

Bad Request:
status_code = 400
body=
"Bad Request"
```



获取message列表（这里是获取被通知的message的）

```json
URL : /messages/{userID}

request body : none

response:
OK:
status_code = 200
body=
[123, 456]
//按message的发布时间戳从大到小排序，返回前50个（不足50个就全返回）

user Not Found:
status_code = 404
body=
"user Not Found"

Bad Request:
status_code = 400
body=
"Bad Request"
```



获取message

```json
URL : /message/{messageID}

request body : none

response:
OK:
status_code = 200
body=
{
    "timeStamp" : "2020-12-01 18:23:30",
    "publisherID" : 123,
    "type" : "Bug", //or "Report" or "Notice"
    "messageContent" : "xxx",
    "reportee" : 123//如果是"Report"才有这项
}

NotFound:
status_code = 404
body=
"message Not Found"
```



------

PUT类

如果request body里面缺参数或参数格式错误后端都会返回status_code = 400和body="Bad Request"，我就不写了



修改个人信息

```json
URL : user/info/{ID}

request body:
{
    "username" : "abc",
    "tele" : "QQ1xxx"
}

response:
OK:
status_code = 200
body=
"OK"

Not Found:
status_code = 404
body=
"user Not Found"
```



修改密码

```json
URL : /user/code/{ID}

request body:
{
    "newPasswordAfterRSA" : "xxxxxx"
}

response:
OK:
status_code = 200
body=
"OK"

Not Found:
status_code = 404
body=
"user Not Found"
```



修改任务信息请求

```json
URL : /mission/{ID}/{missionID}

request body:
{
	"title" : "xxxxx",
	"content" : "xxxxx",
    "applicationEndTime" : "2020-12-01 18:23:30",
    "executionStartTime" : "2020-12-01 18:23:30",
    "executionEndTime" : "2020-12-01 18:23:30",
    "channels" : ["abc", "def"]
}
//这是覆盖操作，请传完整

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

invalid time:
status_code = 400
body=
"invalid time"
//要求publishTime不能更改且要求
//publishTime<applicationEndTime<=executionStartTime<executionEndTime
//如果当前时间>applicationEndTime，则applicationEndTime不能修改（即必须和之前相同）
//executionStartTime和executionEndTime同理
//如果当前时间>applicationEndTime，则title和content和tags均不能修改
```



------

POST类



创建任务请求

```json
URL : /mission/create/{ID}

request body:
{
    "title" : "xxxxx",
	"content" : "xxxxx",
    "applicationEndTime" : "2020-12-01 18:23:30",
    "executionStartTime" : "2020-12-01 18:23:30",
    "executionEndTime" : "2020-12-01 18:23:30",
    "channels" : ["abc", "def"]
}

response:
OK:
status_code = 200
body=
{
    "missionID" : 123
}

user Not Found:
status_code = 404
body=
"user Not Found"

invalid time:
status_code = 400
body=
"invalid time"
//要求publishTime<applicationEndTime<=executionStartTime<executionEndTime
```



加入任务请求

```json
URL : /mission/join/{ID}/{missionID}

request body : none

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

Forbidden:
status_code = 403
body=
"Forbidden"
//比如过了申请期，已在任务内，已申请之类的
```



接受申请者请求

```json
URL : /mission/accpet/{ID}/{missionID}/{applicantID}

request body = none

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

applicant Not Found:
status_code = 404
body=
"applicant Not Found"

Forbidden:
status_code = 403
body=
"Forbidden"
//比如ID不是任务发布者，applicantID不在mission的applicantIDs内，过了申请期
```



拒接任务请求

```json
URL : /mission/reject/{ID}/{missionID}/{applicantID}

request body = none

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

applicant Not Found:
status_code = 404
body=
"applicant Not Found"

Forbidden:
status_code = 403
body=
"Forbidden"
//同上
```



踢出成员请求

```json
URL : /mission/fire/{ID}/{missionID}/{applicantID}

request body = none

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

applicant Not Found:
status_code = 404
body=
"applicant Not Found"

Forbidden:
status_code = 403
body=
"Forbidden"
//自行枚举
```



开始任务

若任务在申请期内，这个请求会把applicationEndTime和executionStartTime设为当前时间

若任务在申请期和执行期之间，这个请求会把executionStartTime设为当前时间

否则无效

```json
URL : /mission/start/{ID}/{missionID}

request body = none

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

Forbidden:
status_code = 403
body=
"Forbidden"
//比如ID不是mission发布者，上面的否则无效
```



结束任务

若任务在执行期内，这个请求会把executionEndTime设为结束时间

否则无效

```json
URL : /mission/finish/{ID}/{missionID}

request body = none

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

Forbidden:
status_code = 403
body=
"Forbidden"
//比如ID不是mission发布者，上面的否则无效
```



删除任务请求

只有在申请期内且任务发布者才能删除任务

```json
URL : /mission/delete/{ID}/{missionID}

request body = none

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

Forbidden:
status_code = 403
body=
"Forbidden"
//无效的情况
```



退出任务请求

只有在申请期内且非任务发布者才能退出任务

```json
URL : /mission/quit/{ID}/{missionID}

request body = none

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

Forbidden:
status_code = 403
body=
"Forbidden"
//无效的情况
```



评分

```json
URL : /user/evaluate/{evaluaterID}/{missionID}/{evaluateeID}

request body:
{
    "score" : 9
}

response:
OK:
status_code = 200
body=
"OK"

evaluater Not Found:
status_code = 404
body=
"evaluater Not Found"

mission Not Found:
status_code = 404
body=
"mission Not Found"

evaluatee Not Found:
status_code = 404
body=
"evaluatee Not Found"

Forbidden:
status_code = 403
body=
"Forbidden"
//已经评过了，跟任务不关联啥的
```



报告bug

这个操作会向id=1的user发一个message

```json
URL : /message/bug/{ID}

request body:
{
	"messageContent" : "xxx"
}

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"
```



举报

这个操作会向id=1的user发一个message

```json
URL : /message/report/{ID}/{reporteeID}

request body:
{
    "messageContent" : "xxx"
}

response:
OK:
status_code = 200
body=
"OK"

user Not Found:
status_code = 404
body=
"user Not Found"

reportee Not Found:
status_code = 404
body=
"reportee Not Found"
```



发送验证码

```json
URL : /user/captcha

request body:
{
    "mailbox" : "xxx@pku.edu.cn"
}

response:
OK:
status_code = 200
body=
"OK"

//还有BadRequest
```



注册

```json
URL /user/register

request body:
{
	"mailbox" : "xxx@pku.edu.cn",
    "username" : "abc",
	"passwordAfterRSA" : "xxxxxx",
    //后端把这个东西解密成原文，再加密成MD5存到数据库中
    "captchaCode" : "xxxxxx"
}
//注册后初始产生的tele为空

response:
OK:
status_code = 200
body=
{
    "UID" : 123
}

status_code = 400
body=
"bad username"
//这个表示username重名了

//还有其它BadRequest
```



登录

```json
URL : /user/login

request body:
{
    "mailbox" : "xxx@pku.edu.cn",
    "passwordAfterRSA" : "xxxxxx"
}

response:
OK:
status_code = 200
body=
{
    "UID" : 123
}

Forbidden:
status_code = 403
body=
"Forbidden"
```

