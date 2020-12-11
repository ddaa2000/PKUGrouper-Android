from rest_framework.views import APIView
from rest_framework.response import Response
from .models import *
from django.utils import timezone
from .someFuncs import *
import random
import time
class DealSelf(APIView):#获取个人信息
    @staticmethod
    def get(request, user_ID):
        response = Response('fuck you!')
        response.status_code=404
        return response

class DealMember(APIView):#获取他人信息
    @staticmethod
    def get(request, getter_ID, mission_ID, gettee_ID):#geter是获取人，getee是被获取人
        return Response('This is a GET of user/member/{getter_ID}/{mission_ID}/{gettee_ID}')


class DealEvaluations(APIView):#获取用户做过的所有评价
    @staticmethod
    def get(request, user_ID):
        return Response('This is a GET of user/evaluations/{user_ID}')

class DealEvaluation(APIView):#根据evaluation_ID获取评价
    @staticmethod
    def get(request, evaluation_ID):
        return Response('This is a GET of user/evaluation/{evaluation_ID}')

class DealInfo(APIView):#修改个人信息
    @staticmethod
    def put(request, user_ID):#request body:user
        return Response(['This is a PUT of user/info/{user_ID}', request.data])
    
class DealCode(APIView):#修改密码
    @staticmethod
    def put(request, user_ID):#request body:password
        return Response(['This is a PUT of user/code/{user_ID}', request.data])

class DealTags(APIView):#修改tagList
    @staticmethod
    def put(request, user_ID):#request body:tagList
        return Response(['This is a PUT of user/tags/{user_ID}', request.data])

class DealEvaluate(APIView):#评分
    @staticmethod
    def post(request, evaluater_ID, mission_ID, evaluatee_ID):#evaluater是评分人，evaluatee是被评分人
        return Response("This is a POST of user/evaluate/{evaluater_ID}/{mission_ID}/{evaluatee_ID}")

captcha_mp = {}
class DealCaptcha(APIView):#获取验证码
    @staticmethod
    def post(request):#request body:user(regiter information only)
        #return Response(['This is a POST of user/captcha', request.data])
        mail = request.data.get("mailbox")
        captcha_mp[mail] = (''.join([str(random.randint(0,9)) for i in range(6)]),
                            time.time())
        if sendMail(mail, captcha_mp[mail][0])==0:
            del captcha_mp[mail]
            return Response("BadRequest",status=400)
        else:
            return Response("OK")

class DealRegister(APIView):#注册
    @staticmethod
    def post(request):#request body:user(regiter information only)
        #return Response(['This is a POST of user/register', request.data])
        mail = request.data.get("mailbox")
        passwordAfterRSA = request.data.get("passwordAfterRSA")
        captchaCode = request.data.get("captchaCode")
        username = request.data.get("username")
        if len(User.objects.filter(username=username)):
            return Response("bad username",status=400)
        if captcha_mp.get(mail) == None:
            return Response("Do not have captcha!",status=400)
        if time.time() - captcha_mp[mail][1] > 30000:
            del captcha_mp[mail]
            return Response("captcha time limit exceed!",status=400)
        if captcha_mp[mail][0] != captchaCode:
            del captcha_mp[mail]
            return Response("captchaCode wrong!",status=400)
        md5code = passwordToMD5(RSAdecypt(passwordAfterRSA))
        person = User(username=username, mailbox=mail, passwordAfterMD5=md5code,
                    )
        person.save()
        del captcha_mp[mail]
        return Response({"UID":person.id})

class DealLogin(APIView):#登录
    @staticmethod
    def post(request):#request body:user(login information only)
        #return Response(['This is a POST of user/login', request.data])
        mail = request.data.get("mailbox")
        passwordAfterRSA = request.data.get("passwordAfterRSA")
        if mail == None or passwordAfterRSA == None:
            Response("info not complete",status=403)

        try:
            who = User.objects.get(mailbox=mail)
        except User.DoesNotExist:
            return Response("user Not Found",status=404)

        code = passwordToMD5(RSAdecypt(passwordAfterRSA))
        if who.passwordAfterMD5 != code:
            return Response("Forbidden",status=404)
        return Response({"UID":who.id})

