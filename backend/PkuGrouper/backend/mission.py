from rest_framework.views import APIView
from rest_framework.response import Response

from .models import *
from django.utils import timezone

class DealMission(APIView):
    @staticmethod
    def get(request, user_ID, mission_ID):#获取任务信息
        return Response('This is a GET of mission/{user_ID}/{missionID}')

    @staticmethod
    def put(request, user_ID, mission_ID):#修改任务请求
        #request body:mission
        return Response(['This is a POST of mission/{user_ID}/{missionID}', request.data])

class DealMissions(APIView):#获取任务列表{tag+关键词}
    @staticmethod
    def get(request, user_ID):#request body:tag+关键词 List
        return Response(['This is a GET of missions/{user_ID}', request.data])

class DealCreate(APIView):#创建任务请求
    @staticmethod
    def post(request, user_ID):#request body:mission
        return Response(['This is a POST of mission/create/{user_ID}', request.data])

class DealDelete(APIView):#删除任务请求
    @staticmethod
    def post(request, user_ID, mission_ID):
        return Response('This is a POST of mission/delete/{user_ID}/{mission_ID}')

class DealJoin(APIView):#加入任务请求
    @staticmethod
    def post(request, user_ID, mission_ID):
        return Response('This is a POST of mission/join/{user_ID}/{mission_ID}')

class DealAccept(APIView):#接受申请者请求
    @staticmethod
    def post(request, user_ID, mission_ID, applicant_ID):
        return Response('This is a POST of mission/accept/{user_ID}/{mission_ID}/{applicant_ID}')

class DealReject(APIView):#拒接申请者请求
    @staticmethod
    def post(request, user_ID, mission_ID, applicant_ID):
        return Response('This is a POST of mission/reject/{user_ID}/{mission_ID}/{applicant_ID}')

class DealFire(APIView):#踢出成员请求
    @staticmethod
    def post(request, user_ID, mission_ID, applicant_ID):
        return Response('This is a POST of mission/fire/{user_ID}/{mission_ID}/{applicant_ID}')

class DealStart(APIView):#开始任务
    @staticmethod
    def post(request, user_ID, mission_ID):
        return Response('This is a POST of mission/start/{user_ID}/{mission_ID}')

class DealFinish(APIView):#结束任务
    @staticmethod
    def post(request, user_ID, mission_ID):
        return Response('This is a POST of mission/finish/{user_ID}/{mission_ID}')

class DealQuit(APIView):#退出任务请求
    @staticmethod
    def post(request, user_ID, mission_ID):
        return Response('This is a POST of mission/quit/{user_ID}/{mission_ID}')
