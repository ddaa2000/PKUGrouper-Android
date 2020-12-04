from rest_framework.views import APIView
from rest_framework.response import Response

from .models import *
from django.utils import timezone
from .someFuncs import *

class DealMessages(APIView):#获取message
    #（这个应该是给出user_ID，返回一个此user未查看的message的数组）
    @staticmethod
    def get(request, user_ID):
        return Response('This is a GET of messages/{user_ID}')

class DealMessage(APIView):#根据message_ID获取信息
    @staticmethod
    def get(request, message_ID):
        return Response('This is a GET of message/{message_ID}')

class DealBug(APIView):#报告bug
    @staticmethod
    def post(request, user_ID):#request body:bug
        return Response(['This is a POST of message/bug/{user_ID}', request.data])

class DealReport(APIView):#举报
    @staticmethod
    def post(request, user_ID, reportee_ID):#request body:report
        return Response(['This is a POST of message/report/{user_ID}/{reportee_ID}', request.data])
