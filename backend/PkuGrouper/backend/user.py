from rest_framework.views import APIView
from rest_framework.response import Response
from .models import *
from django.utils import timezone
from .someFuncs import *

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

class DealCaptcha(APIView):#获取验证码
    @staticmethod
    def post(request):#request body:user(regiter information only)
        return Response(['This is a POST of user/captcha', request.data])

class DealRegister(APIView):#注册
    @staticmethod
    def post(request):#request body:user(regiter information only)
        return Response(['This is a POST of user/register', request.data])

class DealLogin(APIView):#登录
    @staticmethod
    def post(request):#request body:user(login information only)
        return Response(['This is a POST of user/login', request.data])
