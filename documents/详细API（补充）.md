find开头的那几个API还是要和之前一样要加senderID和passwordAfterRSA

/user/findpassword...的那两个不要

下面的全是POST

------

根据missionIDs获取mission详细信息列表

```json
URL : /findmissions/{userID}

request body =
{
    "missionIDs" : [123, 456]
}

response:
OK:
status_code = 200
body=
[//按ID的顺序给出
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
    },
    {
        ...
    },
    {
        ...
    }
]

user Not Found:
status_code = 404
body=
"user Not Found"

mission Not Found://这个只要有一个mission找不到就会被返回
status_code = 404
body=
"mission Not Found"
```



根据messageIDs获取message详细信息列表

```json
URL : /findmessages/{userID}

request body : 
{
    "messageIDs" : [123, 456]
}

response:
OK:
status_code = 200
body=
[
    {
        "timeStamp" : "2020-12-01 18:23:30",
    	"publisherID" : 123,
    	"type" : "Bug", //or "Report" or "Notice"
    	"messageContent" : "xxx",
    	"reportee" : 123//如果是"Report"才有这项
    },
    {...},{...}
    //可以看出，每个对象“是否拥有reportee”是不一致的
]

user Not Found:
status_code = 404
body=
"user Not Found"

message Not Found:
status_code = 404
body=
"message Not Found"
```



根据evaluationIDs获取evaluation详细信息列表

```json
URL : /findevaluations/{userID}

request body =
{
    "evaluationIDs" : [123, 456]
}

response:
OK:
status_code = 200
body=
[
    {
        "timeStamp" : "2020-12-01 18:23:30",
    	"evaluaterID" : 123,
    	"evaluateeID" : 456,
    	"missionID" : 789,  //因为评分是和mission绑定的，所以这个是对应的mission的id
    	"evaluationScore" : 9.87
    },
    {...},{...}
]

user Not Found:
status_code = 404
body=
"user Not Found"

evaluation Not Found:
status_code = 404
body=
"evaluation Not Found"
```



根据getteeIDs获取gettee详细信息列表

```json
URL : /findusers/{getterID}/{missionID}

request body=
{
	"getteeIDs" : [123, 456]
}

response:
OK:
status_code = 200
body =
[
    {
    	"missionStatus" : "tele can be seen",    //or "tele can not be seen"
    	"tele" : "QQ1xxx",      //string,如果"tele can not be seen"则没有这项
    	"averageScore" : 9.87
	},
    {...},{...}
]
//当getter和所有gettee都在missionID的任务里时"tele can be seen"
//当getter不在missionID的任务里，gettee都在missionID的任务里时"tele can not be seen"
//可以看出所有对象的"missionStatus"是一致的

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
//存在一个gettee找不到

Bad Request:
status_code = 400
body=
"Bad Request"
//自己看自己

Forbidden:
status_code = 403
body=
"Forbidden"
//存在一个gettee不在mission_ID的任务里
```



找回密码之获取验证码

```json
URL : /user/fixpasswordcaptcha

request body:
{
    "mailbox" : "xxx@pku.edu.cn"
}

response:
OK:
status_code = 200
body=
"OK"

//还有BadRequest，mailbox所代表的用户不存在
```



找回密码

```json
URL : /user/fixpassword

request body:
{
	"mailbox" : "xxx@pku.edu.cn",
	"passwordAfterRSA" : "xxxxxx",
    "captchaCode" : "xxxxxx"
}

response:
OK:
status_code = 200
body=
{
    "UID" : 123
}

//还有BadRequest
```

